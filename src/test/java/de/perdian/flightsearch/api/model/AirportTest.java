package de.perdian.flightsearch.api.model;

import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AirportTest {

    @Test
    public void testConstructor() {
        Airport airport = new Airport("CGN");
        Assertions.assertEquals("CGN", airport.getCode());
        Assertions.assertNull(airport.getTimezoneId());
    }

    @Test
    public void testConstructorWithTimezoneId() {
        Airport airport = new Airport("CGN", ZoneId.of("Europe/Berlin"));
        Assertions.assertEquals("CGN", airport.getCode());
        Assertions.assertEquals(ZoneId.of("Europe/Berlin"), airport.getTimezoneId());
    }

    @Test
    public void testEquals() {
        Assertions.assertEquals(new Airport("CGN"), new Airport("CGN"));
        Assertions.assertNotEquals(new Airport("CGN"), new Airport("FRA"));
        Assertions.assertFalse(new Airport("CGN").equals("XX"));
    }

}
