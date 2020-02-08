package de.perdian.flightsearch.api.model;

import java.time.Instant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Quote {

    private String provider = null;
    private Instant retrievalDate = null;
    private Price price = null;
    private Links links = null;
    private String details = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static int compareByPrice(Quote q1, Quote q2) {
        return q1.getPrice().compareTo(q2.getPrice());
    }

    public String getProvider() {
        return this.provider;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Instant getRetrievalDate() {
        return this.retrievalDate;
    }
    public void setRetrievalDate(Instant retrievalDate) {
        this.retrievalDate = retrievalDate;
    }

    public Price getPrice() {
        return this.price;
    }
    public void setPrice(Price price) {
        this.price = price;
    }

    public Links getLinks() {
        return this.links;
    }
    public void setLinks(Links links) {
        this.links = links;
    }

    public String getDetails() {
        return this.details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

}
