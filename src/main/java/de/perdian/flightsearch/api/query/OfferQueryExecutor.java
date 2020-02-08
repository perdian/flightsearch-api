package de.perdian.flightsearch.api.query;

import java.io.IOException;
import java.util.List;

import de.perdian.flightsearch.api.model.Offer;

public interface OfferQueryExecutor {

    List<Offer> loadOffers(OfferQuery offerQuery) throws IOException;

}
