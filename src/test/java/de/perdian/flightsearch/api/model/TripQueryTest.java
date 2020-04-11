package de.perdian.flightsearch.api.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TripQueryTest {

    @Test
    public void testConstructor() {
        List<FlightQuery> flightQueries = Arrays.asList(new FlightQuery());
        TripQuery tripQuery = new TripQuery(flightQueries);
        Assertions.assertSame(flightQueries, tripQuery.getFlights());
    }

    @Test
    public void testTest() {
        TripQuery tripQuery = new TripQuery();
        Assertions.assertTrue(tripQuery.test(new Trip()));
    }

    @Test
    public void testTestWithFlightQueries() {
        FlightQuery flightQuery1 = new FlightQuery();
        flightQuery1.setEnforceExactOriginAirportCodes(true);
        flightQuery1.setOriginAirportCodes(Arrays.asList("DUS"));
        FlightQuery flightQuery2 = new FlightQuery();
        flightQuery2.setEnforceExactOriginAirportCodes(true);
        flightQuery2.setOriginAirportCodes(Arrays.asList("FRA"));

        AirportContact departureContact1 = new AirportContact(new Airport("DUS"), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact arrivalContact1 = new AirportContact(new Airport("FRA"), LocalDateTime.of(2000, 1, 2, 15, 00));
        Leg leg1 = new Leg(new Route(departureContact1, arrivalContact1), null);
        Segment segment1 = new Segment(Arrays.asList(leg1));
        Flight flight1 = new Flight(Arrays.asList(segment1));

        AirportContact departureContact2a = new AirportContact(new Airport("FRA"), LocalDateTime.of(2000, 1, 2, 16, 00));
        AirportContact arrivalContact2a = new AirportContact(new Airport("JFK"), LocalDateTime.of(2000, 1, 2, 20, 00));
        Leg leg2a = new Leg(new Route(departureContact2a, arrivalContact2a), null);
        Segment segment2a = new Segment(Arrays.asList(leg2a));
        Flight flight2a = new Flight(Arrays.asList(segment2a));

        AirportContact departureContact2b = new AirportContact(new Airport("MUC"), LocalDateTime.of(2000, 1, 2, 16, 00));
        AirportContact arrivalContact2b = new AirportContact(new Airport("EWR"), LocalDateTime.of(2000, 1, 2, 20, 00));
        Leg leg2b = new Leg(new Route(departureContact2b, arrivalContact2b), null);
        Segment segment2b = new Segment(Arrays.asList(leg2b));
        Flight flight2b = new Flight(Arrays.asList(segment2b));

        TripQuery tripQuery = new TripQuery();
        tripQuery.setFlights(Arrays.asList(flightQuery1, flightQuery2));
        Assertions.assertTrue(tripQuery.test(new Trip(Arrays.asList(flight1, flight2a))));
        Assertions.assertFalse(tripQuery.test(new Trip(Arrays.asList(flight1, flight2b))));
    }

    @Test
    public void testFlattenMultipleAirportsForDepartureAndArrival() {
        FlightQuery flightQuery1 = new FlightQuery();
        flightQuery1.setOriginAirportCodes(Arrays.asList("CGN", "DUS"));
        flightQuery1.setDestinationAirportCodes(Arrays.asList("JFK", "EWR"));
        FlightQuery flightQuery2 = new FlightQuery();
        flightQuery2.setOriginAirportCodes(Arrays.asList("MCO"));
        flightQuery2.setDestinationAirportCodes(Arrays.asList("FRA", "MGL"));
        TripQuery tripQuery = new TripQuery();
        tripQuery.setFlights(Arrays.asList(flightQuery1, flightQuery2));

        List<TripQuery> flattenedTripQueries = tripQuery.flattenMultipleAirportsForDepartureAndArrival();
        Assertions.assertEquals(8, flattenedTripQueries.size());
        Assertions.assertEquals(Arrays.asList("CGN"), flattenedTripQueries.get(0).getFlights().get(0).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedTripQueries.get(0).getFlights().get(0).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(0).getFlights().get(1).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("FRA"), flattenedTripQueries.get(0).getFlights().get(1).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("CGN"), flattenedTripQueries.get(1).getFlights().get(0).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedTripQueries.get(1).getFlights().get(0).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(1).getFlights().get(1).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("MGL"), flattenedTripQueries.get(1).getFlights().get(1).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("CGN"), flattenedTripQueries.get(2).getFlights().get(0).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("EWR"), flattenedTripQueries.get(2).getFlights().get(0).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(2).getFlights().get(1).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("FRA"), flattenedTripQueries.get(2).getFlights().get(1).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("CGN"), flattenedTripQueries.get(3).getFlights().get(0).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("EWR"), flattenedTripQueries.get(3).getFlights().get(0).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(3).getFlights().get(1).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("MGL"), flattenedTripQueries.get(3).getFlights().get(1).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("DUS"), flattenedTripQueries.get(4).getFlights().get(0).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedTripQueries.get(4).getFlights().get(0).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(4).getFlights().get(1).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("FRA"), flattenedTripQueries.get(4).getFlights().get(1).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("DUS"), flattenedTripQueries.get(5).getFlights().get(0).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedTripQueries.get(5).getFlights().get(0).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(5).getFlights().get(1).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("MGL"), flattenedTripQueries.get(5).getFlights().get(1).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("DUS"), flattenedTripQueries.get(6).getFlights().get(0).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("EWR"), flattenedTripQueries.get(6).getFlights().get(0).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(6).getFlights().get(1).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("FRA"), flattenedTripQueries.get(6).getFlights().get(1).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("DUS"), flattenedTripQueries.get(7).getFlights().get(0).getOriginAirportCodes());
        Assertions.assertEquals(Arrays.asList("EWR"), flattenedTripQueries.get(7).getFlights().get(0).getDestinationAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(7).getFlights().get(1).getOriginAirportCodes());
    }

    @Test
    public void testGetTripTypeExpectNull() {
        TripQuery tripQuery = new TripQuery();
        Assertions.assertNull(tripQuery.getTripType());
    }

    @Test
    public void testGetTripTypeExpectOneway() {
        TripQuery tripQuery = new TripQuery();
        tripQuery.setFlights(Arrays.asList(new FlightQuery()));
        Assertions.assertEquals(TripType.ONEWAY, tripQuery.getTripType());
    }

    @Test
    public void testGetTripTypeExpectRoundtrip() {
        FlightQuery flightQuery1 = new FlightQuery();
        flightQuery1.setOriginAirportCodes(Arrays.asList("CGN"));
        flightQuery1.setDestinationAirportCodes(Arrays.asList("JFK"));
        FlightQuery flightQuery2 = new FlightQuery();
        flightQuery2.setOriginAirportCodes(Arrays.asList("JFK"));
        flightQuery2.setDestinationAirportCodes(Arrays.asList("CGN"));
        TripQuery tripQuery = new TripQuery();
        tripQuery.setFlights(Arrays.asList(flightQuery1, flightQuery2));
        Assertions.assertEquals(TripType.ROUNDTRIP, tripQuery.getTripType());
    }

    @Test
    public void testGetTripTypeExpectMultilocationBecauseDifferentReturnStart() {
        FlightQuery flightQuery1 = new FlightQuery();
        flightQuery1.setOriginAirportCodes(Arrays.asList("CGN"));
        flightQuery1.setDestinationAirportCodes(Arrays.asList("JFK"));
        FlightQuery flightQuery2 = new FlightQuery();
        flightQuery2.setOriginAirportCodes(Arrays.asList("EWR"));
        flightQuery2.setDestinationAirportCodes(Arrays.asList("CGN"));
        TripQuery tripQuery = new TripQuery();
        tripQuery.setFlights(Arrays.asList(flightQuery1, flightQuery2));
        Assertions.assertEquals(TripType.MULTILOCATIONTRIP, tripQuery.getTripType());
    }

    @Test
    public void testGetTripTypeExpectMultilocationBecauseDifferentReturnEnd() {
        FlightQuery flightQuery1 = new FlightQuery();
        flightQuery1.setOriginAirportCodes(Arrays.asList("CGN"));
        flightQuery1.setDestinationAirportCodes(Arrays.asList("JFK"));
        FlightQuery flightQuery2 = new FlightQuery();
        flightQuery2.setOriginAirportCodes(Arrays.asList("JFK"));
        flightQuery2.setDestinationAirportCodes(Arrays.asList("DUS"));
        TripQuery tripQuery = new TripQuery();
        tripQuery.setFlights(Arrays.asList(flightQuery1, flightQuery2));
        Assertions.assertEquals(TripType.MULTILOCATIONTRIP, tripQuery.getTripType());
    }

    @Test
    public void testGetTripTypeExpectMultilocationBecauseSizeLargetThanTwo() {
        TripQuery tripQuery = new TripQuery();
        tripQuery.setFlights(Arrays.asList(new FlightQuery(), new FlightQuery(), new FlightQuery()));
        Assertions.assertEquals(TripType.MULTILOCATIONTRIP, tripQuery.getTripType());
    }

}
