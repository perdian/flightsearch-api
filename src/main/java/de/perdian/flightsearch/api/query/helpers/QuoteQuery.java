package de.perdian.flightsearch.api.query.helpers;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.perdian.flightsearch.api.model.Quote;

public class QuoteQuery implements Predicate<Quote> {

    private PriceQuery price = null;
    private Collection<String> blacklistedProviders = null;
    private Collection<String> whitelistedProviders = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public boolean testAny(List<Quote> references) {
        return references.stream().filter(reference -> this.test(reference)).findAny().isPresent();
    }

    @Override
    public boolean test(Quote reference) {
        if (this.getPrice() != null && !this.getPrice().test(reference.getPrice())) {
            return false;
        } else if (this.getBlacklistedProviders() != null && !this.getBlacklistedProviders().isEmpty() && this.getBlacklistedProviders().stream().filter(provider -> provider.equals(reference.getProvider())).findAny().isPresent()) {
            return false;
        } else {
            if (this.getWhitelistedProviders() == null || this.getWhitelistedProviders().isEmpty()) {
                return true;
            } else {
                return this.getWhitelistedProviders().stream().filter(provider -> provider.equals(reference.getProvider())).findAny().isPresent();
            }
        }
    }

    public PriceQuery getPrice() {
        return this.price;
    }
    public void setPrice(PriceQuery price) {
        this.price = price;
    }

    public Collection<String> getBlacklistedProviders() {
        return this.blacklistedProviders;
    }
    public void setBlacklistedProviders(Collection<String> blacklistedProviders) {
        this.blacklistedProviders = blacklistedProviders;
    }

    public Collection<String> getWhitelistedProviders() {
        return this.whitelistedProviders;
    }
    public void setWhitelistedProviders(Collection<String> whitelistedProviders) {
        this.whitelistedProviders = whitelistedProviders;
    }

}
