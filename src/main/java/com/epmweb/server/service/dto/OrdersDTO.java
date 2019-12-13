package com.epmweb.server.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.epmweb.server.domain.enumeration.OrderStatus;

/**
 * A DTO for the {@link com.epmweb.server.domain.Orders} entity.
 */
public class OrdersDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant orderDate;

    private Instant dueDate;

    private Instant shipDate;

    private Integer paymentStatus;

    private Integer orderFlag;

    private String orderNumber;

    private BigDecimal subTotal;

    private BigDecimal taxAmount;

    private BigDecimal frieight;

    private BigDecimal totalDue;

    private String comments;

    private String deliveryInstructions;

    private String internalComments;

    private Instant pickingCompletedWhen;

    @NotNull
    private OrderStatus status;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long orderOnReviewId;

    private Long customerId;

    private Long shipToAddressId;

    private Long billToAddressId;

    private Long shipMethodId;

    private String shipMethodName;

    private Long currencyRateId;

    private Long specialDealsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getShipDate() {
        return shipDate;
    }

    public void setShipDate(Instant shipDate) {
        this.shipDate = shipDate;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getFrieight() {
        return frieight;
    }

    public void setFrieight(BigDecimal frieight) {
        this.frieight = frieight;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(BigDecimal totalDue) {
        this.totalDue = totalDue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public Instant getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public void setPickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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

    public Long getOrderOnReviewId() {
        return orderOnReviewId;
    }

    public void setOrderOnReviewId(Long reviewsId) {
        this.orderOnReviewId = reviewsId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public Long getShipToAddressId() {
        return shipToAddressId;
    }

    public void setShipToAddressId(Long addressesId) {
        this.shipToAddressId = addressesId;
    }

    public Long getBillToAddressId() {
        return billToAddressId;
    }

    public void setBillToAddressId(Long addressesId) {
        this.billToAddressId = addressesId;
    }

    public Long getShipMethodId() {
        return shipMethodId;
    }

    public void setShipMethodId(Long shipMethodId) {
        this.shipMethodId = shipMethodId;
    }

    public String getShipMethodName() {
        return shipMethodName;
    }

    public void setShipMethodName(String shipMethodName) {
        this.shipMethodName = shipMethodName;
    }

    public Long getCurrencyRateId() {
        return currencyRateId;
    }

    public void setCurrencyRateId(Long currencyRateId) {
        this.currencyRateId = currencyRateId;
    }

    public Long getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(Long specialDealsId) {
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

        OrdersDTO ordersDTO = (OrdersDTO) o;
        if (ordersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", shipDate='" + getShipDate() + "'" +
            ", paymentStatus=" + getPaymentStatus() +
            ", orderFlag=" + getOrderFlag() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", subTotal=" + getSubTotal() +
            ", taxAmount=" + getTaxAmount() +
            ", frieight=" + getFrieight() +
            ", totalDue=" + getTotalDue() +
            ", comments='" + getComments() + "'" +
            ", deliveryInstructions='" + getDeliveryInstructions() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", pickingCompletedWhen='" + getPickingCompletedWhen() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", orderOnReview=" + getOrderOnReviewId() +
            ", customer=" + getCustomerId() +
            ", shipToAddress=" + getShipToAddressId() +
            ", billToAddress=" + getBillToAddressId() +
            ", shipMethod=" + getShipMethodId() +
            ", shipMethod='" + getShipMethodName() + "'" +
            ", currencyRate=" + getCurrencyRateId() +
            ", specialDeals=" + getSpecialDealsId() +
            "}";
    }
}
