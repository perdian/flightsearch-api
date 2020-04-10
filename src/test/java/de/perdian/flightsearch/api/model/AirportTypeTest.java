package de.perdian.flightsearch.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AirportTypeTest {

    @Test
    public void testParseValue() {
        Assertions.assertEquals(AirportType.AIRPORT, AirportType.parseValue("AiRpOrT"));
    }

    @Test
    public void testParseValueInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> AirportType.parseValue("INVALID"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> AirportType.parseValue(null));
    }

}
