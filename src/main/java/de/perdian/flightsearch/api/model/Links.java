package de.perdian.flightsearch.api.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Links implements Serializable {

    static final long serialVersionUID = 1L;

    private String shoppingDeeplinkUrl = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public String getShoppingDeeplinkUrl() {
        return this.shoppingDeeplinkUrl;
    }
    public void setShoppingDeeplinkUrl(String shoppingDeeplinkUrl) {
        this.shoppingDeeplinkUrl = shoppingDeeplinkUrl;
    }

}
