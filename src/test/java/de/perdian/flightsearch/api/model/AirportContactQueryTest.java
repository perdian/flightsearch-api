package de.perdian.flightsearch.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AirportContactQueryTest {

    @Test
    public void testConstructor() {
        AirportContactQuery airportContactQuery = new AirportContactQuery(Arrays.asList("CGN", "DUS"), true);
        Assertions.assertEquals(Arrays.asList("CGN", "DUS"), airportContactQuery.getAirportCodes());
        Assertions.assertTrue(airportContactQuery.isEnforceExactAirportCodes());
    }

    @Test
    public void testClone() {
        AirportContactQuery originalQuery = new AirportContactQuery(Arrays.asList("CGN", "DUS"), true);
        AirportContactQuery clonedQuery = originalQuery.clone();
        Assertions.assertNotNull(clonedQuery);
        Assertions.assertNotSame(originalQuery, clonedQuery);
    }

    @Test
    public void testTest() {
        AirportContactQuery airportContactQuery = new AirportContactQuery();
        Assertions.assertTrue(airportContactQuery.test(new AirportContact()));
    }

    @Test
    public void testTestWithAirportCodes() {
        AirportContact airportContact1 = new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 14, 45));
        AirportContact airportContact2 = new AirportContact(new Airport("FRA"), LocalDateTime.of(2000, 1, 2, 14, 45));
        AirportContactQuery airportContactQuery = new AirportContactQuery();
        airportContactQuery.setAirportCodes(Arrays.asList("FRA"));
        Assertions.assertTrue(airportContactQuery.test(airportContact1));
        Assertions.assertTrue(airportContactQuery.test(airportContact2));
    }

    @Test
    public void testTestWithAirportCodesAndEnforceExactCodes() {
        AirportContact airportContact1 = new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 14, 45));
        AirportContact airportContact2 = new AirportContact(new Airport("FRA"), LocalDateTime.of(2000, 1, 2, 14, 45));
        AirportContactQuery airportContactQuery = new AirportContactQuery();
        airportContactQuery.setAirportCodes(Arrays.asList("FRA"));
        airportContactQuery.setEnforceExactAirportCodes(true);
        Assertions.assertFalse(airportContactQuery.test(airportContact1));
        Assertions.assertTrue(airportContactQuery.test(airportContact2));
    }

    @Test
    public void testTestWithDateTime() {
        AirportContact airportContact1 = new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 14, 45));
        AirportContact airportContact2 = new AirportContact(new Airport("FRA"), LocalDateTime.of(2000, 1, 3, 14, 45));
        AirportContactQuery airportContactQuery = new AirportContactQuery();
        airportContactQuery.setDateTime(new DateTimeQuery(LocalDate.of(2000, 1, 2)));
        Assertions.assertTrue(airportContactQuery.test(airportContact1));
        Assertions.assertFalse(airportContactQuery.test(airportContact2));
    }

}
