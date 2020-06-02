package de.perdian.flightsearch.api.model;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DateQueryTest {

    @Test
    public void testConstructor() {
        DateQuery dateQuery = new DateQuery(LocalDate.of(2000, 1, 2));
        Assertions.assertEquals(LocalDate.of(2000, 1, 2), dateQuery.getMinDate());
        Assertions.assertEquals(LocalDate.of(2000, 1, 2), dateQuery.getMaxDate());
    }

    @Test
    public void testConstructorWithTwoValues() {
        DateQuery dateQuery = new DateQuery(LocalDate.of(2000, 1, 2), LocalDate.of(2000, 1, 3));
        Assertions.assertEquals(LocalDate.of(2000, 1, 2), dateQuery.getMinDate());
        Assertions.assertEquals(LocalDate.of(2000, 1, 3), dateQuery.getMaxDate());
    }

    @Test
    public void testSetMinDateString() {
        DateQuery dateQuery = new DateQuery();
        dateQuery.setMinDateString("2000-01-02");
        Assertions.assertEquals(LocalDate.of(2000, 1, 2), dateQuery.getMinDate());
    }

    @Test
    public void testSetMinDateStringEmpty() {
        DateQuery dateQuery = new DateQuery();
        dateQuery.setMinDateString("");
        Assertions.assertNull(dateQuery.getMinDate());
    }

    @Test
    public void testSetMaxDateString() {
        DateQuery dateQuery = new DateQuery();
        dateQuery.setMaxDateString("2000-01-02");
        Assertions.assertEquals(LocalDate.of(2000, 1, 2), dateQuery.getMaxDate());
    }

    @Test
    public void testSetMaxDateStringEmpty() {
        DateQuery dateQuery = new DateQuery();
        dateQuery.setMaxDateString("");
        Assertions.assertNull(dateQuery.getMaxDate());
    }

    @Test
    public void testTestWithMinDate() {
        DateQuery dateQuery = new DateQuery(LocalDate.of(2000, 1, 2), null);
        Assertions.assertFalse(dateQuery.test(LocalDate.of(2000, 1, 1)));
        Assertions.assertTrue(dateQuery.test(LocalDate.of(2000, 1, 2)));
        Assertions.assertTrue(dateQuery.test(LocalDate.of(2000, 1, 3)));
    }

    @Test
    public void testTestWithMaxDate() {
        DateQuery dateQuery = new DateQuery(null, LocalDate.of(2000, 1, 2));
        Assertions.assertTrue(dateQuery.test(LocalDate.of(2000, 1, 1)));
        Assertions.assertTrue(dateQuery.test(LocalDate.of(2000, 1, 2)));
        Assertions.assertFalse(dateQuery.test(LocalDate.of(2000, 1, 3)));
    }

}
