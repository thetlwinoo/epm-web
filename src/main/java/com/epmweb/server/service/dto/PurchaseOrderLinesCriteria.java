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
 * Criteria class for the {@link com.epmweb.server.domain.PurchaseOrderLines} entity. This class is used
 * in {@link com.epmweb.server.web.rest.PurchaseOrderLinesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /purchase-order-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PurchaseOrderLinesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter orderedOuters;

    private StringFilter description;

    private IntegerFilter receivedOuters;

    private BigDecimalFilter expectedUnitPricePerOuter;

    private InstantFilter lastReceiptDate;

    private BooleanFilter isOrderLineFinalized;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter packageTypeId;

    private LongFilter stockItemId;

    private LongFilter purchaseOrderId;

    public PurchaseOrderLinesCriteria(){
    }

    public PurchaseOrderLinesCriteria(PurchaseOrderLinesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.orderedOuters = other.orderedOuters == null ? null : other.orderedOuters.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.receivedOuters = other.receivedOuters == null ? null : other.receivedOuters.copy();
        this.expectedUnitPricePerOuter = other.expectedUnitPricePerOuter == null ? null : other.expectedUnitPricePerOuter.copy();
        this.lastReceiptDate = other.lastReceiptDate == null ? null : other.lastReceiptDate.copy();
        this.isOrderLineFinalized = other.isOrderLineFinalized == null ? null : other.isOrderLineFinalized.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.packageTypeId = other.packageTypeId == null ? null : other.packageTypeId.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.purchaseOrderId = other.purchaseOrderId == null ? null : other.purchaseOrderId.copy();
    }

    @Override
    public PurchaseOrderLinesCriteria copy() {
        return new PurchaseOrderLinesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getOrderedOuters() {
        return orderedOuters;
    }

    public void setOrderedOuters(IntegerFilter orderedOuters) {
        this.orderedOuters = orderedOuters;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getReceivedOuters() {
        return receivedOuters;
    }

    public void setReceivedOuters(IntegerFilter receivedOuters) {
        this.receivedOuters = receivedOuters;
    }

    public BigDecimalFilter getExpectedUnitPricePerOuter() {
        return expectedUnitPricePerOuter;
    }

    public void setExpectedUnitPricePerOuter(BigDecimalFilter expectedUnitPricePerOuter) {
        this.expectedUnitPricePerOuter = expectedUnitPricePerOuter;
    }

    public InstantFilter getLastReceiptDate() {
        return lastReceiptDate;
    }

    public void setLastReceiptDate(InstantFilter lastReceiptDate) {
        this.lastReceiptDate = lastReceiptDate;
    }

    public BooleanFilter getIsOrderLineFinalized() {
        return isOrderLineFinalized;
    }

    public void setIsOrderLineFinalized(BooleanFilter isOrderLineFinalized) {
        this.isOrderLineFinalized = isOrderLineFinalized;
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
        final PurchaseOrderLinesCriteria that = (PurchaseOrderLinesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(orderedOuters, that.orderedOuters) &&
            Objects.equals(description, that.description) &&
            Objects.equals(receivedOuters, that.receivedOuters) &&
            Objects.equals(expectedUnitPricePerOuter, that.expectedUnitPricePerOuter) &&
            Objects.equals(lastReceiptDate, that.lastReceiptDate) &&
            Objects.equals(isOrderLineFinalized, that.isOrderLineFinalized) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(packageTypeId, that.packageTypeId) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(purchaseOrderId, that.purchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderedOuters,
        description,
        receivedOuters,
        expectedUnitPricePerOuter,
        lastReceiptDate,
        isOrderLineFinalized,
        lastEditedBy,
        lastEditedWhen,
        packageTypeId,
        stockItemId,
        purchaseOrderId
        );
    }

    @Override
    public String toString() {
        return "PurchaseOrderLinesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderedOuters != null ? "orderedOuters=" + orderedOuters + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (receivedOuters != null ? "receivedOuters=" + receivedOuters + ", " : "") +
                (expectedUnitPricePerOuter != null ? "expectedUnitPricePerOuter=" + expectedUnitPricePerOuter + ", " : "") +
                (lastReceiptDate != null ? "lastReceiptDate=" + lastReceiptDate + ", " : "") +
                (isOrderLineFinalized != null ? "isOrderLineFinalized=" + isOrderLineFinalized + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (packageTypeId != null ? "packageTypeId=" + packageTypeId + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (purchaseOrderId != null ? "purchaseOrderId=" + purchaseOrderId + ", " : "") +
            "}";
    }

}
