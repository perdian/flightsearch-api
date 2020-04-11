package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Connection implements Serializable {

    static final long serialVersionUID = 1L;

    private AirportContact destination = null;
    private AirportContact origin = null;

    public Connection() {
    }

    public Connection(AirportContact destinationAirportContact, AirportContact originAirportContact) {
        this.setDestination(destinationAirportContact);
        this.setOrigin(originAirportContact);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        if (Objects.equals(this.getDestination().getAirport(), this.getOrigin().getAirport())) {
            toStringBuilder.append("airportCode", this.getOrigin().getAirport().getCode());
        } else {
            toStringBuilder.append("destinationAirportCode", this.getDestination().getAirport().getCode());
            toStringBuilder.append("originAirportCode", this.getOrigin().getAirport().getCode());
        }
        toStringBuilder.append("duration", this.getDuration());
        return toStringBuilder.toString();
    }

    public AirportContact getDestination() {
        return this.destination;
    }
    public void setDestination(AirportContact destination) {
        this.destination = destination;
    }

    public AirportContact getOrigin() {
        return this.origin;
    }
    public void setOrigin(AirportContact origin) {
        this.origin = origin;
    }

    public Duration getDuration() {
        return Duration.between(this.getDestination().getZonedDateTime(), this.getOrigin().getZonedDateTime());
    }

}
