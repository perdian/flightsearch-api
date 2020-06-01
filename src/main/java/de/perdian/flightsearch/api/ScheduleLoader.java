package de.perdian.flightsearch.api;

import java.io.IOException;

import de.perdian.flightsearch.api.model.Schedule;

public interface ScheduleLoader {

    Schedule loadSchedule() throws IOException;

}
