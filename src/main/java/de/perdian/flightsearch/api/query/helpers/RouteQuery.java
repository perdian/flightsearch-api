package de.perdian.flightsearch.api.query.helpers;

public class RouteQuery {

    private String originAirportCode = null;
    private String destinationAirportCode = null;

    @Override
    public String toString() {
        return this.getOriginAirportCode() + "->" + this.getDestinationAirportCode();
    }

    public String getOriginAirportCode() {
        return this.originAirportCode;
    }
    public void setOriginAirportCode(String originAirportCode) {
        this.originAirportCode = originAirportCode;
    }

    public String getDestinationAirportCode() {
        return this.destinationAirportCode;
    }
    public void setDestinationAirportCode(String destinationAirportCode) {
        this.destinationAirportCode = destinationAirportCode;
    }

}