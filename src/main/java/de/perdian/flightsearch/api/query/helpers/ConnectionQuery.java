package de.perdian.flightsearch.api.query.helpers;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.perdian.flightsearch.api.model.Connection;

public class ConnectionQuery implements Predicate<Connection> {

    private DurationQuery duration = null;
    private Collection<String> blacklistedAirportCodes = null;

    public ConnectionQuery() {
    }

    public ConnectionQuery(DurationQuery duration) {
        this.setDuration(duration);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Override
    public boolean test(Connection connection) {
        if (this.getDuration() != null && !this.getDuration().test(connection.getDuration())) {
            return false;
        } else if (!this.testBlacklistedAirportCodes(connection)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean testBlacklistedAirportCodes(Connection connection) {
        if (this.getBlacklistedAirportCodes() != null && !this.getBlacklistedAirportCodes().isEmpty()) {
            for (String airportCode : Arrays.asList(connection.getArrival().getAirport().getCode(), connection.getDeparture().getAirport().getCode())) {
                if (this.getBlacklistedAirportCodes().contains(airportCode)) {
                    return false;
                }
            }
        }
        return true;
    }

    public DurationQuery getDuration() {
        return this.duration;
    }
    public void setDuration(DurationQuery duration) {
        this.duration = duration;
    }

    public Collection<String> getBlacklistedAirportCodes() {
        return this.blacklistedAirportCodes;
    }
    public void setBlacklistedAirportCodes(Collection<String> blacklistedAirportCodes) {
        this.blacklistedAirportCodes = blacklistedAirportCodes;
    }

}
