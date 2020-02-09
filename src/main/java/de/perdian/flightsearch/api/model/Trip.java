package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Trip implements Serializable {

    static final long serialVersionUID = 1L;

    private List<Leg> legs = Collections.emptyList();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Trip) {
            Trip thatTrip = (Trip)that;
            if (this.getLegs().size() != thatTrip.getLegs().size()) {
                return false;
            } else {
                for (int i=0; i < this.getLegs().size(); i++) {
                    if (!this.getLegs().get(i).equals(thatTrip.getLegs().get(i))) {
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
        for (int i=0; i < this.getLegs().size(); i++) {
            hashCodeBuilder.append(i).append(this.getLegs().get(i));
        }
        return hashCodeBuilder.toHashCode();
    }

    public List<Leg> getLegs() {
        return this.legs;
    }
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

}
