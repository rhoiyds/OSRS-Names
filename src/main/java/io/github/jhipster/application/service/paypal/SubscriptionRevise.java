package io.github.jhipster.application.service.paypal;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class SubscriptionRevise {

    @SerializedName("plan_id")
    private String planId;

    public SubscriptionRevise() {}

    public String getPlanId() {
        return planId;
    }

    public SubscriptionRevise setPlanId(String planId) {
        this.planId = planId;
        return this;
    }

    @Override
    public String toString() {
        return "SubscriptionRevise{" +
            "planId=" + getPlanId() +
            "}";
    }
}