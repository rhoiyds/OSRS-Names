package io.github.jhipster.application.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.jhipster.application.service.PayPalClientService;
import io.github.jhipster.application.service.dto.PayPalOrder;

import java.io.IOException;

import javax.validation.Valid;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersGetRequest;

/**
 * REST controller for managing Payments.
 */
@RestController
@RequestMapping("/api")
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayPalClientService payPalClientService;

    public PaymentResource(PayPalClientService payPalClientService) {
        this.payPalClientService = payPalClientService;
    }

    /**
     * {@code POST  /paypal-transaction-complete} : get all the tags.
     *
     * @param orderId the order ID from the client.
     * @throws IOException
     */
    @PostMapping("/paypal-transaction-complete")
    public ResponseEntity<PayPalOrder> completeTransaction(@Valid @RequestBody PayPalOrder order) throws IOException {
        log.debug("REST request to complete transation: {}", order);
        OrdersGetRequest request = new OrdersGetRequest(order.getId());
        HttpResponse<Order> response = this.payPalClientService.client().execute(request);
        log.debug("Respone from order details from paypal {}", response.result().toString());
        return ResponseEntity.ok().body(order);
    }
}
