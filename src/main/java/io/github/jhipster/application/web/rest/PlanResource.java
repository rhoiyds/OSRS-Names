package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.service.paypal.Plan;
import io.github.jhipster.application.service.PayPalClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Tag}.
 */
@RestController
@RequestMapping("/api")
public class PlanResource {

    private final Logger log = LoggerFactory.getLogger(TagResource.class);

    private static final String ENTITY_NAME = "plans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayPalClientService payPalClientService;

    public PlanResource(PayPalClientService payPalClientService) {
        this.payPalClientService = payPalClientService;
    }

    /**
     * {@code GET  /plans} : get all the paypal plans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plans in body.
     */
    @GetMapping("/plans")
    public ResponseEntity<List<Plan>> getAllPlans() {
        log.debug("REST request to get Plans");
        return ResponseEntity.ok().body(this.payPalClientService.getPlans());
    }

}
