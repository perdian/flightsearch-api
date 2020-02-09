package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Connection implements Serializable {

    static final long serialVersionUID = 1L;

    private Airport arrivalAirport = null;
    private Airport departureAirport = null;
    private Duration duration = null;

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        if (Objects.equals(this.getArrivalAirport(), this.getDepartureAirport())) {
            toStringBuilder.append("airportCode", this.getDepartureAirport().getCode());
        } else {
            toStringBuilder.append("arrivalAirportCode", this.getArrivalAirport() == null ? null : this.getArrivalAirport().getCode());
            toStringBuilder.append("departureAirportCode", this.getDepartureAirport() == null ? null : this.getDepartureAirport().getCode());
        }
        toStringBuilder.append("duration", this.getDuration());
        return toStringBuilder.toString();
    }

    public Airport getArrivalAirport() {
        return this.arrivalAirport;
    }
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Airport getDepartureAirport() {
        return this.departureAirport;
    }
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Duration getDuration() {
        return this.duration;
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

}
