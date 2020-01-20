package io.github.jhipster.application.service;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

import org.springframework.stereotype.Service;

import io.github.jhipster.application.config.ApplicationProperties;

@Service
public class PayPalClientService {

  /**
   *Set up the PayPal Java SDK environment with PayPal access credentials.  
   */
  public PayPalEnvironment environment;

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
    this.environment = new PayPalEnvironment(
      applicationProperties.getPayPal().getClientId(), 
      applicationProperties.getPayPal().getSecretId(),
      applicationProperties.getPayPal().getBaseUrl(), 
      applicationProperties.getPayPal().getWebUrl()
      );
    this.client = new PayPalHttpClient(this.environment);
  }
}