package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Flight implements Serializable {

    static final long serialVersionUID = 1L;

    private AirportContact scheduledDeparture = null;
    private AirportContact scheduledArrival = null;
    private Duration scheduledDuration = null;
    private AirportContact actualDeparture = null;
    private AirportContact actualArrival = null;
    private Duration actualDuration = null;
    private Carrier operatingCarrier = null;
    private FlightNumber operatingFlightNumber = null;
    private List<FlightNumber> codeshareFlightNumbers = null;
    private Aircraft aircraft = null;

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("operatingFlightNumber", this.getOperatingFlightNumber());
        toStringBuilder.append("operatingCarrier", this.getOperatingCarrier());
        if (this.getActualDeparture() == null || this.getActualArrival() == null) {
            toStringBuilder.append("scheduledDeparture", this.getScheduledDeparture());
            toStringBuilder.append("scheduledArrival", this.getScheduledArrival());
            toStringBuilder.append("scheduledDuration", this.getScheduledDuration());
        } else {
            toStringBuilder.append("actualDeparture", this.getActualDeparture());
            toStringBuilder.append("actualArrival", this.getActualArrival());
            toStringBuilder.append("actualDuration", this.getActualDuration());
        }
        return toStringBuilder.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Flight) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getScheduledDeparture(), ((Flight)that).getScheduledDeparture());
            equalsBuilder.append(this.getScheduledArrival(), ((Flight)that).getScheduledArrival());
            return equalsBuilder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getScheduledDeparture());
        hashCodeBuilder.append(this.getScheduledArrival());
        return hashCodeBuilder.toHashCode();
    }

    public AirportContact getScheduledDeparture() {
        return this.scheduledDeparture;
    }
    public void setScheduledDeparture(AirportContact scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public AirportContact getScheduledArrival() {
        return this.scheduledArrival;
    }
    public void setScheduledArrival(AirportContact scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public String getScheduleDurationFormatted() {
        if (this.getScheduledDuration() == null) {
            return null;
        } else {
            NumberFormat numberFormat = new DecimalFormat("00");
            StringBuilder result = new StringBuilder();
            result.append(this.getScheduledDuration().toMinutes() / 60);
            result.append(":").append(numberFormat.format(this.getScheduledDuration().toMinutes() % 60));
            return result.toString();
        }
    }
    public Duration getScheduledDuration() {
        return this.scheduledDuration;
    }
    public void setScheduledDuration(Duration scheduledDuration) {
        this.scheduledDuration = scheduledDuration;
    }

    public String getActualDurationFormatted() {
        if (this.getActualDuration() == null) {
            return null;
        } else {
            NumberFormat numberFormat = new DecimalFormat("00");
            StringBuilder result = new StringBuilder();
            result.append(this.getActualDuration().toMinutes() / 60);
            result.append(":").append(numberFormat.format(this.getActualDuration().toMinutes() % 60));
            return result.toString();
        }
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

    public Carrier getOperatingCarrier() {
        return this.operatingCarrier;
    }
    public void setOperatingCarrier(Carrier operatingCarrier) {
        this.operatingCarrier = operatingCarrier;
    }

    public FlightNumber getOperatingFlightNumber() {
        return this.operatingFlightNumber;
    }
    public void setOperatingFlightNumber(FlightNumber operatingFlightNumber) {
        this.operatingFlightNumber = operatingFlightNumber;
    }

    public List<FlightNumber> getCodeshareFlightNumbers() {
        return this.codeshareFlightNumbers;
    }
    public void setCodeshareFlightNumbers(List<FlightNumber> codeshareFlightNumbers) {
        this.codeshareFlightNumbers = codeshareFlightNumbers;
    }

    public Aircraft getAircraft() {
        return this.aircraft;
    }
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

}
