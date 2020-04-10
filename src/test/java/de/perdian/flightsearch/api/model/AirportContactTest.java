package de.perdian.flightsearch.api.model;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AirportContactTest {

    @Test
    public void testConstructor() {
        AirportContact airportContact = new AirportContact(new Airport("CGN"), LocalDateTime.of(2020, 1, 2, 3, 4));
        Assertions.assertEquals("CGN", airportContact.getAirport().getCode());
        Assertions.assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), airportContact.getLocalDateTime());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        AirportContact c1a = new AirportContact(new Airport("CGN"), LocalDateTime.of(2020, 1, 2, 3, 4));
        AirportContact c1b = new AirportContact(new Airport("CGN"), LocalDateTime.of(2020, 1, 2, 3, 4));
        AirportContact c2a = new AirportContact(new Airport("FRA"), LocalDateTime.of(2020, 1, 2, 3, 4));
        AirportContact c2b = new AirportContact(new Airport("CGN"), LocalDateTime.of(2021, 1, 2, 3, 4));
        Assertions.assertEquals(c1a, c1a);
        Assertions.assertEquals(c1a, c1b);
        Assertions.assertNotEquals(c1a, c2a);
        Assertions.assertNotEquals(c1a, c2b);
        Assertions.assertFalse(c2b.equals("INVALID"));
    }

}
