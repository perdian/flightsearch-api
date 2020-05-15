package de.perdian.flightsearch.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FlightNumberTest {

    @Test
    public void testConstructorWithString() {
        FlightNumber flightNumber = new FlightNumber("LH42");
        Assertions.assertEquals("LH", flightNumber.getAirline().getCode());
        Assertions.assertEquals("Lufthansa", flightNumber.getAirline().getName());
        Assertions.assertEquals(42, flightNumber.getFlightNumber());
        Assertions.assertNull(flightNumber.getPostfix());
        Assertions.assertEquals("LH0042", flightNumber.toString());
    }

    @Test
    public void testConstructorWithStringPostfix() {
        FlightNumber flightNumber = new FlightNumber("LH42A");
        Assertions.assertEquals("LH", flightNumber.getAirline().getCode());
        Assertions.assertEquals("Lufthansa", flightNumber.getAirline().getName());
        Assertions.assertEquals(42, flightNumber.getFlightNumber());
        Assertions.assertEquals("A", flightNumber.getPostfix());
        Assertions.assertEquals("LH0042A", flightNumber.toString());
    }

    @Test
    public void testConstructorWithStringInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new FlightNumber("INVALID"));
    }

    @Test
    public void testConstructorWithParametersAirlineObject() {
        FlightNumber flightNumber = new FlightNumber(new Airline("XX"), 42, null);
        Assertions.assertEquals("XX", flightNumber.getAirline().getCode());
        Assertions.assertEquals(42, flightNumber.getFlightNumber());
        Assertions.assertNull(flightNumber.getPostfix());
        Assertions.assertEquals("XX0042", flightNumber.toString());
    }

    @Test
    public void testConstructorWithParametersAirlineObjectAndPostfix() {
        FlightNumber flightNumber = new FlightNumber(new Airline("XX"), 42, "A");
        Assertions.assertEquals("XX", flightNumber.getAirline().getCode());
        Assertions.assertEquals(42, flightNumber.getFlightNumber());
        Assertions.assertEquals("A", flightNumber.getPostfix());
        Assertions.assertEquals("XX0042A", flightNumber.toString());
    }

    @Test
    public void testConstructorWithParametersAirlineString() {
        FlightNumber flightNumber = new FlightNumber("XX", 42, null);
        Assertions.assertEquals("XX", flightNumber.getAirline().getCode());
        Assertions.assertEquals(42, flightNumber.getFlightNumber());
        Assertions.assertNull(flightNumber.getPostfix());
        Assertions.assertEquals("XX0042", flightNumber.toString());
    }

    @Test
    public void testConstructorWithParametersAirlineStringAndPostfix() {
        FlightNumber flightNumber = new FlightNumber("XX", 42, "A");
        Assertions.assertEquals("XX", flightNumber.getAirline().getCode());
        Assertions.assertEquals(42, flightNumber.getFlightNumber());
        Assertions.assertEquals("A", flightNumber.getPostfix());
        Assertions.assertEquals("XX0042A", flightNumber.toString());
    }

}
