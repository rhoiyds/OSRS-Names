package io.github.jhipster.application.service.paypal;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class PricingScheme {

    @SerializedName("fixed_price")
    FixedPrice fixedPrice;

    public FixedPrice getFixedPrice() {
        return fixedPrice;
    }

    public PricingScheme setFixedPrice(FixedPrice fixedPrice) {
        this.fixedPrice = fixedPrice;
        return this;
    }
}
