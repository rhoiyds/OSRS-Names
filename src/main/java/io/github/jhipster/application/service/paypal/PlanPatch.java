package io.github.jhipster.application.service.paypal;

import java.util.Map;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class PlanPatch {
    
    @SerializedName("op")
    String op;

    @SerializedName("path")
    String path;

    @SerializedName("value")
    Map<String, String> value;

    public PlanPatch() {}

    public PlanPatch setOp(String op) {
        this.op = op;
        return this;
    }

    public String getOp() {
        return op;
    }

    public PlanPatch setPath(String path) {
        this.path = path;
        return this;
    }

    public String getPath() {
        return path;
    }

    public PlanPatch setValue(Map<String, String> value) {
        this.value = value;
        return this;
    }

    public Map<String, String> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PlanPatch{" +
            "op=" + getOp() +
            ", path='" + getPath() + "'" +
            ", value='" + getValue().toString() + "'" +
            "}";
    }

}
