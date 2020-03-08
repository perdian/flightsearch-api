package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Route implements Serializable {

    static final long serialVersionUID = 1L;

    private AirportContact departure = null;
    private AirportContact arrival = null;

    public Route() {
    }

    public Route(AirportContact departure, AirportContact arrival) {
        this.setDeparture(departure);
        this.setArrival(arrival);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("departure", this.getDeparture());
        toStringBuilder.append("arrival", this.getArrival());
        return toStringBuilder.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Route) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getDeparture(), ((Route)that).getDeparture());
            equalsBuilder.append(this.getArrival(), ((Route)that).getArrival());
            return equalsBuilder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getDeparture());
        hashCodeBuilder.append(this.getArrival());
        return hashCodeBuilder.toHashCode();
    }

    public String getDurationFormatted() {
        if (this.getDuration() == null) {
            return null;
        } else {
            NumberFormat numberFormat = new DecimalFormat("00");
            StringBuilder result = new StringBuilder();
            result.append(numberFormat.format(this.getDuration().toMinutes() / 60));
            result.append(":").append(numberFormat.format(this.getDuration().toMinutes() % 60));
            return result.toString();
        }
    }
    public Duration getDuration() {
        return Duration.between(this.getDeparture().getZonedDateTime(), this.getArrival().getZonedDateTime());
    }

    public AirportContact getDeparture() {
        return this.departure;
    }
    public void setDeparture(AirportContact departure) {
        this.departure = departure;
    }

    public AirportContact getArrival() {
        return this.arrival;
    }
    public void setArrival(AirportContact arrival) {
        this.arrival = arrival;
    }

}
