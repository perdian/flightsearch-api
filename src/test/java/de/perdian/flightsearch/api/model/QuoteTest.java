package de.perdian.flightsearch.api.model;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuoteTest {

    @Test
    public void testConstructor() {
        Quote quote = new Quote("foo", new Price(BigDecimal.valueOf(42), "EUR"));
        Assertions.assertEquals("foo", quote.getProvider());
        Assertions.assertEquals(BigDecimal.valueOf(42), quote.getPrice().getValue());
        Assertions.assertEquals("EUR", quote.getPrice().getCurrencyCode());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        Quote q1a = new Quote("foo", new Price(BigDecimal.valueOf(42), "EUR"));
        Quote q1b = new Quote("foo", new Price(BigDecimal.valueOf(42), "EUR"));
        Quote q2a = new Quote("foo", new Price(BigDecimal.valueOf(100), "EUR"));
        Quote q2b = new Quote("bar", new Price(BigDecimal.valueOf(100), "EUR"));
        Assertions.assertEquals(q1a, q1a);
        Assertions.assertEquals(q1a, q1b);
        Assertions.assertEquals(q1b, q1a);
        Assertions.assertNotEquals(q1a, q2a);
        Assertions.assertNotEquals(q2a, q1a);
        Assertions.assertNotEquals(q2a, q2b);
        Assertions.assertNotEquals(q2b, q2a);
        Assertions.assertFalse(q1a.equals("INVALID"));
        Assertions.assertFalse(q1a.equals(null));
    }

    @Test
    public void testCompareByPrice() {
        Quote q1 = new Quote("foo", new Price(BigDecimal.valueOf(42), "EUR"));
        Quote q2 = new Quote("foo", new Price(BigDecimal.valueOf(100), "EUR"));
        Assertions.assertEquals(-1, Quote.compareByPrice(q1, q2));
        Assertions.assertEquals(1, Quote.compareByPrice(q2, q1));
        Assertions.assertEquals(0, Quote.compareByPrice(q1, q1));
    }

}
