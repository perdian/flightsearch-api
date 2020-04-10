package de.perdian.flightsearch.api.model;

import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OfferQuery implements Predicate<Offer> {

    private TripQuery trip = null;
    private QuoteQuery quote = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Override
    public boolean test(Offer offer) {
        if (this.getTrip() != null && !this.getTrip().test(offer.getTrip())) {
            return false;
        } else if (this.getQuote() != null && !this.getQuote().testAny(offer.getQuotes())) {
            return false;
        } else {
            return true;
        }
    }

    public TripQuery getTrip() {
        return this.trip;
    }
    public void setTrip(TripQuery trip) {
        this.trip = trip;
    }

    public QuoteQuery getQuote() {
        return this.quote;
    }
    public void setQuote(QuoteQuery quote) {
        this.quote = quote;
    }

}
