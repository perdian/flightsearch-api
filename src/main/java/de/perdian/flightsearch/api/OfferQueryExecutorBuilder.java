package de.perdian.flightsearch.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.perdian.flightsearch.api.model.Offer;

public class OfferQueryExecutorBuilder {

    private List<OfferQueryExecutor> offerQueryExecutors = null;

    public OfferQueryExecutorBuilder() {
        this.setOfferQueryExecutors(new ArrayList<>());
    }

    public OfferQueryExecutor buildConsolidatedOfferQueryExecutor() {
        return offerQuery -> {
            List<Offer> allOffers = new ArrayList<>();
            for (OfferQueryExecutor offerQueryExecutor : this.getOfferQueryExecutors()) {
                allOffers.addAll(offerQueryExecutor.loadOffers(offerQuery));
            }
            return Offer.merge(allOffers);
        };
    }

    public void registerOfferQueryExecutor(OfferQueryExecutor offerQueryExecutor) {
        this.getOfferQueryExecutors().add(offerQueryExecutor);
    }

    public void registerOfferQueryExecutors(Collection<? extends OfferQueryExecutor> offerQueryExecutors) {
        this.getOfferQueryExecutors().addAll(offerQueryExecutors);
    }

    private List<OfferQueryExecutor> getOfferQueryExecutors() {
        return this.offerQueryExecutors;
    }
    private void setOfferQueryExecutors(List<OfferQueryExecutor> offerQueryExecutors) {
        this.offerQueryExecutors = offerQueryExecutors;
    }

}
