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
 * Criteria class for the {@link com.epmweb.server.domain.InvoiceLines} entity. This class is used
 * in {@link com.epmweb.server.web.rest.InvoiceLinesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoice-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvoiceLinesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private IntegerFilter quantity;

    private BigDecimalFilter unitPrice;

    private BigDecimalFilter taxRate;

    private BigDecimalFilter taxAmount;

    private BigDecimalFilter lineProfit;

    private BigDecimalFilter extendedPrice;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter packageTypeId;

    private LongFilter stockItemId;

    private LongFilter invoiceId;

    public InvoiceLinesCriteria(){
    }

    public InvoiceLinesCriteria(InvoiceLinesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.unitPrice = other.unitPrice == null ? null : other.unitPrice.copy();
        this.taxRate = other.taxRate == null ? null : other.taxRate.copy();
        this.taxAmount = other.taxAmount == null ? null : other.taxAmount.copy();
        this.lineProfit = other.lineProfit == null ? null : other.lineProfit.copy();
        this.extendedPrice = other.extendedPrice == null ? null : other.extendedPrice.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.packageTypeId = other.packageTypeId == null ? null : other.packageTypeId.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
    }

    @Override
    public InvoiceLinesCriteria copy() {
        return new InvoiceLinesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    public BigDecimalFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimalFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimalFilter getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimalFilter taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimalFilter getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimalFilter taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimalFilter getLineProfit() {
        return lineProfit;
    }

    public void setLineProfit(BigDecimalFilter lineProfit) {
        this.lineProfit = lineProfit;
    }

    public BigDecimalFilter getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(BigDecimalFilter extendedPrice) {
        this.extendedPrice = extendedPrice;
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

    public LongFilter getPackageTypeId() {
        return packageTypeId;
    }

    public void setPackageTypeId(LongFilter packageTypeId) {
        this.packageTypeId = packageTypeId;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InvoiceLinesCriteria that = (InvoiceLinesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(taxRate, that.taxRate) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(lineProfit, that.lineProfit) &&
            Objects.equals(extendedPrice, that.extendedPrice) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(packageTypeId, that.packageTypeId) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(invoiceId, that.invoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        quantity,
        unitPrice,
        taxRate,
        taxAmount,
        lineProfit,
        extendedPrice,
        lastEditedBy,
        lastEditedWhen,
        packageTypeId,
        stockItemId,
        invoiceId
        );
    }

    @Override
    public String toString() {
        return "InvoiceLinesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (taxRate != null ? "taxRate=" + taxRate + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (lineProfit != null ? "lineProfit=" + lineProfit + ", " : "") +
                (extendedPrice != null ? "extendedPrice=" + extendedPrice + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (packageTypeId != null ? "packageTypeId=" + packageTypeId + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
            "}";
    }

}
