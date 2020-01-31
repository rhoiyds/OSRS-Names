package io.github.jhipster.application.service.paypal;

import com.paypal.http.HttpRequest;

public class SubscriptionGetRequest extends HttpRequest<SubscriptionGetResponse> {

    public SubscriptionGetRequest(String subscriptionId) {
        super("/v1/billing/subscriptions/" + subscriptionId, "GET", SubscriptionGetResponse.class);
        header("Content-Type", "application/json");
    }
    public SubscriptionGetRequest authorization(String authorization) {
        header("Authorization", String.valueOf(authorization));
        return this;
    }

    public SubscriptionGetRequest contentType(String contentType) {
        header("Content-Type", String.valueOf(contentType));
        return this;
    }

}