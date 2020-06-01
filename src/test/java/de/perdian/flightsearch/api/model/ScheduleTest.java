package de.perdian.flightsearch.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScheduleTest {

    @Test
    public void testConstructor() {
        Schedule schedule = new Schedule();
        Assertions.assertEquals(0, schedule.getEntries().size());
        Assertions.assertNull(schedule.getSource());
    }

}
