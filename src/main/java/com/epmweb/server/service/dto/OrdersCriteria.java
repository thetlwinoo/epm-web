package com.epmweb.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.epmweb.server.domain.enumeration.OrderStatus;
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
 * Criteria class for the {@link com.epmweb.server.domain.Orders} entity. This class is used
 * in {@link com.epmweb.server.web.rest.OrdersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdersCriteria implements Serializable, Criteria {
    /**
     * Class for filtering OrderStatus
     */
    public static class OrderStatusFilter extends Filter<OrderStatus> {

        public OrderStatusFilter() {
        }

        public OrderStatusFilter(OrderStatusFilter filter) {
            super(filter);
        }

        @Override
        public OrderStatusFilter copy() {
            return new OrderStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter orderDate;

    private InstantFilter dueDate;

    private InstantFilter shipDate;

    private IntegerFilter paymentStatus;

    private IntegerFilter orderFlag;

    private StringFilter orderNumber;

    private BigDecimalFilter subTotal;

    private BigDecimalFilter taxAmount;

    private BigDecimalFilter frieight;

    private BigDecimalFilter totalDue;

    private StringFilter comments;

    private StringFilter deliveryInstructions;

    private StringFilter internalComments;

    private InstantFilter pickingCompletedWhen;

    private OrderStatusFilter status;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter orderOnReviewId;

    private LongFilter orderLineListId;

    private LongFilter customerId;

    private LongFilter shipToAddressId;

    private LongFilter billToAddressId;

    private LongFilter shipMethodId;

    private LongFilter currencyRateId;

    private LongFilter paymentTransactionId;

    private LongFilter specialDealsId;

    public OrdersCriteria(){
    }

    public OrdersCriteria(OrdersCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.orderDate = other.orderDate == null ? null : other.orderDate.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.shipDate = other.shipDate == null ? null : other.shipDate.copy();
        this.paymentStatus = other.paymentStatus == null ? null : other.paymentStatus.copy();
        this.orderFlag = other.orderFlag == null ? null : other.orderFlag.copy();
        this.orderNumber = other.orderNumber == null ? null : other.orderNumber.copy();
        this.subTotal = other.subTotal == null ? null : other.subTotal.copy();
        this.taxAmount = other.taxAmount == null ? null : other.taxAmount.copy();
        this.frieight = other.frieight == null ? null : other.frieight.copy();
        this.totalDue = other.totalDue == null ? null : other.totalDue.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.deliveryInstructions = other.deliveryInstructions == null ? null : other.deliveryInstructions.copy();
        this.internalComments = other.internalComments == null ? null : other.internalComments.copy();
        this.pickingCompletedWhen = other.pickingCompletedWhen == null ? null : other.pickingCompletedWhen.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.orderOnReviewId = other.orderOnReviewId == null ? null : other.orderOnReviewId.copy();
        this.orderLineListId = other.orderLineListId == null ? null : other.orderLineListId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.shipToAddressId = other.shipToAddressId == null ? null : other.shipToAddressId.copy();
        this.billToAddressId = other.billToAddressId == null ? null : other.billToAddressId.copy();
        this.shipMethodId = other.shipMethodId == null ? null : other.shipMethodId.copy();
        this.currencyRateId = other.currencyRateId == null ? null : other.currencyRateId.copy();
        this.paymentTransactionId = other.paymentTransactionId == null ? null : other.paymentTransactionId.copy();
        this.specialDealsId = other.specialDealsId == null ? null : other.specialDealsId.copy();
    }

    @Override
    public OrdersCriteria copy() {
        return new OrdersCriteria(this);
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

    public InstantFilter getDueDate() {
        return dueDate;
    }

    public void setDueDate(InstantFilter dueDate) {
        this.dueDate = dueDate;
    }

    public InstantFilter getShipDate() {
        return shipDate;
    }

    public void setShipDate(InstantFilter shipDate) {
        this.shipDate = shipDate;
    }

    public IntegerFilter getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(IntegerFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public IntegerFilter getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(IntegerFilter orderFlag) {
        this.orderFlag = orderFlag;
    }

    public StringFilter getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(StringFilter orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimalFilter getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimalFilter subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimalFilter getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimalFilter taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimalFilter getFrieight() {
        return frieight;
    }

    public void setFrieight(BigDecimalFilter frieight) {
        this.frieight = frieight;
    }

    public BigDecimalFilter getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(BigDecimalFilter totalDue) {
        this.totalDue = totalDue;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public StringFilter getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(StringFilter deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public StringFilter getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(StringFilter internalComments) {
        this.internalComments = internalComments;
    }

    public InstantFilter getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public void setPickingCompletedWhen(InstantFilter pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public OrderStatusFilter getStatus() {
        return status;
    }

    public void setStatus(OrderStatusFilter status) {
        this.status = status;
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

    public LongFilter getOrderOnReviewId() {
        return orderOnReviewId;
    }

    public void setOrderOnReviewId(LongFilter orderOnReviewId) {
        this.orderOnReviewId = orderOnReviewId;
    }

    public LongFilter getOrderLineListId() {
        return orderLineListId;
    }

    public void setOrderLineListId(LongFilter orderLineListId) {
        this.orderLineListId = orderLineListId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getShipToAddressId() {
        return shipToAddressId;
    }

    public void setShipToAddressId(LongFilter shipToAddressId) {
        this.shipToAddressId = shipToAddressId;
    }

    public LongFilter getBillToAddressId() {
        return billToAddressId;
    }

    public void setBillToAddressId(LongFilter billToAddressId) {
        this.billToAddressId = billToAddressId;
    }

    public LongFilter getShipMethodId() {
        return shipMethodId;
    }

    public void setShipMethodId(LongFilter shipMethodId) {
        this.shipMethodId = shipMethodId;
    }

    public LongFilter getCurrencyRateId() {
        return currencyRateId;
    }

    public void setCurrencyRateId(LongFilter currencyRateId) {
        this.currencyRateId = currencyRateId;
    }

    public LongFilter getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(LongFilter paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public LongFilter getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(LongFilter specialDealsId) {
        this.specialDealsId = specialDealsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrdersCriteria that = (OrdersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(orderDate, that.orderDate) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(shipDate, that.shipDate) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(orderFlag, that.orderFlag) &&
            Objects.equals(orderNumber, that.orderNumber) &&
            Objects.equals(subTotal, that.subTotal) &&
            Objects.equals(taxAmount, that.taxAmount) &&
            Objects.equals(frieight, that.frieight) &&
            Objects.equals(totalDue, that.totalDue) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(deliveryInstructions, that.deliveryInstructions) &&
            Objects.equals(internalComments, that.internalComments) &&
            Objects.equals(pickingCompletedWhen, that.pickingCompletedWhen) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(orderOnReviewId, that.orderOnReviewId) &&
            Objects.equals(orderLineListId, that.orderLineListId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(shipToAddressId, that.shipToAddressId) &&
            Objects.equals(billToAddressId, that.billToAddressId) &&
            Objects.equals(shipMethodId, that.shipMethodId) &&
            Objects.equals(currencyRateId, that.currencyRateId) &&
            Objects.equals(paymentTransactionId, that.paymentTransactionId) &&
            Objects.equals(specialDealsId, that.specialDealsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderDate,
        dueDate,
        shipDate,
        paymentStatus,
        orderFlag,
        orderNumber,
        subTotal,
        taxAmount,
        frieight,
        totalDue,
        comments,
        deliveryInstructions,
        internalComments,
        pickingCompletedWhen,
        status,
        lastEditedBy,
        lastEditedWhen,
        orderOnReviewId,
        orderLineListId,
        customerId,
        shipToAddressId,
        billToAddressId,
        shipMethodId,
        currencyRateId,
        paymentTransactionId,
        specialDealsId
        );
    }

    @Override
    public String toString() {
        return "OrdersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderDate != null ? "orderDate=" + orderDate + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (shipDate != null ? "shipDate=" + shipDate + ", " : "") +
                (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
                (orderFlag != null ? "orderFlag=" + orderFlag + ", " : "") +
                (orderNumber != null ? "orderNumber=" + orderNumber + ", " : "") +
                (subTotal != null ? "subTotal=" + subTotal + ", " : "") +
                (taxAmount != null ? "taxAmount=" + taxAmount + ", " : "") +
                (frieight != null ? "frieight=" + frieight + ", " : "") +
                (totalDue != null ? "totalDue=" + totalDue + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (deliveryInstructions != null ? "deliveryInstructions=" + deliveryInstructions + ", " : "") +
                (internalComments != null ? "internalComments=" + internalComments + ", " : "") +
                (pickingCompletedWhen != null ? "pickingCompletedWhen=" + pickingCompletedWhen + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (orderOnReviewId != null ? "orderOnReviewId=" + orderOnReviewId + ", " : "") +
                (orderLineListId != null ? "orderLineListId=" + orderLineListId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (shipToAddressId != null ? "shipToAddressId=" + shipToAddressId + ", " : "") +
                (billToAddressId != null ? "billToAddressId=" + billToAddressId + ", " : "") +
                (shipMethodId != null ? "shipMethodId=" + shipMethodId + ", " : "") +
                (currencyRateId != null ? "currencyRateId=" + currencyRateId + ", " : "") +
                (paymentTransactionId != null ? "paymentTransactionId=" + paymentTransactionId + ", " : "") +
                (specialDealsId != null ? "specialDealsId=" + specialDealsId + ", " : "") +
            "}";
    }

}
