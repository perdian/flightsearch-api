package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TripQuery implements Predicate<Trip>, Serializable {

    static final long serialVersionUID = 1L;

    private List<FlightQuery> flights = Collections.emptyList();
    private CabinClass cabinClass = CabinClass.ECONOMY;
    private int passengerCount = 1;

    public TripQuery() {
    }

    public TripQuery(List<FlightQuery> flights) {
        this.setFlights(flights);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("tripType", this.getTripType());
        toStringBuilder.append("legs", this.getFlights());
        toStringBuilder.append("cabinClass", this.getCabinClass());
        toStringBuilder.append("passengerCount", this.getPassengerCount());
        return toStringBuilder.toString();
    }

    @Override
    public boolean test(Trip trip) {
        if (!this.testFlights(trip)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean testFlights(Trip trip) {
        if (this.getFlights().size() != trip.getFlights().size()) {
            return false;
        } else {
            for (int i=0; i < this.getFlights().size(); i++) {
                if (!this.getFlights().get(i).test(trip.getFlights().get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public List<TripQuery> flattenMultipleAirportsForOriginAndDestination() {
        return this.flattenMultipleAirportsForOriginAndDestination(Collections.emptyList(), this.getFlights()).stream()
            .map(legQueries -> {
                TripQuery tripQuery = new TripQuery();
                tripQuery.setCabinClass(this.getCabinClass());
                tripQuery.setFlights(legQueries);
                tripQuery.setPassengerCount(this.getPassengerCount());
                return tripQuery;
            })
            .collect(Collectors.toList());
    }

    private List<List<FlightQuery>> flattenMultipleAirportsForOriginAndDestination(List<List<FlightQuery>> existingList, List<FlightQuery> moreLegs) {
        List<FlightQuery> nextLegs = moreLegs.get(0).flattenMultipleAirportsForOriginAndDestination();
        List<List<FlightQuery>> resultList = new ArrayList<>();
        if (existingList.isEmpty()) {
            nextLegs.forEach(nextLegQuery -> resultList.add(Arrays.asList(nextLegQuery)));
        } else {
            for (List<FlightQuery> existingListItem : existingList) {
                for (FlightQuery nextLeg : nextLegs) {
                    List<FlightQuery> resultListItem = new ArrayList<>(existingListItem);
                    resultListItem.add(nextLeg);
                    resultList.add(resultListItem);
                }
            }
        }
        List<FlightQuery> remainingLegs = moreLegs.subList(1, moreLegs.size());
        return remainingLegs.isEmpty() ? resultList : this.flattenMultipleAirportsForOriginAndDestination(resultList, remainingLegs);
    }

    public TripType getTripType() {
        if (this.getFlights() == null || this.getFlights().isEmpty()) {
            return null;
        } else if (this.getFlights().size() == 1) {
            return TripType.ONEWAY;
        } else if (this.getFlights().size() == 2) {
            boolean firstOriginLastDestinationIdentical = this.getFlights().get(0).getOriginAirportContact().getAirportCodes().containsAll(this.getFlights().get(this.getFlights().size() - 1).getDestinationAirportContact().getAirportCodes()) && this.getFlights().get(this.getFlights().size() - 1).getDestinationAirportContact().getAirportCodes().containsAll(this.getFlights().get(0).getOriginAirportContact().getAirportCodes());
            boolean firstDestinationLastOriginIdentical = this.getFlights().get(0).getDestinationAirportContact().getAirportCodes().containsAll(this.getFlights().get(this.getFlights().size() - 1).getOriginAirportContact().getAirportCodes()) && this.getFlights().get(this.getFlights().size() - 1).getOriginAirportContact().getAirportCodes().containsAll(this.getFlights().get(0).getDestinationAirportContact().getAirportCodes());
            return firstOriginLastDestinationIdentical && firstDestinationLastOriginIdentical ? TripType.ROUNDTRIP : TripType.MULTILOCATIONTRIP;
        } else {
            return TripType.MULTILOCATIONTRIP;
        }
    }

    public List<FlightQuery> getFlights() {
        return this.flights;
    }
    public void setFlights(List<FlightQuery> flights) {
        this.flights = flights;
    }

    public CabinClass getCabinClass() {
        return this.cabinClass;
    }
    public void setCabinClass(CabinClass cabinClass) {
        this.cabinClass = cabinClass;
    }

    public int getPassengerCount() {
        return this.passengerCount;
    }
    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

}
