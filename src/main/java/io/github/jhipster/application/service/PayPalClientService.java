package io.github.jhipster.application.service;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import io.github.jhipster.application.config.ApplicationProperties;
import io.github.jhipster.application.service.paypal.Product;
import io.github.jhipster.application.service.paypal.ProductCreateRequest;
import io.github.jhipster.application.service.paypal.ProductListRequest;
import io.github.jhipster.application.service.paypal.ProductListResponse;

@Component
public class PayPalClientService implements InitializingBean {

  private final Logger log = LoggerFactory.getLogger(PayPalClientService.class);

  /**
   * Set up the PayPal Java SDK environment with PayPal access credentials.
   */
  public PayPalEnvironment environment;

  private ApplicationProperties applicationProperties;

  /**
   * PayPal HTTP client instance with environment that has access credentials
   * context. Use to invoke PayPal APIs.
   */
  PayPalHttpClient client;

  /**
   * Method to get client object
   *
   * @return PayPalHttpClient client
   */
  public PayPalHttpClient client() {
    return this.client;
  }

  public PayPalClientService(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
    this.environment = new PayPalEnvironment(applicationProperties.getPayPal().getCredentials().getClientId(),
        applicationProperties.getPayPal().getCredentials().getSecretId(),
        applicationProperties.getPayPal().getCredentials().getBaseUrl(),
        applicationProperties.getPayPal().getCredentials().getWebUrl());
    this.client = new PayPalHttpClient(this.environment);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.debug("PayPalClientService initialized and running products init");
    Product product = this.applicationProperties.getPayPal().getProduct();

    ProductListRequest productListRequest = new ProductListRequest();
    productListRequest.contentType("application/json");
    HttpResponse<ProductListResponse> productListResponse = this.client.execute(productListRequest);
    
    if (productListResponse.result().getProducts().isEmpty()) {
      ProductCreateRequest productCreateRequest = new ProductCreateRequest();
      productCreateRequest.requestBody(product);
      productCreateRequest.contentType("application/json");
      productCreateRequest.prefer("prefer=representation");
      HttpResponse<Product> response = this.client.execute(productCreateRequest);
      product.setId(response.result().getId());
      log.debug("Created new product on PayPal");
    } else if (productListResponse.result().getProducts().size() == 1) {
      product.setId(productListResponse.result().getProducts().get(0).getId());
      log.debug("Got product from PayPal");
    }
  }
  


  
}