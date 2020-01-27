package io.github.jhipster.application.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpRequest;
import com.paypal.http.HttpResponse;
import com.paypal.http.annotations.ListOf;
import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;
import com.paypal.orders.LinkDescription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.github.jhipster.application.config.ApplicationProperties;
import io.github.jhipster.application.config.ApplicationProperties.Product;

@Component
public class PayPalClientService implements InitializingBean {

  private final Logger log = LoggerFactory.getLogger(PayPalClientService.class);

  /**
   *Set up the PayPal Java SDK environment with PayPal access credentials.  
   */
  public PayPalEnvironment environment;

  private ApplicationProperties applicationProperties;

  /**
   *PayPal HTTP client instance with environment that has access
   *credentials context. Use to invoke PayPal APIs.
   */
  PayPalHttpClient client;

  /**
   *Method to get client object
   *
   *@return PayPalHttpClient client
   */
  public PayPalHttpClient client() {
    return this.client;
  }

  public PayPalClientService(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
    this.environment = new PayPalEnvironment(
      applicationProperties.getPayPal().getCredentials().getClientId(), 
      applicationProperties.getPayPal().getCredentials().getSecretId(),
      applicationProperties.getPayPal().getCredentials().getBaseUrl(), 
      applicationProperties.getPayPal().getCredentials().getWebUrl()
      );
    this.client = new PayPalHttpClient(this.environment);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.debug("PayPalClientService initialized and running products init");
    // HttpResponse<Product> response = this.client.execute(new ProductsGetRequest());
    Product product = this.applicationProperties.getPayPal().getProduct();

    // Succesfull creation of product on PayPal
    // ProductsCreateRequest productCreateRequest = new ProductsCreateRequest();
    // productCreateRequest.requestBody(product);
    // productCreateRequest.contentType("application/json");
    // productCreateRequest.prefer("prefer=representation");
    // HttpResponse<Product> response = this.client.execute(productCreateRequest);

    ProductsGetRequest productGetRequest = new ProductsGetRequest();
    productGetRequest.contentType("application/json");
    HttpResponse<ProductListResponse> response = this.client.execute(productGetRequest);

    log.debug("Resulting call {} {}", response.statusCode(), response.result());
  }
  
  public class ProductsGetRequest extends HttpRequest<ProductListResponse> {

    public ProductsGetRequest() {
        super("/v1/catalogs/products", "GET", ProductListResponse.class);
        header("Content-Type", "application/json");
    }
    public ProductsGetRequest authorization(String authorization) {
        header("Authorization", String.valueOf(authorization));
        return this;
    }

    public ProductsGetRequest contentType(String contentType) {
        header("Content-Type", String.valueOf(contentType));
        return this;
    }

}

  public class ProductsCreateRequest extends HttpRequest<Product> {

    public ProductsCreateRequest() {
        super("/v1/catalogs/products", "POST", Product.class);
        header("Content-Type", "application/json");
    }
    public ProductsCreateRequest authorization(String authorization) {
        header("Authorization", String.valueOf(authorization));
        return this;
    }

    public ProductsCreateRequest contentType(String contentType) {
        header("Content-Type", String.valueOf(contentType));
        return this;
    }

    public ProductsCreateRequest requestBody(Product product) {
      super.requestBody(product);
      return this;
    }

    public ProductsCreateRequest prefer(String prefer) {
      header("Prefer", String.valueOf(prefer));
      return this;
    }

}

    @Model
    public static class ProductListResponse {

        // @SerializedName("total_items")
        // private int totalItems = 0;

        // @SerializedName("total_pages")
        // private int totalPages = 0;

        @SerializedName(value = "links", listClass=LinkDescription.class)
        private List<LinkDescription> links;

        @SerializedName(value = "products", listClass=PayPalProduct.class)
        private List<PayPalProduct> products;

        public ProductListResponse() {}

        // public int getTotalItems() {
        //     return totalItems;
        // }

        // public ProductListResponse setTotalItems(int totalItems) {
        //     this.totalItems = totalItems;
        //     return this;
        // }

        // public int getTotalPages() {
        //     return totalPages;
        // }

        // public ProductListResponse setTotalPages(int totalPages) {
        //     this.totalPages = totalPages;
        //     return this;
        // }

        public List<PayPalProduct> getProducts() {
            return products;
        }

        public ProductListResponse setProducts(List<PayPalProduct> products) {
            this.products = products;
            return this;
        }

      public List<LinkDescription> getLinks() {
          return links;
      }

      public ProductListResponse setLinks(List<LinkDescription> links) {
          this.links = links;
          return this;
      }

        @Override
        public String toString() {
            return "ProductListResponse{" +
                // "totalItems=" + getTotalItems() +
                // ", totalPages='" + getTotalPages() + "'" +
                "}";
        }
    }
  
}