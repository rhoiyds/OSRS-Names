package io.github.jhipster.application.service.paypal;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class PaymentPreferences {

    public Boolean getAutoBillOutstanding() {
        return autoBillOutstanding;
    }

    public PaymentPreferences setAutoBillOutstanding(Boolean autoBillOutstanding) {
        this.autoBillOutstanding = autoBillOutstanding;
        return this;
    }

    public FixedPrice getSetupFee() {
        return setupFee;
    }

    public PaymentPreferences setSetupFee(FixedPrice setupFee) {
        this.setupFee = setupFee;
        return this;
    }

    public String getSetupFeeFailureAction() {
        return setupFeeFailureAction;
    }

    public PaymentPreferences setSetupFeeFailureAction(String setupFreeFailureAction) {
        this.setupFeeFailureAction = setupFreeFailureAction;
        return this;
    }

    public Integer getPaymentFailureThreshold() {
        return paymentFailureThreshold;
    }

    public PaymentPreferences setPaymentFailureThreshold(Integer paymentFailureThreshold) {
        this.paymentFailureThreshold = paymentFailureThreshold;
        return this;
    }

    @SerializedName("auto_bill_outstanding")
    Boolean autoBillOutstanding;

    @SerializedName("setup_fee")
    FixedPrice setupFee;

    @SerializedName("setup_fee_failure_action")
    String setupFeeFailureAction;

    @SerializedName("payment_failure_threshold")
    Integer paymentFailureThreshold;

}
