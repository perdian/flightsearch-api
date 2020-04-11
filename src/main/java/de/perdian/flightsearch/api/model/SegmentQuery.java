package de.perdian.flightsearch.api.model;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SegmentQuery implements Predicate<Segment> {

    private LegQuery leg = null;
    private FlightNumber flightNumber = null;
    private FlightNumber operatingFlightNumber = null;
    private FlightNumber marketingFlightNumber = null;
    private AirportContactQuery originAirportContact = null;
    private AirportContactQuery destinationAirportContact = null;

    public SegmentQuery() {
    }

    public SegmentQuery(LegQuery leg) {
        this.setLeg(leg);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("flightNumber", this.getFlightNumber());
        toStringBuilder.append("leg", this.getLeg());
        toStringBuilder.append("originAirportContact", this.getOriginAirportContact());
        toStringBuilder.append("destinationAirportContact", this.getDestinationAirportContact());
        return toStringBuilder.toString();
    }

    public boolean testAll(List<Segment> segments) {
        for (Segment segment : segments) {
            if (!this.test(segment)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean test(Segment segment) {
        if (this.getLeg() != null && !this.getLeg().testAll(segment.getLegs())) {
            return false;
        } else if (this.getOperatingFlightNumber() != null && !this.getOperatingFlightNumber().equals(segment.getOperatingFlightNumber())) {
            return false;
        } else if (this.getMarketingFlightNumber() != null && !this.getMarketingFlightNumber().equals(segment.getMarketingFlightNumber())) {
            return false;
        } else if (!this.testFlightNumber(segment)) {
            return false;
        } else if (this.getOriginAirportContact() != null && !this.testLegsForAirportContact(this.getOriginAirportContact(), segment.getLegs(), leg -> leg.getScheduledRoute().getOrigin())) {
            return false;
        } else if (this.getDestinationAirportContact() != null && !this.testLegsForAirportContact(this.getDestinationAirportContact(), segment.getLegs(), leg -> leg.getScheduledRoute().getDestination())) {
            return false;
        } else {
            return true;
        }
    }

    private boolean testLegsForAirportContact(AirportContactQuery airportContactQuery, List<Leg> legs, Function<Leg, AirportContact> airportContactFunction) {
        for (Leg leg : legs) {
            if (airportContactQuery.test(airportContactFunction.apply(leg))) {
                return true;
            }
        }
        return false;
    }

    private boolean testFlightNumber(Segment segment) {
        if (this.getFlightNumber() != null) {
            if (this.getFlightNumber().equals(segment.getOperatingFlightNumber())) {
                return true;
            } else if (this.getFlightNumber().equals(segment.getMarketingFlightNumber())) {
                return true;
            } else if (segment.getCodeshareFlightNumbers() != null && segment.getCodeshareFlightNumbers().contains(this.getFlightNumber())) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public LegQuery getLeg() {
        return this.leg;
    }
    public void setLeg(LegQuery leg) {
        this.leg = leg;
    }

    public FlightNumber getFlightNumber() {
        return this.flightNumber;
    }
    public void setFlightNumber(FlightNumber flightNumber) {
        this.flightNumber = flightNumber;
    }

    public FlightNumber getOperatingFlightNumber() {
        return this.operatingFlightNumber;
    }
    public void setOperatingFlightNumber(FlightNumber operatingFlightNumber) {
        this.operatingFlightNumber = operatingFlightNumber;
    }

    public FlightNumber getMarketingFlightNumber() {
        return this.marketingFlightNumber;
    }
    public void setMarketingFlightNumber(FlightNumber marketingFlightNumber) {
        this.marketingFlightNumber = marketingFlightNumber;
    }

    public AirportContactQuery getOriginAirportContact() {
        return this.originAirportContact;
    }
    public void setOriginAirportContact(AirportContactQuery originAirportContact) {
        this.originAirportContact = originAirportContact;
    }

    public AirportContactQuery getDestinationAirportContact() {
        return this.destinationAirportContact;
    }
    public void setDestinationAirportContact(AirportContactQuery destinationAirportContact) {
        this.destinationAirportContact = destinationAirportContact;
    }

}
