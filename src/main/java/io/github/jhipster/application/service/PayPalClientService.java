package io.github.jhipster.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;

import io.github.jhipster.application.service.paypal.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import io.github.jhipster.application.config.ApplicationProperties;

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

  List<String> tierPrices = new ArrayList<String>();
  tierPrices.add("1.99");
  tierPrices.add("2.99");
  tierPrices.add("4.99");

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
    } else if (!productListResponse.result().getProducts().isEmpty()) {
      product.setId(productListResponse.result().getProducts().get(0).getId());
      log.debug("Got product from PayPal");
    }
    List<PayPalPlan> plans = new ArrayList<PayPalPlan>();

    PlanListRequest planListRequest = new PlanListRequest();
    planListRequest.contentType("application/json");
    HttpResponse<PlanListResponse> planListResponse = this.client.execute(planListRequest);
    log.debug("Getting plans result is {}", planListResponse.result());

    if (planListResponse.result() != null && !planListResponse.result().getPlans().isEmpty()) {
      plans = planListResponse.result().getPlans();
      this.plans.addAll(plans);
      log.debug("There were existing plans they were, {}", plans.toString());
    }

    Map<String, String> amountObject = new HashMap<>();
    amountObject.put("value", "test");
    amountObject.put("currency", "USD");

    Frequency genericFrequency = new Frequency();
    genericFrequency.setIntervalCount(1);
    genericFrequency.setIntervalUnit("MONTH");


    FixedPrice genericFixedPrice = new FixedPrice().setCurrencyCode("USD").setValue("1.99");
      PricingScheme genericPricingScheme = new PricingScheme();
      genericPricingScheme.setFixedPrice(genericFixedPrice);

      FixedPrice genericFreePrice = new FixedPrice().setCurrencyCode("USD").setValue("0");

      PaymentPreferences genericPaymentPreferences = new PaymentPreferences()
          .setAutoBillOutstanding(true)
          .setSetupFee(genericFreePrice)
          .setSetupFeeFailureAction("CANCEL")
          .setPaymentFailureThreshold(0);

      BillingCycle genericBillingCycle = new BillingCycle();
      genericBillingCycle.setTenureType("REGULAR");
      genericBillingCycle.setTotalCycles(0);
      genericBillingCycle.setFrequency(genericFrequency);
      genericBillingCycle.setSequence(1);
      genericBillingCycle.setPricingScheme(genericPricingScheme);

      List<BillingCycle> billingCycleList = new ArrayList<>();
      billingCycleList.add(genericBillingCycle);

    PayPalPlan genericPayPalPlan = new PayPalPlan();
    genericPayPalPlan.setStatus("ACTIVE");
    genericPayPalPlan.setBillingCycles(billingCycleList);
    genericPayPalPlan.setProductId(product.getId());
    genericPayPalPlan.setPaymentPreferences(genericPaymentPreferences);

    PlanPatch genericPlanPatch = new PlanPatch();
    genericPlanPatch.setPath("/");
    genericPlanPatch.setOp("replace");
    Map<String, String> value = new HashMap<String, String>();
    value.put("state", "ACTIVE");
    genericPlanPatch.setValue(value);

    for (String amount : tierPrices) {
      if (plans.stream().noneMatch(plan -> plan.getName().equals("$" + amount + " per month"))) {
        amountObject.put("value", amount + "");
        genericPayPalPlan.getBillingCycles().get(0).getPricingScheme().getFixedPrice().setValue(amount);

        genericPayPalPlan.setName("$" + amount + " per month");
        genericPayPalPlan.setDescription("$" + amount + " per month");
        log.debug("Creating plan {}", genericPayPalPlan.toString());
        
        PlanCreateRequest createPlanRequest = new PlanCreateRequest();
        createPlanRequest.contentType("application/json");
        createPlanRequest.requestBody(genericPayPalPlan);
        HttpResponse<PayPalPlan> planCreateResponse = this.client.execute(createPlanRequest);
        log.debug("Created plan {}", planCreateResponse.result().toString());

//        log.debug("Updating plan");
//        PlanUpdateRequest updatePlanRequest = new PlanUpdateRequest(planCreateResponse.result().getId());
//        updatePlanRequest.contentType("application/json");
//        updatePlanRequest.requestBody(genericPlanPatch);
//        HttpResponse<PayPalPlan> planUpdateResponse = this.client.execute(updatePlanRequest);
//        log.debug("Updated plan");
//
//        this.plans.add(planUpdateResponse.result());
      }
    }


    
  }
  
}
