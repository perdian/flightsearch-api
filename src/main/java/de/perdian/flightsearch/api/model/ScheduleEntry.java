package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ScheduleEntry implements Serializable {

    static final long serialVersionUID = 1L;

    private Segment segment = null;
    private LocalDate startDate = null;
    private LocalDate endDate = null;
    private List<DayOfWeek> days = Collections.emptyList();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public Segment getSegment() {
        return this.segment;
    }
    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<DayOfWeek> getDays() {
        return this.days;
    }
    public void setDays(List<DayOfWeek> days) {
        this.days = days;
    }

}
