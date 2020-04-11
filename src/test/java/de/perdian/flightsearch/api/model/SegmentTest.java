package de.perdian.flightsearch.api.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SegmentTest {

    @Test
    public void testConstructor() {
        List<Leg> legs = Arrays.asList(new Leg());
        Segment segment = new Segment(legs);
        Assertions.assertSame(legs, segment.getLegs());
    }

    @Test
    public void testGetFirstLeg() {
        Leg leg1 = new Leg();
        Leg leg2 = new Leg();
        Assertions.assertEquals(leg1, new Segment(Arrays.asList(leg1, leg2)).getFirstLeg());
        Assertions.assertEquals(leg1, new Segment(Arrays.asList(leg1)).getFirstLeg());
        Assertions.assertNull(new Segment(Collections.emptyList()).getFirstLeg());
    }

    @Test
    public void testGetLastLeg() {
        Leg leg1 = new Leg();
        Leg leg2 = new Leg();
        Assertions.assertEquals(leg2, new Segment(Arrays.asList(leg1, leg2)).getLastLeg());
        Assertions.assertEquals(leg2, new Segment(Arrays.asList(leg1)).getLastLeg());
        Assertions.assertNull(new Segment(Collections.emptyList()).getLastLeg());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        AirportContact departureContact1 = new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact arrivalContact1 = new AirportContact(new Airport("FRA"), LocalDateTime.of(2000, 1, 2, 15, 00));
        Leg leg1 = new Leg(new Route(departureContact1, arrivalContact1), null);
        AirportContact departureContact2 = new AirportContact(new Airport("DUS"), LocalDateTime.of(2000, 1, 3, 16, 00));
        AirportContact arrivalContact2 = new AirportContact(new Airport("JFK"), LocalDateTime.of(2000, 1, 3, 17, 00));
        Leg leg2 = new Leg(new Route(departureContact2, arrivalContact2), null);
        Segment segment1a = new Segment(Arrays.asList(leg1));
        Segment segment1b = new Segment(Arrays.asList(leg1));
        Segment segment2 = new Segment(Arrays.asList(leg1, leg2));
        Assertions.assertEquals(segment1a, segment1a);
        Assertions.assertEquals(segment1a, segment1b);
        Assertions.assertEquals(segment1b, segment1a);
        Assertions.assertNotEquals(segment1a, segment2);
        Assertions.assertNotEquals(segment2, segment1a);
        Assertions.assertFalse(segment1a.equals("INVALID"));
        Assertions.assertFalse(segment1a.equals(null));
    }

}
