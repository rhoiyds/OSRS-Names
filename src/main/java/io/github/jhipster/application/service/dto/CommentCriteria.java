package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link io.github.jhipster.application.domain.Comment} entity. This class is used
 * in {@link io.github.jhipster.application.web.rest.CommentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter timestamp;

    private StringFilter text;

    private LongFilter offerId;

    private LongFilter ownerId;

    public CommentCriteria(){
    }

    public CommentCriteria(CommentCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.offerId = other.offerId == null ? null : other.offerId.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
    }

    @Override
    public CommentCriteria copy() {
        return new CommentCriteria(this);
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

    public StringFilter getText() {
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public LongFilter getOfferId() {
        return offerId;
    }

    public void setOfferId(LongFilter offerId) {
        this.offerId = offerId;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommentCriteria that = (CommentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(text, that.text) &&
            Objects.equals(offerId, that.offerId) &&
            Objects.equals(ownerId, that.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        timestamp,
        text,
        offerId,
        ownerId
        );
    }

    @Override
    public String toString() {
        return "CommentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
                (offerId != null ? "offerId=" + offerId + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
            "}";
    }

}
