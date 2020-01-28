package io.github.jhipster.application.service.paypal;
import com.paypal.http.HttpRequest;

public class PlanCreateRequest extends HttpRequest<PayPalPlan> {

    public PlanCreateRequest() {
        super("/v1/payments/billing-plans", "POST", PayPalPlan.class);
        header("Content-Type", "application/json");
    }
    public PlanCreateRequest authorization(String authorization) {
        header("Authorization", String.valueOf(authorization));
        return this;
    }

    public PlanCreateRequest contentType(String contentType) {
        header("Content-Type", String.valueOf(contentType));
        return this;
    }

    public PlanCreateRequest requestBody(PayPalPlan payPalPlan) {
      super.requestBody(payPalPlan);
      return this;
    }

}