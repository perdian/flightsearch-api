package de.perdian.flightsearch.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AircraftTypeRepositoryTest {

    @Test
    public void testLoadAircraftTypeByCodeIata() {
        AircraftType aircraftType = AircraftTypeRepository.getInstance().loadAircraftTypeByCode("320");
        Assertions.assertEquals("320", aircraftType.getCode());
        Assertions.assertEquals("Airbus A320", aircraftType.getTitle());
    }

    @Test
    public void testLoadAircraftTypeByCodeIcao() {
        AircraftType aircraftType = AircraftTypeRepository.getInstance().loadAircraftTypeByCode("A320");
        Assertions.assertEquals("320", aircraftType.getCode());
        Assertions.assertEquals("Airbus A320", aircraftType.getTitle());
    }

}
