package de.perdian.flightsearch.api.query.helpers;

import java.math.BigDecimal;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.perdian.flightsearch.api.model.Price;

public class PriceQuery implements Predicate<Price> {

    private BigDecimal min = null;
    private BigDecimal max = null;

    public PriceQuery() {
    }

    public PriceQuery(BigDecimal min, BigDecimal max) {
        this.setMin(min);
        this.setMax(max);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Override
    public boolean test(Price reference) {
        if (this.getMin() != null && (reference == null || reference.getValue() == null || this.getMin().doubleValue() > reference.getValue().doubleValue())) {
            return false;
        } else if (this.getMax() != null && (reference == null || reference.getValue() == null || this.getMax().doubleValue() < reference.getValue().doubleValue())) {
            return false;
        } else {
            return true;
        }
    }

    public BigDecimal getMin() {
        return this.min;
    }
    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return this.max;
    }
    public void setMax(BigDecimal max) {
        this.max = max;
    }

}
