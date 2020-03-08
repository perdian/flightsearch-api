package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Trip implements Serializable {

    static final long serialVersionUID = 1L;

    private List<Flight> flights = Collections.emptyList();

    public Trip() {
    }

    public Trip(List<Flight> flights) {
        this.setFlights(flights);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Trip) {
            Trip thatTrip = (Trip)that;
            if (this.getFlights().size() != thatTrip.getFlights().size()) {
                return false;
            } else {
                for (int i=0; i < this.getFlights().size(); i++) {
                    if (!this.getFlights().get(i).equals(thatTrip.getFlights().get(i))) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        for (int i=0; i < this.getFlights().size(); i++) {
            hashCodeBuilder.append(i).append(this.getFlights().get(i));
        }
        return hashCodeBuilder.toHashCode();
    }

    public List<Flight> getFlights() {
        return this.flights;
    }
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

}
