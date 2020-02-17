package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LegItem implements Serializable {

    static final long serialVersionUID = 1L;

    private Flight flight = null;
    private Connection connection = null;
    private CabinClass cabinClass = null;
    private String seatNumber = null;
    private FlightNumber marketingFlightNumber = null;
    private Carrier marketingCarrier = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof LegItem) {
            return Objects.equals(this.getFlight(), ((LegItem)that).getFlight());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getFlight() == null ? 0 : this.getFlight().hashCode();
    }

    public String getSingleLine() {
        NumberFormat numberFormat = new DecimalFormat("00");
        StringBuilder line = new StringBuilder();
        if (this.getMarketingFlightNumber() != null) {
            line.append(" ").append(this.getMarketingFlightNumber()).append(" | ");;
        } else if (this.getFlight().getOperatingFlightNumber() != null) {
            line.append(" ").append(this.getFlight().getOperatingFlightNumber()).append(" | ");
        }
        line.append(this.getFlight().getScheduledDeparture().getAirport().getCode()).append(" - ").append(this.getFlight().getScheduledArrival().getAirport().getCode());
        if (this.getFlight().getScheduledDeparture() != null) {
            line.append(" | ").append(numberFormat.format(this.getFlight().getScheduledDuration().toHours())).append("h ").append(numberFormat.format(this.getFlight().getScheduledDuration().toMinutes() % 60)).append("min");
        }
        if (this.getFlight().getOperatingCarrier() != null && this.getFlight().getOperatingCarrier().getCode() != null && this.getMarketingCarrier() != null && !Objects.equals(this.getFlight().getOperatingCarrier().getCode(),  this.getMarketingCarrier().getCode())) {
            line.append(" (by ").append(this.getFlight().getOperatingCarrier().getCode()).append(")");
        } else if (this.getMarketingCarrier() != null && StringUtils.isEmpty(this.getMarketingCarrier().getCode()) && StringUtils.isNotEmpty(this.getMarketingCarrier().getName())) {
            line.append(" (").append(this.getMarketingCarrier().getName()).append(")");
        }
        return line.toString();
    }

    public Flight getFlight() {
        return this.flight;
    }
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Connection getConnection() {
        return this.connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public CabinClass getCabinClass() {
        return this.cabinClass;
    }
    public void setCabinClass(CabinClass cabinClass) {
        this.cabinClass = cabinClass;
    }

    public String getSeatNumber() {
        return this.seatNumber;
    }
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public FlightNumber getMarketingFlightNumber() {
        return this.marketingFlightNumber;
    }
    public void setMarketingFlightNumber(FlightNumber marketingFlightNumber) {
        this.marketingFlightNumber = marketingFlightNumber;
    }

    public Carrier getMarketingCarrier() {
        return this.marketingCarrier;
    }
    public void setMarketingCarrier(Carrier marketingCarrier) {
        this.marketingCarrier = marketingCarrier;
    }

}
