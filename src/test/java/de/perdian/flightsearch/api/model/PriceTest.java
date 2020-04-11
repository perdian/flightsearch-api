package de.perdian.flightsearch.api.model;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void testConstructor() {
        Price price = new Price(BigDecimal.valueOf(42), "EUR");
        Assertions.assertEquals(BigDecimal.valueOf(42), price.getValue());
        Assertions.assertEquals("EUR", price.getCurrencyCode());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        Price p1a = new Price(BigDecimal.valueOf(42), "EUR");
        Price p1b = new Price(BigDecimal.valueOf(42), "EUR");
        Price p2 = new Price(BigDecimal.valueOf(100), "EUR");
        Assertions.assertEquals(p1a, p1a);
        Assertions.assertEquals(p1a, p1b);
        Assertions.assertEquals(p1b, p1a);
        Assertions.assertNotEquals(p1a, p2);
        Assertions.assertNotEquals(p2, p1a);
        Assertions.assertFalse(p1a.equals("INVALID"));
        Assertions.assertFalse(p1a.equals(null));
    }

    @Test
    public void testCompareByValue() {
        Price p1 = new Price(BigDecimal.valueOf(42), "EUR");
        Price p2 = new Price(BigDecimal.valueOf(100), "EUR");
        Assertions.assertEquals(-1, Price.compareByValue(p1, p2));
        Assertions.assertEquals(1, Price.compareByValue(p2, p1));
        Assertions.assertEquals(0, Price.compareByValue(p1, p1));
    }

}
