package io.github.jhipster.application.service.paypal;

import com.paypal.http.HttpRequest;

public class SubscriptionCancelRequest extends HttpRequest<Void> {

    public SubscriptionCancelRequest(String subscriptionId) {
        super("/v1/billing/subscriptions/" + subscriptionId + "/cancel", "POST", Void.class);
        header("Content-Type", "application/json");
    }
    public SubscriptionCancelRequest authorization(String authorization) {
        header("Authorization", String.valueOf(authorization));
        return this;
    }

    public SubscriptionCancelRequest contentType(String contentType) {
        header("Content-Type", String.valueOf(contentType));
        return this;
    }

    public SubscriptionCancelRequest requestBody(SubscriptionCancelReason reason) {
      super.requestBody(reason);
      return this;
    }

}