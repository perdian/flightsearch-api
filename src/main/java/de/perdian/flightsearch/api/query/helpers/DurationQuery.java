package de.perdian.flightsearch.api.query.helpers;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DurationQuery implements Serializable, Predicate<Duration> {

    static final long serialVersionUID = 1L;

    private Duration min = null;
    private Duration max = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean test(Duration reference) {
        if (this.getMin() != null && this.getMin().toMillis() > reference.toMillis()) {
            return false;
        } else if (this.getMax() != null && this.getMax().toMillis() < reference.toMillis()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean testAll(Collection<Duration> references) {
        for (Duration reference : references) {
            if (!this.test(reference)) {
                return false;
            }
        }
        return true;
    }

    public Duration getMin() {
        return this.min;
    }
    public void setMin(Duration min) {
        this.min = min;
    }

    public Duration getMax() {
        return this.max;
    }
    public void setMax(Duration max) {
        this.max = max;
    }

}
