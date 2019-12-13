package com.epmweb.server.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.epmweb.server.domain.ProductDocument} entity.
 */
public class ProductDocumentDTO implements Serializable {

    private Long id;

    private String videoUrl;

    @Lob
    private String highlights;

    @Lob
    private String longDescription;

    @Lob
    private String shortDescription;

    @Lob
    private String description;

    @Lob
    private String careInstructions;

    private String productType;

    private String modelName;

    private String modelNumber;

    private String fabricType;

    @Lob
    private String specialFeatures;

    private String productComplianceCertificate;

    private Boolean genuineAndLegal;

    private String countryOfOrigin;

    @Lob
    private String usageAndSideEffects;

    @Lob
    private String safetyWarnning;

    private String warrantyPeriod;

    private String warrantyPolicy;


    private Long warrantyTypeId;

    private String warrantyTypeName;

    private Long cultureId;

    private String cultureName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCareInstructions() {
        return careInstructions;
    }

    public void setCareInstructions(String careInstructions) {
        this.careInstructions = careInstructions;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getFabricType() {
        return fabricType;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public String getProductComplianceCertificate() {
        return productComplianceCertificate;
    }

    public void setProductComplianceCertificate(String productComplianceCertificate) {
        this.productComplianceCertificate = productComplianceCertificate;
    }

    public Boolean isGenuineAndLegal() {
        return genuineAndLegal;
    }

    public void setGenuineAndLegal(Boolean genuineAndLegal) {
        this.genuineAndLegal = genuineAndLegal;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getUsageAndSideEffects() {
        return usageAndSideEffects;
    }

    public void setUsageAndSideEffects(String usageAndSideEffects) {
        this.usageAndSideEffects = usageAndSideEffects;
    }

    public String getSafetyWarnning() {
        return safetyWarnning;
    }

    public void setSafetyWarnning(String safetyWarnning) {
        this.safetyWarnning = safetyWarnning;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyPolicy() {
        return warrantyPolicy;
    }

    public void setWarrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
    }

    public Long getWarrantyTypeId() {
        return warrantyTypeId;
    }

    public void setWarrantyTypeId(Long warrantyTypesId) {
        this.warrantyTypeId = warrantyTypesId;
    }

    public String getWarrantyTypeName() {
        return warrantyTypeName;
    }

    public void setWarrantyTypeName(String warrantyTypesName) {
        this.warrantyTypeName = warrantyTypesName;
    }

    public Long getCultureId() {
        return cultureId;
    }

    public void setCultureId(Long cultureId) {
        this.cultureId = cultureId;
    }

    public String getCultureName() {
        return cultureName;
    }

    public void setCultureName(String cultureName) {
        this.cultureName = cultureName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDocumentDTO productDocumentDTO = (ProductDocumentDTO) o;
        if (productDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDocumentDTO{" +
            "id=" + getId() +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", highlights='" + getHighlights() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            ", description='" + getDescription() + "'" +
            ", careInstructions='" + getCareInstructions() + "'" +
            ", productType='" + getProductType() + "'" +
            ", modelName='" + getModelName() + "'" +
            ", modelNumber='" + getModelNumber() + "'" +
            ", fabricType='" + getFabricType() + "'" +
            ", specialFeatures='" + getSpecialFeatures() + "'" +
            ", productComplianceCertificate='" + getProductComplianceCertificate() + "'" +
            ", genuineAndLegal='" + isGenuineAndLegal() + "'" +
            ", countryOfOrigin='" + getCountryOfOrigin() + "'" +
            ", usageAndSideEffects='" + getUsageAndSideEffects() + "'" +
            ", safetyWarnning='" + getSafetyWarnning() + "'" +
            ", warrantyPeriod='" + getWarrantyPeriod() + "'" +
            ", warrantyPolicy='" + getWarrantyPolicy() + "'" +
            ", warrantyType=" + getWarrantyTypeId() +
            ", warrantyType='" + getWarrantyTypeName() + "'" +
            ", culture=" + getCultureId() +
            ", culture='" + getCultureName() + "'" +
            "}";
    }
}
