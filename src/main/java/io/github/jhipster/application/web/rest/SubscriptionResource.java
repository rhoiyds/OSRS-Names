package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.enumeration.TierType;
import io.github.jhipster.application.service.PayPalClientService;
import io.github.jhipster.application.service.PaymentService;
import io.github.jhipster.application.service.UserService;
import io.github.jhipster.application.service.dto.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

/**
 * REST controller for managing Subscriptions.
 */
@RestController
@RequestMapping("/api")
public class SubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(TagResource.class);

    private static final String ENTITY_NAME = "subscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayPalClientService payPalClientService;

    private final UserService userService;

    private final PaymentService paymentService;


    public SubscriptionResource(PayPalClientService payPalClientService, UserService userService, PaymentService paymentService) {
        this.payPalClientService = payPalClientService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    /**
     * {@code GET  /subscription/cancel} : cancel current subscription for active user
     *
     */
    @PostMapping("/subscription/cancel")
    public void cancelSubscription() throws IOException {
        log.debug("REST request to get cancel users current subscription");
        Optional<String> subscriptionId = this.paymentService.getCurrentSubscriptionForUser();
        this.payPalClientService.cancelSubscription(subscriptionId.get());
        UserDTO userDTO = userService.getUserWithAuthorities().map(UserDTO::new).orElseThrow();
        userDTO.setTier(TierType.FREE);
        userService.updateUser(userDTO);
    }

}
