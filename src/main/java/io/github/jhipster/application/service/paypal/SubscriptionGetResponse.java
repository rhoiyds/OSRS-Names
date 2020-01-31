package io.github.jhipster.application.service.paypal;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class SubscriptionGetResponse {

    @SerializedName("plan_id")
    private String planId;

  public String getPlanId() {
      return planId;
  }

  public SubscriptionGetResponse setPlanId(String planId) {
      this.planId = planId;
      return this;
  }
  
}