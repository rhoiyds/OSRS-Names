package io.github.jhipster.application.service.paypal;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class BillingCycle {

    @SerializedName("pricing_scheme")
    PricingScheme pricingScheme;

    @SerializedName("total_cycles")
    int totalCycles;

    @SerializedName("tenure_type")
    String tenureType;

    @SerializedName("sequence")
    int sequence;

    public Frequency getFrequency() {
        return frequency;
    }

    public BillingCycle setFrequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    @SerializedName("frequency")
    Frequency frequency;

    public PricingScheme getPricingScheme() {
        return pricingScheme;
    }

    public BillingCycle setPricingScheme(PricingScheme pricingScheme) {
        this.pricingScheme = pricingScheme;
        return this;
    }

    public int getTotalCycles() {
        return totalCycles;
    }

    public BillingCycle setTotalCycles(int totalCycles) {
        this.totalCycles = totalCycles;
        return this;
    }

    public String getTenureType() {
        return tenureType;
    }

    public BillingCycle setTenureType(String tenureType) {
        this.tenureType = tenureType;
        return this;
    }

    public int getSequence() {
        return sequence;
    }

    public BillingCycle setSequence(int sequence) {
        this.sequence = sequence;
        return this;
    }

}
