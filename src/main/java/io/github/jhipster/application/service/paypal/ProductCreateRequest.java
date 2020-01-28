package io.github.jhipster.application.service.paypal;
import com.paypal.http.HttpRequest;

public class ProductCreateRequest extends HttpRequest<Product> {

    public ProductCreateRequest() {
        super("/v1/catalogs/products", "POST", Product.class);
        header("Content-Type", "application/json");
    }
    public ProductCreateRequest authorization(String authorization) {
        header("Authorization", String.valueOf(authorization));
        return this;
    }

    public ProductCreateRequest contentType(String contentType) {
        header("Content-Type", String.valueOf(contentType));
        return this;
    }

    public ProductCreateRequest requestBody(Product product) {
      super.requestBody(product);
      return this;
    }

    public ProductCreateRequest prefer(String prefer) {
      header("Prefer", String.valueOf(prefer));
      return this;
    }

}