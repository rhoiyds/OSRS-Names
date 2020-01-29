package io.github.jhipster.application.service.paypal;

import java.util.List;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class PayPalPlan {
    
    @SerializedName("id")
    String id;

    @SerializedName("product_id")
    String productId;

    @SerializedName("name")
    String name;

    @SerializedName("description")
    String description;

    public PaymentPreferences getPaymentPreferences() {
        return paymentPreferences;
    }

    public PayPalPlan setPaymentPreferences(PaymentPreferences paymentPreferences) {
        this.paymentPreferences = paymentPreferences;
        return this;
    }

    @SerializedName("status")
    String status;

    @SerializedName(value="billing_cycles", listClass = BillingCycle.class)
    List<BillingCycle> billingCycles;

    @SerializedName("payment_preferences")
    PaymentPreferences paymentPreferences;

    public PayPalPlan() {}

    public PayPalPlan setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public PayPalPlan setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public PayPalPlan setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public PayPalPlan setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PayPalPlan setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatus() {
        return status;
    }


    public PayPalPlan setBillingCycles(List<BillingCycle> billingCycles) {
        this.billingCycles = billingCycles;
        return this;
    }

    public List<BillingCycle> getBillingCycles() {
        return billingCycles;
    }

    @Override
    public String toString() {
        return "PayPalPlan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }

}
