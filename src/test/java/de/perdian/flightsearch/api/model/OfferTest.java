package de.perdian.flightsearch.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OfferTest {

    @Test
    public void testConstructor() {
        Trip trip = new Trip();
        List<Quote> quotes = new ArrayList<>();
        Offer offer = new Offer(trip, quotes);
        Assertions.assertEquals(trip, offer.getTrip());
        Assertions.assertSame(quotes, offer.getQuotes());
    }

    @Test
    public void testCompareByPrice() {
        Quote q1 = new Quote("foo", new Price(BigDecimal.valueOf(10), "EUR"));
        Quote q2 = new Quote("foo", new Price(BigDecimal.valueOf(20), "EUR"));
        Quote q3 = new Quote("foo", new Price(BigDecimal.valueOf(30), "EUR"));
        Offer o1 = new Offer(null, Arrays.asList(q3, q1));
        Offer o2a = new Offer(null, Arrays.asList(q2));
        Offer o2b = new Offer(null, Arrays.asList(q2));
        Assertions.assertEquals(0, Offer.compareByPrice(o1, o1));
        Assertions.assertEquals(-1, Offer.compareByPrice(o1, o2a));
        Assertions.assertEquals(-1, Offer.compareByPrice(o1, o2b));
        Assertions.assertEquals(1, Offer.compareByPrice(o2a, o1));
    }

    @Test
    public void testMerge() {
        Quote q1a = new Quote("foo", new Price(BigDecimal.valueOf(300), "EUR"));
        Quote q1b = new Quote("foo", new Price(BigDecimal.valueOf(20), "EUR"));
        Quote q2 = new Quote("foo", new Price(BigDecimal.valueOf(30), "EUR"));
        Route r1a = new Route(new AirportContact(new Airport("CGN"), LocalDateTime.of(2000, 1, 2, 10, 00)), new AirportContact(new Airport("FRA"), LocalDateTime.of(2000, 1, 2, 11, 00)));
        Route r1b = new Route(new AirportContact(new Airport("FRA"), LocalDateTime.of(2000, 1, 2, 14, 00)), new AirportContact(new Airport("MUC"), LocalDateTime.of(2000, 1, 2, 16, 00)));
        Flight f1 = new Flight(Arrays.asList(new Segment(Arrays.asList(new Leg(r1a, null)))));
        Flight f2 = new Flight(Arrays.asList(new Segment(Arrays.asList(new Leg(r1a, null), new Leg(r1b, null)))));
        Trip t1 = new Trip(Arrays.asList(f1));
        Trip t2 = new Trip(Arrays.asList(f2));
        Offer o1a = new Offer(t1, Arrays.asList(q1a));
        Offer o1b = new Offer(t1, Arrays.asList(q1b));
        Offer o2 = new Offer(t2, Arrays.asList(q2));

        List<Offer> mergedOffers = Offer.merge(Arrays.asList(o1a, o2, o1b));
        Assertions.assertEquals(2, mergedOffers.size());
        Assertions.assertEquals(t1, mergedOffers.get(0).getTrip());
        Assertions.assertEquals(2, mergedOffers.get(0).getQuotes().size());
        Assertions.assertEquals(q1b, mergedOffers.get(0).getQuotes().get(0));
        Assertions.assertEquals(q1a, mergedOffers.get(0).getQuotes().get(1));
        Assertions.assertEquals(t2, mergedOffers.get(1).getTrip());
        Assertions.assertEquals(1, mergedOffers.get(1).getQuotes().size());
        Assertions.assertEquals(q2, mergedOffers.get(1).getQuotes().get(0));
    }


}
