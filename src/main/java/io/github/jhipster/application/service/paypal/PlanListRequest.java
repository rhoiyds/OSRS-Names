package io.github.jhipster.application.service.paypal;

import com.paypal.http.HttpRequest;

public class PlanListRequest extends HttpRequest<PlanListResponse> {

    public PlanListRequest() {
        super("/v1/billing/plans", "GET", PlanListResponse.class);
        header("Content-Type", "application/json");
    }
    public PlanListRequest authorization(String authorization) {
        header("Authorization", String.valueOf(authorization));
        return this;
    }

    public PlanListRequest contentType(String contentType) {
        header("Content-Type", String.valueOf(contentType));
        return this;
    }

}
