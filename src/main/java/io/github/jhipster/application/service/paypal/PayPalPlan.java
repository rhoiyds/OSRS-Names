package io.github.jhipster.application.service.paypal;

import java.util.List;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class PayPalPlan {
    
    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("description")
    String description;

    @SerializedName("type")
    String type;

    @SerializedName("state")
    String state;

    @SerializedName(value="payment_definitions", listClass = PaymentDefinition.class)
    List<PaymentDefinition> paymentDefinitions;

    public PayPalPlan() {}

    PayPalPlan setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public PayPalPlan setType(String type) {
        this.type = type;
        return this;
    }

    public String getType() {
        return type;
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

    public PayPalPlan setState(String state) {
        this.state = state;
        return this;
    }

    public String getState() {
        return state;
    }


    public PayPalPlan setPaymentDefinitions(List<PaymentDefinition> paymentDefinitions) {
        this.paymentDefinitions = paymentDefinitions;
        return this;
    }

    public List<PaymentDefinition> getPaymentDefinitions() {
        return paymentDefinitions;
    }

    @Override
    public String toString() {
        return "PayPalPlan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }

}
