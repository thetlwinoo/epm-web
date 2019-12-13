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
 * Criteria class for the {@link com.epmweb.server.domain.Suppliers} entity. This class is used
 * in {@link com.epmweb.server.web.rest.SuppliersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /suppliers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SuppliersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter supplierReference;

    private StringFilter bankAccountName;

    private StringFilter bankAccountBranch;

    private StringFilter bankAccountCode;

    private StringFilter bankAccountNumber;

    private StringFilter bankInternationalCode;

    private IntegerFilter paymentDays;

    private StringFilter internalComments;

    private StringFilter phoneNumber;

    private StringFilter faxNumber;

    private StringFilter websiteURL;

    private StringFilter webServiceUrl;

    private IntegerFilter creditRating;

    private BooleanFilter activeFlag;

    private StringFilter thumbnailUrl;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private StringFilter userId;

    private LongFilter supplierCategoryId;

    private LongFilter deliveryMethodId;

    private LongFilter deliveryCityId;

    private LongFilter postalCityId;

    public SuppliersCriteria(){
    }

    public SuppliersCriteria(SuppliersCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.supplierReference = other.supplierReference == null ? null : other.supplierReference.copy();
        this.bankAccountName = other.bankAccountName == null ? null : other.bankAccountName.copy();
        this.bankAccountBranch = other.bankAccountBranch == null ? null : other.bankAccountBranch.copy();
        this.bankAccountCode = other.bankAccountCode == null ? null : other.bankAccountCode.copy();
        this.bankAccountNumber = other.bankAccountNumber == null ? null : other.bankAccountNumber.copy();
        this.bankInternationalCode = other.bankInternationalCode == null ? null : other.bankInternationalCode.copy();
        this.paymentDays = other.paymentDays == null ? null : other.paymentDays.copy();
        this.internalComments = other.internalComments == null ? null : other.internalComments.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.faxNumber = other.faxNumber == null ? null : other.faxNumber.copy();
        this.websiteURL = other.websiteURL == null ? null : other.websiteURL.copy();
        this.webServiceUrl = other.webServiceUrl == null ? null : other.webServiceUrl.copy();
        this.creditRating = other.creditRating == null ? null : other.creditRating.copy();
        this.activeFlag = other.activeFlag == null ? null : other.activeFlag.copy();
        this.thumbnailUrl = other.thumbnailUrl == null ? null : other.thumbnailUrl.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.supplierCategoryId = other.supplierCategoryId == null ? null : other.supplierCategoryId.copy();
        this.deliveryMethodId = other.deliveryMethodId == null ? null : other.deliveryMethodId.copy();
        this.deliveryCityId = other.deliveryCityId == null ? null : other.deliveryCityId.copy();
        this.postalCityId = other.postalCityId == null ? null : other.postalCityId.copy();
    }

    @Override
    public SuppliersCriteria copy() {
        return new SuppliersCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSupplierReference() {
        return supplierReference;
    }

    public void setSupplierReference(StringFilter supplierReference) {
        this.supplierReference = supplierReference;
    }

    public StringFilter getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(StringFilter bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public StringFilter getBankAccountBranch() {
        return bankAccountBranch;
    }

    public void setBankAccountBranch(StringFilter bankAccountBranch) {
        this.bankAccountBranch = bankAccountBranch;
    }

    public StringFilter getBankAccountCode() {
        return bankAccountCode;
    }

    public void setBankAccountCode(StringFilter bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
    }

    public StringFilter getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(StringFilter bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public StringFilter getBankInternationalCode() {
        return bankInternationalCode;
    }

    public void setBankInternationalCode(StringFilter bankInternationalCode) {
        this.bankInternationalCode = bankInternationalCode;
    }

    public IntegerFilter getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(IntegerFilter paymentDays) {
        this.paymentDays = paymentDays;
    }

    public StringFilter getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(StringFilter internalComments) {
        this.internalComments = internalComments;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(StringFilter faxNumber) {
        this.faxNumber = faxNumber;
    }

    public StringFilter getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(StringFilter websiteURL) {
        this.websiteURL = websiteURL;
    }

    public StringFilter getWebServiceUrl() {
        return webServiceUrl;
    }

    public void setWebServiceUrl(StringFilter webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    public IntegerFilter getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(IntegerFilter creditRating) {
        this.creditRating = creditRating;
    }

    public BooleanFilter getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(BooleanFilter activeFlag) {
        this.activeFlag = activeFlag;
    }

    public StringFilter getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(StringFilter thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public InstantFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(InstantFilter validFrom) {
        this.validFrom = validFrom;
    }

    public InstantFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(InstantFilter validTo) {
        this.validTo = validTo;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public LongFilter getSupplierCategoryId() {
        return supplierCategoryId;
    }

    public void setSupplierCategoryId(LongFilter supplierCategoryId) {
        this.supplierCategoryId = supplierCategoryId;
    }

    public LongFilter getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(LongFilter deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public LongFilter getDeliveryCityId() {
        return deliveryCityId;
    }

    public void setDeliveryCityId(LongFilter deliveryCityId) {
        this.deliveryCityId = deliveryCityId;
    }

    public LongFilter getPostalCityId() {
        return postalCityId;
    }

    public void setPostalCityId(LongFilter postalCityId) {
        this.postalCityId = postalCityId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SuppliersCriteria that = (SuppliersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(supplierReference, that.supplierReference) &&
            Objects.equals(bankAccountName, that.bankAccountName) &&
            Objects.equals(bankAccountBranch, that.bankAccountBranch) &&
            Objects.equals(bankAccountCode, that.bankAccountCode) &&
            Objects.equals(bankAccountNumber, that.bankAccountNumber) &&
            Objects.equals(bankInternationalCode, that.bankInternationalCode) &&
            Objects.equals(paymentDays, that.paymentDays) &&
            Objects.equals(internalComments, that.internalComments) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(faxNumber, that.faxNumber) &&
            Objects.equals(websiteURL, that.websiteURL) &&
            Objects.equals(webServiceUrl, that.webServiceUrl) &&
            Objects.equals(creditRating, that.creditRating) &&
            Objects.equals(activeFlag, that.activeFlag) &&
            Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(supplierCategoryId, that.supplierCategoryId) &&
            Objects.equals(deliveryMethodId, that.deliveryMethodId) &&
            Objects.equals(deliveryCityId, that.deliveryCityId) &&
            Objects.equals(postalCityId, that.postalCityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        supplierReference,
        bankAccountName,
        bankAccountBranch,
        bankAccountCode,
        bankAccountNumber,
        bankInternationalCode,
        paymentDays,
        internalComments,
        phoneNumber,
        faxNumber,
        websiteURL,
        webServiceUrl,
        creditRating,
        activeFlag,
        thumbnailUrl,
        validFrom,
        validTo,
        userId,
        supplierCategoryId,
        deliveryMethodId,
        deliveryCityId,
        postalCityId
        );
    }

    @Override
    public String toString() {
        return "SuppliersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (supplierReference != null ? "supplierReference=" + supplierReference + ", " : "") +
                (bankAccountName != null ? "bankAccountName=" + bankAccountName + ", " : "") +
                (bankAccountBranch != null ? "bankAccountBranch=" + bankAccountBranch + ", " : "") +
                (bankAccountCode != null ? "bankAccountCode=" + bankAccountCode + ", " : "") +
                (bankAccountNumber != null ? "bankAccountNumber=" + bankAccountNumber + ", " : "") +
                (bankInternationalCode != null ? "bankInternationalCode=" + bankInternationalCode + ", " : "") +
                (paymentDays != null ? "paymentDays=" + paymentDays + ", " : "") +
                (internalComments != null ? "internalComments=" + internalComments + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (faxNumber != null ? "faxNumber=" + faxNumber + ", " : "") +
                (websiteURL != null ? "websiteURL=" + websiteURL + ", " : "") +
                (webServiceUrl != null ? "webServiceUrl=" + webServiceUrl + ", " : "") +
                (creditRating != null ? "creditRating=" + creditRating + ", " : "") +
                (activeFlag != null ? "activeFlag=" + activeFlag + ", " : "") +
                (thumbnailUrl != null ? "thumbnailUrl=" + thumbnailUrl + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (supplierCategoryId != null ? "supplierCategoryId=" + supplierCategoryId + ", " : "") +
                (deliveryMethodId != null ? "deliveryMethodId=" + deliveryMethodId + ", " : "") +
                (deliveryCityId != null ? "deliveryCityId=" + deliveryCityId + ", " : "") +
                (postalCityId != null ? "postalCityId=" + postalCityId + ", " : "") +
            "}";
    }

}
