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
    private LocalDate validFrom = null;
    private LocalDate validTo = null;
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

    public LocalDate getValidFrom() {
        return this.validFrom;
    }
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return this.validTo;
    }
    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public List<DayOfWeek> getDays() {
        return this.days;
    }
    public void setDays(List<DayOfWeek> days) {
        this.days = days;
    }

}
