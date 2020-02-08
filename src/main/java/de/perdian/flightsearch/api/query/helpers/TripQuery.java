package de.perdian.flightsearch.api.query.helpers;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

import de.perdian.flightsearch.api.model.CabinClass;
import de.perdian.flightsearch.api.model.Leg;
import de.perdian.flightsearch.api.model.Trip;

public class TripQuery implements Predicate<Trip>, Serializable {

    static final long serialVersionUID = 1L;

    private List<LegQuery> legs = null;
    private CabinClass cabinClass = CabinClass.ECONOMY;
    private int passengerCount = 1;

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
