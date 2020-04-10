package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FlightQuery implements Serializable, Predicate<Flight> {

    static final long serialVersionUID = 1L;

    private List<String> originAirportCodes = null;
    private boolean enforceExactOriginAirportCodes = false;
    private List<String> destinationAirportCodes = null;
    private boolean enforceExactDestinationAirportCodes = false;
    private DateTimeQuery departureDateTime = null;
    private DateTimeQuery arrivalDateTime = null;
    private DurationQuery totalDuration = null;
    private SegmentQuery segment = null;
    private ConnectionQuery connection = null;

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("originAirportCodes", this.getOriginAirportCodes());
        toStringBuilder.append("destinationAirportCodes", this.getDestinationAirportCodes());
        toStringBuilder.append("departureDateTime", this.getDepartureDateTime());
        toStringBuilder.append("arrivalDateTime", this.getArrivalDateTime());
        toStringBuilder.append("segment", this.getSegment());
        return toStringBuilder.toString();
    }

    @Override
    public boolean test(Flight flight) {
        if (this.getDepartureDateTime() != null && !this.getDepartureDateTime().test(flight.getFirstSegment().getFirstLeg().getScheduledRoute().getDeparture().getLocalDateTime())) {
            return false;
        } else if (this.getArrivalDateTime() != null && !this.getArrivalDateTime().test(flight.getLastSegment().getLastLeg().getScheduledRoute().getArrival().getLocalDateTime())) {
            return false;
        } else if (this.getTotalDuration() != null && !this.getTotalDuration().test(flight.getTotalScheduledDuration())) {
            return false;
        } else if (this.isEnforceExactOriginAirportCodes() && this.getOriginAirportCodes() != null && !this.getOriginAirportCodes().isEmpty() && !this.getOriginAirportCodes().contains(flight.getFirstSegment().getFirstLeg().getScheduledRoute().getDeparture().getAirport().getCode())) {
            return false;
        } else if (this.isEnforceExactDestinationAirportCodes() && this.getDestinationAirportCodes() != null && !this.getDestinationAirportCodes().isEmpty() && !this.getDestinationAirportCodes().contains(flight.getLastSegment().getLastLeg().getScheduledRoute().getArrival().getAirport().getCode())) {
            return false;
        } else if (this.getSegment() != null && !this.getSegment().testAll(flight.getSegments())) {
            return false;
        } else if (this.getConnection() != null && !this.getConnection().testAll(flight.computeScheduledConnections())) {
            return false;
        } else {
            return true;
        }
    }

    public List<FlightQuery> flattenMultipleAirportsForDepartureAndArrival() {
        if (this.getOriginAirportCodes() == null || this.getOriginAirportCodes().isEmpty()) {
            throw new IllegalArgumentException("No origin airport codes specified!");
        } else if (this.getDestinationAirportCodes() == null || this.getDestinationAirportCodes().isEmpty()) {
            throw new IllegalArgumentException("No destination airport codes specified!");
        } else {
            List<FlightQuery> flightQueries = new ArrayList<>(this.getOriginAirportCodes().size() * this.getDestinationAirportCodes().size());
            for (String originAirportCode : this.getOriginAirportCodes()) {
                for (String destinationAirportCode : this.getDestinationAirportCodes()) {
                    FlightQuery flightQuery = new FlightQuery();
                    flightQuery.setArrivalDateTime(this.getArrivalDateTime());
                    flightQuery.setDepartureDateTime(this.getDepartureDateTime());
                    flightQuery.setDestinationAirportCodes(Arrays.asList(destinationAirportCode));
                    flightQuery.setOriginAirportCodes(Arrays.asList(originAirportCode));
                    flightQuery.setSegment(this.getSegment());
                    flightQueries.add(flightQuery);
                }
            }
            return Collections.unmodifiableList(flightQueries);
        }
    }

    public List<String> getOriginAirportCodes() {
        return this.originAirportCodes;
    }
    public void setOriginAirportCodes(List<String> originAirportCodes) {
        this.originAirportCodes = originAirportCodes;
    }

    public boolean isEnforceExactDestinationAirportCodes() {
        return this.enforceExactDestinationAirportCodes;
    }
    public void setEnforceExactDestinationAirportCodes(boolean enforceExactDestinationAirportCodes) {
        this.enforceExactDestinationAirportCodes = enforceExactDestinationAirportCodes;
    }

    public List<String> getDestinationAirportCodes() {
        return this.destinationAirportCodes;
    }
    public void setDestinationAirportCodes(List<String> destinationAirportCodes) {
        this.destinationAirportCodes = destinationAirportCodes;
    }

    public boolean isEnforceExactOriginAirportCodes() {
        return this.enforceExactOriginAirportCodes;
    }
    public void setEnforceExactOriginAirportCodes(boolean enforceExactOriginAirportCodes) {
        this.enforceExactOriginAirportCodes = enforceExactOriginAirportCodes;
    }

    public SegmentQuery getSegment() {
        return this.segment;
    }
    public void setSegment(SegmentQuery segment) {
        this.segment = segment;
    }

    public DateTimeQuery getDepartureDateTime() {
        return this.departureDateTime;
    }
    public void setDepartureDateTime(DateTimeQuery departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public DateTimeQuery getArrivalDateTime() {
        return this.arrivalDateTime;
    }
    public void setArrivalDateTime(DateTimeQuery arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
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

}
