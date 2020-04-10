package de.perdian.flightsearch.api;

import java.io.IOException;
import java.util.List;

import de.perdian.flightsearch.api.model.Offer;
import de.perdian.flightsearch.api.model.OfferQuery;

public interface OfferQueryExecutor {

    List<Offer> loadOffers(OfferQuery offerQuery) throws IOException;

}
