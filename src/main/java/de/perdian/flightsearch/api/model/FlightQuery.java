package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FlightQuery implements Serializable, Cloneable, Predicate<Flight> {

    static final long serialVersionUID = 1L;

    private AirportContactQuery originAirportContact = null;
    private AirportContactQuery destinationAirportContact = null;
    private DurationQuery totalDuration = null;
    private SegmentQuery segment = null;
    private ConnectionQuery connection = null;
    private Integer maxConnections = null;

    @Override
    public FlightQuery clone() {
        try {
            return (FlightQuery)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cannot clone class: " + this.getClass().getName(), e);
        }
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("originAirportContact", this.getOriginAirportContact());
        toStringBuilder.append("destinationAirportContact", this.getDestinationAirportContact());
        toStringBuilder.append("segment", this.getSegment());
        return toStringBuilder.toString();
    }

    @Override
    public boolean test(Flight flight) {
        if (this.getOriginAirportContact() != null && !this.getOriginAirportContact().test(flight.getFirstSegment().getFirstLeg().getScheduledRoute().getOrigin())) {
            return false;
        } else if (this.getDestinationAirportContact() != null && !this.getDestinationAirportContact().test(flight.getLastSegment().getLastLeg().getScheduledRoute().getDestination())) {
            return false;
        } else if (this.getTotalDuration() != null && !this.getTotalDuration().test(flight.getTotalScheduledDuration())) {
            return false;
        } else if (this.getSegment() != null && !this.getSegment().testAll(flight.getSegments())) {
            return false;
        } else if (!this.testConnections(flight.computeScheduledConnections())) {
            return false;
        } else {
            return true;
        }
    }

    private boolean testConnections(List<Connection> connections) {
        if (this.getMaxConnections() != null && this.getMaxConnections().intValue() < connections.size()) {
            return false;
        } else if (this.getConnection() != null && !this.getConnection().testAll(connections)) {
            return false;
        } else {
            return true;
        }
    }

    public List<FlightQuery> flattenMultipleAirportsForOriginAndDestination() {
        if (this.getOriginAirportContact() == null || this.getOriginAirportContact().getAirportCodes() == null || this.getOriginAirportContact().getAirportCodes().isEmpty()) {
            throw new IllegalArgumentException("No origin airport codes specified!");
        } else if (this.getDestinationAirportContact() == null || this.getDestinationAirportContact().getAirportCodes() == null || this.getDestinationAirportContact().getAirportCodes().isEmpty()) {
            throw new IllegalArgumentException("No destination airport codes specified!");
        } else if (this.getOriginAirportContact().getAirportCodes().size() == 1 && this.getDestinationAirportContact().getAirportCodes().size() == 1) {
            return Arrays.asList(this);
        } else {
            List<FlightQuery> flightQueries = new ArrayList<>(this.getOriginAirportContact().getAirportCodes().size() * this.getDestinationAirportContact().getAirportCodes().size());
            for (String originAirportCode : this.getOriginAirportContact().getAirportCodes()) {
                for (String destinationAirportCode : this.getDestinationAirportContact().getAirportCodes()) {
                    AirportContactQuery newOriginAirportContactQuery = this.getOriginAirportContact().clone();
                    newOriginAirportContactQuery.setAirportCodes(Arrays.asList(originAirportCode));
                    AirportContactQuery newDestinationAirportContactQuery = this.getOriginAirportContact().clone();
                    newDestinationAirportContactQuery.setAirportCodes(Arrays.asList(destinationAirportCode));
                    FlightQuery flightQuery = this.clone();
                    flightQuery.setOriginAirportContact(newOriginAirportContactQuery);
                    flightQuery.setDestinationAirportContact(newDestinationAirportContactQuery);
                    flightQueries.add(flightQuery);
                }
            }
            return Collections.unmodifiableList(flightQueries);
        }
    }

    public AirportContactQuery getOriginAirportContact() {
        return this.originAirportContact;
    }
    public void setOriginAirportContact(AirportContactQuery originAirportContact) {
        this.originAirportContact = originAirportContact;
    }

    public AirportContactQuery getDestinationAirportContact() {
        return this.destinationAirportContact;
    }
    public void setDestinationAirportContact(AirportContactQuery destinationAirportContact) {
        this.destinationAirportContact = destinationAirportContact;
    }

    public SegmentQuery getSegment() {
        return this.segment;
    }
    public void setSegment(SegmentQuery segment) {
        this.segment = segment;
    }

    public DurationQuery getTotalDuration() {
        return this.totalDuration;
    }
    public void setTotalDuration(DurationQuery totalDuration) {
        this.totalDuration = totalDuration;
    }

    public ConnectionQuery getConnection() {
        return this.connection;
    }
    public void setConnection(ConnectionQuery connection) {
        this.connection = connection;
    }

    public Integer getMaxConnections() {
        return this.maxConnections;
    }
    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

}
