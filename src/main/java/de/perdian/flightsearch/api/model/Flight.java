package de.perdian.flightsearch.api.model;

import java.io.Serializable;
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
    private Carrier operatingCarrier = null;
    private FlightNumber operatingFlightNumber = null;
    private List<FlightNumber> codeshareFlightNumbers = null;
    private FlightHistory flightHistory = null;

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("operatingFlightNumber", this.getOperatingFlightNumber());
        toStringBuilder.append("operatingCarrier", this.getOperatingCarrier());
        if (this.getFlightHistory() == null || this.getFlightHistory().getActualDeparture() == null || this.getFlightHistory().getActualArrival() == null) {
            toStringBuilder.append("scheduledDeparture", this.getScheduledDeparture());
            toStringBuilder.append("scheduledArrival", this.getScheduledArrival());
            toStringBuilder.append("scheduledDuration", this.getScheduledDuration());
        } else {
            toStringBuilder.append("actualDeparture", this.getFlightHistory().getActualDeparture());
            toStringBuilder.append("actualArrival", this.getFlightHistory().getActualArrival());
            toStringBuilder.append("actualDuration", this.getFlightHistory().getActualDuration());
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

    public Duration getScheduledDuration() {
        return this.scheduledDuration;
    }
    public void setScheduledDuration(Duration scheduledDuration) {
        this.scheduledDuration = scheduledDuration;
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

    public FlightHistory getFlightHistory() {
        return this.flightHistory;
    }
    public void setFlightHistory(FlightHistory flightHistory) {
        this.flightHistory = flightHistory;
    }

}
