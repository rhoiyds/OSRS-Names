package io.github.jhipster.application.config;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Rsnsales.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final PayPal payPal = new PayPal();

    public PayPal getPayPal() {
        return payPal;
    }

    public static class Credentials {

        private String clientId = "";

        private String secretId = "";

        private String baseUrl = "";

        private String webUrl = "";

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getSecretId() {
            return secretId;
        }

        public void setSecretId(String secretId) {
            this.secretId = secretId;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }

    @Model
    public static class Product {

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

    public static class PayPal {

        private Product product;

        private Credentials credentials;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

    }

}
