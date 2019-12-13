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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.epmweb.server.domain.PurchaseOrders} entity. This class is used
 * in {@link com.epmweb.server.web.rest.PurchaseOrdersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /purchase-orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PurchaseOrdersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter orderDate;

    private InstantFilter expectedDeliveryDate;

    private StringFilter supplierReference;

    private IntegerFilter isOrderFinalized;

    private StringFilter comments;

    private StringFilter internalComments;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter purchaseOrderLineListId;

    private LongFilter contactPersonId;

    private LongFilter supplierId;

    private LongFilter deliveryMethodId;

    public PurchaseOrdersCriteria(){
    }

    public PurchaseOrdersCriteria(PurchaseOrdersCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.orderDate = other.orderDate == null ? null : other.orderDate.copy();
        this.expectedDeliveryDate = other.expectedDeliveryDate == null ? null : other.expectedDeliveryDate.copy();
        this.supplierReference = other.supplierReference == null ? null : other.supplierReference.copy();
        this.isOrderFinalized = other.isOrderFinalized == null ? null : other.isOrderFinalized.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.internalComments = other.internalComments == null ? null : other.internalComments.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.purchaseOrderLineListId = other.purchaseOrderLineListId == null ? null : other.purchaseOrderLineListId.copy();
        this.contactPersonId = other.contactPersonId == null ? null : other.contactPersonId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.deliveryMethodId = other.deliveryMethodId == null ? null : other.deliveryMethodId.copy();
    }

    @Override
    public PurchaseOrdersCriteria copy() {
        return new PurchaseOrdersCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(InstantFilter orderDate) {
        this.orderDate = orderDate;
    }

    public InstantFilter getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(InstantFilter expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public StringFilter getSupplierReference() {
        return supplierReference;
    }

    public void setSupplierReference(StringFilter supplierReference) {
        this.supplierReference = supplierReference;
    }

    public IntegerFilter getIsOrderFinalized() {
        return isOrderFinalized;
    }

    public void setIsOrderFinalized(IntegerFilter isOrderFinalized) {
        this.isOrderFinalized = isOrderFinalized;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public StringFilter getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(StringFilter internalComments) {
        this.internalComments = internalComments;
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

    public LongFilter getPurchaseOrderLineListId() {
        return purchaseOrderLineListId;
    }

    public void setPurchaseOrderLineListId(LongFilter purchaseOrderLineListId) {
        this.purchaseOrderLineListId = purchaseOrderLineListId;
    }

    public LongFilter getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(LongFilter contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(LongFilter deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PurchaseOrdersCriteria that = (PurchaseOrdersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(orderDate, that.orderDate) &&
            Objects.equals(expectedDeliveryDate, that.expectedDeliveryDate) &&
            Objects.equals(supplierReference, that.supplierReference) &&
            Objects.equals(isOrderFinalized, that.isOrderFinalized) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(internalComments, that.internalComments) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(purchaseOrderLineListId, that.purchaseOrderLineListId) &&
            Objects.equals(contactPersonId, that.contactPersonId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(deliveryMethodId, that.deliveryMethodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderDate,
        expectedDeliveryDate,
        supplierReference,
        isOrderFinalized,
        comments,
        internalComments,
        lastEditedBy,
        lastEditedWhen,
        purchaseOrderLineListId,
        contactPersonId,
        supplierId,
        deliveryMethodId
        );
    }

    @Override
    public String toString() {
        return "PurchaseOrdersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderDate != null ? "orderDate=" + orderDate + ", " : "") +
                (expectedDeliveryDate != null ? "expectedDeliveryDate=" + expectedDeliveryDate + ", " : "") +
                (supplierReference != null ? "supplierReference=" + supplierReference + ", " : "") +
                (isOrderFinalized != null ? "isOrderFinalized=" + isOrderFinalized + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (internalComments != null ? "internalComments=" + internalComments + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (purchaseOrderLineListId != null ? "purchaseOrderLineListId=" + purchaseOrderLineListId + ", " : "") +
                (contactPersonId != null ? "contactPersonId=" + contactPersonId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (deliveryMethodId != null ? "deliveryMethodId=" + deliveryMethodId + ", " : "") +
            "}";
    }

}
