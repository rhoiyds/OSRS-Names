package io.github.jhipster.application.service.paypal;
import com.paypal.http.HttpRequest;

public class PlanUpdateRequest extends HttpRequest<PayPalPlan> {

    public PlanUpdateRequest(String id) {
        super("/v1/payments/billing-plans/" + id, "PATCH", PayPalPlan.class);
        header("Content-Type", "application/json");
    }
    public PlanUpdateRequest authorization(String authorization) {
        header("Authorization", String.valueOf(authorization));
        return this;
    }

    public PlanUpdateRequest contentType(String contentType) {
        header("Content-Type", String.valueOf(contentType));
        return this;
    }

    public PlanUpdateRequest requestBody(PlanPatch planPatch) {
      super.requestBody(planPatch);
      return this;
    }

}