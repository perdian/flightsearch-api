package de.perdian.flightsearch.api.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RouteTest {

    @Test
    public void testConstructor() {
        AirportContact originContact = new AirportContact();
        AirportContact destinationContact = new AirportContact();
        Route route = new Route(originContact, destinationContact);
        Assertions.assertSame(originContact, route.getOrigin());
        Assertions.assertSame(destinationContact, route.getDestination());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        AirportContact originContact1 = new AirportContact(new Airport("CGN"), LocalDate.of(2000, 1, 2), LocalTime.of(13, 00));
        AirportContact destinationContact1 = new AirportContact(new Airport("MCO"), LocalDate.of(2000, 1, 2), LocalTime.of(16, 00));
        Route route1 = new Route(originContact1, destinationContact1);
        AirportContact originContact2 = new AirportContact(new Airport("CGN"), LocalDate.of(2000, 1, 2), LocalTime.of(13, 00));
        AirportContact destinationContact2 = new AirportContact(new Airport("MCO"), LocalDate.of(2000, 1, 2), LocalTime.of(16, 00));
        Route route2 = new Route(originContact2, destinationContact2);
        AirportContact contact4 = new AirportContact(new Airport("JFK"), LocalDate.of(2000, 1, 2), LocalTime.of(13, 00));
        Route route1x = new Route(originContact1, contact4);
        Route route1y = new Route(contact4, destinationContact1);
        Assertions.assertEquals(route1, route1);
        Assertions.assertEquals(route1, route2);
        Assertions.assertNotEquals(route1, route1x);
        Assertions.assertNotEquals(route1, route1y);
        Assertions.assertFalse(route1.equals("INVALID"));
        Assertions.assertFalse(route1.equals(null));
    }

    @Test
    public void testGetDuration() {
        AirportContact originContact = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDate.of(2000, 1, 2), LocalTime.of(13, 00));
        AirportContact destinationContact = new AirportContact(new Airport("FRA", ZoneId.of("Europe/Berlin")), LocalDate.of(2000, 1, 2), LocalTime.of(16, 00));
        Route route = new Route(originContact, destinationContact);
        Assertions.assertEquals(Duration.ofHours(3), route.getDuration());
    }

}
