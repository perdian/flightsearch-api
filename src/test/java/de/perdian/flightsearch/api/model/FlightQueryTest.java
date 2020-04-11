package de.perdian.flightsearch.api.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FlightQueryTest {

    @Test
    public void testTest() {
        FlightQuery flightQuery = new FlightQuery();
        Assertions.assertTrue(flightQuery.test(this.createFlight()));
    }

    @Test
    public void testTestWithDestinationAirportContact() {
        AirportContactQuery airportContactQuery = new AirportContactQuery();
        airportContactQuery.setDateTime(new DateTimeQuery(LocalDate.of(2000, 1, 3)));
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setDestinationAirportContact(airportContactQuery);
        Assertions.assertTrue(flightQuery.test(this.createFlight()));
    }

    @Test
    public void testTestWithDestinationAirportContactFailed() {
        AirportContactQuery airportContactQuery = new AirportContactQuery();
        airportContactQuery.setDateTime(new DateTimeQuery(LocalDate.of(2000, 5, 2)));
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setDestinationAirportContact(airportContactQuery);
        Assertions.assertFalse(flightQuery.test(this.createFlight()));
    }

    @Test
    public void testTestWithOriginAirportContact() {
        AirportContactQuery airportContactQuery = new AirportContactQuery();
        airportContactQuery.setDateTime(new DateTimeQuery(LocalDate.of(2000, 1, 2)));
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setOriginAirportContact(airportContactQuery);
        Assertions.assertTrue(flightQuery.test(this.createFlight()));
    }

    @Test
    public void testTestWithOriginAirportContactFailed() {
        AirportContactQuery airportContactQuery = new AirportContactQuery();
        airportContactQuery.setDateTime(new DateTimeQuery(LocalDate.of(2000, 5, 2)));
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setOriginAirportContact(airportContactQuery);
        Assertions.assertFalse(flightQuery.test(this.createFlight()));
    }

    @Test
    public void testTestWithConnection() {
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setConnection(new ConnectionQuery(new DurationQuery(null, Duration.ofHours(3))));
        Assertions.assertTrue(flightQuery.test(this.createFlight()));
    }

    @Test
    public void testTestWithConnectionFailed() {
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setConnection(new ConnectionQuery(new DurationQuery(null, Duration.ofHours(1))));
        Assertions.assertFalse(flightQuery.test(this.createFlight()));
    }

    @Test
    public void testTestWithSegment() {
        LegQuery legQuery = new LegQuery();
        legQuery.setBlacklistedAirportCodes(Arrays.asList("BER"));
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setSegment(new SegmentQuery(legQuery));
        Assertions.assertTrue(flightQuery.test(this.createFlight()));
    }

    @Test
    public void testTestWithSegmentFailed() {
        LegQuery legQuery = new LegQuery();
        legQuery.setBlacklistedAirportCodes(Arrays.asList("FRA"));
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setSegment(new SegmentQuery(legQuery));
        Assertions.assertFalse(flightQuery.test(this.createFlight()));
    }

    @Test
    public void testTestWithTotalDuration() {
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setTotalDuration(new DurationQuery(null, Duration.ofHours(24)));
        Assertions.assertTrue(flightQuery.test(this.createFlight()));
    }

    @Test
    public void testTestWithTotalDurationFailed() {
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setTotalDuration(new DurationQuery(null, Duration.ofHours(5)));
        Assertions.assertFalse(flightQuery.test(this.createFlight()));
    }

    @Test
    public void testFlattenMultipleAirportsForDepartureAndArrival() {
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setOriginAirportContact(new AirportContactQuery(Arrays.asList("CGN"), false));
        flightQuery.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("JFK"), false));
        List<FlightQuery> flattenedQueries = flightQuery.flattenMultipleAirportsForDepartureAndArrival();
        Assertions.assertEquals(1, flattenedQueries.size());
        Assertions.assertEquals(flightQuery, flattenedQueries.get(0));
    }

    @Test
    public void testFlattenMultipleAirportsForDepartureAndArrivalMultipleOriginAirportCodes() {
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setOriginAirportContact(new AirportContactQuery(Arrays.asList("CGN", "DUS"), false));
        flightQuery.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("JFK"), false));
        List<FlightQuery> flattenedQueries = flightQuery.flattenMultipleAirportsForDepartureAndArrival();
        Assertions.assertEquals(2, flattenedQueries.size());
        Assertions.assertEquals(Arrays.asList("CGN"), flattenedQueries.get(0).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedQueries.get(0).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("DUS"), flattenedQueries.get(1).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedQueries.get(1).getDestinationAirportContact().getAirportCodes());
    }

    @Test
    public void testFlattenMultipleAirportsForDepartureAndArrivalMultipleOriginAirportCodesMultipleDestinationAirportCodes() {
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setOriginAirportContact(new AirportContactQuery(Arrays.asList("CGN", "DUS"), false));
        flightQuery.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("JFK", "EWR"), false));
        List<FlightQuery> flattenedQueries = flightQuery.flattenMultipleAirportsForDepartureAndArrival();
        Assertions.assertEquals(4, flattenedQueries.size());
        Assertions.assertEquals(Arrays.asList("CGN"), flattenedQueries.get(0).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedQueries.get(0).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("CGN"), flattenedQueries.get(1).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("EWR"), flattenedQueries.get(1).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("DUS"), flattenedQueries.get(2).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("JFK"), flattenedQueries.get(2).getDestinationAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("DUS"), flattenedQueries.get(3).getOriginAirportContact().getAirportCodes());
        Assertions.assertEquals(Arrays.asList("EWR"), flattenedQueries.get(3).getDestinationAirportContact().getAirportCodes());
    }

    private Flight createFlight() {
        AirportContact ac1 = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 10, 00));
        AirportContact ac2 = new AirportContact(new Airport("FRA", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 11, 00));
        AirportContact ac3 = new AirportContact(new Airport("FRA", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 13, 00));
        AirportContact ac4 = new AirportContact(new Airport("JFK", ZoneId.of("America/New_York")), LocalDateTime.of(2000, 1, 2, 18, 00));
        AirportContact ac5 = new AirportContact(new Airport("JFK", ZoneId.of("America/New_York")), LocalDateTime.of(2000, 1, 2, 18, 30));
        AirportContact ac6 = new AirportContact(new Airport("MCO", ZoneId.of("America/New_York")), LocalDateTime.of(2000, 1, 3, 01, 00));
        Segment s1 = new Segment(Arrays.asList(new Leg(new Route(ac1, ac2), null)));
        Segment s2 = new Segment(Arrays.asList(new Leg(new Route(ac3, ac4), null), new Leg(new Route(ac5, ac6), null)));
        return new Flight(Arrays.asList(s1, s2));
    }

}
