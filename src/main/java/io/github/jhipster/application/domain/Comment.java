package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @NotNull
    @Column(name = "comment", nullable = false)
    private String comment;

    @OneToOne(optional = false)
    @JoinColumn()
    private User owner;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Trade trade;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Offer offer;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private MiddlemanRequest middlemanRequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Comment timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public Comment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getOwner() {
        return owner;
    }

    public Comment owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Trade getTrade() {
        return trade;
    }

    public Comment trade(Trade trade) {
        this.trade = trade;
        return this;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public Offer getOffer() {
        return offer;
    }

    public Comment offer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public MiddlemanRequest getMiddlemanRequest() {
        return middlemanRequest;
    }

    public Comment middlemanRequest(MiddlemanRequest middlemanRequest) {
        this.middlemanRequest = middlemanRequest;
        return this;
    }

    public void setMiddlemanRequest(MiddlemanRequest middlemanRequest) {
        this.middlemanRequest = middlemanRequest;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", timestamp='" + getTimestamp() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
