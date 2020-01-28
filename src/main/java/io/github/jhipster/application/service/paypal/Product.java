package io.github.jhipster.application.service.paypal;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

@Model
public class Product {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name = "";

    @SerializedName("description")
    private String description = "";

    @SerializedName("type")
    private String type = "";

    @SerializedName("category")
    private String category = "";

    @SerializedName("image_url")
    private String imageUrl = "";

    @SerializedName("home_url")
    private String homeUrl = "";

    public Product() {}

    public String getId() {
        return id;
    }

    public Product setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getType() {
        return type;
    }

    public Product setType(String type) {
        this.type = type;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public Product setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
        return this;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", category='" + getCategory() + "'" +
            ", homeUrl='" + getHomeUrl() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}