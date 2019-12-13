package com.epmweb.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.epmweb.server.domain.SupplierTransactions} entity. This class is used
 * in {@link com.epmweb.server.web.rest.SupplierTransactionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /supplier-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplierTransactionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter supplierInvoiceNumber;

    private InstantFilter transactionDate;

    private BigDecimalFilter amountExcludingTax;

    private BigDecimalFilter taxAmount;

    private BigDecimalFilter transactionAmount;

    private BigDecimalFilter outstandingBalance;

    private InstantFilter finalizationDate;

    private BooleanFilter isFinalized;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter supplierId;

    private LongFilter transactionTypeId;

    private LongFilter purchaseOrderId;

    public SupplierTransactionsCriteria(){
    }

    public SupplierTransactionsCriteria(SupplierTransactionsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.supplierInvoiceNumber = other.supplierInvoiceNumber == null ? null : other.supplierInvoiceNumber.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
        this.amountExcludingTax = other.amountExcludingTax == null ? null : other.amountExcludingTax.copy();
        this.taxAmount = other.taxAmount == null ? null : other.taxAmount.copy();
        this.transactionAmount = other.transactionAmount == null ? null : other.transactionAmount.copy();
        this.outstandingBalance = other.outstandingBalance == null ? null : other.outstandingBalance.copy();
        this.finalizationDate = other.finalizationDate == null ? null : other.finalizationDate.copy();
        this.isFinalized = other.isFinalized == null ? null : other.isFinalized.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.transactionTypeId = other.transactionTypeId == null ? null : other.transactionTypeId.copy();
        this.purchaseOrderId = other.purchaseOrderId == null ? null : other.purchaseOrderId.copy();
    }

    @Override
    public SupplierTransactionsCriteria copy() {
        return new SupplierTransactionsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSupplierInvoiceNumber() {
        return supplierInvoiceNumber;
    }

    public void setSupplierInvoiceNumber(StringFilter supplierInvoiceNumber) {
        this.supplierInvoiceNumber = supplierInvoiceNumber;
    }

    public InstantFilter getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(InstantFilter transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimalFilter getAmountExcludingTax() {
        return amountExcludingTax;
    }

    public void setAmountExcludingTax(BigDecimalFilter amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }

    public BigDecimalFilter getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimalFilter taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimalFilter getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimalFilter transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimalFilter getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimalFilter outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public InstantFilter getFinalizationDate() {
        return finalizationDate;
    }

    public void setFinalizationDate(InstantFilter finalizationDate) {
        this.finalizationDate = finalizationDate;
    }

    public BooleanFilter getIsFinalized() {
        return isFinalized;
    }

    public void setIsFinalized(BooleanFilter isFinalized) {
        this.isFinalized = isFinalized;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public InstantFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(InstantFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(LongFilter transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public LongFilter getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(LongFilter purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplierTransactionsCriteria that = (SupplierTransactionsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(supplierInvoiceNumber, that.supplierInvoiceNumber) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(amountExcludingTax, that.amountExcludingTax) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(transactionAmount, that.transactionAmount) &&
            Objects.equals(outstandingBalance, that.outstandingBalance) &&
            Objects.equals(finalizationDate, that.finalizationDate) &&
            Objects.equals(isFinalized, that.isFinalized) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(transactionTypeId, that.transactionTypeId) &&
            Objects.equals(purchaseOrderId, that.purchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        supplierInvoiceNumber,
        transactionDate,
        amountExcludingTax,
        taxAmount,
        transactionAmount,
        outstandingBalance,
        finalizationDate,
        isFinalized,
        lastEditedBy,
        lastEditedWhen,
        supplierId,
        transactionTypeId,
        purchaseOrderId
        );
    }

    @Override
    public String toString() {
        return "SupplierTransactionsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (supplierInvoiceNumber != null ? "supplierInvoiceNumber=" + supplierInvoiceNumber + ", " : "") +
                (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") +
                (amountExcludingTax != null ? "amountExcludingTax=" + amountExcludingTax + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (transactionAmount != null ? "transactionAmount=" + transactionAmount + ", " : "") +
                (outstandingBalance != null ? "outstandingBalance=" + outstandingBalance + ", " : "") +
                (finalizationDate != null ? "finalizationDate=" + finalizationDate + ", " : "") +
                (isFinalized != null ? "isFinalized=" + isFinalized + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (transactionTypeId != null ? "transactionTypeId=" + transactionTypeId + ", " : "") +
                (purchaseOrderId != null ? "purchaseOrderId=" + purchaseOrderId + ", " : "") +
            "}";
    }

}
