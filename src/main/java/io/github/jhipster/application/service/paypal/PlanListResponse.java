package io.github.jhipster.application.service.paypal;

import java.util.List;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;
import com.paypal.orders.LinkDescription;

@Model
public class PlanListResponse {

    @SerializedName(value = "links", listClass=LinkDescription.class)
    private List<LinkDescription> links;

    @SerializedName(value = "plans", listClass=PayPalPlan.class)
    private List<PayPalPlan> plans;

    public PlanListResponse() {}

    public List<PayPalPlan> getPlans() {
        return plans;
    }

    public PlanListResponse setPlans(List<PayPalPlan> plans) {
        this.plans = plans;
        return this;
    }

  public List<LinkDescription> getLinks() {
      return links;
  }

  public PlanListResponse setLinks(List<LinkDescription> links) {
      this.links = links;
      return this;
  }
  
}