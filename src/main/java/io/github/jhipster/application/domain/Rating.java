package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Rating.
 */
@Entity
@Table(name = "rating")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rating")
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "description")
    private String description;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private User owner;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private User recipient;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private Trade trade;

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

    public Rating timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getRating() {
        return rating;
    }

    public Rating rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public Rating description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public Rating owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public User getRecipient() {
        return recipient;
    }

    public Rating recipient(User user) {
        this.recipient = user;
        return this;
    }

    public void setRecipient(User user) {
        this.recipient = user;
    }

    public Trade getTrade() {
        return trade;
    }

    public Rating trade(Trade trade) {
        this.trade = trade;
        return this;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rating)) {
            return false;
        }
        return id != null && id.equals(((Rating) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rating{" +
            "id=" + getId() +
            ", timestamp='" + getTimestamp() + "'" +
            ", rating=" + getRating() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
