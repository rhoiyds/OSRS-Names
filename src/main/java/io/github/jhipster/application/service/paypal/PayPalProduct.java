package io.github.jhipster.application.service.paypal;

import java.util.List;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;
import com.paypal.orders.LinkDescription;

@Model
public class PayPalProduct {
    
    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("description")
    String description;

    @SerializedName("create_time")
    String createTime;

    @SerializedName(value="links", listClass = LinkDescription.class)
    List<LinkDescription> links;

    public PayPalProduct() {}

    PayPalProduct setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    PayPalProduct setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    String getCreateTime() {
        return createTime;
    }

    PayPalProduct setName(String name) {
        this.name = name;
        return this;
    }

    String getName() {
        return name;
    }

    PayPalProduct setDescription(String description) {
        this.description = description;
        return this;
    }

    String getDescription() {
        return description;
    }

    PayPalProduct setLinks(List<LinkDescription> links) {
        this.links = links;
        return this;
    }

    List<LinkDescription> getLinks() {
        return links;
    }

    @Override
    public String toString() {
        return "PayPalProduct{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }

}
