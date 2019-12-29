package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import io.github.jhipster.application.domain.enumeration.TradeStatus;

/**
 * A Trade.
 */
@Entity
@Table(name = "trade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "listing_owner_status")
    private TradeStatus listingOwnerStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_owner_status")
    private TradeStatus offerOwnerStatus;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Offer offer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TradeStatus getListingOwnerStatus() {
        return listingOwnerStatus;
    }

    public Trade listingOwnerStatus(TradeStatus listingOwnerStatus) {
        this.listingOwnerStatus = listingOwnerStatus;
        return this;
    }

    public void setListingOwnerStatus(TradeStatus listingOwnerStatus) {
        this.listingOwnerStatus = listingOwnerStatus;
    }

    public TradeStatus getOfferOwnerStatus() {
        return offerOwnerStatus;
    }

    public Trade offerOwnerStatus(TradeStatus offerOwnerStatus) {
        this.offerOwnerStatus = offerOwnerStatus;
        return this;
    }

    public void setOfferOwnerStatus(TradeStatus offerOwnerStatus) {
        this.offerOwnerStatus = offerOwnerStatus;
    }

    public Offer getOffer() {
        return offer;
    }

    public Trade offer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trade)) {
            return false;
        }
        return id != null && id.equals(((Trade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trade{" +
            "id=" + getId() +
            ", listingOwnerStatus='" + getListingOwnerStatus() + "'" +
            ", offerOwnerStatus='" + getOfferOwnerStatus() + "'" +
            "}";
    }
}
