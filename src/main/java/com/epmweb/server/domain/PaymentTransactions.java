package com.epmweb.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PaymentTransactions.
 */
@Entity
@Table(name = "payment_transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "returned_completed_payment_data", nullable = false)
    private String returnedCompletedPaymentData;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @OneToOne
    @JoinColumn(unique = true)
    private Orders paymentOnOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReturnedCompletedPaymentData() {
        return returnedCompletedPaymentData;
    }

    public PaymentTransactions returnedCompletedPaymentData(String returnedCompletedPaymentData) {
        this.returnedCompletedPaymentData = returnedCompletedPaymentData;
        return this;
    }

    public void setReturnedCompletedPaymentData(String returnedCompletedPaymentData) {
        this.returnedCompletedPaymentData = returnedCompletedPaymentData;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public PaymentTransactions lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public PaymentTransactions lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Orders getPaymentOnOrder() {
        return paymentOnOrder;
    }

    public PaymentTransactions paymentOnOrder(Orders orders) {
        this.paymentOnOrder = orders;
        return this;
    }

    public void setPaymentOnOrder(Orders orders) {
        this.paymentOnOrder = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentTransactions)) {
            return false;
        }
        return id != null && id.equals(((PaymentTransactions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PaymentTransactions{" +
            "id=" + getId() +
            ", returnedCompletedPaymentData='" + getReturnedCompletedPaymentData() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
