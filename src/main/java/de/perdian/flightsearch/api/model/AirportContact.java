package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AirportContact implements Serializable {

    static final long serialVersionUID = 1L;

    private Airport airport = null;
    private LocalDateTime localDateTime = null;
    private String terminal = null;
    private String gate = null;

    @Override
    public String toString() {
        return this.getAirport().getCode() + "@" + this.getLocalDateTime();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof AirportContact) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getAirport(), ((AirportContact)that).getAirport());
            equalsBuilder.append(this.getLocalDateTime(), ((AirportContact)that).getLocalDateTime());
            return equalsBuilder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getAirport());
        hashCodeBuilder.append(this.getLocalDateTime());
        return hashCodeBuilder.toHashCode();
    }

    public Airport getAirport() {
        return this.airport;
    }
    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getTerminal() {
        return this.terminal;
    }
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getGate() {
        return this.gate;
    }
    public void setGate(String gate) {
        this.gate = gate;
    }

}
