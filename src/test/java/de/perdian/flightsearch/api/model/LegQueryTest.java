package de.perdian.flightsearch.api.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LegQueryTest {

    @Test
    public void testTest() {
        AirportContact ac1 = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact ac2 = new AirportContact(new Airport("MUC", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 15, 05));
        LegQuery legQuery = new LegQuery();
        Assertions.assertTrue(legQuery.test(new Leg(new Route(ac1, ac2), null)));
    }

    @Test
    public void testTestWithBlacklistedAirportCodes() {
        AirportContact ac1 = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact ac2 = new AirportContact(new Airport("MUC", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 15, 05));
        LegQuery legQuery = new LegQuery();
        legQuery.setBlacklistedAirportCodes(Arrays.asList("MCO"));
        Assertions.assertTrue(legQuery.test(new Leg(new Route(ac1, ac2), null)));
    }

    @Test
    public void testTestWithBlacklistedAirportCodesFailedDeparture() {
        AirportContact ac1 = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact ac2 = new AirportContact(new Airport("MUC", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 15, 05));
        LegQuery legQuery = new LegQuery();
        legQuery.setBlacklistedAirportCodes(Arrays.asList("CGN", "MCO"));
        Assertions.assertFalse(legQuery.test(new Leg(new Route(ac1, ac2), null)));
    }

    @Test
    public void testTestWithBlacklistedAirportCodesFailedArrival() {
        AirportContact ac1 = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact ac2 = new AirportContact(new Airport("MUC", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 15, 05));
        LegQuery legQuery = new LegQuery();
        legQuery.setBlacklistedAirportCodes(Arrays.asList("MUC"));
        Assertions.assertFalse(legQuery.test(new Leg(new Route(ac1, ac2), null)));
    }

    @Test
    public void testTestWithDuration() {
        AirportContact ac1 = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact ac2 = new AirportContact(new Airport("MUC", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 15, 05));
        LegQuery legQuery = new LegQuery();
        legQuery.setDuration(new DurationQuery(null, Duration.ofMinutes(70)));
        Assertions.assertTrue(legQuery.test(new Leg(new Route(ac1, ac2), null)));
    }

    @Test
    public void testTestWithDurationFailed() {
        AirportContact ac1 = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact ac2 = new AirportContact(new Airport("MUC", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 15, 05));
        LegQuery legQuery = new LegQuery();
        legQuery.setDuration(new DurationQuery(null, Duration.ofMinutes(30)));
        Assertions.assertFalse(legQuery.test(new Leg(new Route(ac1, ac2), null)));
    }

    @Test
    public void testAll() {
        AirportContact ac1 = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 14, 00));
        AirportContact ac2 = new AirportContact(new Airport("MUC", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 15, 05));
        AirportContact ac3a = new AirportContact(new Airport("DUS", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 16, 00));
        AirportContact ac3b = new AirportContact(new Airport("DUS", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 18, 00));
        LegQuery legQuery = new LegQuery();
        legQuery.setDuration(new DurationQuery(null, Duration.ofMinutes(90)));
        Assertions.assertTrue(legQuery.testAll(Arrays.asList(new Leg(new Route(ac1, ac2), null), new Leg(new Route(ac2, ac3a), null))));
        Assertions.assertFalse(legQuery.testAll(Arrays.asList(new Leg(new Route(ac1, ac2), null), new Leg(new Route(ac2, ac3b), null))));
    }

}
