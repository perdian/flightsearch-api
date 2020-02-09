package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public static int compareByPrice(Offer o1, Offer o2) {
        BigDecimal p1 = Optional.ofNullable(o1.getQuotes()).orElseGet(Collections::emptyList).stream().min(Quote::compareByPrice).map(Quote::getPrice).map(Price::getValue).orElseGet(() -> BigDecimal.ZERO);
        BigDecimal p2 = Optional.ofNullable(o2.getQuotes()).orElseGet(Collections::emptyList).stream().min(Quote::compareByPrice).map(Quote::getPrice).map(Price::getValue).orElseGet(() -> BigDecimal.ZERO);
        return p1.compareTo(p2);
    }

    public static List<Offer> merge(Collection<Offer> offers) {
        Map<Trip, List<Quote>> quotesByTrip = new HashMap<>();
        offers.stream().forEach(offer -> quotesByTrip.compute(offer.getTrip(), (k, v) -> v == null ? new ArrayList<>() : v).addAll(offer.getQuotes()));
        List<Offer> mergedOffers = new ArrayList<>(quotesByTrip.size());
        for (Map.Entry<Trip, List<Quote>> quotesByTripEntry : quotesByTrip.entrySet()) {
            List<Quote> mergedQuotes = quotesByTripEntry.getValue();
            Collections.sort(mergedQuotes, Quote::compareByPrice);
            Offer mergedOffer = new Offer();
            mergedOffer.setTrip(quotesByTripEntry.getKey());
            mergedOffer.setQuotes(Collections.unmodifiableList(mergedQuotes));
            mergedOffers.add(mergedOffer);
        }
        mergedOffers.sort(Offer::compareByPrice);
        return Collections.unmodifiableList(mergedOffers);
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
