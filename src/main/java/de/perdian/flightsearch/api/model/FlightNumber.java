package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlightNumber implements Serializable {

    private static final Pattern PATTERN = Pattern.compile("([A-Za-z0-9]{2})([0-9]{1,4})([A-Za-z]*)");

    static final long serialVersionUID = 1L;

    private String carrierCode = null;
    private int flightNumber = 0;
    private String postfix = null;

    public static FlightNumber parse(String flightNumber) {
        Matcher matcher = PATTERN.matcher(flightNumber);
        if (matcher.matches()) {
            return new FlightNumber(matcher.group(1).toUpperCase(), Integer.parseInt(matcher.group(2), 10), matcher.group(3).toUpperCase());
        } else {
            throw new IllegalArgumentException("Invalid flight number: " + flightNumber);
        }
    }

    private FlightNumber(String carrierCode, int flightNumber, String postfix) {
        this.setCarrierCode(carrierCode);
        this.setFlightNumber(flightNumber);
        this.setPostfix(postfix);
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = new DecimalFormat("0000");
        StringBuilder result = new StringBuilder();
        result.append(this.getCarrierCode());
        result.append(numberFormat.format(this.getFlightNumber()));
        result.append(Optional.ofNullable(this.getPostfix()).orElse(""));
        return result.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof FlightNumber) {
            return this.toString().equals(((FlightNumber)that).toString());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public String getCarrierCode() {
        return this.carrierCode;
    }
    private void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public int getFlightNumber() {
        return this.flightNumber;
    }
    private void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getPostfix() {
        return this.postfix;
    }
    private void setPostfix(String postfix) {
        this.postfix = postfix;
    }

}
