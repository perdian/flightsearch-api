package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Flight implements Serializable {

    static final long serialVersionUID = 1L;

    private List<Segment> segments = Collections.emptyList();

    public Flight() {
    }

    public Flight(List<Segment> segments) {
        this.setSegments(segments);
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
            if (this.getSegments().size() != thatFlight.getSegments().size()) {
                return false;
            } else {
                for (int i=0; i < this.getSegments().size(); i++) {
                    if (!this.getSegments().get(i).equals(thatFlight.getSegments().get(i))) {
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
        for (int i=0; i < this.getSegments().size(); i++) {
            hashCodeBuilder.append(i).append(this.getSegments().get(i));
        }
        return hashCodeBuilder.toHashCode();
    }

    public List<Connection> computeScheduledConnections() {
        List<Leg> flattenedLegs = this.getSegments().stream().flatMap(segment -> segment.getLegs().stream()).collect(Collectors.toList());
        if (flattenedLegs.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<Connection> connections = new ArrayList<>(flattenedLegs.size() - 1);
            for (int i=1; i < flattenedLegs.size(); i++) {
                connections.add(new Connection(flattenedLegs.get(i-1).getScheduledRoute().getDestination(), flattenedLegs.get(i).getScheduledRoute().getOrigin()));
            }
            return Collections.unmodifiableList(connections);
        }
    }

    public Duration getTotalScheduledDuration() {
        ZonedDateTime firstItemOrigin = this.getFirstSegment().getFirstLeg().getScheduledRoute().getOrigin().getZonedDateTime();
        ZonedDateTime lastItemDestination = this.getLastSegment().getLastLeg().getScheduledRoute().getDestination().getZonedDateTime();
        return Duration.between(firstItemOrigin, lastItemDestination);
    }

    public Segment getFirstSegment() {
        return this.getSegments().isEmpty() ? null : this.getSegments().get(0);
    }
    public Segment getLastSegment() {
        return this.getSegments().isEmpty() ? null : this.getSegments().get(this.getSegments().size() - 1);
    }
    public List<Segment> getSegments() {
        return this.segments;
    }
    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

}
