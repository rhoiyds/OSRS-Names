package io.github.jhipster.application.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;

import io.github.jhipster.application.service.paypal.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.github.jhipster.application.config.ApplicationProperties;

@Component
public class PayPalClientService implements InitializingBean {

  private final Logger log = LoggerFactory.getLogger(PayPalClientService.class);

  public PayPalEnvironment environment;

  private ApplicationProperties applicationProperties;

  PayPalHttpClient client;

  List<Plan> plans = new ArrayList<Plan>();

  Product product;

  public PayPalHttpClient client() {
    return this.client;
  }

  public List<Plan> getPlans() {
    return this.plans;
  }

  public PayPalClientService(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
    this.environment = new PayPalEnvironment(applicationProperties.getPayPal().getCredentials().getClientId(),
        applicationProperties.getPayPal().getCredentials().getSecretId(),
        applicationProperties.getPayPal().getCredentials().getBaseUrl(),
        applicationProperties.getPayPal().getCredentials().getWebUrl());
    this.client = new PayPalHttpClient(this.environment);
  }

  public void cancelSubscription(String subscriptionId) throws IOException {
    SubscriptionCancelRequest subscriptionCancelRequest = new SubscriptionCancelRequest(subscriptionId);
    subscriptionCancelRequest.contentType("application/json");
    subscriptionCancelRequest.requestBody(new SubscriptionCancelReason().setReason("Unsubscribe"));
    this.client.execute(subscriptionCancelRequest);
  }

  @Override
  public void afterPropertiesSet() throws IOException {
    log.debug("PayPalClientService initializing products and plans");
    this.initializeProduct();
    this.initializePlans();
  }

  private void initializeProduct() throws IOException {
    this.product = this.applicationProperties.getPayPal().getProduct();
    ProductListRequest productListRequest = new ProductListRequest();
    productListRequest.contentType("application/json");
    HttpResponse<ProductListResponse> productListResponse = this.client.execute(productListRequest);

    if (productListResponse.result().getProducts().isEmpty()) {
      ProductCreateRequest productCreateRequest = new ProductCreateRequest();
      productCreateRequest.requestBody(product);
      productCreateRequest.contentType("application/json");
      productCreateRequest.prefer("prefer=representation");
      HttpResponse<Product> response = this.client.execute(productCreateRequest);
      this.product.setId(response.result().getId());
      log.debug("Created new product on PayPal {}", response.result().toString());
    } else if (!productListResponse.result().getProducts().isEmpty()) {
      this.product.setId(productListResponse.result().getProducts().get(0).getId());
      log.debug("Retrieved existing product {} from PayPal and set id {}", this.product.getName(), productListResponse.result().getProducts().get(0).getId());
    }
  }

  private void initializePlans() throws IOException {
    this.plans = this.applicationProperties.getPayPal().getPlans();

    PlanListRequest planListRequest = new PlanListRequest();
    planListRequest.contentType("application/json");
    HttpResponse<PlanListResponse> planListResponse = this.client.execute(planListRequest);

    for (Plan plan : this.plans) {
      Optional<PayPalPlan> payPalPlan = planListResponse.result().getPlans().stream()
          .filter(ppp -> ppp.getName().equals(plan.getName())).findFirst();
      if (payPalPlan.isPresent()) {
        plan.setId(payPalPlan.get().getId());
        log.debug("Retrieved existing plan {} from PayPal and set id {}", plan.getName(), plan.getId());
      }
    }

    for (Plan plan : this.plans.stream().filter(plan -> StringUtils.isEmpty(plan.getId())).collect(Collectors.toList())) {
        plan.setId(this.createNewPlan(plan).getId());
    }

    this.plans = this.plans.stream().filter(plan -> !StringUtils.isEmpty(plan.getId())).collect(Collectors.toList());
  }

  private PayPalPlan createNewPlan(Plan plan) throws IOException {

    Frequency frequency = new Frequency();
    frequency.setIntervalCount(1);
    frequency.setIntervalUnit("MONTH");

    FixedPrice fixedPrice = new FixedPrice().setCurrencyCode("USD").setValue(plan.getCost());
    PricingScheme pricingScheme = new PricingScheme();
    pricingScheme.setFixedPrice(fixedPrice);

    FixedPrice freePrice = new FixedPrice().setCurrencyCode("USD").setValue("0");

    PaymentPreferences paymentPreferences = new PaymentPreferences()
        .setAutoBillOutstanding(true)
        .setSetupFee(freePrice)
        .setSetupFeeFailureAction("CANCEL")
        .setPaymentFailureThreshold(0);

    BillingCycle billingCycle = new BillingCycle();
    billingCycle.setTenureType("REGULAR");
    billingCycle.setTotalCycles(0);
    billingCycle.setFrequency(frequency);
    billingCycle.setSequence(1);
    billingCycle.setPricingScheme(pricingScheme);

    List<BillingCycle> billingCycleList = new ArrayList<>();
    billingCycleList.add(billingCycle);

    PayPalPlan payPalPlan = new PayPalPlan();
    payPalPlan.setStatus("ACTIVE");
    payPalPlan.setBillingCycles(billingCycleList);
    payPalPlan.setProductId(this.product.getId());
    payPalPlan.setPaymentPreferences(paymentPreferences);

    payPalPlan.setName(plan.getName());
    payPalPlan.setDescription("Costing $" + plan.getCost() + "USD per month");

    PlanCreateRequest createPlanRequest = new PlanCreateRequest();
    createPlanRequest.contentType("application/json");
    createPlanRequest.requestBody(payPalPlan);
    HttpResponse<PayPalPlan> planCreateResponse = this.client.execute(createPlanRequest);
    log.debug("Created new plan {} on PayPal with id {}", plan.getName(), planCreateResponse.result().getId());
    return planCreateResponse.result();
  }
}
