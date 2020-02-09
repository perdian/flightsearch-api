package de.perdian.flightsearch.api.model;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Airport implements Serializable {

    static final long serialVersionUID = 1L;

    private String code = null;
    private String name = null;
    private String city = null;
    private String countryCode = null;
    private Float latitude = null;
    private Float longitude = null;
    private ZoneOffset timezoneOffset = null;
    private ZoneId timezoneId = null;
    private String type = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Airport) {
            return Objects.equals(this.getCode(), ((Airport)that).getCode());
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
    void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }
    void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return this.city;
    }
    void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return this.countryCode;
    }
    void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Float getLatitude() {
        return this.latitude;
    }
    void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return this.longitude;
    }
    void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public ZoneOffset getTimezoneOffset() {
        return this.timezoneOffset;
    }
    void setTimezoneOffset(ZoneOffset timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public ZoneId getTimezoneId() {
        return this.timezoneId;
    }
    void setTimezoneId(ZoneId timezoneId) {
        this.timezoneId = timezoneId;
    }

    public String getType() {
        return this.type;
    }
    void setType(String type) {
        this.type = type;
    }

}
