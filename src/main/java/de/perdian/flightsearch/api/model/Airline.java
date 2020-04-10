package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Airline implements Serializable {

    static final long serialVersionUID = 1L;

    private String code = null;
    private String name = null;
    private String alias = null;
    private String callsign = null;
    private String countryCode = null;
    private String logoUrl = null;

    public Airline() {
    }

    public Airline(String code) {
        this.setCode(code);
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("code", this.getCode());
        toStringBuilder.append("name", this.getName());
        return toStringBuilder.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Airline) {
            return Objects.equals(this.getCode(), ((Airline)that).getCode());
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

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return this.alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCallsign() {
        return this.callsign;
    }
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getCountryCode() {
        return this.countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLogoUrl() {
        return this.logoUrl;
    }
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
