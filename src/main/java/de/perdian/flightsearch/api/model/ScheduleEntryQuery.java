package de.perdian.flightsearch.api.model;

import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ScheduleEntryQuery implements Predicate<ScheduleEntry> {

    private SegmentQuery segment = null;
    private DateQuery validFrom = null;
    private DateQuery validTo = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Override
    public boolean test(ScheduleEntry entry) {
        if (this.getSegment() != null && !this.getSegment().test(entry.getSegment())) {
            return false;
        } else if (this.getValidFrom() != null && !this.getValidFrom().test(entry.getValidFrom())) {
            return false;
        } else if (this.getValidTo() != null && !this.getValidTo().test(entry.getValidTo())) {
            return false;
        } else {
            return true;
        }
    }

    public SegmentQuery getSegment() {
        return this.segment;
    }
    public void setSegment(SegmentQuery segment) {
        this.segment = segment;
    }

    public DateQuery getValidFrom() {
        return this.validFrom;
    }
    public void setValidFrom(DateQuery validFrom) {
        this.validFrom = validFrom;
    }

    public DateQuery getValidTo() {
        return this.validTo;
    }
    public void setValidTo(DateQuery validTo) {
        this.validTo = validTo;
    }

}
