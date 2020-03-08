package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Leg implements Serializable {

    static final long serialVersionUID = 1L;

    private Route scheduledRoute = null;
    private Route actualRoute = null;
    private Carrier operatingCarrier = null;
    private FlightNumber operatingFlightNumber = null;
    private FlightNumber marketingFlightNumber = null;
    private List<FlightNumber> codeshareFlightNumbers = null;
    private Aircraft aircraft = null;

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("operatingFlightNumber", this.getOperatingFlightNumber());
        toStringBuilder.append("operatingCarrier", this.getOperatingCarrier());
        toStringBuilder.append("marketingFlightNumber", this.getMarketingFlightNumber());
        toStringBuilder.append("scheduledRoute", this.getScheduledRoute());
        toStringBuilder.append("actualRoute", this.getActualRoute());
        return toStringBuilder.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Leg) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getScheduledRoute(), ((Leg)that).getScheduledRoute());
            equalsBuilder.append(this.getActualRoute(), ((Leg)that).getActualRoute());
            return equalsBuilder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getScheduledRoute());
        hashCodeBuilder.append(this.getActualRoute());
        return hashCodeBuilder.toHashCode();
    }

    public Route getScheduledRoute() {
        return this.scheduledRoute;
    }
    public void setScheduledRoute(Route scheduledRoute) {
        this.scheduledRoute = scheduledRoute;
    }

    public Route getActualRoute() {
        return this.actualRoute;
    }
    public void setActualRoute(Route actualRoute) {
        this.actualRoute = actualRoute;
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

    public FlightNumber getMarketingFlightNumber() {
        return this.marketingFlightNumber;
    }
    public void setMarketingFlightNumber(FlightNumber marketingFlightNumber) {
        this.marketingFlightNumber = marketingFlightNumber;
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
