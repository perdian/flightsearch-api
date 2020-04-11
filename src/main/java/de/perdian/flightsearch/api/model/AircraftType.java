package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AircraftType implements Serializable {

    static final long serialVersionUID = 1L;

    private String code = null;
    private String title = null;

    public AircraftType() {
    }

    public AircraftType(String code) {
        this.setCode(code);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("code", this.getCode());
        toStringBuilder.append("title", this.getTitle());
        return toStringBuilder.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof AircraftType) {
            return Objects.equals(this.getCode(), ((AircraftType)that).getCode());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getCode() == null ? 0 : this.getCode().hashCode();
    }

    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
