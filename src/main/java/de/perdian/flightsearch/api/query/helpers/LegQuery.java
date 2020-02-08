package de.perdian.flightsearch.api.query.helpers;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.perdian.flightsearch.api.model.Airport;
import de.perdian.flightsearch.api.model.Leg;

public class LegQuery implements Serializable, Predicate<Leg> {

    static final long serialVersionUID = 1L;

    private List<String> originAirportCodes = null;
    private List<String> destinationAirportCodes = null;
    private List<String> blacklistedAirportCodes = Arrays.asList("SVO");
    private DateTimeQuery departureDateTime = null;
    private DateTimeQuery arrivalDateTime = null;
    private DurationQuery flightDuration = null;
    private DurationQuery transferDuration = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public List<LegQuery> flattenMultipleAirportsForDepartureAndArrival() {
        List<LegQuery> legQueries = new ArrayList<>(this.getOriginAirportCodes().size() * this.getDestinationAirportCodes().size());
        for (String originAirportCode : this.getOriginAirportCodes()) {
            for (String destinationAirportCode : this.getDestinationAirportCodes()) {
                LegQuery legQuery = new LegQuery();
                legQuery.setArrivalDateTime(this.getArrivalDateTime());
                legQuery.setBlacklistedAirportCodes(this.getBlacklistedAirportCodes());
                legQuery.setDepartureDateTime(this.getDepartureDateTime());
                legQuery.setDestinationAirportCodes(Arrays.asList(destinationAirportCode));
                legQuery.setFlightDuration(this.getFlightDuration());
                legQuery.setOriginAirportCodes(Arrays.asList(originAirportCode));
                legQuery.setTransferDuration(this.getTransferDuration());
                legQueries.add(legQuery);
            }
        }
        return legQueries;
    }

    @Override
    public boolean test(Leg leg) {
        if (this.getOriginAirportCodes() != null && !this.getOriginAirportCodes().isEmpty() && !this.getOriginAirportCodes().contains(leg.getFirstItem().getFlight().getScheduledDeparture().getAirport().getCode())) {
            return false;
        } else if (this.getDestinationAirportCodes() != null && !this.getDestinationAirportCodes().isEmpty() && !this.getDestinationAirportCodes().contains(leg.getLastItem().getFlight().getScheduledArrival().getAirport().getCode())) {
            return false;
        } else if (!this.testBlacklistedAirportCodes(LegQuery.collectAirports(leg))) {
            return false;
        } else if (this.getDepartureDateTime() != null && !this.getDepartureDateTime().test(leg.getFirstItem().getFlight().getScheduledDeparture().getLocalDateTime())) {
            return false;
        } else if (this.getArrivalDateTime() != null && !this.getArrivalDateTime().test(leg.getLastItem().getFlight().getScheduledArrival().getLocalDateTime())) {
            return false;
        } else if (this.getFlightDuration() != null && !this.getFlightDuration().testAll(LegQuery.collectFlightDurations(leg))) {
            return false;
        } else if (this.getTransferDuration() != null && !this.getTransferDuration().testAll(LegQuery.collectTransferDurations(leg))) {
            return false;
        } else {
            return true;
        }
    }

    private boolean testBlacklistedAirportCodes(Collection<Airport> airports) {
        if (this.getBlacklistedAirportCodes() != null && !this.getBlacklistedAirportCodes().isEmpty()) {
            for (Airport airport : airports) {
                if (this.getBlacklistedAirportCodes().contains(airport.getCode())) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<Duration> collectFlightDurations(Leg leg) {
        return leg.getItems().stream()
            .map(legItem -> legItem.getFlight().getScheduledDuration())
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private static List<Duration> collectTransferDurations(Leg leg) {
        return leg.getItems().stream()
            .map(legItem -> legItem.getConnection())
            .filter(Objects::nonNull)
            .map(connection -> connection.getDuration())
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private static Set<Airport> collectAirports(Leg leg) {
        return leg.getItems().stream()
            .map(legItem -> legItem.getFlight())
            .flatMap(flight -> Stream.of(flight.getScheduledDeparture().getAirport(), flight.getScheduledArrival().getAirport()))
            .collect(Collectors.toSet());
    }

    public List<String> getOriginAirportCodes() {
        return this.originAirportCodes;
    }
    public void setOriginAirportCodes(List<String> originAirportCodes) {
        this.originAirportCodes = originAirportCodes;
    }

    public List<String> getDestinationAirportCodes() {
        return this.destinationAirportCodes;
    }
    public void setDestinationAirportCodes(List<String> destinationAirportCodes) {
        this.destinationAirportCodes = destinationAirportCodes;
    }

    public List<String> getBlacklistedAirportCodes() {
        return this.blacklistedAirportCodes;
    }
    public void setBlacklistedAirportCodes(List<String> blacklistedAirportCodes) {
        this.blacklistedAirportCodes = blacklistedAirportCodes;
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

    public DurationQuery getFlightDuration() {
        return this.flightDuration;
    }
    public void setFlightDuration(DurationQuery flightDuration) {
        this.flightDuration = flightDuration;
    }

    public DurationQuery getTransferDuration() {
        return this.transferDuration;
    }
    public void setTransferDuration(DurationQuery transferDuration) {
        this.transferDuration = transferDuration;
    }

}
