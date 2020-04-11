package de.perdian.flightsearch.api.model;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OfferGroupTest {

    @Test
    public void testConstructor() {
        List<Offer> offers = Arrays.asList(new Offer());
        OfferGroup offerGroup = new OfferGroup("title", offers);
        Assertions.assertEquals("title", offerGroup.getTitle());
        Assertions.assertEquals(offers, offerGroup.getOffers());
    }

}
