package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.MiddlemanRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MiddlemanRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MiddlemanRequestRepository extends JpaRepository<MiddlemanRequest, Long> {

}
