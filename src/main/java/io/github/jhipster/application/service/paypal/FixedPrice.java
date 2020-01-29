package io.github.jhipster.application.service.paypal;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class FixedPrice {

    @SerializedName("value")
    String value;

    public String getValue() {
        return value;
    }

    public FixedPrice setValue(String value) {
        this.value = value;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public FixedPrice setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    @SerializedName("currency_code")
    String currencyCode;

}
