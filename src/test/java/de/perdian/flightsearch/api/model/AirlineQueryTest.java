package de.perdian.flightsearch.api.model;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AirlineQueryTest {

    @Test
    public void testConstructor() {
        AirlineQuery airlineQuery = new AirlineQuery();
        Assertions.assertNull(airlineQuery.getExcludeCodes());
        Assertions.assertNull(airlineQuery.getRestrictCodes());
    }

    @Test
    public void testTest() {
        AirlineQuery airlineQuery = new AirlineQuery();
        Assertions.assertTrue(airlineQuery.test(new Airline()));
    }

    @Test
    public void testTestWithRestrictAirportCodes() {
        AirlineQuery airlineQuery = new AirlineQuery();
        airlineQuery.setRestrictCodes(Arrays.asList("LH"));
        Assertions.assertTrue(airlineQuery.test(new Airline("LH")));
        Assertions.assertFalse(airlineQuery.test(new Airline("XY")));
    }

    @Test
    public void testTestWithExcludeAirportCodes() {
        AirlineQuery airlineQuery = new AirlineQuery();
        airlineQuery.setExcludeCodes(Arrays.asList("LH"));
        Assertions.assertFalse(airlineQuery.test(new Airline("LH")));
        Assertions.assertTrue(airlineQuery.test(new Airline("XY")));
    }

}
