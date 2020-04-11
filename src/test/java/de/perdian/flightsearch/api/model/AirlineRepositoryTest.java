package de.perdian.flightsearch.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AirlineRepositoryTest {

    @Test
    public void testLoadAirlineByCode() {
        Airline airline = AirlineRepository.getInstance().loadAirlineByCode("LH");
        Assertions.assertEquals("LH", airline.getCode());
        Assertions.assertEquals("Lufthansa", airline.getName());
        Assertions.assertEquals("LUFTHANSA", airline.getCallsign());
        Assertions.assertNull(airline.getAlias());
        Assertions.assertEquals("DE", airline.getCountryCode());
    }

}
