package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Listing entity.
 */
@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Query(value = "SELECT l FROM Listing l where l.active = TRUE")
    Page<Listing> findAllActive(Pageable pageable);

    @Query("select listing from Listing listing")
    List<Listing> findByOwnerIsCurrentUser();

    @Query(value = "select distinct listing from Listing listing left join fetch listing.tags",
        countQuery = "select count(distinct listing) from Listing listing")
    Page<Listing> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct listing from Listing listing left join fetch listing.tags")
    List<Listing> findAllWithEagerRelationships();

    @Query("select listing from Listing listing left join fetch listing.tags where listing.id =:id")
    Optional<Listing> findOneWithEagerRelationships(@Param("id") Long id);

}
