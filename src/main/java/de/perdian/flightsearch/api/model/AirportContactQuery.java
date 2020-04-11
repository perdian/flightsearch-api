package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AirportContactQuery implements Serializable, Cloneable, Predicate<AirportContact> {

    static final long serialVersionUID = 1L;

    private List<String> airportCodes = Collections.emptyList();
    private boolean enforceExactAirportCodes = false;
    private DateTimeQuery dateTime = null;

    public AirportContactQuery() {
    }

    public AirportContactQuery(List<String> aiportCodes, boolean enforceExactAirportCodes) {
        this.setAirportCodes(aiportCodes);
        this.setEnforceExactAirportCodes(enforceExactAirportCodes);
    }

    @Override
    public AirportContactQuery clone() {
        try {
            return (AirportContactQuery)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cannot clone class: " + this.getClass().getName(), e);
        }
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("airportCodes", this.getAirportCodes());
        toStringBuilder.append("dateTime", this.getDateTime());
        return toStringBuilder.toString();
    }

    @Override
    public boolean test(AirportContact airportContact) {
        if (this.isEnforceExactAirportCodes() && this.getAirportCodes() != null && !this.getAirportCodes().isEmpty() && !this.getAirportCodes().contains(airportContact.getAirport().getCode())) {
            return false;
        } else if (this.getDateTime() != null && !this.getDateTime().test(airportContact.getLocalDateTime())) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> getAirportCodes() {
        return this.airportCodes;
    }
    public void setAirportCodes(List<String> airportCodes) {
        this.airportCodes = airportCodes;
    }

    public boolean isEnforceExactAirportCodes() {
        return this.enforceExactAirportCodes;
    }
    public void setEnforceExactAirportCodes(boolean enforceExactAirportCodes) {
        this.enforceExactAirportCodes = enforceExactAirportCodes;
    }

    public DateTimeQuery getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(DateTimeQuery dateTime) {
        this.dateTime = dateTime;
    }

}
