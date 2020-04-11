package de.perdian.flightsearch.api.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RouteTest {

    @Test
    public void testConstructor() {
        AirportContact departureContact = new AirportContact();
        AirportContact arrivalContact = new AirportContact();
        Route route = new Route(departureContact, arrivalContact);
        Assertions.assertSame(departureContact, route.getDeparture());
        Assertions.assertSame(arrivalContact, route.getArrival());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        AirportContact departureContact1 = new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 13, 00));
        AirportContact arrivalContact1 = new AirportContact(new Airport("MCO"), LocalDateTime.of(2000, 1, 2, 16, 00));
        Route route1 = new Route(departureContact1, arrivalContact1);
        AirportContact departureContact2 = new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 13, 00));
        AirportContact arrivalContact2 = new AirportContact(new Airport("MCO"), LocalDateTime.of(2000, 1, 2, 16, 00));
        Route route2 = new Route(departureContact2, arrivalContact2);
        AirportContact contact4 = new AirportContact(new Airport("JFK"), LocalDateTime.of(2000, 1, 2, 13, 00));
        Route route1x = new Route(departureContact1, contact4);
        Route route1y = new Route(contact4, arrivalContact1);
        Assertions.assertEquals(route1, route1);
        Assertions.assertEquals(route1, route2);
        Assertions.assertNotEquals(route1, route1x);
        Assertions.assertNotEquals(route1, route1y);
        Assertions.assertFalse(route1.equals("INVALID"));
        Assertions.assertFalse(route1.equals(null));
    }

    @Test
    public void testGetDuration() {
        AirportContact departureContact = new AirportContact(new Airport("CGN", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 13, 00));
        AirportContact arrivalContact = new AirportContact(new Airport("FRA", ZoneId.of("Europe/Berlin")), LocalDateTime.of(2000, 1, 2, 16, 00));
        Route route = new Route(departureContact, arrivalContact);
        Assertions.assertEquals(Duration.ofHours(3), route.getDuration());
    }

}
