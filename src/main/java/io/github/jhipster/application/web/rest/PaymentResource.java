package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Payment;
import io.github.jhipster.application.domain.User;
import io.github.jhipster.application.domain.enumeration.TierType;
import io.github.jhipster.application.service.ListingService;
import io.github.jhipster.application.service.PayPalClientService;
import io.github.jhipster.application.service.PaymentService;
import io.github.jhipster.application.service.TradeService;
import io.github.jhipster.application.service.UserService;
import io.github.jhipster.application.service.dto.UserDTO;
import io.github.jhipster.application.service.paypal.SubscriptionGetRequest;
import io.github.jhipster.application.service.paypal.SubscriptionGetResponse;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import com.paypal.http.HttpResponse;

import java.time.Instant;


/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Payment}.
 */
@RestController
@RequestMapping("/api")
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    private static final String ENTITY_NAME = "payment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentService paymentService;

    private final UserService userService;

    private final PayPalClientService payPalClientService;

    private final TradeService tradeService;

    private final ListingService listingService;

    public PaymentResource(PaymentService paymentService, UserService userService, PayPalClientService payPalClientService, TradeService tradeService, ListingService listingService) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.payPalClientService = payPalClientService;
        this.tradeService = tradeService;
        this.listingService = listingService;
    }

    /**
     * {@code POST  /payments} : Create a new payment.
     *
     * @param payment the payment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payment, or with status {@code 400 (Bad Request)} if the payment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) throws URISyntaxException, IOException {
        log.debug("REST request to save Payment : {}", payment);
        if (payment.getId() != null) {
            throw new BadRequestAlertException("A new payment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<User> user = this.userService.getUserWithAuthorities();
        if (!user.isPresent()) {
            throw new BadRequestAlertException("You must create an entity as a logged user", ENTITY_NAME, "notloggeduser");
        }
        payment.setUser(user.get());
        payment.setTimestamp(Instant.now());
        SubscriptionGetRequest request = new SubscriptionGetRequest(payment.getSubscriptionId());
        HttpResponse<SubscriptionGetResponse> response = this.payPalClientService.client().execute(request);
        String tierName = this.payPalClientService.getPlans().stream().filter(plan -> {
           return plan.getId().equals(response.result().getPlanId());
        }).map(plan -> plan.getName()).findFirst().get().toUpperCase();
        Optional<UserDTO> userDTO = user.map(UserDTO::new);
        log.debug("Response Tier from PayPal {}", TierType.valueOf(tierName));
        userDTO.get().setTier(TierType.valueOf(tierName));
        userService.updateUser(userDTO.get());
        Payment result = paymentService.save(payment);
        this.listingService.changeOutstandingListingsStatus(tradeService.getAllCompletedTrades(), true);
        return ResponseEntity.created(new URI("/api/payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payments} : Updates an existing payment.
     *
     * @param payment the payment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payment,
     * or with status {@code 400 (Bad Request)} if the payment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payments")
    public ResponseEntity<Payment> updatePayment(@RequestBody Payment payment) throws URISyntaxException {
        log.debug("REST request to update Payment : {}", payment);
        if (payment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Payment result = paymentService.save(payment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, payment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payments} : get all the payments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payments in body.
     */
    @GetMapping("/payments")
    public List<Payment> getAllPayments() {
        log.debug("REST request to get all Payments");
        return paymentService.findAll();
    }

    /**
     * {@code GET  /payments/:id} : get the "id" payment.
     *
     * @param id the id of the payment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        log.debug("REST request to get Payment : {}", id);
        Optional<Payment> payment = paymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payment);
    }

    /**
     * {@code DELETE  /payments/:id} : delete the "id" payment.
     *
     * @param id the id of the payment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        log.debug("REST request to delete Payment : {}", id);
        paymentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

}
