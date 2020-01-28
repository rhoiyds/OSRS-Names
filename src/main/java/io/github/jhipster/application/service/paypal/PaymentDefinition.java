package io.github.jhipster.application.service.paypal;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class PaymentDefinition {
    
    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("type")
    String type;

    @SerializedName("amount")
    Object amount;

    @SerializedName("frequency")
    String frequency;

    @SerializedName("frequency_interval")
    String frequencyInterval;

    @SerializedName("cycles")
    String cycles;

    public PaymentDefinition() {}

    PaymentDefinition setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public PaymentDefinition setType(String type) {
        this.type = type;
        return this;
    }

    String getType() {
        return type;
    }

    public PaymentDefinition setName(String name) {
        this.name = name;
        return this;
    }

    String getName() {
        return name;
    }

    public PaymentDefinition setAmount(Object amount) {
        this.amount = amount;
        return this;
    }

    Object getAmount() {
        return amount;
    }

    public PaymentDefinition setFrequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    String getFrequency() {
        return frequency;
    }

    public PaymentDefinition setFrequencyInterval(String frequencyInterval) {
        this.frequencyInterval = frequencyInterval;
        return this;
    }

    String getFrequencyInterval() {
        return frequencyInterval;
    }

    public PaymentDefinition setCycles(String cycles) {
        this.cycles = cycles;
        return this;
    }

    String getCycles() {
        return cycles;
    }


    @Override
    public String toString() {
        return "PaymentDefinition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", amount='" + getAmount().toString() + "'" +
            ", frequency='" + getFrequency() + "'" +
            ", frequencyInterval='" + getFrequencyInterval() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }

}
