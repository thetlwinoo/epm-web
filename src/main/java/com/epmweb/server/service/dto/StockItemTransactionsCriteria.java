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
 * Criteria class for the {@link com.epmweb.server.domain.StockItemTransactions} entity. This class is used
 * in {@link com.epmweb.server.web.rest.StockItemTransactionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stock-item-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockItemTransactionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter transactionOccuredWhen;

    private BigDecimalFilter quantity;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter stockItemId;

    private LongFilter customerId;

    private LongFilter invoiceId;

    private LongFilter supplierId;

    private LongFilter transactionTypeId;

    private LongFilter purchaseOrderId;

    public StockItemTransactionsCriteria(){
    }

    public StockItemTransactionsCriteria(StockItemTransactionsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.transactionOccuredWhen = other.transactionOccuredWhen == null ? null : other.transactionOccuredWhen.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.transactionTypeId = other.transactionTypeId == null ? null : other.transactionTypeId.copy();
        this.purchaseOrderId = other.purchaseOrderId == null ? null : other.purchaseOrderId.copy();
    }

    @Override
    public StockItemTransactionsCriteria copy() {
        return new StockItemTransactionsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getTransactionOccuredWhen() {
        return transactionOccuredWhen;
    }

    public void setTransactionOccuredWhen(InstantFilter transactionOccuredWhen) {
        this.transactionOccuredWhen = transactionOccuredWhen;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
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

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
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
        final StockItemTransactionsCriteria that = (StockItemTransactionsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(transactionOccuredWhen, that.transactionOccuredWhen) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(transactionTypeId, that.transactionTypeId) &&
            Objects.equals(purchaseOrderId, that.purchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        transactionOccuredWhen,
        quantity,
        lastEditedBy,
        lastEditedWhen,
        stockItemId,
        customerId,
        invoiceId,
        supplierId,
        transactionTypeId,
        purchaseOrderId
        );
    }

    @Override
    public String toString() {
        return "StockItemTransactionsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (transactionOccuredWhen != null ? "transactionOccuredWhen=" + transactionOccuredWhen + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (transactionTypeId != null ? "transactionTypeId=" + transactionTypeId + ", " : "") +
                (purchaseOrderId != null ? "purchaseOrderId=" + purchaseOrderId + ", " : "") +
            "}";
    }

}
