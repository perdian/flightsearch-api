package de.perdian.flightsearch.api.query.helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.perdian.flightsearch.api.model.CabinClass;
import de.perdian.flightsearch.api.model.Leg;
import de.perdian.flightsearch.api.model.Trip;

public class TripQuery implements Predicate<Trip>, Serializable {

    static final long serialVersionUID = 1L;

    private List<LegQuery> legs = null;
    private CabinClass cabinClass = CabinClass.ECONOMY;
    private int passengerCount = 1;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public TripType computeTripType() {
        if (this.getLegs().size() == 1) {
            return TripType.ONEWAY;
        } else if (this.getLegs().size() == 2) {
            boolean firstDepartureLastArrivalIdentical = this.getLegs().get(0).getOriginAirportCodes().containsAll(this.getLegs().get(this.getLegs().size() - 1).getDestinationAirportCodes()) && this.getLegs().get(this.getLegs().size() - 1).getDestinationAirportCodes().containsAll(this.getLegs().get(0).getOriginAirportCodes());
            boolean firstArrivalLastDepartureIdentical = this.getLegs().get(0).getDestinationAirportCodes().containsAll(this.getLegs().get(this.getLegs().size() - 1).getOriginAirportCodes()) && this.getLegs().get(this.getLegs().size() - 1).getOriginAirportCodes().containsAll(this.getLegs().get(0).getDestinationAirportCodes());
            return firstDepartureLastArrivalIdentical && firstArrivalLastDepartureIdentical ? TripType.ROUNDTRIP : TripType.MULTILOCATIONTRIP;
        } else {
            return TripType.MULTILOCATIONTRIP;
        }
    }

    @Override
    public boolean test(Trip trip) {
        if (!this.testLegs(trip.getLegs())) {
            return false;
        } else {
            return true;
        }
    }

    private boolean testLegs(List<Leg> legs) {
        if (this.getLegs().size() != legs.size()) {
            return false;
        } else {
            for (int i=0; i < this.getLegs().size(); i++) {
                if (!this.getLegs().get(i).test(legs.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public List<TripQuery> flattenMultipleAirportsForDepartureAndArrival() {
        return this.flattenMultipleAirportsForDepartureAndArrival(Collections.emptyList(), this.getLegs()).stream()
            .map(legQueries -> {
                TripQuery tripQuery = new TripQuery();
                tripQuery.setCabinClass(this.getCabinClass());
                tripQuery.setLegs(legQueries);
                tripQuery.setPassengerCount(this.getPassengerCount());
                return tripQuery;
            })
            .collect(Collectors.toList());
    }

    private List<List<LegQuery>> flattenMultipleAirportsForDepartureAndArrival(List<List<LegQuery>> existingList, List<LegQuery> nextFlightQueries) {
        List<LegQuery> nextRouteQueries = nextFlightQueries.get(0).flattenMultipleAirportsForDepartureAndArrival();
        List<List<LegQuery>> resultList = new ArrayList<>();
        if (existingList.isEmpty()) {
            nextRouteQueries.forEach(nextRouteQuery -> resultList.add(Arrays.asList(nextRouteQuery)));
        } else {
            for (List<LegQuery> existingListItem : existingList) {
                for (LegQuery nextRouteQuery : nextRouteQueries) {
                    List<LegQuery> resultListItem = new ArrayList<>(existingListItem);
                    resultListItem.add(nextRouteQuery);
                    resultList.add(resultListItem);
                }
            }
        }
        List<LegQuery> remainingFlights = nextFlightQueries.subList(1, nextFlightQueries.size());
        return remainingFlights.isEmpty() ? resultList : this.flattenMultipleAirportsForDepartureAndArrival(resultList, remainingFlights);
    }

    public List<LegQuery> getLegs() {
        return this.legs;
    }
    public void setLegs(List<LegQuery> legs) {
        this.legs = legs;
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