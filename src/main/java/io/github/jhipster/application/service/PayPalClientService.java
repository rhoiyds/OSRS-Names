package io.github.jhipster.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import io.github.jhipster.application.config.ApplicationProperties;
import io.github.jhipster.application.service.paypal.PayPalPlan;
import io.github.jhipster.application.service.paypal.PaymentDefinition;
import io.github.jhipster.application.service.paypal.PlanCreateRequest;
import io.github.jhipster.application.service.paypal.PlanListRequest;
import io.github.jhipster.application.service.paypal.PlanListResponse;
import io.github.jhipster.application.service.paypal.PlanPatch;
import io.github.jhipster.application.service.paypal.PlanUpdateRequest;
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

  List<PayPalPlan> plans = new ArrayList<PayPalPlan>();

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

    List<PayPalPlan> plans = new ArrayList<PayPalPlan>();

    PlanListRequest planListRequest = new PlanListRequest();
    planListRequest.contentType("application/json");
    HttpResponse<PlanListResponse> planListResponse = this.client.execute(planListRequest);
    log.debug("Getting plans result is {}", planListResponse.result());

    if (planListResponse.result() != null ) {
      plans = planListResponse.result().getPlans();
      this.plans.addAll(plans);
      log.debug("There were existing plans they were, {}", plans.toString());
    }

    List<Integer> tierPrices = List.of(2, 4, 7);

    PaymentDefinition genericPaymentDefinition = new PaymentDefinition();

    Map<String, String> amountObject = new HashMap<>();
    amountObject.put("value", "test");
    amountObject.put("currency", "USD");

    genericPaymentDefinition.setName("Regular payment definition");
    genericPaymentDefinition.setCycles("0");
    genericPaymentDefinition.setFrequency("MONTH");
    genericPaymentDefinition.setFrequencyInterval("1");

    PayPalPlan genericPayPalPlan = new PayPalPlan();
    genericPayPalPlan.setType("INFINITE");

    PlanPatch genericPlanPatch = new PlanPatch();
    genericPlanPatch.setPath("/");
    genericPlanPatch.setOp("replace");
    genericPlanPatch.setValue(Map.of("state", "ACTIVE"));

    for (Integer amount : tierPrices) {
      if (plans.stream().noneMatch(plan -> plan.getName().equals("$" + amount + " per month"))) {
        amountObject.put("value", amount + "");
        genericPaymentDefinition.setAmount(amountObject);
        genericPayPalPlan.setPaymentDefinitions(List.of(genericPaymentDefinition));
        genericPayPalPlan.setName("$" + amount + " per month");
        genericPayPalPlan.setDescription("$" + amount + " per month");
        log.debug("Creating plan {}", genericPayPalPlan.toString());
        
        PlanCreateRequest createPlanRequest = new PlanCreateRequest();
        createPlanRequest.contentType("application/json");
        createPlanRequest.requestBody(genericPayPalPlan);
        HttpResponse<PayPalPlan> planCreateResponse = this.client.execute(createPlanRequest);
        log.debug("Created plan {}", planCreateResponse.result().toString());

        log.debug("Updating plan");
        PlanUpdateRequest updatePlanRequest = new PlanUpdateRequest(planCreateResponse.result().getId());
        updatePlanRequest.contentType("application/json");
        updatePlanRequest.requestBody(genericPlanPatch);
        HttpResponse<PayPalPlan> planUpdateResponse = this.client.execute(updatePlanRequest);
        log.debug("Updated plan");
        
        this.plans.add(planUpdateResponse.result());
      }
    }


    
  }
  
}