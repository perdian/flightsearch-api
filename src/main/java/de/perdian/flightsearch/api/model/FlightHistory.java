package de.perdian.flightsearch.api.model;

import java.time.Duration;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FlightHistory {

    private AirportContact actualDeparture = null;
    private AirportContact actualArrival = null;
    private Duration actualDuration = null;
    private Aircraft aircraft = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public AirportContact getActualDeparture() {
        return this.actualDeparture;
    }
    public void setActualDeparture(AirportContact actualDeparture) {
        this.actualDeparture = actualDeparture;
    }

    public AirportContact getActualArrival() {
        return this.actualArrival;
    }
    public void setActualArrival(AirportContact actualArrival) {
        this.actualArrival = actualArrival;
    }

    public Duration getActualDuration() {
        return this.actualDuration;
    }
    public void setActualDuration(Duration actualDuration) {
        this.actualDuration = actualDuration;
    }

    public Aircraft getAircraft() {
        return this.aircraft;
    }
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

}
