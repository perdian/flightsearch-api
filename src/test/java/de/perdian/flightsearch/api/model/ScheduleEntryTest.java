package de.perdian.flightsearch.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScheduleEntryTest {

    @Test
    public void testConstructor() {
        ScheduleEntry scheduleEntry = new ScheduleEntry();
        Assertions.assertEquals(0, scheduleEntry.getDays().size());
    }

}
