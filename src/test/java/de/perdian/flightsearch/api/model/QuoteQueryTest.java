package de.perdian.flightsearch.api.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuoteQueryTest {

    @Test
    public void testConstructor() {
        PriceQuery priceQuery = new PriceQuery(BigDecimal.valueOf(42), BigDecimal.valueOf(400));
        QuoteQuery quoteQuery = new QuoteQuery(priceQuery);
        Assertions.assertEquals(priceQuery, quoteQuery.getPrice());
    }

    @Test
    public void testTest() {
        QuoteQuery quoteQuery = new QuoteQuery();
        Assertions.assertTrue(quoteQuery.test(new Quote("foo", new Price(BigDecimal.valueOf(41), "EUR"))));
        Assertions.assertTrue(quoteQuery.test(null));
        Assertions.assertTrue(quoteQuery.test(new Quote()));
    }

    @Test
    public void testTestAny() {
        QuoteQuery quoteQuery = new QuoteQuery();
        quoteQuery.setPrice(new PriceQuery(null, BigDecimal.valueOf(50)));
        Quote q1 = new Quote("foo", new Price(BigDecimal.valueOf(42), "EUR"));
        Quote q2 = new Quote("foo", new Price(BigDecimal.valueOf(90), "EUR"));
        Quote q3 = new Quote("foo", new Price(BigDecimal.valueOf(100), "EUR"));
        Assertions.assertTrue(quoteQuery.testAny(Arrays.asList(q1, q2, q3)));
        Assertions.assertFalse(quoteQuery.testAny(Arrays.asList(q2, q3)));
        Assertions.assertFalse(quoteQuery.testAny(Collections.emptyList()));
    }

}
