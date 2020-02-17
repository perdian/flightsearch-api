package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Leg implements Serializable {

    static final long serialVersionUID = 1L;

    private List<LegItem> items = Collections.emptyList();

    public Leg() {
    }

    public Leg(List<LegItem> items) {
        this.setItems(items);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

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

    public Duration getScheduledDuration() {
        Instant firstItemDeparture = this.getFirstItem().getFlight().getScheduledDeparture().getLocalDateTime().atZone(this.getFirstItem().getFlight().getScheduledDeparture().getAirport().getTimezoneId()).toInstant();
        Instant lastItemArrival = this.getLastItem().getFlight().getScheduledArrival().getLocalDateTime().atZone(this.getLastItem().getFlight().getScheduledArrival().getAirport().getTimezoneId()).toInstant();
        return Duration.between(firstItemDeparture, lastItemArrival);
    }
    public String getScheduledDurationFormatted() {
        if (this.getScheduledDuration() == null) {
            return null;
        } else {
            NumberFormat numberFormat = new DecimalFormat("00");
            StringBuilder result = new StringBuilder();
            result.append(this.getScheduledDuration().toMinutes() / 60);
            result.append(":").append(numberFormat.format(this.getScheduledDuration().toMinutes() % 60));
            return result.toString();
        }
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
