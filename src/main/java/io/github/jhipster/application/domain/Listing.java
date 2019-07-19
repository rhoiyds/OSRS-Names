package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import io.github.jhipster.application.domain.enumeration.ListingType;

/**
 * A Listing.
 */
@Entity
@Table(name = "listing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "listing")
public class Listing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ListingType type;

    @NotNull
    @Column(name = "rsn", nullable = false)
    private String rsn;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "listing")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Offer> offers = new HashSet<>();

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

    public Listing timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public ListingType getType() {
        return type;
    }

    public Listing type(ListingType type) {
        this.type = type;
        return this;
    }

    public void setType(ListingType type) {
        this.type = type;
    }

    public String getRsn() {
        return rsn;
    }

    public Listing rsn(String rsn) {
        this.rsn = rsn;
        return this;
    }

    public void setRsn(String rsn) {
        this.rsn = rsn;
    }

    public Long getAmount() {
        return amount;
    }

    public Listing amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public Listing description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public Listing user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public Listing offers(Set<Offer> offers) {
        this.offers = offers;
        return this;
    }

    public Listing addOffer(Offer offer) {
        this.offers.add(offer);
        offer.setListing(this);
        return this;
    }

    public Listing removeOffer(Offer offer) {
        this.offers.remove(offer);
        offer.setListing(null);
        return this;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Listing)) {
            return false;
        }
        return id != null && id.equals(((Listing) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Listing{" +
            "id=" + getId() +
            ", timestamp='" + getTimestamp() + "'" +
            ", type='" + getType() + "'" +
            ", rsn='" + getRsn() + "'" +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
