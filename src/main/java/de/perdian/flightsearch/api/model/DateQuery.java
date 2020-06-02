package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DateQuery implements Serializable, Predicate<LocalDate> {

    static final long serialVersionUID = 1;

    private LocalDate minDate = null;
    private LocalDate maxDate = null;

    public DateQuery() {
    }

    public DateQuery(LocalDate date) {
        this.setMinDate(date);
        this.setMaxDate(date);
    }

    public DateQuery(LocalDate minDate, LocalDate maxDate) {
        this.setMinDate(minDate);
        this.setMaxDate(maxDate);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("minDate", this.getMinDate());
        toStringBuilder.append("maxDate", this.getMaxDate());
        return toStringBuilder.toString();
    }

    @Override
    public boolean test(LocalDate reference) {
        if (this.getMinDate() != null && (reference == null || (!this.getMinDate().equals(reference) && !this.getMinDate().isBefore(reference)))) {
            return false;
        } else if (this.getMaxDate() != null && (reference == null || (!this.getMaxDate().equals(reference) && !this.getMaxDate().isAfter(reference)))) {
            return false;
        } else {
            return true;
        }
    }

    public LocalDate getMinDate() {
        return this.minDate;
    }
    public void setMinDate(LocalDate minDate) {
        this.minDate = minDate;
    }
    public void setMinDateString(String dateString) {
        this.setMinDate(StringUtils.isEmpty(dateString) ? null : LocalDate.parse(dateString));
    }

    public LocalDate getMaxDate() {
        return this.maxDate;
    }
    public void setMaxDate(LocalDate maxDate) {
        this.maxDate = maxDate;
    }
    public void setMaxDateString(String dateString) {
        this.setMaxDate(StringUtils.isEmpty(dateString) ? null : LocalDate.parse(dateString));
    }

}
