package io.github.jhipster.application.service;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.stereotype.Service;


@Service
public class PayPalClientService {

  /**
   *Set up the PayPal Java SDK environment with PayPal access credentials.  
   *This sample uses SandboxEnvironment. In production, use LiveEnvironment.
   */
  private PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
    "ARwEW4CwV1LibQSbCy1uEBcV56-F8UynTe3bs0vPGxJjh56oTCXQdNFPOBWdTvZEFqrWeSRfqsMP4VO1",
    "EHW5Kb2G7sl8vcqqQyliG3I5nSBF5imwKKHWDCNyQJn3Ac-oDo7491r6FvN2R1_jVab5fkzgooBTObdH");

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