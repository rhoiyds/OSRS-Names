package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.application.domain.enumeration.TradeStatus;
import io.github.jhipster.application.domain.enumeration.TradeStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.jhipster.application.domain.Trade} entity. This class is used
 * in {@link io.github.jhipster.application.web.rest.TradeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TradeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TradeStatus
     */
    public static class TradeStatusFilter extends Filter<TradeStatus> {

        public TradeStatusFilter() {
        }

        public TradeStatusFilter(TradeStatusFilter filter) {
            super(filter);
        }

        @Override
        public TradeStatusFilter copy() {
            return new TradeStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TradeStatusFilter listingOwnerStatus;

    private TradeStatusFilter offerOwnerStatus;

    private LongFilter offerId;

    public TradeCriteria(){
    }

    public TradeCriteria(TradeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.listingOwnerStatus = other.listingOwnerStatus == null ? null : other.listingOwnerStatus.copy();
        this.offerOwnerStatus = other.offerOwnerStatus == null ? null : other.offerOwnerStatus.copy();
        this.offerId = other.offerId == null ? null : other.offerId.copy();
    }

    @Override
    public TradeCriteria copy() {
        return new TradeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TradeStatusFilter getListingOwnerStatus() {
        return listingOwnerStatus;
    }

    public void setListingOwnerStatus(TradeStatusFilter listingOwnerStatus) {
        this.listingOwnerStatus = listingOwnerStatus;
    }

    public TradeStatusFilter getOfferOwnerStatus() {
        return offerOwnerStatus;
    }

    public void setOfferOwnerStatus(TradeStatusFilter offerOwnerStatus) {
        this.offerOwnerStatus = offerOwnerStatus;
    }

    public LongFilter getOfferId() {
        return offerId;
    }

    public void setOfferId(LongFilter offerId) {
        this.offerId = offerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TradeCriteria that = (TradeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(listingOwnerStatus, that.listingOwnerStatus) &&
            Objects.equals(offerOwnerStatus, that.offerOwnerStatus) &&
            Objects.equals(offerId, that.offerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        listingOwnerStatus,
        offerOwnerStatus,
        offerId
        );
    }

    @Override
    public String toString() {
        return "TradeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (listingOwnerStatus != null ? "listingOwnerStatus=" + listingOwnerStatus + ", " : "") +
                (offerOwnerStatus != null ? "offerOwnerStatus=" + offerOwnerStatus + ", " : "") +
                (offerId != null ? "offerId=" + offerId + ", " : "") +
            "}";
    }

}
