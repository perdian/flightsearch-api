package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OfferGroup implements Serializable {

    static final long serialVersionUID = 1L;

    private String title = null;
    private List<Offer> offers = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<Offer> getOffers() {
        return this.offers;
    }
    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

}
