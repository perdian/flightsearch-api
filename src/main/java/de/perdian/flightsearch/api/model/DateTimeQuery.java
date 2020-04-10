package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DateTimeQuery implements Serializable, Predicate<LocalDateTime> {

    static final long serialVersionUID = 1;

    private LocalDate date = null;
    private LocalTime minTime = null;
    private LocalTime maxTime = null;

    public DateTimeQuery() {
    }

    public DateTimeQuery(LocalDate date) {
        this.setDate(date);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("date", this.getDate());
        if (this.getMinTime() != null) {
            toStringBuilder.append("minTime", this.getMinTime());
        }
        if (this.getMaxTime() != null) {
            toStringBuilder.append("maxTime", this.getMinTime());
        }
        return toStringBuilder.toString();
    }

    @Override
    public boolean test(LocalDateTime reference) {
        if (this.getDate() != null && !this.getDate().equals(reference.toLocalDate())) {
            return false;
        } else if (this.getMinTime() != null && this.getMinTime().isAfter(reference.toLocalTime())) {
            return false;
        } else if (this.getMaxTime() != null && this.getMaxTime().isBefore(reference.toLocalTime())) {
            return false;
        } else {
            return true;
        }
    }

    public LocalDate getDate() {
        return this.date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setDateString(String dateString) {
        this.setDate(StringUtils.isEmpty(dateString) ? null : LocalDate.parse(dateString));
    }

    public LocalTime getMinTime() {
        return this.minTime;
    }
    public void setMinTime(LocalTime minTime) {
        this.minTime = minTime;
    }
    public void setMinTimeString(String minTimeString) {
        this.setMinTime(StringUtils.isEmpty(minTimeString) ? null : LocalTime.parse(minTimeString, DateTimeFormatter.ofPattern("HH:mm", Locale.GERMANY)));
    }

    public LocalTime getMaxTime() {
        return this.maxTime;
    }
    public void setMaxTime(LocalTime maxTime) {
        this.maxTime = maxTime;
    }
    public void setMaxTimeString(String minTimeString) {
        this.setMaxTime(StringUtils.isEmpty(minTimeString) ? null : LocalTime.parse(minTimeString, DateTimeFormatter.ofPattern("HH:mm", Locale.GERMANY)));
    }

}
