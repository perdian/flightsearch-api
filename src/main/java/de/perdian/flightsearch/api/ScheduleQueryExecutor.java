package de.perdian.flightsearch.api;

import java.io.IOException;

import de.perdian.flightsearch.api.model.Schedule;
import de.perdian.flightsearch.api.model.ScheduleQuery;

public interface ScheduleQueryExecutor {

    Schedule loadSchedule(ScheduleQuery scheduleQuery) throws IOException;

}
