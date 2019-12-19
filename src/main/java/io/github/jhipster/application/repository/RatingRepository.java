package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Rating;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Rating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>, JpaSpecificationExecutor<Rating> {

    @Query("select rating from Rating rating where rating.owner.username = ?#{principal.username}")
    List<Rating> findByOwnerIsCurrentUser();

    @Query("select rating from Rating rating where rating.recipient.username = ?#{principal.username}")
    List<Rating> findByRecipientIsCurrentUser();

}
