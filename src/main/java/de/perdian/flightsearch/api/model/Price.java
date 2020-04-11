package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Price implements Serializable {

    static final long serialVersionUID = 1L;

    private BigDecimal value = null;
    private String currencyCode = null;

    public Price() {
    }

    public Price(BigDecimal value, String currencyCode) {
        this.setValue(value);
        this.setCurrencyCode(currencyCode);
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        StringBuilder result = new StringBuilder();
        result.append(numberFormat.format(this.getValue()));
        result.append(" ").append(this.getCurrencyCode());
        return result.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Price) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getValue(), ((Price)that).getValue());
            equalsBuilder.append(this.getCurrencyCode(), ((Price)that).getCurrencyCode());
            return equalsBuilder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getValue());
        hashCodeBuilder.append(this.getCurrencyCode());
        return hashCodeBuilder.toHashCode();
    }

    public static int compareByValue(Price p1, Price p2) {
        return p1.getValue().compareTo(p2.getValue());
    }

    public BigDecimal getValue() {
        return this.value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}
