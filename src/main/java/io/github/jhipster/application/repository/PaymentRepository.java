package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Payment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select payment from Payment payment where payment.user.username = ?#{principal.username}")
    List<Payment> findByUserIsCurrentUser();

    @Query("select subscriptionId from Payment payment where payment.user.username = ?#{principal.username} order by payment.timestamp desc")
    List<String> getCurrentSubscriptionForUser(Pageable pageable);

}
