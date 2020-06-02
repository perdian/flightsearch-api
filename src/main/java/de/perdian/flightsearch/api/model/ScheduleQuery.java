package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ScheduleQuery implements Predicate<Schedule>, Serializable {

    static final long serialVersionUID = 1L;

    private ScheduleEntryQuery entry = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Override
    public boolean test(Schedule schedule) {
        return true;
    }

    public ScheduleEntryQuery getEntry() {
        return this.entry;
    }
    public void setEntry(ScheduleEntryQuery entry) {
        this.entry = entry;
    }

}
