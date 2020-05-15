package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Segment implements Serializable {

    static final long serialVersionUID = 1L;

    private Airline operatingAirline = null;
    private FlightNumber operatingFlightNumber = null;
    private FlightNumber marketingFlightNumber = null;
    private List<FlightNumber> codeshareFlightNumbers = null;
    private List<Leg> legs = Collections.emptyList();

    public Segment() {
    }

    public Segment(List<Leg> legs) {
        this.setLegs(legs);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("operatingAirline", this.getOperatingFlightNumber());
        toStringBuilder.append("operatingFlightNumber", this.getOperatingFlightNumber());
        toStringBuilder.append("marketingFlightNumber", this.getMarketingFlightNumber());
        return toStringBuilder.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Segment) {
            EqualsBuilder equalsBulder = new EqualsBuilder();
            equalsBulder.append(this.getLegs(), ((Segment)that).getLegs());
            return equalsBulder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBulder = new HashCodeBuilder();
        hashCodeBulder.append(this.getLegs());
        return hashCodeBulder.toHashCode();
    }

    public Airline getOperatingAirline() {
        return this.operatingAirline;
    }
    public void setOperatingAirline(Airline operatingAirline) {
        this.operatingAirline = operatingAirline;
    }

    public FlightNumber getOperatingFlightNumber() {
        return this.operatingFlightNumber;
    }
    public void setOperatingFlightNumber(FlightNumber operatingFlightNumber) {
        this.operatingFlightNumber = operatingFlightNumber;
    }

    public FlightNumber getMarketingFlightNumber() {
        return this.marketingFlightNumber;
    }
    public void setMarketingFlightNumber(FlightNumber marketingFlightNumber) {
        this.marketingFlightNumber = marketingFlightNumber;
    }

    public List<FlightNumber> getCodeshareFlightNumbers() {
        return this.codeshareFlightNumbers;
    }
    public void setCodeshareFlightNumbers(List<FlightNumber> codeshareFlightNumbers) {
        this.codeshareFlightNumbers = codeshareFlightNumbers;
    }

    public Leg getFirstLeg() {
        return this.getLegs().isEmpty() ? null : this.getLegs().get(0);
    }
    public Leg getLastLeg() {
        return this.getLegs().isEmpty() ? null : this.getLegs().get(this.getLegs().size() - 1);
    }
    public List<Leg> getLegs() {
        return this.legs;
    }
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

}
