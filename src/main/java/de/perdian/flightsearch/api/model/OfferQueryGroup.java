package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OfferQueryGroup implements Serializable {

    static final long serialVersionUID = 1L;

    private String title = null;
    private List<OfferQuery> queries = Collections.emptyList();

    public OfferQueryGroup() {
    }

    public OfferQueryGroup(String title, List<OfferQuery> queries) {
        this.setTitle(title);
        this.setQueries(queries);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<OfferQuery> getQueries() {
        return this.queries;
    }
    public void setQueries(List<OfferQuery> queries) {
        this.queries = queries;
    }

}
