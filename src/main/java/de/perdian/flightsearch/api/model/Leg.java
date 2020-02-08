package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Leg implements Serializable {

    static final long serialVersionUID = 1L;

    private List<LegItem> items = Collections.emptyList();

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Leg) {
            Leg thatLeg = (Leg)that;
            if (this.getItems().size() != thatLeg.getItems().size()) {
                return false;
            } else {
                for (int i=0; i < this.getItems().size(); i++) {
                    if (!this.getItems().get(i).equals(thatLeg.getItems().get(i))) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        for (int i=0; i < this.getItems().size(); i++) {
            hashCodeBuilder.append(i).append(this.getItems().get(i));
        }
        return hashCodeBuilder.toHashCode();
    }

    public LegItem getFirstItem() {
        return this.getItems().get(0);
    }
    public LegItem getLastItem() {
        return this.getItems().get(this.getItems().size() - 1);
    }
    public List<LegItem> getItems() {
        return this.items;
    }
    public void setItems(List<LegItem> items) {
        this.items = items;
    }

}
