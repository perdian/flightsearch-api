package de.perdian.flightsearch.api.query.helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.perdian.flightsearch.api.model.Connection;
import de.perdian.flightsearch.api.model.Flight;
import de.perdian.flightsearch.api.model.Leg;

public class FlightQuery implements Serializable, Predicate<Flight> {

    static final long serialVersionUID = 1L;

    private List<String> originAirportCodes = null;
    private boolean enforceExactOriginAirportCodes = false;
    private List<String> destinationAirportCodes = null;
    private boolean enforceExactDestinationAirportCodes = false;
    private DateTimeQuery departureDateTime = null;
    private DateTimeQuery arrivalDateTime = null;
    private DurationQuery totalDuration = null;
    private LegQuery leg = null;
    private ConnectionQuery connection = null;

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("originAirportCodes", this.getOriginAirportCodes());
        toStringBuilder.append("destinationAirportCodes", this.getDestinationAirportCodes());
        toStringBuilder.append("departureDateTime", this.getDepartureDateTime());
        toStringBuilder.append("arrivalDateTime", this.getArrivalDateTime());
        toStringBuilder.append("leg", this.getLeg());
        return toStringBuilder.toString();
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
                    flightQuery.setLeg(this.getLeg());
                    flightQueries.add(flightQuery);
                }
            }
            return Collections.unmodifiableList(flightQueries);
        }
    }

    @Override
    public boolean test(Flight flight) {
        if (this.getDepartureDateTime() != null && !this.getDepartureDateTime().test(flight.getFirstLeg().getScheduledRoute().getDeparture().getLocalDateTime())) {
            return false;
        } else if (this.getArrivalDateTime() != null && !this.getArrivalDateTime().test(flight.getLastLeg().getScheduledRoute().getArrival().getLocalDateTime())) {
            return false;
        } else if (this.getTotalDuration() != null && !this.getTotalDuration().test(flight.getTotalScheduledDuration())) {
            return false;
        } else if (this.isEnforceExactOriginAirportCodes() && this.getOriginAirportCodes() != null && !this.getOriginAirportCodes().isEmpty() && !this.getOriginAirportCodes().contains(flight.getFirstLeg().getScheduledRoute().getDeparture().getAirport().getCode())) {
            return false;
        } else if (this.isEnforceExactDestinationAirportCodes() && this.getDestinationAirportCodes() != null && !this.getDestinationAirportCodes().isEmpty() && !this.getDestinationAirportCodes().contains(flight.getLastLeg().getScheduledRoute().getArrival().getAirport().getCode())) {
            return false;
        } else if (!this.testLegs(flight)) {
            return false;
        } else if (!this.testConnections(flight)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean testLegs(Flight flight) {
        if (this.getLeg() != null) {
            for (Leg leg : flight.getLegs()) {
                if (!this.getLeg().test(leg)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean testConnections(Flight flight) {
        if (this.getConnection() != null) {
            for (Connection connection : flight.computeScheduledConnections()) {
                if (!this.getConnection().test(connection)) {
                    return false;
                }
            }
        }
        return true;
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

    public LegQuery getLeg() {
        return this.leg;
    }
    public void setLeg(LegQuery leg) {
        this.leg = leg;
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
