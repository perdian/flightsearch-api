package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AirportContact implements Serializable {

    static final long serialVersionUID = 1L;

    private Airport airport = null;
    private LocalDate localDate = null;
    private LocalTime localTime = null;
    private String terminal = null;
    private String gate = null;

    public AirportContact() {
    }

    public AirportContact(Airport airport, LocalDate localDate, LocalTime localTime) {
        this.setAirport(airport);
        this.setLocalDate(localDate);
        this.setLocalTime(localTime);
    }

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
        return this.getLocalDate() == null || this.getLocalTime() == null ? null : LocalDateTime.of(this.getLocalDate(), this.getLocalTime());
    }
    public ZonedDateTime getZonedDateTime() {
        return this.getLocalDate() == null || this.getLocalTime() == null ? null : LocalDateTime.of(this.getLocalDate(), this.getLocalTime()).atZone(this.getAirport().getTimezoneId());
    }

    public LocalDate getLocalDate() {
        return this.localDate;
    }
    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return this.localTime;
    }
    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
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
