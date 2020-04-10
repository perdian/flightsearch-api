package de.perdian.flightsearch.api.model;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LegTest {

    @Test
    public void testConstructor() {
        Route r1 = new Route(new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 12, 00)), new AirportContact(new Airport("MUC"), LocalDateTime.of(2000, 1, 2, 12, 30)));
        Route r2 = new Route(new AirportContact(new Airport("MUC"), LocalDateTime.of(2000, 1, 2, 13, 15)), new AirportContact(new Airport("MCO"), LocalDateTime.of(2000, 1, 2, 14, 15)));
        Leg leg = new Leg(r1, r2);
        Assertions.assertEquals(r1, leg.getScheduledRoute());
        Assertions.assertEquals(r2, leg.getActualRoute());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEqualsScheduledRoute() {
        Route r1 = new Route(new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 12, 00)), new AirportContact(new Airport("MUC"), LocalDateTime.of(2000, 1, 2, 12, 30)));
        Route r2 = new Route(new AirportContact(new Airport("MUC"), LocalDateTime.of(2000, 1, 2, 13, 15)), new AirportContact(new Airport("MCO"), LocalDateTime.of(2000, 1, 2, 14, 15)));
        Leg leg1a = new Leg(r1, null);
        Leg leg1b = new Leg(r1, null);
        Leg leg2 = new Leg(r2, null);
        Assertions.assertEquals(leg1a, leg1a);
        Assertions.assertEquals(leg1a, leg1b);
        Assertions.assertEquals(leg1b, leg1a);
        Assertions.assertNotEquals(leg1b, leg2);
        Assertions.assertFalse(leg1a.equals("INVALID"));
        Assertions.assertFalse(leg1a.equals(null));
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEqualsActualRoute() {
        Route r1 = new Route(new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 12, 00)), new AirportContact(new Airport("MUC"), LocalDateTime.of(2000, 1, 2, 12, 30)));
        Route r2 = new Route(new AirportContact(new Airport("MUC"), LocalDateTime.of(2000, 1, 2, 13, 15)), new AirportContact(new Airport("MCO"), LocalDateTime.of(2000, 1, 2, 14, 15)));
        Leg leg1a = new Leg(null, r1);
        Leg leg1b = new Leg(null, r1);
        Leg leg2 = new Leg(null, r2);
        Assertions.assertEquals(leg1a, leg1a);
        Assertions.assertEquals(leg1a, leg1b);
        Assertions.assertEquals(leg1b, leg1a);
        Assertions.assertNotEquals(leg1b, leg2);
        Assertions.assertFalse(leg1a.equals("INVALID"));
        Assertions.assertFalse(leg1a.equals(null));
    }

}
