package io.github.jhipster.application.service.paypal;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class Frequency {

    @SerializedName("interval_unit")
    String intervalUnit;

    public String getIntervalUnit() {
        return intervalUnit;
    }

    public Frequency setIntervalUnit(String intervalUnit) {
        this.intervalUnit = intervalUnit;
        return this;
    }

    public int getIntervalCount() {
        return intervalCount;
    }

    public Frequency setIntervalCount(int intervalCount) {
        this.intervalCount = intervalCount;
        return this;
    }

    @SerializedName("interval_count")
    int intervalCount;


}
