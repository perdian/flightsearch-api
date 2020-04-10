package de.perdian.flightsearch.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AirlineTest {

    @Test
    public void testConstructor() {
        Airline airline = new Airline("AB");
        Assertions.assertEquals("AB", airline.getCode());
    }

}
