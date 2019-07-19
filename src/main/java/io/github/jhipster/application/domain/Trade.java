package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import io.github.jhipster.application.domain.enumeration.TradeStatusType;

/**
 * A Trade.
 */
@Entity
@Table(name = "trade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "trade")
public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TradeStatusType status;

    @OneToMany(mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToOne(mappedBy = "trade")
    @JsonIgnore
    private Offer offer;

    @OneToOne(mappedBy = "trade")
    @JsonIgnore
    private MiddlemanRequest middlemanRequest;

    @OneToOne(mappedBy = "trade")
    @JsonIgnore
    private Rating rating;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TradeStatusType getStatus() {
        return status;
    }

    public Trade status(TradeStatusType status) {
        this.status = status;
        return this;
    }

    public void setStatus(TradeStatusType status) {
        this.status = status;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Trade comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Trade addComment(Comment comment) {
        this.comments.add(comment);
        comment.setOwner(this);
        return this;
    }

    public Trade removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setOwner(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
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

    public MiddlemanRequest getMiddlemanRequest() {
        return middlemanRequest;
    }

    public Trade middlemanRequest(MiddlemanRequest middlemanRequest) {
        this.middlemanRequest = middlemanRequest;
        return this;
    }

    public void setMiddlemanRequest(MiddlemanRequest middlemanRequest) {
        this.middlemanRequest = middlemanRequest;
    }

    public Rating getRating() {
        return rating;
    }

    public Trade rating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
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
            ", status='" + getStatus() + "'" +
            "}";
    }
}
