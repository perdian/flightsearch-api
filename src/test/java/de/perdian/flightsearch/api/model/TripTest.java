package de.perdian.flightsearch.api.model;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TripTest {

    @Test
    public void testConstructor() {
        List<Flight> flights = Arrays.asList(new Flight());
        Trip trip = new Trip(flights);
        Assertions.assertSame(flights, trip.getFlights());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        Flight f1a = new Flight();
        Flight f1b = new Flight();
        Flight f2 = new Flight(Arrays.asList(new Segment()));

        Trip trip1a = new Trip(Arrays.asList(f1a));
        Trip trip1b = new Trip(Arrays.asList(f1b));
        Trip trip2a = new Trip(Arrays.asList(f2));
        Trip trip2b = new Trip();
        Assertions.assertEquals(trip1a, trip1a);
        Assertions.assertEquals(trip1a, trip1b);
        Assertions.assertEquals(trip1b, trip1a);
        Assertions.assertNotEquals(trip1a, trip2a);
        Assertions.assertNotEquals(trip1a, trip2b);
        Assertions.assertFalse(trip1a.equals("INVALID"));
        Assertions.assertFalse(trip1a.equals(null));
    }

}
