package de.perdian.flightsearch.api.model;

import java.io.Serializable;
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

    public Duration getDuration() {
        return Duration.between(this.getArrival().getZonedDateTime(), this.getDeparture().getZonedDateTime());
    }

}
