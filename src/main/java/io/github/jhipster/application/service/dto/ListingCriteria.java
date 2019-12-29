package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.application.domain.enumeration.ListingType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link io.github.jhipster.application.domain.Listing} entity. This class is used
 * in {@link io.github.jhipster.application.web.rest.ListingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /listings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ListingCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ListingType
     */
    public static class ListingTypeFilter extends Filter<ListingType> {

        public ListingTypeFilter() {
        }

        public ListingTypeFilter(ListingTypeFilter filter) {
            super(filter);
        }

        @Override
        public ListingTypeFilter copy() {
            return new ListingTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter timestamp;

    private ListingTypeFilter type;

    private StringFilter rsn;

    private LongFilter amount;

    private StringFilter description;

    private BooleanFilter active;

    private LongFilter ownerId;

    private LongFilter tagsId;

    public ListingCriteria(){
    }

    public ListingCriteria(ListingCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.rsn = other.rsn == null ? null : other.rsn.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.tagsId = other.tagsId == null ? null : other.tagsId.copy();
    }

    @Override
    public ListingCriteria copy() {
        return new ListingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(InstantFilter timestamp) {
        this.timestamp = timestamp;
    }

    public ListingTypeFilter getType() {
        return type;
    }

    public void setType(ListingTypeFilter type) {
        this.type = type;
    }

    public StringFilter getRsn() {
        return rsn;
    }

    public void setRsn(StringFilter rsn) {
        this.rsn = rsn;
    }

    public LongFilter getAmount() {
        return amount;
    }

    public void setAmount(LongFilter amount) {
        this.amount = amount;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getTagsId() {
        return tagsId;
    }

    public void setTagsId(LongFilter tagsId) {
        this.tagsId = tagsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ListingCriteria that = (ListingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(type, that.type) &&
            Objects.equals(rsn, that.rsn) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(description, that.description) &&
            Objects.equals(active, that.active) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(tagsId, that.tagsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        timestamp,
        type,
        rsn,
        amount,
        description,
        active,
        ownerId,
        tagsId
        );
    }

    @Override
    public String toString() {
        return "ListingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (rsn != null ? "rsn=" + rsn + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (tagsId != null ? "tagsId=" + tagsId + ", " : "") +
            "}";
    }

}
