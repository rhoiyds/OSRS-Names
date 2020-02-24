package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.Tag;
import io.github.jhipster.application.domain.enumeration.ListingType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data  repository for the Listing entity.
 */
@Repository
public interface ListingRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {

    @Query(value = "SELECT l FROM Listing l where l.active = TRUE")
    Page<Listing> findAllActive(Pageable pageable);

    @Query("select listing from Listing listing where listing.owner.username = ?#{principal.username}")
    List<Listing> findByOwnerIsCurrentUser();

    @Query(value = "select distinct listing from Listing listing left join fetch listing.tags",
        countQuery = "select count(distinct listing) from Listing listing")
    Page<Listing> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct listing from Listing listing left join fetch listing.tags")
    List<Listing> findAllWithEagerRelationships();

    @Query("select listing from Listing listing left join fetch listing.tags where listing.id =:id")
    Optional<Listing> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select distinct listing from Listing listing left join fetch listing.tags where listing.owner.username = ?#{principal.username} and listing not in (:listings)")
    List<Listing> getOutstandingListings(@Param("listings") List<Listing> listings);

    @Query("select count(distinct listing) from Listing listing where listing.owner.username = ?#{principal.username}")
    Integer countTotalListingsForUser();

    List<Listing> findByRsnLikeOrTagsIn(String rsn, Set<Tag> tags);

    Long countByTypeIsAndActiveTrue(ListingType type);

    Long countByTagsContainsAndActiveTrue(Tag tag);
    
}
