package de.perdian.flightsearch.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AircraftTypeTest {

    @Test
    public void testConstructor() {
        AircraftType aircraftType = new AircraftType("A320");
        Assertions.assertEquals("A320", aircraftType.getCode());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        Assertions.assertEquals(new AircraftType("A320"), new AircraftType("A320"));
        Assertions.assertNotEquals(new AircraftType("A320"), new AircraftType("A321"));
        Assertions.assertFalse(new AircraftType("A321").equals("XX"));
    }

}
