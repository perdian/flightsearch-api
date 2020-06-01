package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Schedule implements Serializable {

    static final long serialVersionUID = 1L;

    private String source = null;
    private List<ScheduleEntry> entries = Collections.emptyList();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public List<ScheduleEntry> getEntries() {
        return this.entries;
    }
    public void setEntries(List<ScheduleEntry> entries) {
        this.entries = entries;
    }


}
