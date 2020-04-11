package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Aircraft implements Serializable {

    static final long serialVersionUID = 1L;

    private AircraftType type = null;
    private String typeDescription = null;
    private String registration = null;
    private String name = null;

    public Aircraft() {
    }

    public Aircraft(AircraftType type) {
        this.setType(type);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Aircraft) {
            return Objects.equals(this.getType(), ((Aircraft)that).getType());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getType() == null ? 0 : this.getType().hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public AircraftType getType() {
        return this.type;
    }
    public void setType(AircraftType type) {
        this.type = type;
    }

    public String getTypeDescription() {
        return this.typeDescription;
    }
    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getRegistration() {
        return this.registration;
    }
    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
