package de.perdian.flightsearch.api.model;

import java.time.LocalDate;
import java.time.LocalTime;
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
        flightQuery1.setOriginAirportContact(new AirportContactQuery(Arrays.asList("DUS"), true));
        FlightQuery flightQuery2 = new FlightQuery();
        flightQuery2.setOriginAirportContact(new AirportContactQuery(Arrays.asList("FRA"), true));

        AirportContact originContact1 = new AirportContact(new Airport("DUS"), LocalDate.of(2000, 1, 2), LocalTime.of(14, 00));
        AirportContact destinationContact1 = new AirportContact(new Airport("FRA"), LocalDate.of(2000, 1, 2), LocalTime.of(15, 00));
        Leg leg1 = new Leg(new Route(originContact1, destinationContact1), null);
        Segment segment1 = new Segment(Arrays.asList(leg1));
        Flight flight1 = new Flight(Arrays.asList(segment1));

        AirportContact originContact2a = new AirportContact(new Airport("FRA"), LocalDate.of(2000, 1, 2), LocalTime.of(16, 00));
        AirportContact destinationContact2a = new AirportContact(new Airport("JFK"), LocalDate.of(2000, 1, 2), LocalTime.of(20, 00));
        Leg leg2a = new Leg(new Route(originContact2a, destinationContact2a), null);
        Segment segment2a = new Segment(Arrays.asList(leg2a));
        Flight flight2a = new Flight(Arrays.asList(segment2a));

        AirportContact originContact2b = new AirportContact(new Airport("MUC"), LocalDate.of(2000, 1, 2), LocalTime.of(16, 00));
        AirportContact destinationContact2b = new AirportContact(new Airport("EWR"), LocalDate.of(2000, 1, 2), LocalTime.of(20, 00));
        Leg leg2b = new Leg(new Route(originContact2b, destinationContact2b), null);
        Segment segment2b = new Segment(Arrays.asList(leg2b));
        Flight flight2b = new Flight(Arrays.asList(segment2b));

        TripQuery tripQuery = new TripQuery();
        tripQuery.setFlights(Arrays.asList(flightQuery1, flightQuery2));
        Assertions.assertTrue(tripQuery.test(new Trip(Arrays.asList(flight1, flight2a))));
        Assertions.assertFalse(tripQuery.test(new Trip(Arrays.asList(flight1, flight2b))));
    }

    @Test
    public void testFlattenMultipleAirportsForOriginAndDestination() {
        FlightQuery flightQuery1 = new FlightQuery();
        flightQuery1.setOriginAirportContact(new AirportContactQuery(Arrays.asList("CGN", "DUS"), false));
        flightQuery1.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("JFK", "EWR"), false));
        FlightQuery flightQuery2 = new FlightQuery();
        flightQuery2.setOriginAirportContact(new AirportContactQuery(Arrays.asList("MCO"), false));
        flightQuery2.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("FRA", "MGL"), false));
        TripQuery tripQuery = new TripQuery();
        tripQuery.setFlights(Arrays.asList(flightQuery1, flightQuery2));

        List<TripQuery> flattenedTripQueries = tripQuery.flattenMultipleAirportsForOriginAndDestination();
        Assertions.assertEquals(8, flattenedTripQueries.size());
        Assertions.assertEquals(Arrays.asList("CGN"), flattenedTripQueries.get(0).getFlights().get(0).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedTripQueries.get(0).getFlights().get(0).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(0).getFlights().get(1).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("FRA"), flattenedTripQueries.get(0).getFlights().get(1).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("CGN"), flattenedTripQueries.get(1).getFlights().get(0).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedTripQueries.get(1).getFlights().get(0).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(1).getFlights().get(1).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MGL"), flattenedTripQueries.get(1).getFlights().get(1).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("CGN"), flattenedTripQueries.get(2).getFlights().get(0).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("EWR"), flattenedTripQueries.get(2).getFlights().get(0).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(2).getFlights().get(1).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("FRA"), flattenedTripQueries.get(2).getFlights().get(1).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("CGN"), flattenedTripQueries.get(3).getFlights().get(0).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("EWR"), flattenedTripQueries.get(3).getFlights().get(0).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(3).getFlights().get(1).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MGL"), flattenedTripQueries.get(3).getFlights().get(1).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("DUS"), flattenedTripQueries.get(4).getFlights().get(0).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedTripQueries.get(4).getFlights().get(0).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(4).getFlights().get(1).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("FRA"), flattenedTripQueries.get(4).getFlights().get(1).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("DUS"), flattenedTripQueries.get(5).getFlights().get(0).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedTripQueries.get(5).getFlights().get(0).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(5).getFlights().get(1).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MGL"), flattenedTripQueries.get(5).getFlights().get(1).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("DUS"), flattenedTripQueries.get(6).getFlights().get(0).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("EWR"), flattenedTripQueries.get(6).getFlights().get(0).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(6).getFlights().get(1).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("FRA"), flattenedTripQueries.get(6).getFlights().get(1).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("DUS"), flattenedTripQueries.get(7).getFlights().get(0).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("EWR"), flattenedTripQueries.get(7).getFlights().get(0).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MCO"), flattenedTripQueries.get(7).getFlights().get(1).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("MGL"), flattenedTripQueries.get(7).getFlights().get(1).getDestinationAirportContact().getAirportCodes());
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
        flightQuery1.setOriginAirportContact(new AirportContactQuery(Arrays.asList("CGN"), false));
        flightQuery1.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("JFK"), false));
        FlightQuery flightQuery2 = new FlightQuery();
        flightQuery2.setOriginAirportContact(new AirportContactQuery(Arrays.asList("JFK"), false));
        flightQuery2.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("CGN"), false));
        TripQuery tripQuery = new TripQuery();
        tripQuery.setFlights(Arrays.asList(flightQuery1, flightQuery2));
        Assertions.assertEquals(TripType.ROUNDTRIP, tripQuery.getTripType());
    }

    @Test
    public void testGetTripTypeExpectMultilocationBecauseDifferentReturnStart() {
        FlightQuery flightQuery1 = new FlightQuery();
        flightQuery1.setOriginAirportContact(new AirportContactQuery(Arrays.asList("CGN"), false));
        flightQuery1.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("JFK"), false));
        FlightQuery flightQuery2 = new FlightQuery();
        flightQuery2.setOriginAirportContact(new AirportContactQuery(Arrays.asList("EWR"), false));
        flightQuery2.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("CGN"), false));
        TripQuery tripQuery = new TripQuery();
        tripQuery.setFlights(Arrays.asList(flightQuery1, flightQuery2));
        Assertions.assertEquals(TripType.MULTILOCATIONTRIP, tripQuery.getTripType());
    }

    @Test
    public void testGetTripTypeExpectMultilocationBecauseDifferentReturnEnd() {
        FlightQuery flightQuery1 = new FlightQuery();
        flightQuery1.setOriginAirportContact(new AirportContactQuery(Arrays.asList("CGN"), false));
        flightQuery1.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("JFK"), false));
        FlightQuery flightQuery2 = new FlightQuery();
        flightQuery2.setOriginAirportContact(new AirportContactQuery(Arrays.asList("JFK"), false));
        flightQuery2.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("DUS"), false));
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
