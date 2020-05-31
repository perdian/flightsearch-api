package de.perdian.flightsearch.api.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConnectionQueryTest {

    @Test
    public void testConstructor() {
        DurationQuery durationQuery = new DurationQuery();
        ConnectionQuery connectionQuery = new ConnectionQuery(durationQuery);
        Assertions.assertEquals(durationQuery, connectionQuery.getDuration());
    }

    @Test
    public void testTestWithDuration() {
        DurationQuery durationQuery = new DurationQuery(Duration.ofMinutes(10), Duration.ofMinutes(60));
        ConnectionQuery connectionQuery = new ConnectionQuery(durationQuery);

        Airport a1 = new Airport("CGN", ZoneId.of("Europe/Berlin"));
        Airport a2 = new Airport("FRA", ZoneId.of("Europe/Berlin"));
        AirportContact c1 = new AirportContact(a1, LocalDate.of(2000, 1, 2), LocalTime.of(3, 4));
        AirportContact c2a = new AirportContact(a2, LocalDate.of(2000, 1, 2), LocalTime.of(3, 35));
        AirportContact c2b = new AirportContact(a2, LocalDate.of(2000, 1, 2), LocalTime.of(3, 40));
        AirportContact c3 = new AirportContact(a2, LocalDate.of(2000, 1, 3), LocalTime.of(3, 4));

        Assertions.assertTrue(connectionQuery.test(new Connection(c1, c2a)));
        Assertions.assertTrue(connectionQuery.test(new Connection(c1, c2b)));
        Assertions.assertFalse(connectionQuery.test(new Connection(c1, c3)));
        Assertions.assertTrue(connectionQuery.testAll(Arrays.asList(new Connection(c1, c2a), new Connection(c1, c2b))));
        Assertions.assertFalse(connectionQuery.testAll(Arrays.asList(new Connection(c1, c2a), new Connection(c1, c3))));
    }

    @Test
    public void testTestWithBlacklistedAirportCodes() {
        ConnectionQuery connectionQuery = new ConnectionQuery();
        connectionQuery.setBlacklistedAirportCodes(Arrays.asList("DUS"));

        AirportContact c1 = new AirportContact(new Airport("CGN"), LocalDate.of(2000, 1, 2), LocalTime.of(3, 4));
        AirportContact c2 = new AirportContact(new Airport("FRA"), LocalDate.of(2000, 1, 2), LocalTime.of(3, 35));
        AirportContact c3 = new AirportContact(new Airport("DUS"), LocalDate.of(2000, 1, 2), LocalTime.of(3, 40));

        Assertions.assertTrue(connectionQuery.test(new Connection(c1, c2)));
        Assertions.assertFalse(connectionQuery.test(new Connection(c1, c3)));
        Assertions.assertTrue(connectionQuery.test(new Connection(c2, c1)));
        Assertions.assertFalse(connectionQuery.test(new Connection(c3, c1)));
        Assertions.assertTrue(connectionQuery.testAll(Arrays.asList(new Connection(c1, c2), new Connection(c2, c1))));
        Assertions.assertFalse(connectionQuery.testAll(Arrays.asList(new Connection(c1, c2), new Connection(c1, c3))));
    }

}
