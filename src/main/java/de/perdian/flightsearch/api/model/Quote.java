package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.time.Instant;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Quote implements Serializable {

    static final long serialVersionUID = 1L;

    private String provider = null;
    private Instant retrievalDate = null;
    private Price price = null;
    private String details = null;
    private String shoppingDeeplinkUrl = null;

    public Quote() {
    }

    public Quote(String provider, Price price) {
        this.setProvider(provider);
        this.setPrice(price);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Quote) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getProvider(), ((Quote)that).getProvider());
            equalsBuilder.append(this.getPrice(), ((Quote)that).getPrice());
            return equalsBuilder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getProvider());
        hashCodeBuilder.append(this.getPrice());
        return hashCodeBuilder.toHashCode();
    }

    public static int compareByPrice(Quote q1, Quote q2) {
        return Price.compareByValue(q1.getPrice(), q2.getPrice());
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

    public String getDetails() {
        return this.details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    public String getShoppingDeeplinkUrl() {
        return this.shoppingDeeplinkUrl;
    }
    public void setShoppingDeeplinkUrl(String shoppingDeeplinkUrl) {
        this.shoppingDeeplinkUrl = shoppingDeeplinkUrl;
    }

}
