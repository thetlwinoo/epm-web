package com.epmweb.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A CurrencyRate.
 */
@Entity
@Table(name = "currency_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CurrencyRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "currency_rate_date", nullable = false)
    private Instant currencyRateDate;

    @Column(name = "fromcode")
    private String fromcode;

    @Column(name = "tocode")
    private String tocode;

    @Column(name = "average_rate", precision = 21, scale = 2)
    private BigDecimal averageRate;

    @Column(name = "end_of_day_rate", precision = 21, scale = 2)
    private BigDecimal endOfDayRate;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCurrencyRateDate() {
        return currencyRateDate;
    }

    public CurrencyRate currencyRateDate(Instant currencyRateDate) {
        this.currencyRateDate = currencyRateDate;
        return this;
    }

    public void setCurrencyRateDate(Instant currencyRateDate) {
        this.currencyRateDate = currencyRateDate;
    }

    public String getFromcode() {
        return fromcode;
    }

    public CurrencyRate fromcode(String fromcode) {
        this.fromcode = fromcode;
        return this;
    }

    public void setFromcode(String fromcode) {
        this.fromcode = fromcode;
    }

    public String getTocode() {
        return tocode;
    }

    public CurrencyRate tocode(String tocode) {
        this.tocode = tocode;
        return this;
    }

    public void setTocode(String tocode) {
        this.tocode = tocode;
    }

    public BigDecimal getAverageRate() {
        return averageRate;
    }

    public CurrencyRate averageRate(BigDecimal averageRate) {
        this.averageRate = averageRate;
        return this;
    }

    public void setAverageRate(BigDecimal averageRate) {
        this.averageRate = averageRate;
    }

    public BigDecimal getEndOfDayRate() {
        return endOfDayRate;
    }

    public CurrencyRate endOfDayRate(BigDecimal endOfDayRate) {
        this.endOfDayRate = endOfDayRate;
        return this;
    }

    public void setEndOfDayRate(BigDecimal endOfDayRate) {
        this.endOfDayRate = endOfDayRate;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public CurrencyRate lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public CurrencyRate lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyRate)) {
            return false;
        }
        return id != null && id.equals(((CurrencyRate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
            "id=" + getId() +
            ", currencyRateDate='" + getCurrencyRateDate() + "'" +
            ", fromcode='" + getFromcode() + "'" +
            ", tocode='" + getTocode() + "'" +
            ", averageRate=" + getAverageRate() +
            ", endOfDayRate=" + getEndOfDayRate() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
