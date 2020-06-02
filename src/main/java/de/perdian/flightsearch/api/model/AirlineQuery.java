package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

public class AirlineQuery implements Serializable, Predicate<Airline> {

    static final long serialVersionUID = 1L;

    private List<String> restrictCodes = null;
    private List<String> excludeCodes = null;

    public AirlineQuery() {
    }

    public AirlineQuery(List<String> restrictCodes) {
        this.setRestrictCodes(restrictCodes);
    }

    @Override
    public boolean test(Airline airline) {
        if (!this.testCodes(airline)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean testCodes(Airline airline) {
        if (this.getRestrictCodes() != null && !this.getRestrictCodes().isEmpty() && !this.getRestrictCodes().contains(airline.getCode())) {
            return false;
        } else if (this.getExcludeCodes() != null && !this.getExcludeCodes().isEmpty() && this.getExcludeCodes().contains(airline.getCode())) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> getRestrictCodes() {
        return this.restrictCodes;
    }
    public void setRestrictCodes(List<String> restrictCodes) {
        this.restrictCodes = restrictCodes;
    }

    public List<String> getExcludeCodes() {
        return this.excludeCodes;
    }
    public void setExcludeCodes(List<String> excludeCodes) {
        this.excludeCodes = excludeCodes;
    }

}
