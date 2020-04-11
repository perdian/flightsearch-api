package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.time.Duration;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Route implements Serializable {

    static final long serialVersionUID = 1L;

    private AirportContact origin = null;
    private AirportContact destination = null;

    public Route() {
    }

    public Route(AirportContact origin, AirportContact destination) {
        this.setOrigin(origin);
        this.setDestination(destination);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("origin", this.getOrigin());
        toStringBuilder.append("destination", this.getDestination());
        return toStringBuilder.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Route) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getOrigin(), ((Route)that).getOrigin());
            equalsBuilder.append(this.getDestination(), ((Route)that).getDestination());
            return equalsBuilder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getOrigin());
        hashCodeBuilder.append(this.getDestination());
        return hashCodeBuilder.toHashCode();
    }

    public Duration getDuration() {
        return Duration.between(this.getOrigin().getZonedDateTime(), this.getDestination().getZonedDateTime());
    }

    public AirportContact getOrigin() {
        return this.origin;
    }
    public void setOrigin(AirportContact origin) {
        this.origin = origin;
    }

    public AirportContact getDestination() {
        return this.destination;
    }
    public void setDestination(AirportContact destination) {
        this.destination = destination;
    }

}
