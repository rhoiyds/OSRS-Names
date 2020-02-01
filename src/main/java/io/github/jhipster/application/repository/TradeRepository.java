package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.Trade;
import io.github.jhipster.application.domain.enumeration.TradeStatus;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Trade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TradeRepository extends JpaRepository<Trade, Long>, JpaSpecificationExecutor<Trade> {

    @Query("select trade.offer.listing from Trade trade where trade.offer.listing.owner.username = ?#{principal.username} and trade.offerOwnerStatus != :pendingStatus and trade.listingOwnerStatus != :pendingStatus")
    List<Listing> findAllCompletedTrades(@Param("pendingStatus") TradeStatus pendingStatus);

}
