package de.perdian.flightsearch.api.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LegQuery implements Predicate<Leg> {

    private DurationQuery duration = null;
    private Collection<String> blacklistedAirportCodes = null;

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("duration", this.getDuration());
        toStringBuilder.append("blacklistedAirportCodes", this.getBlacklistedAirportCodes());
        return toStringBuilder.toString();
    }

    public boolean testAll(List<Leg> legs) {
        for (Leg leg : legs) {
            if (!this.test(leg)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean test(Leg leg) {
        if (this.getDuration() != null && !this.getDuration().test(leg.getScheduledRoute().getDuration())) {
            return false;
        } else if (!this.testBlacklistedAirportCodes(leg)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean testBlacklistedAirportCodes(Leg leg) {
        if (this.getBlacklistedAirportCodes() != null && !this.getBlacklistedAirportCodes().isEmpty()) {
            for (String airportCode : Arrays.asList(leg.getScheduledRoute().getOrigin().getAirport().getCode(), leg.getScheduledRoute().getDestination().getAirport().getCode())) {
                if (this.getBlacklistedAirportCodes().contains(airportCode)) {
                    return false;
                }
            }
            if (leg.getActualRoute() != null) {
                for (String airportCode : Arrays.asList(leg.getActualRoute().getOrigin().getAirport().getCode(), leg.getActualRoute().getDestination().getAirport().getCode())) {
                    if (this.getBlacklistedAirportCodes().contains(airportCode)) {
                        return false;
                    }
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
