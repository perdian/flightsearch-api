package de.perdian.flightsearch.api.model;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceQueryTest {

    @Test
    public void testConstructor() {
        PriceQuery priceQuery = new PriceQuery(BigDecimal.valueOf(42), BigDecimal.valueOf(400));
        Assertions.assertEquals(BigDecimal.valueOf(42), priceQuery.getMin());
        Assertions.assertEquals(BigDecimal.valueOf(400), priceQuery.getMax());
    }

    @Test
    public void testTest() {
        PriceQuery priceQuery = new PriceQuery();
        Assertions.assertTrue(priceQuery.test(new Price(BigDecimal.valueOf(41), "EUR")));
        Assertions.assertTrue(priceQuery.test(null));
        Assertions.assertTrue(priceQuery.test(new Price()));
    }

    @Test
    public void testTestWithMin() {
        PriceQuery priceQuery = new PriceQuery(BigDecimal.valueOf(42), null);
        Assertions.assertFalse(priceQuery.test(new Price(BigDecimal.valueOf(41), "EUR")));
        Assertions.assertTrue(priceQuery.test(new Price(BigDecimal.valueOf(42), "EUR")));
        Assertions.assertTrue(priceQuery.test(new Price(BigDecimal.valueOf(400), "EUR")));
        Assertions.assertFalse(priceQuery.test(null));
        Assertions.assertFalse(priceQuery.test(new Price()));
    }

    @Test
    public void testTestWithMax() {
        PriceQuery priceQuery = new PriceQuery(null, BigDecimal.valueOf(42));
        Assertions.assertTrue(priceQuery.test(new Price(BigDecimal.valueOf(41), "EUR")));
        Assertions.assertTrue(priceQuery.test(new Price(BigDecimal.valueOf(42), "EUR")));
        Assertions.assertFalse(priceQuery.test(new Price(BigDecimal.valueOf(43), "EUR")));
        Assertions.assertFalse(priceQuery.test(null));
        Assertions.assertFalse(priceQuery.test(new Price()));
    }

    @Test
    public void testTestWithMinAndMax() {
        PriceQuery priceQuery = new PriceQuery(BigDecimal.valueOf(10), BigDecimal.valueOf(20));
        Assertions.assertFalse(priceQuery.test(new Price(BigDecimal.valueOf(9), "EUR")));
        Assertions.assertTrue(priceQuery.test(new Price(BigDecimal.valueOf(10), "EUR")));
        Assertions.assertTrue(priceQuery.test(new Price(BigDecimal.valueOf(15), "EUR")));
        Assertions.assertTrue(priceQuery.test(new Price(BigDecimal.valueOf(20), "EUR")));
        Assertions.assertFalse(priceQuery.test(new Price(BigDecimal.valueOf(21), "EUR")));
        Assertions.assertFalse(priceQuery.test(null));
        Assertions.assertFalse(priceQuery.test(new Price()));
    }

}
