package io.github.jhipster.application.service;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PayPalClientService {

  @Value("${application.paypal.clientId}")
  private String clientId;

  @Value("${application.paypal.secretId}")
  private String secretId;

  @Value("${application.paypal.baseUrl}")
  private String baseUrl;

  @Value("${application.paypal.webUrl}")
  private String webUrl;

  /**
   *Set up the PayPal Java SDK environment with PayPal access credentials.  
   */
  private PayPalEnvironment environment = new PayPalEnvironment(clientId, secretId, baseUrl, webUrl);

  /**
   *PayPal HTTP client instance with environment that has access
   *credentials context. Use to invoke PayPal APIs.
   */
  PayPalHttpClient client = new PayPalHttpClient(environment);

  /**
   *Method to get client object
   *
   *@return PayPalHttpClient client
   */
  public PayPalHttpClient client() {
    return this.client;
  }
}