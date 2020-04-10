package de.perdian.flightsearch.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AircraftTest {

    @Test
    public void testConstructor() {
        Aircraft aircraft = new Aircraft("A320");
        Assertions.assertEquals("A320", aircraft.getTypeCode());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        Assertions.assertEquals(new Aircraft("A320"), new Aircraft("A320"));
        Assertions.assertNotEquals(new Aircraft("A320"), new Aircraft("A321"));
        Assertions.assertFalse(new Aircraft("A321").equals("XX"));
    }

}
