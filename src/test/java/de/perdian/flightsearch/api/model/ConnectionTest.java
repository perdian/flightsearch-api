package de.perdian.flightsearch.api.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConnectionTest {

    @Test
    public void testConstructor() {
        AirportContact c1 = new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 3, 4));
        AirportContact c2 = new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 2, 3, 4, 5));
        Connection connection = new Connection(c1, c2);
        Assertions.assertEquals(c1, connection.getArrival());
        Assertions.assertEquals(c2, connection.getDeparture());
    }

    @Test
    public void testGetDuration() {
        Airport a1 = new Airport("CGN", ZoneId.of("Europe/Berlin"));
        Airport a2 = new Airport("CGN", ZoneId.of("Europe/Berlin"));
        AirportContact c1 = new AirportContact(a1, LocalDateTime.of(2000, 1, 2, 3, 4));
        AirportContact c2 = new AirportContact(a2, LocalDateTime.of(2000, 1, 2, 4, 5));
        Connection connection = new Connection(c1, c2);
        Assertions.assertEquals(Duration.ofMinutes(61), connection.getDuration());
    }

}
