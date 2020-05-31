package de.perdian.flightsearch.api.model;

import java.time.LocalDate;
import java.time.LocalTime;
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
        AirportContact originContact1 = new AirportContact(new Airport("CGN"), LocalDate.of(2000, 1, 2), LocalTime.of(14, 00));
        AirportContact destinationContact1 = new AirportContact(new Airport("FRA"), LocalDate.of(2000, 1, 2), LocalTime.of(15, 00));
        Leg leg1 = new Leg(new Route(originContact1, destinationContact1), null);
        AirportContact originContact2 = new AirportContact(new Airport("DUS"), LocalDate.of(2000, 1, 3), LocalTime.of(16, 00));
        AirportContact destinationContact2 = new AirportContact(new Airport("JFK"), LocalDate.of(2000, 1, 3), LocalTime.of(17, 00));
        Leg leg2 = new Leg(new Route(originContact2, destinationContact2), null);
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
