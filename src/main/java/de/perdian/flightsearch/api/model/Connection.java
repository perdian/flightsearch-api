package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Connection implements Serializable {

    static final long serialVersionUID = 1L;

    private Airport arrivalAirport = null;
    private Airport departureAirport = null;
    private Duration duration = null;

    public Connection() {
    }

    public Connection(AirportContact arrivalAirportContact, AirportContact departureAirportContact) {
        this.setArrivalAirport(arrivalAirportContact.getAirport());
        this.setDepartureAirport(departureAirportContact.getAirport());
        this.setDuration(Duration.between(arrivalAirportContact.getZonedDateTime(), departureAirportContact.getZonedDateTime()));
    }

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

    public String getDurationFormatted() {
        if (this.getDuration() == null) {
            return null;
        } else {
            NumberFormat numberFormat = new DecimalFormat("00");
            StringBuilder result = new StringBuilder();
            result.append(this.getDuration().toMinutes() / 60);
            result.append(":").append(numberFormat.format(this.getDuration().toMinutes() % 60));
            return result.toString();
        }
    }
    public Duration getDuration() {
        return this.duration;
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

}
