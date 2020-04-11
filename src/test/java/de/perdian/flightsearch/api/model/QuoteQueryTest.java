package de.perdian.flightsearch.api.model;

import java.math.BigDecimal;

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

}
