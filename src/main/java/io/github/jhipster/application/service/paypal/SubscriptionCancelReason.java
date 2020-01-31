package io.github.jhipster.application.service.paypal;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class SubscriptionCancelReason {

    @SerializedName("reason")
    private String reason;

    public SubscriptionCancelReason() {}

    public String getReason() {
        return reason;
    }

    public SubscriptionCancelReason setReason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public String toString() {
        return "SubscriptionCancelReason{" +
            "reason=" + getReason()  +
            "}";
    }
}