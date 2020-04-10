package de.perdian.flightsearch.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DateTimeQueryTest {

    @Test
    public void testConstructor() {
        DateTimeQuery dateTimeQuery = new DateTimeQuery(LocalDate.of(2000, 1, 2));
        Assertions.assertEquals(LocalDate.of(2000, 1, 2), dateTimeQuery.getDate());
    }

    @Test
    public void testSetDateString() {
        DateTimeQuery dateTimeQuery = new DateTimeQuery();
        dateTimeQuery.setDateString("2000-01-02");
        Assertions.assertEquals(LocalDate.of(2000, 1, 2), dateTimeQuery.getDate());
    }

    @Test
    public void testSetDateStringEmpty() {
        DateTimeQuery dateTimeQuery = new DateTimeQuery();
        dateTimeQuery.setDateString("");
        Assertions.assertNull(dateTimeQuery.getDate());
    }

    @Test
    public void testSetMinTimeString() {
        DateTimeQuery dateTimeQuery = new DateTimeQuery();
        dateTimeQuery.setMinTimeString("14:15");
        Assertions.assertEquals(LocalTime.of(14, 15), dateTimeQuery.getMinTime());
    }

    @Test
    public void testSetMinTimeStringEmpty() {
        DateTimeQuery dateTimeQuery = new DateTimeQuery();
        dateTimeQuery.setMinTimeString("");
        Assertions.assertNull(dateTimeQuery.getMinTime());
    }

    @Test
    public void testSetMaxTimeString() {
        DateTimeQuery dateTimeQuery = new DateTimeQuery();
        dateTimeQuery.setMaxTimeString("14:15");
        Assertions.assertEquals(LocalTime.of(14, 15), dateTimeQuery.getMaxTime());
    }

    @Test
    public void testSetMaxTimeStringEmpty() {
        DateTimeQuery dateTimeQuery = new DateTimeQuery();
        dateTimeQuery.setMaxTimeString("");
        Assertions.assertNull(dateTimeQuery.getMaxTime());
    }

    @Test
    public void testTestWithDate() {
        DateTimeQuery dateTimeQuery = new DateTimeQuery(LocalDate.of(2000, 1, 2));
        Assertions.assertTrue(dateTimeQuery.test(LocalDateTime.of(2000, 1, 2, 12, 13)));
        Assertions.assertFalse(dateTimeQuery.test(LocalDateTime.of(2000, 1, 3, 12, 13)));
    }

    @Test
    public void testTestWithDateAndTime() {
        DateTimeQuery dateTimeQuery = new DateTimeQuery(LocalDate.of(2000, 1, 2));
        dateTimeQuery.setMinTime(LocalTime.of(14, 0, 0));
        dateTimeQuery.setMaxTime(LocalTime.of(15, 59, 59));
        Assertions.assertFalse(dateTimeQuery.test(LocalDateTime.of(2000, 1, 2, 13, 59)));
        Assertions.assertTrue(dateTimeQuery.test(LocalDateTime.of(2000, 1, 2, 14, 0)));
        Assertions.assertTrue(dateTimeQuery.test(LocalDateTime.of(2000, 1, 2, 15, 0)));
        Assertions.assertTrue(dateTimeQuery.test(LocalDateTime.of(2000, 1, 2, 15, 59)));
        Assertions.assertFalse(dateTimeQuery.test(LocalDateTime.of(2000, 1, 2, 16, 0)));
        Assertions.assertFalse(dateTimeQuery.test(LocalDateTime.of(2000, 1, 2, 20, 0)));
    }

}
