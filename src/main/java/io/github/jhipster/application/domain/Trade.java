package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

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

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private Offer offer;

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
            ", status='" + getStatus() + "'" +
            "}";
    }
}
