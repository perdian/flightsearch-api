package de.perdian.flightsearch.api.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Leg implements Serializable {

    static final long serialVersionUID = 1L;

    private Route scheduledRoute = null;
    private Route actualRoute = null;
    private Aircraft aircraft = null;

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("scheduledRoute", this.getScheduledRoute());
        toStringBuilder.append("actualRoute", this.getActualRoute());
        toStringBuilder.append("aircraft", this.getAircraft());
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

    public Aircraft getAircraft() {
        return this.aircraft;
    }
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

}
