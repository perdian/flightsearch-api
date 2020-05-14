package de.perdian.flightsearch.impl.ebookers;

import java.io.IOException;
import java.io.StringReader;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.perdian.flightsearch.api.OfferQueryExecutor;
import de.perdian.flightsearch.api.model.Aircraft;
import de.perdian.flightsearch.api.model.AircraftTypeRepository;
import de.perdian.flightsearch.api.model.AirlineRepository;
import de.perdian.flightsearch.api.model.Airport;
import de.perdian.flightsearch.api.model.AirportContact;
import de.perdian.flightsearch.api.model.AirportRepository;
import de.perdian.flightsearch.api.model.Flight;
import de.perdian.flightsearch.api.model.FlightNumber;
import de.perdian.flightsearch.api.model.FlightQuery;
import de.perdian.flightsearch.api.model.Leg;
import de.perdian.flightsearch.api.model.Offer;
import de.perdian.flightsearch.api.model.OfferQuery;
import de.perdian.flightsearch.api.model.Price;
import de.perdian.flightsearch.api.model.Quote;
import de.perdian.flightsearch.api.model.Route;
import de.perdian.flightsearch.api.model.Segment;
import de.perdian.flightsearch.api.model.Trip;
import de.perdian.flightsearch.api.model.TripQuery;
import de.perdian.flightsearch.api.model.TripType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EbookersOfferQueryExecutor implements OfferQueryExecutor {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final Logger log = LoggerFactory.getLogger(EbookersOfferQueryExecutor.class);
    private Supplier<OkHttpClient> httpClientSupplier = () -> new OkHttpClient();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public List<Offer> loadOffers(OfferQuery offerQuery) throws IOException {

        List<TripQuery> tripQueries = offerQuery.getTrip().flattenMultipleAirportsForOriginAndDestination();
        log.debug("Loading offers for {} queries using {}", tripQueries.size(), this);

        OkHttpClient httpClient = this.getHttpClientSupplier().get();
        List<Offer> resultOffers = new ArrayList<>();
        for (TripQuery tripQuery : tripQueries) {
            resultOffers.addAll(this.loadOffersForTripQuery(tripQuery, offerQuery, httpClient));
        }
        return Offer.merge(resultOffers);

    }

    private List<Offer> loadOffersForTripQuery(TripQuery tripQuery, OfferQuery offerQuery, OkHttpClient httpClient) throws IOException {
        if (TripType.MULTILOCATIONTRIP.equals(tripQuery.getTripType()) || TripType.ROUNDTRIP.equals(tripQuery.getTripType())) {
            return this.loadOffersForTripQueryMultiLocation(tripQuery, offerQuery, httpClient);
        } else {
            log.info("Invalid trip type for ebookers: {}", tripQuery.getTripType());
            return Collections.emptyList();
        }
    }

    private List<Offer> loadOffersForTripQueryMultiLocation(TripQuery tripQuery, OfferQuery offerQuery, OkHttpClient httpClient) throws IOException {

        StringBuilder initialSearchUrl = new StringBuilder();
        initialSearchUrl.append("https://www.ebookers.de/Flights-Search");
        initialSearchUrl.append("?trip=multi");
        for (int flightIndex=0; flightIndex < tripQuery.getFlights().size(); flightIndex++) {
            FlightQuery flightQuery = tripQuery.getFlights().get(flightIndex);
            initialSearchUrl.append("&leg").append(flightIndex+1).append("=");
            initialSearchUrl.append("from:").append(flightQuery.getOriginAirportContact().getAirportCodes().get(0));
            initialSearchUrl.append(",to:").append(flightQuery.getDestinationAirportContact().getAirportCodes().get(0));
            initialSearchUrl.append(",departure:").append(DATE_FORMATTER.format(flightQuery.getOriginAirportContact().getDateTime().getDate()));
            initialSearchUrl.append("TANYT");
        }
        initialSearchUrl.append("&passengers=adults:").append(tripQuery.getPassengerCount()).append(",children:0,seniors:0,infantinlap:0");
        initialSearchUrl.append("&options=cabinclass:economy");
        initialSearchUrl.append("&mode=search");
        initialSearchUrl.append("&origref=www.ebookers.de");

        log.debug("Sending search request to ebookers: {}", tripQuery);
        Request initialSearchRequest = new Request.Builder().get().url(initialSearchUrl.toString()).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/73.0").header("Accept", "*/*") .build();
        try (Response initialSearchResponse = httpClient.newCall(initialSearchRequest).execute()) {
            String initialSearchResponseContent = initialSearchResponse.body().string();
            String continuationIdNeedle = "<div id=\"originalContinuationId\">";
            int continuationIdStart = initialSearchResponseContent.indexOf(continuationIdNeedle);
            int continuationIdEnd = continuationIdStart < 0 ? -1 : initialSearchResponseContent.indexOf("</div>", continuationIdStart);
            String continuationId = continuationIdStart < 0 || continuationIdEnd < continuationIdStart ? null : initialSearchResponseContent.substring(continuationIdStart + continuationIdNeedle.length(), continuationIdEnd);
            if (StringUtils.isEmpty(continuationId)) {
                log.warn("Invalid response returned from ebookers. No continuation id found in response");
                return Collections.emptyList();
            } else {
                return this.loadOffersForContinuationId(continuationId, httpClient);
            }
        }

    }

    private List<Offer> loadOffersForContinuationId(String continuationId, OkHttpClient httpClient) throws IOException {
        StringBuilder continuationRequestUrl = new StringBuilder();
        continuationRequestUrl.append("https://www.ebookers.de/Flight-Search-Paging");
        continuationRequestUrl.append("?c=").append(continuationId);
        continuationRequestUrl.append("&is=1&sp=asc&cz=200&cn=0&ul=0");
        Request continuationRequest = new Request.Builder().get().url(continuationRequestUrl.toString()).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/73.0").build();
        try (Response continuationResponse = httpClient.newCall(continuationRequest).execute()) {
            String continuationResponseContent = continuationResponse.body().string();
            JsonObject continuationResponseJson = Json.createReader(new StringReader(continuationResponseContent)).readObject();
            Map<String, Flight> flightsById = this.parseFlightsById(continuationResponseJson.getJsonObject("content").getJsonObject("legs"));
            JsonObject jsonOffers = continuationResponseJson.getJsonObject("content").getJsonObject("offers");
            List<Offer> offers = new ArrayList<>();
            for (String offerId : jsonOffers.keySet()) {
                JsonObject jsonOffer = jsonOffers.getJsonObject(offerId);
                Offer offer = new Offer();
                offer.setTrip(new Trip(jsonOffer.getJsonArray("legIds").stream().map(value -> flightsById.get(((JsonString)value).getString())).collect(Collectors.toList())));
                offer.setQuotes(Arrays.asList(this.parseQuoteFromPrice(jsonOffer.getJsonObject("price"))));
                offers.add(offer);
            }
            return offers;
        }
    }

    private Map<String, Flight> parseFlightsById(JsonObject jsonFlights) {
        Map<String, Flight> flightsById = new LinkedHashMap<>();
        for (String id : jsonFlights.keySet()) {
            flightsById.put(id, this.parseFlight(jsonFlights.getJsonObject(id)));
        }
        return flightsById;
    }

    private Flight parseFlight(JsonObject jsonFlight) {
        JsonArray jsonTimelineArray = jsonFlight.getJsonArray("timeline");
        List<Segment> segments = new ArrayList<>();
        for (int i=0; i < jsonTimelineArray.size(); i++) {
            JsonObject jsonTimelineItem = (JsonObject)jsonTimelineArray.get(i);
            if ("Segment".equalsIgnoreCase(jsonTimelineItem.getString("type"))) {
                segments.add(this.parseSegmentFromTimelineItem(jsonTimelineItem));
            }
        }
        return new Flight(segments);
    }

    private Segment parseSegmentFromTimelineItem(JsonObject jsonTimelineItem) {

      AirportContact originContact = this.parseAirportContactFromTimeline(jsonTimelineItem, "departureAirport", "departureTime");
      AirportContact destinationContact = this.parseAirportContactFromTimeline(jsonTimelineItem, "arrivalAirport", "arrivalTime");
      Leg leg = new Leg();
      leg.setAircraft(this.parseAircraftFromTimeline(jsonTimelineItem));
      leg.setScheduledRoute(new Route(originContact, destinationContact));

      Segment segment = new Segment();
      segment.setLegs(Arrays.asList(leg));
      segment.setMarketingFlightNumber(this.parseFlightNumber(jsonTimelineItem.getJsonObject("carrier")));
      return segment;

    }

    private Aircraft parseAircraftFromTimeline(JsonObject jsonTimelineItem) {
        String aircraftCode = jsonTimelineItem.getJsonObject("carrier").getString("planeCode");
        if (StringUtils.isEmpty(aircraftCode)) {
            return null;
        } else {
            Aircraft aircraft = new Aircraft();
            aircraft.setType(AircraftTypeRepository.getInstance().loadAircraftTypeByCode(aircraftCode));
            aircraft.setTypeDescription(jsonTimelineItem.getJsonObject("carrier").getString("plane"));
            return aircraft;
        }
    }

    private FlightNumber parseFlightNumber(JsonObject jsonCarrier) {
        FlightNumber flightNumber = new FlightNumber(jsonCarrier.getString("airlineCode"), Integer.parseInt(jsonCarrier.getString("flightNumber")), null);
        String operatedByAirlineCode = jsonCarrier.getString("operatedByAirlineCode");
        if (StringUtils.isNotEmpty(operatedByAirlineCode)) {
            flightNumber.setOperatingAirline(AirlineRepository.getInstance().loadAirlineByCode(operatedByAirlineCode));
        }
        return flightNumber;
    }

    private AirportContact parseAirportContactFromTimeline(JsonObject jsonTimeline, String airportNodeName, String dateNodeName) {
        Airport airport = AirportRepository.getInstance().loadAirportByCode(jsonTimeline.getJsonObject(airportNodeName).getString("code"));
        OffsetDateTime departureTime = OffsetDateTime.parse(jsonTimeline.getJsonObject(dateNodeName).getString("isoStr"));
        AirportContact airportContact = new AirportContact();
        airportContact.setAirport(airport);
        airportContact.setLocalDateTime(departureTime.atZoneSameInstant(airport.getTimezoneId()).toLocalDateTime());
        return airportContact;
    }

    private Quote parseQuoteFromPrice(JsonObject jsonPrice) {
        Price price = new Price();
        price.setCurrencyCode(jsonPrice.getString("currencyCode"));
        price.setValue(jsonPrice.getJsonNumber("totalPriceAsDecimal").bigDecimalValue());
        Quote quote = new Quote();
        quote.setProvider("ebookers.de");
        quote.setPrice(price);
        return quote;
    }

    public Supplier<OkHttpClient> getHttpClientSupplier() {
        return this.httpClientSupplier;
    }
    public void setHttpClientSupplier(Supplier<OkHttpClient> httpClientSupplier) {
        this.httpClientSupplier = httpClientSupplier;
    }

}
