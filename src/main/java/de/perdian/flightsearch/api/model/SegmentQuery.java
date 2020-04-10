package de.perdian.flightsearch.api.model;

import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SegmentQuery implements Predicate<Segment> {

    private LegQuery leg = null;

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("leg", this.getLeg());
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

}
