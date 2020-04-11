package de.perdian.flightsearch.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AircraftTest {

    @Test
    public void testConstructor() {
        Aircraft aircraft = new Aircraft(new AircraftType("A320"));
        Assertions.assertEquals("A320", aircraft.getType().getCode());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        Assertions.assertEquals(new Aircraft(new AircraftType("A320")), new Aircraft(new AircraftType("A320")));
        Assertions.assertNotEquals(new Aircraft(new AircraftType("A320")), new Aircraft(new AircraftType("A321")));
        Assertions.assertFalse(new Aircraft(new AircraftType("A321")).equals("XX"));
    }

}
