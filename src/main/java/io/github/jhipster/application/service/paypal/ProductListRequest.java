package io.github.jhipster.application.service.paypal;

import com.paypal.http.HttpRequest;

public class ProductListRequest extends HttpRequest<ProductListResponse> {

    public ProductListRequest() {
        super("/v1/catalogs/products", "GET", ProductListResponse.class);
        header("Content-Type", "application/json");
    }
    public ProductListRequest authorization(String authorization) {
        header("Authorization", String.valueOf(authorization));
        return this;
    }

    public ProductListRequest contentType(String contentType) {
        header("Content-Type", String.valueOf(contentType));
        return this;
    }

}