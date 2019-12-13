package com.epmweb.server.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.epmweb.server.domain.StockItemTemp} entity.
 */
public class StockItemTempDTO implements Serializable {

    private Long id;

    @NotNull
    private String stockItemName;

    @NotNull
    private String vendorCode;

    @NotNull
    private String vendorSKU;

    private String barcode;

    private Long barcodeTypeId;

    private String barcodeTypeName;

    private String productType;

    @NotNull
    private Long productCategoryId;

    private String productCategoryName;

    private Long productAttributeSetId;

    private Long productAttributeId;

    private String productAttributeValue;

    private Long productOptionSetId;

    private Long productOptionId;

    private String productOptionValue;

    private String modelName;

    private String modelNumber;

    private Long materialId;

    private String materialName;

    @Lob
    private String shortDescription;

    @Lob
    private String description;

    private Long productBrandId;

    private String productBrandName;

    @Lob
    private String highlights;

    @Lob
    private String searchDetails;

    @Lob
    private String careInstructions;

    private String dangerousGoods;

    private String videoUrl;

    private BigDecimal unitPrice;

    private BigDecimal remommendedRetailPrice;

    private String currencyCode;

    private Integer quantityOnHand;

    private String warrantyPeriod;

    private String warrantyPolicy;

    private Long warrantyTypeId;

    private String warrantyTypeName;

    @Lob
    private String whatInTheBox;

    private Integer itemLength;

    private Integer itemWidth;

    private Integer itemHeight;

    private BigDecimal itemWeight;

    private Integer itemPackageLength;

    private Integer itemPackageWidth;

    private Integer itemPackageHeight;

    private BigDecimal itemPackageWeight;

    private Long itemLengthUnitMeasureId;

    private String itemLengthUnitMeasureCode;

    private Long itemWidthUnitMeasureId;

    private String itemWidthUnitMeasureCode;

    private Long itemHeightUnitMeasureId;

    private String itemHeightUnitMeasureCode;

    private Long itemWeightUnitMeasureId;

    private String itemWeightUnitMeasureCode;

    private Long itemPackageLengthUnitMeasureId;

    private String itemPackageLengthUnitMeasureCode;

    private Long itemPackageWidthUnitMeasureId;

    private String itemPackageWidthUnitMeasureCode;

    private Long itemPackageHeightUnitMeasureId;

    private String itemPackageHeightUnitMeasureCode;

    private Long itemPackageWeightUnitMeasureId;

    private String itemPackageWeightUnitMeasureCode;

    private Integer noOfPieces;

    private Integer noOfItems;

    private String manufacture;

    @Lob
    private String specialFeactures;

    private String productComplianceCertificate;

    private Boolean genuineAndLegal;

    private String countryOfOrigin;

    @Lob
    private String usageAndSideEffects;

    @Lob
    private String safetyWarnning;

    private Instant sellStartDate;

    private Instant sellEndDate;

