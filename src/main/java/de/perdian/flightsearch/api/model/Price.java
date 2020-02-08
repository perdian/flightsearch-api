package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Price implements Serializable, Comparable<Price> {

    static final long serialVersionUID = 1L;

    private String currencyCode = null;
    private BigDecimal value = null;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.getValueString());
        result.append(" ").append(this.getCurrencyCode());
        return result.toString();
    }

    @Override
    public int compareTo(Price that) {
        return this.getValue().compareTo(that.getValue());
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Price) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getCurrencyCode(), ((Price)that).getCurrencyCode());
            equalsBuilder.append(this.getValue(), ((Price)that).getValue());
            return equalsBuilder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getCurrencyCode());
        hashCodeBuilder.append(this.getValue());
        return hashCodeBuilder.toHashCode();
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getValueString() {
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        return numberFormat.format(this.getValue());
    }
    public BigDecimal getValue() {
        return this.value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
