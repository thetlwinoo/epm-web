package com.epmweb.server.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.CustomerTransactions} entity.
 */
public class CustomerTransactionsDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant transactionDate;

    @NotNull
    private BigDecimal amountExcludingTax;

    @NotNull
    private BigDecimal taxAmount;

    @NotNull
    private BigDecimal transactionAmount;

    @NotNull
    private BigDecimal outstandingBalance;

    private Instant finalizationDate;

    private Boolean isFinalized;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long customerId;

    private Long paymentTransactionId;

    private Long transactionTypeId;

    private String transactionTypeName;

    private Long invoiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmountExcludingTax() {
        return amountExcludingTax;
    }

    public void setAmountExcludingTax(BigDecimal amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public Instant getFinalizationDate() {
        return finalizationDate;
    }

    public void setFinalizationDate(Instant finalizationDate) {
        this.finalizationDate = finalizationDate;
    }

    public Boolean isIsFinalized() {
        return isFinalized;
    }

    public void setIsFinalized(Boolean isFinalized) {
        this.isFinalized = isFinalized;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public Long getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(Long paymentTransactionsId) {
        this.paymentTransactionId = paymentTransactionsId;
    }

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypesId) {
        this.transactionTypeId = transactionTypesId;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypesName) {
        this.transactionTypeName = transactionTypesName;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoicesId) {
        this.invoiceId = invoicesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerTransactionsDTO customerTransactionsDTO = (CustomerTransactionsDTO) o;
        if (customerTransactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerTransactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerTransactionsDTO{" +
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
            ", customer=" + getCustomerId() +
            ", paymentTransaction=" + getPaymentTransactionId() +
            ", transactionType=" + getTransactionTypeId() +
            ", transactionType='" + getTransactionTypeName() + "'" +
            ", invoice=" + getInvoiceId() +
            "}";
    }
}
