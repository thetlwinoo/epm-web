package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A CustomerTransactions.
 */
@Entity
@Table(name = "customer_transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

    @NotNull
    @Column(name = "amount_excluding_tax", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountExcludingTax;

    @NotNull
    @Column(name = "tax_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    @NotNull
    @Column(name = "transaction_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal transactionAmount;

    @NotNull
    @Column(name = "outstanding_balance", precision = 21, scale = 2, nullable = false)
    private BigDecimal outstandingBalance;

    @Column(name = "finalization_date")
    private Instant finalizationDate;

    @Column(name = "is_finalized")
    private Boolean isFinalized;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @ManyToOne
    @JsonIgnoreProperties("customerTransactions")
    private Customers customer;

    @ManyToOne
    @JsonIgnoreProperties("customerTransactions")
    private PaymentTransactions paymentTransaction;

    @ManyToOne
    @JsonIgnoreProperties("customerTransactions")
    private TransactionTypes transactionType;

    @ManyToOne
    @JsonIgnoreProperties("customerTransactions")
    private Invoices invoice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public CustomerTransactions transactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmountExcludingTax() {
        return amountExcludingTax;
    }

    public CustomerTransactions amountExcludingTax(BigDecimal amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
        return this;
    }

    public void setAmountExcludingTax(BigDecimal amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public CustomerTransactions taxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public CustomerTransactions transactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public CustomerTransactions outstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
        return this;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public Instant getFinalizationDate() {
        return finalizationDate;
    }

    public CustomerTransactions finalizationDate(Instant finalizationDate) {
        this.finalizationDate = finalizationDate;
        return this;
    }

    public void setFinalizationDate(Instant finalizationDate) {
        this.finalizationDate = finalizationDate;
    }

    public Boolean isIsFinalized() {
        return isFinalized;
    }

    public CustomerTransactions isFinalized(Boolean isFinalized) {
        this.isFinalized = isFinalized;
        return this;
    }

    public void setIsFinalized(Boolean isFinalized) {
        this.isFinalized = isFinalized;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public CustomerTransactions lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public CustomerTransactions lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Customers getCustomer() {
        return customer;
    }

    public CustomerTransactions customer(Customers customers) {
        this.customer = customers;
        return this;
    }

    public void setCustomer(Customers customers) {
        this.customer = customers;
    }

    public PaymentTransactions getPaymentTransaction() {
        return paymentTransaction;
    }

    public CustomerTransactions paymentTransaction(PaymentTransactions paymentTransactions) {
        this.paymentTransaction = paymentTransactions;
        return this;
    }

    public void setPaymentTransaction(PaymentTransactions paymentTransactions) {
        this.paymentTransaction = paymentTransactions;
    }

    public TransactionTypes getTransactionType() {
        return transactionType;
    }

    public CustomerTransactions transactionType(TransactionTypes transactionTypes) {
        this.transactionType = transactionTypes;
        return this;
    }

    public void setTransactionType(TransactionTypes transactionTypes) {
        this.transactionType = transactionTypes;
    }

    public Invoices getInvoice() {
        return invoice;
    }

    public CustomerTransactions invoice(Invoices invoices) {
        this.invoice = invoices;
        return this;
    }

    public void setInvoice(Invoices invoices) {
        this.invoice = invoices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerTransactions)) {
            return false;
        }
        return id != null && id.equals(((CustomerTransactions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerTransactions{" +
            "id=" + getId() +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", amountExcludingTax=" + getAmountExcludingTax() +
            ", taxAmount=" + getTaxAmount() +
            ", transactionAmount=" + getTransactionAmount() +
            ", outstandingBalance=" + getOutstandingBalance() +
            ", finalizationDate='" + getFinalizationDate() + "'" +
            ", isFinalized='" + isIsFinalized() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
