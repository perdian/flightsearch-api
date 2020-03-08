package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Aircraft implements Serializable {

    static final long serialVersionUID = 1L;

    private String typeCode = null;
    private String typeDescription = null;
    private String registration = null;
    private String name = null;

    public Aircraft() {
    }

    public Aircraft(String typeCode) {
        this.setTypeCode(typeCode);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Aircraft) {
            return Objects.equals(this.getTypeCode(), ((Aircraft)that).getTypeCode());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getTypeCode() == null ? 0 : this.getTypeCode().hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public String getTypeCode() {
        return this.typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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
