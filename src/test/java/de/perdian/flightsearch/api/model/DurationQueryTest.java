package de.perdian.flightsearch.api.model;

import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DurationQueryTest {

    @Test
    public void testConstructor() {
        DurationQuery durationQuery = new DurationQuery(Duration.ofSeconds(42), Duration.ofSeconds(43));
        Assertions.assertEquals(Duration.ofSeconds(42), durationQuery.getMin());
        Assertions.assertEquals(Duration.ofSeconds(43), durationQuery.getMax());
    }

    @Test
    public void testTest() {
        DurationQuery durationQuery = new DurationQuery(Duration.ofSeconds(10), Duration.ofSeconds(20));
        Assertions.assertFalse(durationQuery.test(Duration.ofSeconds(9)));
        Assertions.assertTrue(durationQuery.test(Duration.ofSeconds(10)));
        Assertions.assertTrue(durationQuery.test(Duration.ofSeconds(15)));
        Assertions.assertTrue(durationQuery.test(Duration.ofSeconds(20)));
        Assertions.assertFalse(durationQuery.test(Duration.ofSeconds(21)));
    }

    @Test
    public void testTestAll() {
        DurationQuery durationQuery = new DurationQuery(Duration.ofSeconds(10), Duration.ofSeconds(20));
        Assertions.assertTrue(durationQuery.testAll(Arrays.asList(Duration.ofSeconds(10), Duration.ofSeconds(15))));
        Assertions.assertFalse(durationQuery.testAll(Arrays.asList(Duration.ofSeconds(9), Duration.ofSeconds(20), Duration.ofSeconds(25))));
    }

}