    private Integer status;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long uploadTransactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(String stockItemName) {
        this.stockItemName = stockItemName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorSKU() {
        return vendorSKU;
    }

    public void setVendorSKU(String vendorSKU) {
        this.vendorSKU = vendorSKU;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Long getBarcodeTypeId() {
        return barcodeTypeId;
    }

    public void setBarcodeTypeId(Long barcodeTypeId) {
        this.barcodeTypeId = barcodeTypeId;
    }

    public String getBarcodeTypeName() {
        return barcodeTypeName;
    }

    public void setBarcodeTypeName(String barcodeTypeName) {
        this.barcodeTypeName = barcodeTypeName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Long getProductAttributeSetId() {
        return productAttributeSetId;
    }

    public void setProductAttributeSetId(Long productAttributeSetId) {
        this.productAttributeSetId = productAttributeSetId;
    }

    public Long getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(Long productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getProductAttributeValue() {
        return productAttributeValue;
    }

    public void setProductAttributeValue(String productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
    }

    public Long getProductOptionSetId() {
        return productOptionSetId;
    }

    public void setProductOptionSetId(Long productOptionSetId) {
        this.productOptionSetId = productOptionSetId;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getProductOptionValue() {
        return productOptionValue;
    }

    public void setProductOptionValue(String productOptionValue) {
        this.productOptionValue = productOptionValue;
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

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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

    public Long getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(Long productBrandId) {
        this.productBrandId = productBrandId;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getSearchDetails() {
        return searchDetails;
    }

    public void setSearchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
    }

    public String getCareInstructions() {
        return careInstructions;
    }

    public void setCareInstructions(String careInstructions) {
        this.careInstructions = careInstructions;
    }

    public String getDangerousGoods() {
        return dangerousGoods;
    }

    public void setDangerousGoods(String dangerousGoods) {
        this.dangerousGoods = dangerousGoods;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getRemommendedRetailPrice() {
        return remommendedRetailPrice;
    }

    public void setRemommendedRetailPrice(BigDecimal remommendedRetailPrice) {
        this.remommendedRetailPrice = remommendedRetailPrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
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

    public void setWarrantyTypeId(Long warrantyTypeId) {
        this.warrantyTypeId = warrantyTypeId;
    }

    public String getWarrantyTypeName() {
        return warrantyTypeName;
    }

    public void setWarrantyTypeName(String warrantyTypeName) {
        this.warrantyTypeName = warrantyTypeName;
    }

    public String getWhatInTheBox() {
        return whatInTheBox;
    }

    public void setWhatInTheBox(String whatInTheBox) {
        this.whatInTheBox = whatInTheBox;
    }

    public Integer getItemLength() {
        return itemLength;
    }

    public void setItemLength(Integer itemLength) {
        this.itemLength = itemLength;
    }

    public Integer getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(Integer itemWidth) {
        this.itemWidth = itemWidth;
    }

    public Integer getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(Integer itemHeight) {
        this.itemHeight = itemHeight;
    }

    public BigDecimal getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(BigDecimal itemWeight) {
        this.itemWeight = itemWeight;
    }

    public Integer getItemPackageLength() {
        return itemPackageLength;
    }

    public void setItemPackageLength(Integer itemPackageLength) {
        this.itemPackageLength = itemPackageLength;
    }

    public Integer getItemPackageWidth() {
        return itemPackageWidth;
    }

    public void setItemPackageWidth(Integer itemPackageWidth) {
        this.itemPackageWidth = itemPackageWidth;
    }

    public Integer getItemPackageHeight() {
        return itemPackageHeight;
    }

    public void setItemPackageHeight(Integer itemPackageHeight) {
        this.itemPackageHeight = itemPackageHeight;
    }

    public BigDecimal getItemPackageWeight() {
        return itemPackageWeight;
    }

    public void setItemPackageWeight(BigDecimal itemPackageWeight) {
        this.itemPackageWeight = itemPackageWeight;
    }

    public Long getItemLengthUnitMeasureId() {
        return itemLengthUnitMeasureId;
    }

    public void setItemLengthUnitMeasureId(Long itemLengthUnitMeasureId) {
        this.itemLengthUnitMeasureId = itemLengthUnitMeasureId;
    }

    public String getItemLengthUnitMeasureCode() {
        return itemLengthUnitMeasureCode;
    }

    public void setItemLengthUnitMeasureCode(String itemLengthUnitMeasureCode) {
        this.itemLengthUnitMeasureCode = itemLengthUnitMeasureCode;
    }

    public Long getItemWidthUnitMeasureId() {
        return itemWidthUnitMeasureId;
    }

    public void setItemWidthUnitMeasureId(Long itemWidthUnitMeasureId) {
        this.itemWidthUnitMeasureId = itemWidthUnitMeasureId;
    }

    public String getItemWidthUnitMeasureCode() {
        return itemWidthUnitMeasureCode;
    }

    public void setItemWidthUnitMeasureCode(String itemWidthUnitMeasureCode) {
        this.itemWidthUnitMeasureCode = itemWidthUnitMeasureCode;
    }

    public Long getItemHeightUnitMeasureId() {
        return itemHeightUnitMeasureId;
    }

    public void setItemHeightUnitMeasureId(Long itemHeightUnitMeasureId) {
        this.itemHeightUnitMeasureId = itemHeightUnitMeasureId;
    }

    public String getItemHeightUnitMeasureCode() {
        return itemHeightUnitMeasureCode;
    }

    public void setItemHeightUnitMeasureCode(String itemHeightUnitMeasureCode) {
        this.itemHeightUnitMeasureCode = itemHeightUnitMeasureCode;
    }

    public Long getItemWeightUnitMeasureId() {
        return itemWeightUnitMeasureId;
    }

    public void setItemWeightUnitMeasureId(Long itemWeightUnitMeasureId) {
        this.itemWeightUnitMeasureId = itemWeightUnitMeasureId;
    }

    public String getItemWeightUnitMeasureCode() {
        return itemWeightUnitMeasureCode;
    }

    public void setItemWeightUnitMeasureCode(String itemWeightUnitMeasureCode) {
        this.itemWeightUnitMeasureCode = itemWeightUnitMeasureCode;
    }

    public Long getItemPackageLengthUnitMeasureId() {
        return itemPackageLengthUnitMeasureId;
    }

    public void setItemPackageLengthUnitMeasureId(Long itemPackageLengthUnitMeasureId) {
        this.itemPackageLengthUnitMeasureId = itemPackageLengthUnitMeasureId;
    }

    public String getItemPackageLengthUnitMeasureCode() {
        return itemPackageLengthUnitMeasureCode;
    }

    public void setItemPackageLengthUnitMeasureCode(String itemPackageLengthUnitMeasureCode) {
        this.itemPackageLengthUnitMeasureCode = itemPackageLengthUnitMeasureCode;
    }

    public Long getItemPackageWidthUnitMeasureId() {
        return itemPackageWidthUnitMeasureId;
    }

    public void setItemPackageWidthUnitMeasureId(Long itemPackageWidthUnitMeasureId) {
        this.itemPackageWidthUnitMeasureId = itemPackageWidthUnitMeasureId;
    }

    public String getItemPackageWidthUnitMeasureCode() {
        return itemPackageWidthUnitMeasureCode;
    }

    public void setItemPackageWidthUnitMeasureCode(String itemPackageWidthUnitMeasureCode) {
        this.itemPackageWidthUnitMeasureCode = itemPackageWidthUnitMeasureCode;
    }

    public Long getItemPackageHeightUnitMeasureId() {
        return itemPackageHeightUnitMeasureId;
    }

    public void setItemPackageHeightUnitMeasureId(Long itemPackageHeightUnitMeasureId) {
        this.itemPackageHeightUnitMeasureId = itemPackageHeightUnitMeasureId;
    }

    public String getItemPackageHeightUnitMeasureCode() {
        return itemPackageHeightUnitMeasureCode;
    }

    public void setItemPackageHeightUnitMeasureCode(String itemPackageHeightUnitMeasureCode) {
        this.itemPackageHeightUnitMeasureCode = itemPackageHeightUnitMeasureCode;
    }

    public Long getItemPackageWeightUnitMeasureId() {
        return itemPackageWeightUnitMeasureId;
    }

    public void setItemPackageWeightUnitMeasureId(Long itemPackageWeightUnitMeasureId) {
        this.itemPackageWeightUnitMeasureId = itemPackageWeightUnitMeasureId;
    }

    public String getItemPackageWeightUnitMeasureCode() {
        return itemPackageWeightUnitMeasureCode;
    }

    public void setItemPackageWeightUnitMeasureCode(String itemPackageWeightUnitMeasureCode) {
        this.itemPackageWeightUnitMeasureCode = itemPackageWeightUnitMeasureCode;
    }

    public Integer getNoOfPieces() {
        return noOfPieces;
    }

    public void setNoOfPieces(Integer noOfPieces) {
        this.noOfPieces = noOfPieces;
    }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(Integer noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getSpecialFeactures() {
        return specialFeactures;
    }

    public void setSpecialFeactures(String specialFeactures) {
        this.specialFeactures = specialFeactures;
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

    public Instant getSellStartDate() {
        return sellStartDate;
    }

    public void setSellStartDate(Instant sellStartDate) {
        this.sellStartDate = sellStartDate;
    }

    public Instant getSellEndDate() {
        return sellEndDate;
    }

    public void setSellEndDate(Instant sellEndDate) {
        this.sellEndDate = sellEndDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public Long getUploadTransactionId() {
        return uploadTransactionId;
    }

    public void setUploadTransactionId(Long uploadTransactionsId) {
        this.uploadTransactionId = uploadTransactionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItemTempDTO stockItemTempDTO = (StockItemTempDTO) o;
        if (stockItemTempDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockItemTempDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockItemTempDTO{" +
            "id=" + getId() +
            ", stockItemName='" + getStockItemName() + "'" +
            ", vendorCode='" + getVendorCode() + "'" +
            ", vendorSKU='" + getVendorSKU() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", barcodeTypeId=" + getBarcodeTypeId() +
            ", barcodeTypeName='" + getBarcodeTypeName() + "'" +
            ", productType='" + getProductType() + "'" +
            ", productCategoryId=" + getProductCategoryId() +
            ", productCategoryName='" + getProductCategoryName() + "'" +
            ", productAttributeSetId=" + getProductAttributeSetId() +
            ", productAttributeId=" + getProductAttributeId() +
            ", productAttributeValue='" + getProductAttributeValue() + "'" +
            ", productOptionSetId=" + getProductOptionSetId() +
            ", productOptionId=" + getProductOptionId() +
            ", productOptionValue='" + getProductOptionValue() + "'" +
            ", modelName='" + getModelName() + "'" +
            ", modelNumber='" + getModelNumber() + "'" +
            ", materialId=" + getMaterialId() +
            ", materialName='" + getMaterialName() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            ", description='" + getDescription() + "'" +
            ", productBrandId=" + getProductBrandId() +
            ", productBrandName='" + getProductBrandName() + "'" +
            ", highlights='" + getHighlights() + "'" +
            ", searchDetails='" + getSearchDetails() + "'" +
            ", careInstructions='" + getCareInstructions() + "'" +
            ", dangerousGoods='" + getDangerousGoods() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", remommendedRetailPrice=" + getRemommendedRetailPrice() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", quantityOnHand=" + getQuantityOnHand() +
            ", warrantyPeriod='" + getWarrantyPeriod() + "'" +
            ", warrantyPolicy='" + getWarrantyPolicy() + "'" +
            ", warrantyTypeId=" + getWarrantyTypeId() +
            ", warrantyTypeName='" + getWarrantyTypeName() + "'" +
            ", whatInTheBox='" + getWhatInTheBox() + "'" +
            ", itemLength=" + getItemLength() +
            ", itemWidth=" + getItemWidth() +
            ", itemHeight=" + getItemHeight() +
            ", itemWeight=" + getItemWeight() +
            ", itemPackageLength=" + getItemPackageLength() +
            ", itemPackageWidth=" + getItemPackageWidth() +
            ", itemPackageHeight=" + getItemPackageHeight() +
            ", itemPackageWeight=" + getItemPackageWeight() +
            ", itemLengthUnitMeasureId=" + getItemLengthUnitMeasureId() +
            ", itemLengthUnitMeasureCode='" + getItemLengthUnitMeasureCode() + "'" +
            ", itemWidthUnitMeasureId=" + getItemWidthUnitMeasureId() +
            ", itemWidthUnitMeasureCode='" + getItemWidthUnitMeasureCode() + "'" +
            ", itemHeightUnitMeasureId=" + getItemHeightUnitMeasureId() +
            ", itemHeightUnitMeasureCode='" + getItemHeightUnitMeasureCode() + "'" +
            ", itemWeightUnitMeasureId=" + getItemWeightUnitMeasureId() +
            ", itemWeightUnitMeasureCode='" + getItemWeightUnitMeasureCode() + "'" +
            ", itemPackageLengthUnitMeasureId=" + getItemPackageLengthUnitMeasureId() +
            ", itemPackageLengthUnitMeasureCode='" + getItemPackageLengthUnitMeasureCode() + "'" +
            ", itemPackageWidthUnitMeasureId=" + getItemPackageWidthUnitMeasureId() +
            ", itemPackageWidthUnitMeasureCode='" + getItemPackageWidthUnitMeasureCode() + "'" +
            ", itemPackageHeightUnitMeasureId=" + getItemPackageHeightUnitMeasureId() +
            ", itemPackageHeightUnitMeasureCode='" + getItemPackageHeightUnitMeasureCode() + "'" +
            ", itemPackageWeightUnitMeasureId=" + getItemPackageWeightUnitMeasureId() +
            ", itemPackageWeightUnitMeasureCode='" + getItemPackageWeightUnitMeasureCode() + "'" +
            ", noOfPieces=" + getNoOfPieces() +
            ", noOfItems=" + getNoOfItems() +
            ", manufacture='" + getManufacture() + "'" +
            ", specialFeactures='" + getSpecialFeactures() + "'" +
            ", productComplianceCertificate='" + getProductComplianceCertificate() + "'" +
            ", genuineAndLegal='" + isGenuineAndLegal() + "'" +
            ", countryOfOrigin='" + getCountryOfOrigin() + "'" +
            ", usageAndSideEffects='" + getUsageAndSideEffects() + "'" +
            ", safetyWarnning='" + getSafetyWarnning() + "'" +
            ", sellStartDate='" + getSellStartDate() + "'" +
            ", sellEndDate='" + getSellEndDate() + "'" +
            ", status=" + getStatus() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", uploadTransaction=" + getUploadTransactionId() +
            "}";
    }
}
