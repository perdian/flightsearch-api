package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Flight implements Serializable {

    static final long serialVersionUID = 1L;

    private List<Leg> legs = Collections.emptyList();

    public Flight() {
    }

    public Flight(List<Leg> legs) {
        this.setLegs(legs);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Flight) {
            Flight thatFlight = (Flight)that;
            if (this.getLegs().size() != thatFlight.getLegs().size()) {
                return false;
            } else {
                for (int i=0; i < this.getLegs().size(); i++) {
                    if (!this.getLegs().get(i).equals(thatFlight.getLegs().get(i))) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        for (int i=0; i < this.getLegs().size(); i++) {
            hashCodeBuilder.append(i).append(this.getLegs().get(i));
        }
        return hashCodeBuilder.toHashCode();
    }

    public List<Connection> computeScheduledConnections() {
        if (this.getLegs().isEmpty()) {
            return Collections.emptyList();
        } else {
            List<Connection> connections = new ArrayList<>(this.getLegs().size() - 1);
            for (int i=1; i < this.getLegs().size(); i++) {
                connections.add(new Connection(this.getLegs().get(i-1).getScheduledRoute().getArrival(), this.getLegs().get(i).getScheduledRoute().getDeparture()));
            }
            return Collections.unmodifiableList(connections);
        }
    }

    public Duration getTotalScheduledDuration() {
        ZonedDateTime firstItemDeparture = this.getFirstLeg().getScheduledRoute().getDeparture().getZonedDateTime();
        ZonedDateTime lastItemArrival = this.getLastLeg().getScheduledRoute().getArrival().getZonedDateTime();
        return Duration.between(firstItemDeparture, lastItemArrival);
    }
    public String getTotalScheduledDurationFormatted() {
        if (this.getTotalScheduledDuration() == null) {
            return null;
        } else {
            NumberFormat numberFormat = new DecimalFormat("00");
            StringBuilder result = new StringBuilder();
            result.append(numberFormat.format(this.getTotalScheduledDuration().toMinutes() / 60));
            result.append(":").append(numberFormat.format(this.getTotalScheduledDuration().toMinutes() % 60));
            return result.toString();
        }
    }

    public Leg getFirstLeg() {
        return this.getLegs().isEmpty() ? null : this.getLegs().get(0);
    }
    public Leg getLastLeg() {
        return this.getLegs().isEmpty() ? null : this.getLegs().get(this.getLegs().size() - 1);
    }
    public List<Leg> getLegs() {
        return this.legs;
    }
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

}
