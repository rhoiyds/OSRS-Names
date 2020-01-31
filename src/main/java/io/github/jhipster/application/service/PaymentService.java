package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Payment;
import io.github.jhipster.application.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Payment}.
 */
@Service
@Transactional
public class PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Save a payment.
     *
     * @param payment the entity to save.
     * @return the persisted entity.
     */
    public Payment save(Payment payment) {
        log.debug("Request to save Payment : {}", payment);
        return paymentRepository.save(payment);
    }

    /**
     * Get all the payments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Payment> findAll() {
        log.debug("Request to get all Payments");
        return paymentRepository.findAll();
    }


    /**
     * Get one payment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Payment> findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        return paymentRepository.findById(id);
    }

    /**
     * Delete the payment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.deleteById(id);
    }

     /**
     * Get subscriptionId of the last payment by id.
     *
     * @param id the id of the entity.
     */
    public Optional<String> getCurrentSubscriptionForUser() {
        log.debug("Request to find current subscription for currently logged user");
        List<String> results = paymentRepository.getCurrentSubscriptionForUser(new PageRequest(0,1));
        return Optional.of(results.get(0));
    }
}
