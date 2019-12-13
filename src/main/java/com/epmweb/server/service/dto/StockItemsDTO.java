package com.epmweb.server.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.StockItems} entity.
 */
public class StockItemsDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String vendorCode;

    private String vendorSKU;

    private String generatedSKU;

    private String barcode;

    @NotNull
    private BigDecimal unitPrice;

    private BigDecimal recommendedRetailPrice;

    @NotNull
    private Integer quantityOnHand;

    private Integer itemLength;

    private Integer itemWidth;

    private Integer itemHeight;

    private BigDecimal itemWeight;

    private Integer itemPackageLength;

    private Integer itemPackageWidth;

    private Integer itemPackageHeight;

    private BigDecimal itemPackageWeight;

    private Integer noOfPieces;

    private Integer noOfItems;

    private String manufacture;

    private String marketingComments;

    private String internalComments;

    private Instant sellStartDate;

    private Instant sellEndDate;

    private Integer sellCount;

    private String customFields;

    private String thumbnailUrl;

    private Boolean activeInd;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long stockItemOnReviewLineId;

    private Long itemLengthUnitId;

    private String itemLengthUnitCode;

    private Long itemWidthUnitId;

    private String itemWidthUnitCode;

    private Long itemHeightUnitId;

    private String itemHeightUnitCode;

    private Long packageLengthUnitId;

    private String packageLengthUnitCode;

    private Long packageWidthUnitId;

    private String packageWidthUnitCode;

    private Long packageHeightUnitId;

    private String packageHeightUnitCode;

    private Long itemPackageWeightUnitId;

    private String itemPackageWeightUnitCode;

    private Long productAttributeId;

    private String productAttributeValue;

    private Long productOptionId;

    private String productOptionValue;

    private Long materialId;

    private String materialName;

    private Long currencyId;

    private String currencyCode;

    private Long barcodeTypeId;

    private String barcodeTypeName;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGeneratedSKU() {
        return generatedSKU;
    }

    public void setGeneratedSKU(String generatedSKU) {
        this.generatedSKU = generatedSKU;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getRecommendedRetailPrice() {
        return recommendedRetailPrice;
    }

    public void setRecommendedRetailPrice(BigDecimal recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
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

    public String getMarketingComments() {
        return marketingComments;
    }

    public void setMarketingComments(String marketingComments) {
        this.marketingComments = marketingComments;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
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

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getCustomFields() {
        return customFields;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
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

    public Long getStockItemOnReviewLineId() {
        return stockItemOnReviewLineId;
    }

    public void setStockItemOnReviewLineId(Long reviewLinesId) {
        this.stockItemOnReviewLineId = reviewLinesId;
    }

    public Long getItemLengthUnitId() {
        return itemLengthUnitId;
    }

    public void setItemLengthUnitId(Long unitMeasureId) {
        this.itemLengthUnitId = unitMeasureId;
    }

    public String getItemLengthUnitCode() {
        return itemLengthUnitCode;
    }

    public void setItemLengthUnitCode(String unitMeasureCode) {
        this.itemLengthUnitCode = unitMeasureCode;
    }

    public Long getItemWidthUnitId() {
        return itemWidthUnitId;
    }

    public void setItemWidthUnitId(Long unitMeasureId) {
        this.itemWidthUnitId = unitMeasureId;
    }

    public String getItemWidthUnitCode() {
        return itemWidthUnitCode;
    }

    public void setItemWidthUnitCode(String unitMeasureCode) {
        this.itemWidthUnitCode = unitMeasureCode;
    }

    public Long getItemHeightUnitId() {
        return itemHeightUnitId;
    }

    public void setItemHeightUnitId(Long unitMeasureId) {
        this.itemHeightUnitId = unitMeasureId;
    }

    public String getItemHeightUnitCode() {
        return itemHeightUnitCode;
    }

    public void setItemHeightUnitCode(String unitMeasureCode) {
        this.itemHeightUnitCode = unitMeasureCode;
    }

    public Long getPackageLengthUnitId() {
        return packageLengthUnitId;
    }

    public void setPackageLengthUnitId(Long unitMeasureId) {
        this.packageLengthUnitId = unitMeasureId;
    }

    public String getPackageLengthUnitCode() {
        return packageLengthUnitCode;
    }

    public void setPackageLengthUnitCode(String unitMeasureCode) {
        this.packageLengthUnitCode = unitMeasureCode;
    }

    public Long getPackageWidthUnitId() {
        return packageWidthUnitId;
    }

    public void setPackageWidthUnitId(Long unitMeasureId) {
        this.packageWidthUnitId = unitMeasureId;
    }

    public String getPackageWidthUnitCode() {
        return packageWidthUnitCode;
    }

    public void setPackageWidthUnitCode(String unitMeasureCode) {
        this.packageWidthUnitCode = unitMeasureCode;
    }

    public Long getPackageHeightUnitId() {
        return packageHeightUnitId;
    }

    public void setPackageHeightUnitId(Long unitMeasureId) {
        this.packageHeightUnitId = unitMeasureId;
    }

    public String getPackageHeightUnitCode() {
        return packageHeightUnitCode;
    }

    public void setPackageHeightUnitCode(String unitMeasureCode) {
        this.packageHeightUnitCode = unitMeasureCode;
    }

    public Long getItemPackageWeightUnitId() {
        return itemPackageWeightUnitId;
    }

    public void setItemPackageWeightUnitId(Long unitMeasureId) {
        this.itemPackageWeightUnitId = unitMeasureId;
    }

    public String getItemPackageWeightUnitCode() {
        return itemPackageWeightUnitCode;
    }

    public void setItemPackageWeightUnitCode(String unitMeasureCode) {
        this.itemPackageWeightUnitCode = unitMeasureCode;
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

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialsId) {
        this.materialId = materialsId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialsName) {
        this.materialName = materialsName;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getBarcodeTypeId() {
        return barcodeTypeId;
    }

    public void setBarcodeTypeId(Long barcodeTypesId) {
        this.barcodeTypeId = barcodeTypesId;
    }

    public String getBarcodeTypeName() {
        return barcodeTypeName;
    }

    public void setBarcodeTypeName(String barcodeTypesName) {
        this.barcodeTypeName = barcodeTypesName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productsId) {
        this.productId = productsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItemsDTO stockItemsDTO = (StockItemsDTO) o;
        if (stockItemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockItemsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockItemsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vendorCode='" + getVendorCode() + "'" +
            ", vendorSKU='" + getVendorSKU() + "'" +
            ", generatedSKU='" + getGeneratedSKU() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", recommendedRetailPrice=" + getRecommendedRetailPrice() +
            ", quantityOnHand=" + getQuantityOnHand() +
            ", itemLength=" + getItemLength() +
            ", itemWidth=" + getItemWidth() +
            ", itemHeight=" + getItemHeight() +
            ", itemWeight=" + getItemWeight() +
            ", itemPackageLength=" + getItemPackageLength() +
            ", itemPackageWidth=" + getItemPackageWidth() +
            ", itemPackageHeight=" + getItemPackageHeight() +
            ", itemPackageWeight=" + getItemPackageWeight() +
            ", noOfPieces=" + getNoOfPieces() +
            ", noOfItems=" + getNoOfItems() +
            ", manufacture='" + getManufacture() + "'" +
            ", marketingComments='" + getMarketingComments() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", sellStartDate='" + getSellStartDate() + "'" +
            ", sellEndDate='" + getSellEndDate() + "'" +
            ", sellCount=" + getSellCount() +
            ", customFields='" + getCustomFields() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", stockItemOnReviewLine=" + getStockItemOnReviewLineId() +
            ", itemLengthUnit=" + getItemLengthUnitId() +
            ", itemLengthUnit='" + getItemLengthUnitCode() + "'" +
            ", itemWidthUnit=" + getItemWidthUnitId() +
            ", itemWidthUnit='" + getItemWidthUnitCode() + "'" +
            ", itemHeightUnit=" + getItemHeightUnitId() +
            ", itemHeightUnit='" + getItemHeightUnitCode() + "'" +
            ", packageLengthUnit=" + getPackageLengthUnitId() +
            ", packageLengthUnit='" + getPackageLengthUnitCode() + "'" +
            ", packageWidthUnit=" + getPackageWidthUnitId() +
            ", packageWidthUnit='" + getPackageWidthUnitCode() + "'" +
            ", packageHeightUnit=" + getPackageHeightUnitId() +
            ", packageHeightUnit='" + getPackageHeightUnitCode() + "'" +
            ", itemPackageWeightUnit=" + getItemPackageWeightUnitId() +
            ", itemPackageWeightUnit='" + getItemPackageWeightUnitCode() + "'" +
            ", productAttribute=" + getProductAttributeId() +
            ", productAttribute='" + getProductAttributeValue() + "'" +
            ", productOption=" + getProductOptionId() +
            ", productOption='" + getProductOptionValue() + "'" +
            ", material=" + getMaterialId() +
            ", material='" + getMaterialName() + "'" +
            ", currency=" + getCurrencyId() +
            ", currency='" + getCurrencyCode() + "'" +
            ", barcodeType=" + getBarcodeTypeId() +
            ", barcodeType='" + getBarcodeTypeName() + "'" +
            ", product=" + getProductId() +
            "}";
    }
}
