package de.perdian.flightsearch.api.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FlightTest {

    @Test
    public void testConstructor() {
        List<Segment> segments = Arrays.asList(new Segment(), new Segment());
        Flight flight = new Flight(segments);
        Assertions.assertEquals(segments, flight.getSegments());
    }

    @Test
    public void testEquals() {
        AirportContact ac1 = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 3, 4));
        AirportContact ac2 = new AirportContact(new Airport("FRA", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 4, 5));
        AirportContact ac3 = new AirportContact(new Airport("JFK", ZoneId.of("America/New_York")), LocalDateTime.of(2000, 1, 2, 6, 7));
        Leg l1 = new Leg(new Route(ac1, ac2), null);
        Leg l2 = new Leg(new Route(ac1, ac3), null);
        Segment s1 = new Segment(Arrays.asList(l1));
        Segment s2 = new Segment(Arrays.asList(l1, l2));
        Segment s3 = new Segment(Arrays.asList(l2));
        Flight flight1a = new Flight(Arrays.asList(s1, s2));
        Flight flight1b = new Flight(Arrays.asList(s1, s2));
        Flight flight2 = new Flight(Arrays.asList(s1, s3));
        Flight flight3 = new Flight(Arrays.asList(s1));
        Assertions.assertEquals(flight1a, flight1b);
        Assertions.assertEquals(flight1b, flight1a);
        Assertions.assertNotEquals(flight1a, flight2);
        Assertions.assertNotEquals(flight1a, flight3);
    }

    @Test
    public void testComputeScheduledConnectionsAndTotalScheduledDuration() {
        Airport a1 = new Airport("CGN", ZoneId.of("Europe/Berlin"));
        Airport a2 = new Airport("FRA", ZoneId.of("Europe/Berlin"));
        Airport a3 = new Airport("MUC", ZoneId.of("Europe/Berlin"));
        Route r1 = new Route(new AirportContact(a1, LocalDateTime.of(2000, 1, 2, 12, 00)), new AirportContact(a2, LocalDateTime.of(2000, 1, 2, 12, 30)));
        Route r2 = new Route(new AirportContact(a2, LocalDateTime.of(2000, 1, 2, 13, 15)), new AirportContact(a3, LocalDateTime.of(2000, 1, 2, 14, 15)));
        Route r3 = new Route(new AirportContact(a3, LocalDateTime.of(2000, 1, 2, 15, 15)), new AirportContact(a2, LocalDateTime.of(2000, 1, 2, 17, 05)));
        Segment s1 = new Segment(Arrays.asList(new Leg(r1, null)));
        Segment s2 = new Segment(Arrays.asList(new Leg(r2, null), new Leg(r3, null)));
        Flight flight = new Flight(Arrays.asList(s1, s2));
        List<Connection> scheduledConnections = flight.computeScheduledConnections();
        Assertions.assertEquals(2, scheduledConnections.size());
        Assertions.assertEquals(r1.getArrival(), scheduledConnections.get(0).getArrival());
        Assertions.assertEquals(r2.getDeparture(), scheduledConnections.get(0).getDeparture());
        Assertions.assertEquals(Duration.ofMinutes(45), scheduledConnections.get(0).getDuration());
        Assertions.assertEquals(r2.getArrival(), scheduledConnections.get(1).getArrival());
        Assertions.assertEquals(r3.getDeparture(), scheduledConnections.get(1).getDeparture());
        Assertions.assertEquals(Duration.ofMinutes(60), scheduledConnections.get(1).getDuration());
        Assertions.assertEquals(Duration.ofHours(5).plusMinutes(5), flight.getTotalScheduledDuration());
    }

    @Test
    public void testGetFirstSegment() {
        Segment s = new Segment();
        Assertions.assertSame(s, new Flight(Arrays.asList(s, new Segment())).getFirstSegment());
        Assertions.assertSame(s, new Flight(Arrays.asList(s)).getFirstSegment());
        Assertions.assertNull(new Flight().getFirstSegment());
    }

    @Test
    public void testGetLastSegment() {
        Segment s = new Segment();
        Assertions.assertSame(s, new Flight(Arrays.asList(new Segment(), s)).getLastSegment());
        Assertions.assertSame(s, new Flight(Arrays.asList(s)).getLastSegment());
        Assertions.assertNull(new Flight().getLastSegment());
    }

}
