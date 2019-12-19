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
 * Criteria class for the {@link io.github.jhipster.application.domain.Rating} entity. This class is used
 * in {@link io.github.jhipster.application.web.rest.RatingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ratings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RatingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter score;

    private StringFilter message;

    private InstantFilter timestamp;

    private LongFilter ownerId;

    private LongFilter recipientId;

    private LongFilter tradeId;

    public RatingCriteria(){
    }

    public RatingCriteria(RatingCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.score = other.score == null ? null : other.score.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.recipientId = other.recipientId == null ? null : other.recipientId.copy();
        this.tradeId = other.tradeId == null ? null : other.tradeId.copy();
    }

    @Override
    public RatingCriteria copy() {
        return new RatingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getScore() {
        return score;
    }

    public void setScore(IntegerFilter score) {
        this.score = score;
    }

    public StringFilter getMessage() {
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public InstantFilter getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(InstantFilter timestamp) {
        this.timestamp = timestamp;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(LongFilter recipientId) {
        this.recipientId = recipientId;
    }

    public LongFilter getTradeId() {
        return tradeId;
    }

    public void setTradeId(LongFilter tradeId) {
        this.tradeId = tradeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RatingCriteria that = (RatingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(score, that.score) &&
            Objects.equals(message, that.message) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(recipientId, that.recipientId) &&
            Objects.equals(tradeId, that.tradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        score,
        message,
        timestamp,
        ownerId,
        recipientId,
        tradeId
        );
    }

    @Override
    public String toString() {
        return "RatingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (score != null ? "score=" + score + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (recipientId != null ? "recipientId=" + recipientId + ", " : "") +
                (tradeId != null ? "tradeId=" + tradeId + ", " : "") +
            "}";
    }

}
