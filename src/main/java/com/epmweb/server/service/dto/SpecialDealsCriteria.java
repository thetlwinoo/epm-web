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
 * Criteria class for the {@link com.epmweb.server.domain.SpecialDeals} entity. This class is used
 * in {@link com.epmweb.server.web.rest.SpecialDealsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /special-deals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SpecialDealsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter dealDescription;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private BigDecimalFilter discountAmount;

    private BigDecimalFilter discountPercentage;

    private StringFilter discountCode;

    private BigDecimalFilter unitPrice;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter cartDiscountId;

    private LongFilter orderDiscountId;

    private LongFilter buyingGroupId;

    private LongFilter customerCategoryId;

    private LongFilter customerId;

    private LongFilter productCategoryId;

    private LongFilter stockItemId;

    public SpecialDealsCriteria(){
    }

    public SpecialDealsCriteria(SpecialDealsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dealDescription = other.dealDescription == null ? null : other.dealDescription.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.discountAmount = other.discountAmount == null ? null : other.discountAmount.copy();
        this.discountPercentage = other.discountPercentage == null ? null : other.discountPercentage.copy();
        this.discountCode = other.discountCode == null ? null : other.discountCode.copy();
        this.unitPrice = other.unitPrice == null ? null : other.unitPrice.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.cartDiscountId = other.cartDiscountId == null ? null : other.cartDiscountId.copy();
        this.orderDiscountId = other.orderDiscountId == null ? null : other.orderDiscountId.copy();
        this.buyingGroupId = other.buyingGroupId == null ? null : other.buyingGroupId.copy();
        this.customerCategoryId = other.customerCategoryId == null ? null : other.customerCategoryId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
    }

    @Override
    public SpecialDealsCriteria copy() {
        return new SpecialDealsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(StringFilter dealDescription) {
        this.dealDescription = dealDescription;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public BigDecimalFilter getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimalFilter discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimalFilter getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimalFilter discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public StringFilter getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(StringFilter discountCode) {
        this.discountCode = discountCode;
    }

    public BigDecimalFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimalFilter unitPrice) {
        this.unitPrice = unitPrice;
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

    public LongFilter getCartDiscountId() {
        return cartDiscountId;
    }

    public void setCartDiscountId(LongFilter cartDiscountId) {
        this.cartDiscountId = cartDiscountId;
    }

    public LongFilter getOrderDiscountId() {
        return orderDiscountId;
    }

    public void setOrderDiscountId(LongFilter orderDiscountId) {
        this.orderDiscountId = orderDiscountId;
    }

    public LongFilter getBuyingGroupId() {
        return buyingGroupId;
    }

    public void setBuyingGroupId(LongFilter buyingGroupId) {
        this.buyingGroupId = buyingGroupId;
    }

    public LongFilter getCustomerCategoryId() {
        return customerCategoryId;
    }

    public void setCustomerCategoryId(LongFilter customerCategoryId) {
        this.customerCategoryId = customerCategoryId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SpecialDealsCriteria that = (SpecialDealsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dealDescription, that.dealDescription) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(discountAmount, that.discountAmount) &&
            Objects.equals(discountPercentage, that.discountPercentage) &&
            Objects.equals(discountCode, that.discountCode) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(cartDiscountId, that.cartDiscountId) &&
            Objects.equals(orderDiscountId, that.orderDiscountId) &&
            Objects.equals(buyingGroupId, that.buyingGroupId) &&
            Objects.equals(customerCategoryId, that.customerCategoryId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(stockItemId, that.stockItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dealDescription,
        startDate,
        endDate,
        discountAmount,
        discountPercentage,
        discountCode,
        unitPrice,
        lastEditedBy,
        lastEditedWhen,
        cartDiscountId,
        orderDiscountId,
        buyingGroupId,
        customerCategoryId,
        customerId,
        productCategoryId,
        stockItemId
        );
    }

    @Override
    public String toString() {
        return "SpecialDealsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dealDescription != null ? "dealDescription=" + dealDescription + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (discountAmount != null ? "discountAmount=" + discountAmount + ", " : "") +
                (discountPercentage != null ? "discountPercentage=" + discountPercentage + ", " : "") +
                (discountCode != null ? "discountCode=" + discountCode + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (cartDiscountId != null ? "cartDiscountId=" + cartDiscountId + ", " : "") +
                (orderDiscountId != null ? "orderDiscountId=" + orderDiscountId + ", " : "") +
                (buyingGroupId != null ? "buyingGroupId=" + buyingGroupId + ", " : "") +
                (customerCategoryId != null ? "customerCategoryId=" + customerCategoryId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
            "}";
    }

}
