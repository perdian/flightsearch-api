package de.perdian.flightsearch.api.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SegmentQueryTest {

    @Test
    public void testConstructor() {
        LegQuery legQuery = new LegQuery();
        SegmentQuery segmentQuery = new SegmentQuery(legQuery);
        Assertions.assertEquals(legQuery, segmentQuery.getLeg());
    }

    @Test
    public void testTest() {
        SegmentQuery segmentQuery = new SegmentQuery();
        Assertions.assertTrue(segmentQuery.test(new Segment()));
    }

    @Test
    public void testTestWithLegQuery() {
        AirportContact ac1a = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact ac1b = new AirportContact(new Airport("MUC", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 15, 05));
        Leg leg1 = new Leg(new Route(ac1a, ac1b), null);
        Segment segment1 = new Segment(Arrays.asList(leg1));

        LegQuery legQuery = new LegQuery();
        SegmentQuery segmentQuery = new SegmentQuery(legQuery);
        Assertions.assertTrue(segmentQuery.test(segment1));
    }

    @Test
    public void testTestWithLegQueryFailed() {
        AirportContact ac1a = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact ac1b = new AirportContact(new Airport("MUC", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 15, 05));
        Leg leg1 = new Leg(new Route(ac1a, ac1b), null);
        Segment segment1 = new Segment(Arrays.asList(leg1));

        LegQuery legQuery = new LegQuery();
        legQuery.setBlacklistedAirportCodes(Arrays.asList("CGN"));
        SegmentQuery segmentQuery = new SegmentQuery(legQuery);
        Assertions.assertFalse(segmentQuery.test(segment1));
    }

    @Test
    public void testTestWithOperatingFlightNumber() {
        Segment matchingSegment = new Segment();
        matchingSegment.setOperatingFlightNumber(new FlightNumber(new Airline("LH"), 123, null));
        Segment otherSegment = new Segment();
        otherSegment.setOperatingFlightNumber(new FlightNumber(new Airline("UA"), 456, null));
        SegmentQuery segmentQuery = new SegmentQuery();
        segmentQuery.setOperatingFlightNumber(new FlightNumber(new Airline("LH"), 123, null));
        Assertions.assertTrue(segmentQuery.test(matchingSegment));
        Assertions.assertFalse(segmentQuery.test(otherSegment));
    }

    @Test
    public void testTestWithMarketingFlightNumber() {
        Segment matchingSegment = new Segment();
        matchingSegment.setMarketingFlightNumber(new FlightNumber(new Airline("LH"), 123, null));
        Segment otherSegment = new Segment();
        otherSegment.setMarketingFlightNumber(new FlightNumber(new Airline("UA"), 456, null));
        SegmentQuery segmentQuery = new SegmentQuery();
        segmentQuery.setMarketingFlightNumber(new FlightNumber(new Airline("LH"), 123, null));
        Assertions.assertTrue(segmentQuery.test(matchingSegment));
        Assertions.assertFalse(segmentQuery.test(otherSegment));
    }

    @Test
    public void testTestWithFlightNumberCodeshareFlightNumber() {
        Segment matchingSegment = new Segment();
        matchingSegment.setCodeshareFlightNumbers(Arrays.asList(new FlightNumber(new Airline("LH"), 123, null)));
        Segment otherSegment = new Segment();
        SegmentQuery segmentQuery = new SegmentQuery();
        segmentQuery.setFlightNumber(new FlightNumber(new Airline("LH"), 123, null));
        Assertions.assertTrue(segmentQuery.test(matchingSegment));
        Assertions.assertFalse(segmentQuery.test(otherSegment));
    }

    @Test
    public void testTestWithFlightNumberMarketingFlightNumber() {
        Segment matchingSegment = new Segment();
        matchingSegment.setMarketingFlightNumber(new FlightNumber(new Airline("LH"), 123, null));
        Segment otherSegment = new Segment();
        SegmentQuery segmentQuery = new SegmentQuery();
        segmentQuery.setFlightNumber(new FlightNumber(new Airline("LH"), 123, null));
        Assertions.assertTrue(segmentQuery.test(matchingSegment));
        Assertions.assertFalse(segmentQuery.test(otherSegment));
    }

    @Test
    public void testTestWithFlightNumberOperatingFlightNumber() {
        Segment matchingSegment = new Segment();
        matchingSegment.setOperatingFlightNumber(new FlightNumber(new Airline("LH"), 123, null));
        Segment otherSegment = new Segment();
        SegmentQuery segmentQuery = new SegmentQuery();
        segmentQuery.setFlightNumber(new FlightNumber(new Airline("LH"), 123, null));
        Assertions.assertTrue(segmentQuery.test(matchingSegment));
        Assertions.assertFalse(segmentQuery.test(otherSegment));
    }

    @Test
    public void testTestWithOriginAirportContact() {
        AirportContact machtchingLeg1Origin = new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 13, 00));
        AirportContact machtchingLeg1Destination = new AirportContact(new Airport("FRA"), LocalDateTime.of(2000, 1, 2, 14, 00));
        Leg matchingLeg1 = new Leg(new Route(machtchingLeg1Origin, machtchingLeg1Destination), null);
        Segment matchingSegment = new Segment(Arrays.asList(matchingLeg1));
        Segment otherSegment = new Segment();
        SegmentQuery segmentQuery = new SegmentQuery();
        segmentQuery.setOriginAirportContact(new AirportContactQuery(Arrays.asList("CGN"), false));
        Assertions.assertTrue(segmentQuery.test(matchingSegment));
        Assertions.assertFalse(segmentQuery.test(otherSegment));
    }

    @Test
    public void testTestWithDestinationAirportContact() {
        AirportContact machtchingLeg1Origin = new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 13, 00));
        AirportContact machtchingLeg1Destination = new AirportContact(new Airport("FRA"), LocalDateTime.of(2000, 1, 2, 14, 00));
        Leg matchingLeg1 = new Leg(new Route(machtchingLeg1Origin, machtchingLeg1Destination), null);
        Segment matchingSegment = new Segment(Arrays.asList(matchingLeg1));
        Segment otherSegment = new Segment();
        SegmentQuery segmentQuery = new SegmentQuery();
        segmentQuery.setDestinationAirportContact(new AirportContactQuery(Arrays.asList("FRA"), false));
        Assertions.assertTrue(segmentQuery.test(matchingSegment));
        Assertions.assertFalse(segmentQuery.test(otherSegment));
    }

    @Test
    public void testAll() {
        AirportContact ac1 = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact ac2a = new AirportContact(new Airport("MUC", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 15, 05));
        AirportContact ac2b = new AirportContact(new Airport("FRA", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 15, 05));
        Leg leg1 = new Leg(new Route(ac1, ac2a), null);
        Segment segment1 = new Segment(Arrays.asList(leg1));
        Leg leg2 = new Leg(new Route(ac1, ac2b), null);
        Segment segment2 = new Segment(Arrays.asList(leg2));

        LegQuery legQuery = new LegQuery();
        legQuery.setBlacklistedAirportCodes(Arrays.asList("FRA"));
        Assertions.assertTrue(new SegmentQuery(legQuery).testAll(Arrays.asList(segment1)));
        Assertions.assertFalse(new SegmentQuery(legQuery).testAll(Arrays.asList(segment1, segment2)));
        Assertions.assertFalse(new SegmentQuery(legQuery).testAll(Arrays.asList(segment2)));
    }

}
