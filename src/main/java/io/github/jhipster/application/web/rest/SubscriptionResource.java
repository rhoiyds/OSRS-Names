package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.enumeration.TierType;
import io.github.jhipster.application.service.ListingService;
import io.github.jhipster.application.service.PayPalClientService;
import io.github.jhipster.application.service.PaymentService;
import io.github.jhipster.application.service.TradeService;
import io.github.jhipster.application.service.UserService;
import io.github.jhipster.application.service.dto.UserDTO;
import io.github.jhipster.application.service.paypal.Plan;

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

    private final ListingService listingService;

    private final TradeService tradeService;

    public SubscriptionResource(PayPalClientService payPalClientService, UserService userService, PaymentService paymentService, ListingService listingService, TradeService tradeService) {
        this.payPalClientService = payPalClientService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.listingService = listingService;
        this.tradeService = tradeService;
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
        listingService.changeOutstandingListingsStatus(this.tradeService.getAllCompletedTrades(), false);
    }

    /**
     * {@code GET  /subscription/revise/tier} : cancel current subscription for active user
     *
     */
    @PostMapping("/subscription/revise/{tier}")
    public void reviseSubscription(@PathVariable("tier") TierType tier) throws IOException {
        log.debug("REST request to change users current subscription to {}", tier);
        Optional<String> subscriptionId = this.paymentService.getCurrentSubscriptionForUser();
        Plan plan = this.payPalClientService.getPlans().stream().filter(p -> p.getName().toLowerCase().equals(tier.toString().toLowerCase())).findFirst().get();
        this.payPalClientService.reviseSubscription(subscriptionId.get(), plan.getId());
        UserDTO userDTO = userService.getUserWithAuthorities().map(UserDTO::new).orElseThrow();
        userDTO.setTier(tier);
        userService.updateUser(userDTO);
    }

}
