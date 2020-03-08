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

    private AirportContact arrival = null;
    private AirportContact departure = null;

    public Connection() {
    }

    public Connection(AirportContact arrivalAirportContact, AirportContact departureAirportContact) {
        this.setArrival(arrivalAirportContact);
        this.setDeparture(departureAirportContact);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        if (Objects.equals(this.getArrival().getAirport(), this.getDeparture().getAirport())) {
            toStringBuilder.append("airportCode", this.getDeparture().getAirport().getCode());
        } else {
            toStringBuilder.append("arrivalAirportCode", this.getArrival().getAirport().getCode());
            toStringBuilder.append("departureAirportCode", this.getDeparture().getAirport().getCode());
        }
        toStringBuilder.append("duration", this.getDuration());
        return toStringBuilder.toString();
    }

    public AirportContact getArrival() {
        return this.arrival;
    }
    public void setArrival(AirportContact arrival) {
        this.arrival = arrival;
    }

    public AirportContact getDeparture() {
        return this.departure;
    }
    public void setDeparture(AirportContact departure) {
        this.departure = departure;
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
        return Duration.between(this.getArrival().getZonedDateTime(), this.getDeparture().getZonedDateTime());
    }

}
