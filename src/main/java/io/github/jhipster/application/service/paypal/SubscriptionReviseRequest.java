package io.github.jhipster.application.service.paypal;

import com.paypal.http.HttpRequest;

public class SubscriptionReviseRequest extends HttpRequest<Void> {

    public SubscriptionReviseRequest(String subscriptionId) {
        super("/v1/billing/subscriptions/" + subscriptionId + "/revise", "POST", Void.class);
        header("Content-Type", "application/json");
    }
    public SubscriptionReviseRequest authorization(String authorization) {
        header("Authorization", String.valueOf(authorization));
        return this;
    }

    public SubscriptionReviseRequest contentType(String contentType) {
        header("Content-Type", String.valueOf(contentType));
        return this;
    }

    public SubscriptionReviseRequest requestBody(SubscriptionRevise body) {
        super.requestBody(body);
        return this;
      }

}