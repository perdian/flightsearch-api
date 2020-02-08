package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Offer implements Serializable {

    static final long serialVersionUID = 1L;

    private Trip trip = null;
    private List<Quote> quotes = Collections.emptyList();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Trip getTrip() {
        return this.trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public List<Quote> getQuotes() {
        return this.quotes;
    }
    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

}
