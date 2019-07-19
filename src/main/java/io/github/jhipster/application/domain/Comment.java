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

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @NotNull
    @Column(name = "comment", nullable = false)
    private String comment;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Trade owner;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Offer owner;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private MiddlemanRequest owner;

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

    public User getUser() {
        return user;
    }

    public Comment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trade getOwner() {
        return owner;
    }

    public Comment owner(Trade trade) {
        this.owner = trade;
        return this;
    }

    public void setOwner(Trade trade) {
        this.owner = trade;
    }

    public Offer getOwner() {
        return owner;
    }

    public Comment owner(Offer offer) {
        this.owner = offer;
        return this;
    }

    public void setOwner(Offer offer) {
        this.owner = offer;
    }

    public MiddlemanRequest getOwner() {
        return owner;
    }

    public Comment owner(MiddlemanRequest middlemanRequest) {
        this.owner = middlemanRequest;
        return this;
    }

    public void setOwner(MiddlemanRequest middlemanRequest) {
        this.owner = middlemanRequest;
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
