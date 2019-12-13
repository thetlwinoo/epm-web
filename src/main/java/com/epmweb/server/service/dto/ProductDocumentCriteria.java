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

/**
 * Criteria class for the {@link com.epmweb.server.domain.ProductDocument} entity. This class is used
 * in {@link com.epmweb.server.web.rest.ProductDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductDocumentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter videoUrl;

    private StringFilter productType;

    private StringFilter modelName;

    private StringFilter modelNumber;

    private StringFilter fabricType;

    private StringFilter productComplianceCertificate;

    private BooleanFilter genuineAndLegal;

    private StringFilter countryOfOrigin;

    private StringFilter warrantyPeriod;

    private StringFilter warrantyPolicy;

    private LongFilter warrantyTypeId;

    private LongFilter cultureId;

    public ProductDocumentCriteria(){
    }

    public ProductDocumentCriteria(ProductDocumentCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.videoUrl = other.videoUrl == null ? null : other.videoUrl.copy();
        this.productType = other.productType == null ? null : other.productType.copy();
        this.modelName = other.modelName == null ? null : other.modelName.copy();
        this.modelNumber = other.modelNumber == null ? null : other.modelNumber.copy();
        this.fabricType = other.fabricType == null ? null : other.fabricType.copy();
        this.productComplianceCertificate = other.productComplianceCertificate == null ? null : other.productComplianceCertificate.copy();
        this.genuineAndLegal = other.genuineAndLegal == null ? null : other.genuineAndLegal.copy();
        this.countryOfOrigin = other.countryOfOrigin == null ? null : other.countryOfOrigin.copy();
        this.warrantyPeriod = other.warrantyPeriod == null ? null : other.warrantyPeriod.copy();
        this.warrantyPolicy = other.warrantyPolicy == null ? null : other.warrantyPolicy.copy();
        this.warrantyTypeId = other.warrantyTypeId == null ? null : other.warrantyTypeId.copy();
        this.cultureId = other.cultureId == null ? null : other.cultureId.copy();
    }

    @Override
    public ProductDocumentCriteria copy() {
        return new ProductDocumentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(StringFilter videoUrl) {
        this.videoUrl = videoUrl;
    }

    public StringFilter getProductType() {
        return productType;
    }

    public void setProductType(StringFilter productType) {
        this.productType = productType;
    }

    public StringFilter getModelName() {
        return modelName;
    }

    public void setModelName(StringFilter modelName) {
        this.modelName = modelName;
    }

    public StringFilter getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(StringFilter modelNumber) {
        this.modelNumber = modelNumber;
    }

    public StringFilter getFabricType() {
        return fabricType;
    }

    public void setFabricType(StringFilter fabricType) {
        this.fabricType = fabricType;
    }

    public StringFilter getProductComplianceCertificate() {
        return productComplianceCertificate;
    }

    public void setProductComplianceCertificate(StringFilter productComplianceCertificate) {
        this.productComplianceCertificate = productComplianceCertificate;
    }

    public BooleanFilter getGenuineAndLegal() {
        return genuineAndLegal;
    }

    public void setGenuineAndLegal(BooleanFilter genuineAndLegal) {
        this.genuineAndLegal = genuineAndLegal;
    }

    public StringFilter getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(StringFilter countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public StringFilter getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(StringFilter warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public StringFilter getWarrantyPolicy() {
        return warrantyPolicy;
    }

    public void setWarrantyPolicy(StringFilter warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
    }

    public LongFilter getWarrantyTypeId() {
        return warrantyTypeId;
    }

    public void setWarrantyTypeId(LongFilter warrantyTypeId) {
        this.warrantyTypeId = warrantyTypeId;
    }

    public LongFilter getCultureId() {
        return cultureId;
    }

    public void setCultureId(LongFilter cultureId) {
        this.cultureId = cultureId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductDocumentCriteria that = (ProductDocumentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(videoUrl, that.videoUrl) &&
            Objects.equals(productType, that.productType) &&
            Objects.equals(modelName, that.modelName) &&
            Objects.equals(modelNumber, that.modelNumber) &&
            Objects.equals(fabricType, that.fabricType) &&
            Objects.equals(productComplianceCertificate, that.productComplianceCertificate) &&
            Objects.equals(genuineAndLegal, that.genuineAndLegal) &&
            Objects.equals(countryOfOrigin, that.countryOfOrigin) &&
            Objects.equals(warrantyPeriod, that.warrantyPeriod) &&
            Objects.equals(warrantyPolicy, that.warrantyPolicy) &&
            Objects.equals(warrantyTypeId, that.warrantyTypeId) &&
            Objects.equals(cultureId, that.cultureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        videoUrl,
        productType,
        modelName,
        modelNumber,
        fabricType,
        productComplianceCertificate,
        genuineAndLegal,
        countryOfOrigin,
        warrantyPeriod,
        warrantyPolicy,
        warrantyTypeId,
        cultureId
        );
    }

    @Override
    public String toString() {
        return "ProductDocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (videoUrl != null ? "videoUrl=" + videoUrl + ", " : "") +
                (productType != null ? "productType=" + productType + ", " : "") +
                (modelName != null ? "modelName=" + modelName + ", " : "") +
                (modelNumber != null ? "modelNumber=" + modelNumber + ", " : "") +
                (fabricType != null ? "fabricType=" + fabricType + ", " : "") +
                (productComplianceCertificate != null ? "productComplianceCertificate=" + productComplianceCertificate + ", " : "") +
                (genuineAndLegal != null ? "genuineAndLegal=" + genuineAndLegal + ", " : "") +
                (countryOfOrigin != null ? "countryOfOrigin=" + countryOfOrigin + ", " : "") +
                (warrantyPeriod != null ? "warrantyPeriod=" + warrantyPeriod + ", " : "") +
                (warrantyPolicy != null ? "warrantyPolicy=" + warrantyPolicy + ", " : "") +
                (warrantyTypeId != null ? "warrantyTypeId=" + warrantyTypeId + ", " : "") +
                (cultureId != null ? "cultureId=" + cultureId + ", " : "") +
            "}";
    }

}
