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
 * Criteria class for the {@link com.epmweb.server.domain.StockItemTemp} entity. This class is used
 * in {@link com.epmweb.server.web.rest.StockItemTempResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stock-item-temps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockItemTempCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter stockItemName;

    private StringFilter vendorCode;

    private StringFilter vendorSKU;

    private StringFilter barcode;

    private LongFilter barcodeTypeId;

    private StringFilter barcodeTypeName;

    private StringFilter productType;

    private LongFilter productCategoryId;

    private StringFilter productCategoryName;

    private LongFilter productAttributeSetId;

    private LongFilter productAttributeId;

    private StringFilter productAttributeValue;

    private LongFilter productOptionSetId;

    private LongFilter productOptionId;

    private StringFilter productOptionValue;

    private StringFilter modelName;

    private StringFilter modelNumber;

    private LongFilter materialId;

    private StringFilter materialName;

    private LongFilter productBrandId;

    private StringFilter productBrandName;

    private StringFilter dangerousGoods;

    private StringFilter videoUrl;

    private BigDecimalFilter unitPrice;

    private BigDecimalFilter remommendedRetailPrice;

    private StringFilter currencyCode;

    private IntegerFilter quantityOnHand;

    private StringFilter warrantyPeriod;

    private StringFilter warrantyPolicy;

    private LongFilter warrantyTypeId;

    private StringFilter warrantyTypeName;

    private IntegerFilter itemLength;

    private IntegerFilter itemWidth;

    private IntegerFilter itemHeight;

    private BigDecimalFilter itemWeight;

    private IntegerFilter itemPackageLength;

    private IntegerFilter itemPackageWidth;

    private IntegerFilter itemPackageHeight;

    private BigDecimalFilter itemPackageWeight;

    private LongFilter itemLengthUnitMeasureId;

    private StringFilter itemLengthUnitMeasureCode;

    private LongFilter itemWidthUnitMeasureId;

    private StringFilter itemWidthUnitMeasureCode;

    private LongFilter itemHeightUnitMeasureId;

    private StringFilter itemHeightUnitMeasureCode;

    private LongFilter itemWeightUnitMeasureId;

    private StringFilter itemWeightUnitMeasureCode;

    private LongFilter itemPackageLengthUnitMeasureId;

    private StringFilter itemPackageLengthUnitMeasureCode;

    private LongFilter itemPackageWidthUnitMeasureId;

    private StringFilter itemPackageWidthUnitMeasureCode;

    private LongFilter itemPackageHeightUnitMeasureId;

    private StringFilter itemPackageHeightUnitMeasureCode;

    private LongFilter itemPackageWeightUnitMeasureId;

    private StringFilter itemPackageWeightUnitMeasureCode;

    private IntegerFilter noOfPieces;

    private IntegerFilter noOfItems;

    private StringFilter manufacture;

    private StringFilter productComplianceCertificate;

    private BooleanFilter genuineAndLegal;

    private StringFilter countryOfOrigin;

    private InstantFilter sellStartDate;

    private InstantFilter sellEndDate;

    private IntegerFilter status;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter uploadTransactionId;

    public StockItemTempCriteria(){
    }

    public StockItemTempCriteria(StockItemTempCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.stockItemName = other.stockItemName == null ? null : other.stockItemName.copy();
        this.vendorCode = other.vendorCode == null ? null : other.vendorCode.copy();
        this.vendorSKU = other.vendorSKU == null ? null : other.vendorSKU.copy();
        this.barcode = other.barcode == null ? null : other.barcode.copy();
        this.barcodeTypeId = other.barcodeTypeId == null ? null : other.barcodeTypeId.copy();
        this.barcodeTypeName = other.barcodeTypeName == null ? null : other.barcodeTypeName.copy();
        this.productType = other.productType == null ? null : other.productType.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.productCategoryName = other.productCategoryName == null ? null : other.productCategoryName.copy();
        this.productAttributeSetId = other.productAttributeSetId == null ? null : other.productAttributeSetId.copy();
        this.productAttributeId = other.productAttributeId == null ? null : other.productAttributeId.copy();
        this.productAttributeValue = other.productAttributeValue == null ? null : other.productAttributeValue.copy();
        this.productOptionSetId = other.productOptionSetId == null ? null : other.productOptionSetId.copy();
        this.productOptionId = other.productOptionId == null ? null : other.productOptionId.copy();
        this.productOptionValue = other.productOptionValue == null ? null : other.productOptionValue.copy();
        this.modelName = other.modelName == null ? null : other.modelName.copy();
        this.modelNumber = other.modelNumber == null ? null : other.modelNumber.copy();
        this.materialId = other.materialId == null ? null : other.materialId.copy();
        this.materialName = other.materialName == null ? null : other.materialName.copy();
        this.productBrandId = other.productBrandId == null ? null : other.productBrandId.copy();
        this.productBrandName = other.productBrandName == null ? null : other.productBrandName.copy();
        this.dangerousGoods = other.dangerousGoods == null ? null : other.dangerousGoods.copy();
        this.videoUrl = other.videoUrl == null ? null : other.videoUrl.copy();
        this.unitPrice = other.unitPrice == null ? null : other.unitPrice.copy();
        this.remommendedRetailPrice = other.remommendedRetailPrice == null ? null : other.remommendedRetailPrice.copy();
        this.currencyCode = other.currencyCode == null ? null : other.currencyCode.copy();
        this.quantityOnHand = other.quantityOnHand == null ? null : other.quantityOnHand.copy();
        this.warrantyPeriod = other.warrantyPeriod == null ? null : other.warrantyPeriod.copy();
        this.warrantyPolicy = other.warrantyPolicy == null ? null : other.warrantyPolicy.copy();
        this.warrantyTypeId = other.warrantyTypeId == null ? null : other.warrantyTypeId.copy();
        this.warrantyTypeName = other.warrantyTypeName == null ? null : other.warrantyTypeName.copy();
        this.itemLength = other.itemLength == null ? null : other.itemLength.copy();
        this.itemWidth = other.itemWidth == null ? null : other.itemWidth.copy();
        this.itemHeight = other.itemHeight == null ? null : other.itemHeight.copy();
        this.itemWeight = other.itemWeight == null ? null : other.itemWeight.copy();
        this.itemPackageLength = other.itemPackageLength == null ? null : other.itemPackageLength.copy();
        this.itemPackageWidth = other.itemPackageWidth == null ? null : other.itemPackageWidth.copy();
        this.itemPackageHeight = other.itemPackageHeight == null ? null : other.itemPackageHeight.copy();
        this.itemPackageWeight = other.itemPackageWeight == null ? null : other.itemPackageWeight.copy();
        this.itemLengthUnitMeasureId = other.itemLengthUnitMeasureId == null ? null : other.itemLengthUnitMeasureId.copy();
        this.itemLengthUnitMeasureCode = other.itemLengthUnitMeasureCode == null ? null : other.itemLengthUnitMeasureCode.copy();
        this.itemWidthUnitMeasureId = other.itemWidthUnitMeasureId == null ? null : other.itemWidthUnitMeasureId.copy();
        this.itemWidthUnitMeasureCode = other.itemWidthUnitMeasureCode == null ? null : other.itemWidthUnitMeasureCode.copy();
        this.itemHeightUnitMeasureId = other.itemHeightUnitMeasureId == null ? null : other.itemHeightUnitMeasureId.copy();
        this.itemHeightUnitMeasureCode = other.itemHeightUnitMeasureCode == null ? null : other.itemHeightUnitMeasureCode.copy();
        this.itemWeightUnitMeasureId = other.itemWeightUnitMeasureId == null ? null : other.itemWeightUnitMeasureId.copy();
        this.itemWeightUnitMeasureCode = other.itemWeightUnitMeasureCode == null ? null : other.itemWeightUnitMeasureCode.copy();
        this.itemPackageLengthUnitMeasureId = other.itemPackageLengthUnitMeasureId == null ? null : other.itemPackageLengthUnitMeasureId.copy();
        this.itemPackageLengthUnitMeasureCode = other.itemPackageLengthUnitMeasureCode == null ? null : other.itemPackageLengthUnitMeasureCode.copy();
        this.itemPackageWidthUnitMeasureId = other.itemPackageWidthUnitMeasureId == null ? null : other.itemPackageWidthUnitMeasureId.copy();
        this.itemPackageWidthUnitMeasureCode = other.itemPackageWidthUnitMeasureCode == null ? null : other.itemPackageWidthUnitMeasureCode.copy();
        this.itemPackageHeightUnitMeasureId = other.itemPackageHeightUnitMeasureId == null ? null : other.itemPackageHeightUnitMeasureId.copy();
        this.itemPackageHeightUnitMeasureCode = other.itemPackageHeightUnitMeasureCode == null ? null : other.itemPackageHeightUnitMeasureCode.copy();
        this.itemPackageWeightUnitMeasureId = other.itemPackageWeightUnitMeasureId == null ? null : other.itemPackageWeightUnitMeasureId.copy();
        this.itemPackageWeightUnitMeasureCode = other.itemPackageWeightUnitMeasureCode == null ? null : other.itemPackageWeightUnitMeasureCode.copy();
        this.noOfPieces = other.noOfPieces == null ? null : other.noOfPieces.copy();
        this.noOfItems = other.noOfItems == null ? null : other.noOfItems.copy();
        this.manufacture = other.manufacture == null ? null : other.manufacture.copy();
        this.productComplianceCertificate = other.productComplianceCertificate == null ? null : other.productComplianceCertificate.copy();
        this.genuineAndLegal = other.genuineAndLegal == null ? null : other.genuineAndLegal.copy();
        this.countryOfOrigin = other.countryOfOrigin == null ? null : other.countryOfOrigin.copy();
        this.sellStartDate = other.sellStartDate == null ? null : other.sellStartDate.copy();
        this.sellEndDate = other.sellEndDate == null ? null : other.sellEndDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.uploadTransactionId = other.uploadTransactionId == null ? null : other.uploadTransactionId.copy();
    }

    @Override
    public StockItemTempCriteria copy() {
        return new StockItemTempCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(StringFilter stockItemName) {
        this.stockItemName = stockItemName;
    }

    public StringFilter getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(StringFilter vendorCode) {
        this.vendorCode = vendorCode;
    }

    public StringFilter getVendorSKU() {
        return vendorSKU;
    }

    public void setVendorSKU(StringFilter vendorSKU) {
        this.vendorSKU = vendorSKU;
    }

    public StringFilter getBarcode() {
        return barcode;
    }

    public void setBarcode(StringFilter barcode) {
        this.barcode = barcode;
    }

    public LongFilter getBarcodeTypeId() {
        return barcodeTypeId;
    }

    public void setBarcodeTypeId(LongFilter barcodeTypeId) {
        this.barcodeTypeId = barcodeTypeId;
    }

    public StringFilter getBarcodeTypeName() {
        return barcodeTypeName;
    }

    public void setBarcodeTypeName(StringFilter barcodeTypeName) {
        this.barcodeTypeName = barcodeTypeName;
    }

    public StringFilter getProductType() {
        return productType;
    }

    public void setProductType(StringFilter productType) {
        this.productType = productType;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public StringFilter getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(StringFilter productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public LongFilter getProductAttributeSetId() {
        return productAttributeSetId;
    }

    public void setProductAttributeSetId(LongFilter productAttributeSetId) {
        this.productAttributeSetId = productAttributeSetId;
    }

    public LongFilter getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(LongFilter productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public StringFilter getProductAttributeValue() {
        return productAttributeValue;
    }

    public void setProductAttributeValue(StringFilter productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
    }

    public LongFilter getProductOptionSetId() {
        return productOptionSetId;
    }

    public void setProductOptionSetId(LongFilter productOptionSetId) {
        this.productOptionSetId = productOptionSetId;
    }

    public LongFilter getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(LongFilter productOptionId) {
        this.productOptionId = productOptionId;
    }

    public StringFilter getProductOptionValue() {
        return productOptionValue;
    }

    public void setProductOptionValue(StringFilter productOptionValue) {
        this.productOptionValue = productOptionValue;
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

    public LongFilter getMaterialId() {
        return materialId;
    }

    public void setMaterialId(LongFilter materialId) {
        this.materialId = materialId;
    }

    public StringFilter getMaterialName() {
        return materialName;
    }

    public void setMaterialName(StringFilter materialName) {
        this.materialName = materialName;
    }

    public LongFilter getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(LongFilter productBrandId) {
        this.productBrandId = productBrandId;
    }

    public StringFilter getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(StringFilter productBrandName) {
        this.productBrandName = productBrandName;
    }

    public StringFilter getDangerousGoods() {
        return dangerousGoods;
    }

    public void setDangerousGoods(StringFilter dangerousGoods) {
        this.dangerousGoods = dangerousGoods;
    }

    public StringFilter getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(StringFilter videoUrl) {
        this.videoUrl = videoUrl;
    }

    public BigDecimalFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimalFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimalFilter getRemommendedRetailPrice() {
        return remommendedRetailPrice;
    }

    public void setRemommendedRetailPrice(BigDecimalFilter remommendedRetailPrice) {
        this.remommendedRetailPrice = remommendedRetailPrice;
    }

    public StringFilter getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(StringFilter currencyCode) {
        this.currencyCode = currencyCode;
    }

    public IntegerFilter getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(IntegerFilter quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
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

    public StringFilter getWarrantyTypeName() {
        return warrantyTypeName;
    }

    public void setWarrantyTypeName(StringFilter warrantyTypeName) {
        this.warrantyTypeName = warrantyTypeName;
    }

    public IntegerFilter getItemLength() {
        return itemLength;
    }

    public void setItemLength(IntegerFilter itemLength) {
        this.itemLength = itemLength;
    }

    public IntegerFilter getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(IntegerFilter itemWidth) {
        this.itemWidth = itemWidth;
    }

    public IntegerFilter getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(IntegerFilter itemHeight) {
        this.itemHeight = itemHeight;
    }

    public BigDecimalFilter getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(BigDecimalFilter itemWeight) {
        this.itemWeight = itemWeight;
    }

    public IntegerFilter getItemPackageLength() {
        return itemPackageLength;
    }

    public void setItemPackageLength(IntegerFilter itemPackageLength) {
        this.itemPackageLength = itemPackageLength;
    }

    public IntegerFilter getItemPackageWidth() {
        return itemPackageWidth;
    }

    public void setItemPackageWidth(IntegerFilter itemPackageWidth) {
        this.itemPackageWidth = itemPackageWidth;
    }

    public IntegerFilter getItemPackageHeight() {
        return itemPackageHeight;
    }

    public void setItemPackageHeight(IntegerFilter itemPackageHeight) {
        this.itemPackageHeight = itemPackageHeight;
    }

    public BigDecimalFilter getItemPackageWeight() {
        return itemPackageWeight;
    }

    public void setItemPackageWeight(BigDecimalFilter itemPackageWeight) {
        this.itemPackageWeight = itemPackageWeight;
    }

    public LongFilter getItemLengthUnitMeasureId() {
        return itemLengthUnitMeasureId;
    }

    public void setItemLengthUnitMeasureId(LongFilter itemLengthUnitMeasureId) {
        this.itemLengthUnitMeasureId = itemLengthUnitMeasureId;
    }

    public StringFilter getItemLengthUnitMeasureCode() {
        return itemLengthUnitMeasureCode;
    }

    public void setItemLengthUnitMeasureCode(StringFilter itemLengthUnitMeasureCode) {
        this.itemLengthUnitMeasureCode = itemLengthUnitMeasureCode;
    }

    public LongFilter getItemWidthUnitMeasureId() {
        return itemWidthUnitMeasureId;
    }

    public void setItemWidthUnitMeasureId(LongFilter itemWidthUnitMeasureId) {
        this.itemWidthUnitMeasureId = itemWidthUnitMeasureId;
    }

    public StringFilter getItemWidthUnitMeasureCode() {
        return itemWidthUnitMeasureCode;
    }

    public void setItemWidthUnitMeasureCode(StringFilter itemWidthUnitMeasureCode) {
        this.itemWidthUnitMeasureCode = itemWidthUnitMeasureCode;
    }

    public LongFilter getItemHeightUnitMeasureId() {
        return itemHeightUnitMeasureId;
    }

    public void setItemHeightUnitMeasureId(LongFilter itemHeightUnitMeasureId) {
        this.itemHeightUnitMeasureId = itemHeightUnitMeasureId;
    }

    public StringFilter getItemHeightUnitMeasureCode() {
        return itemHeightUnitMeasureCode;
    }

    public void setItemHeightUnitMeasureCode(StringFilter itemHeightUnitMeasureCode) {
        this.itemHeightUnitMeasureCode = itemHeightUnitMeasureCode;
    }

    public LongFilter getItemWeightUnitMeasureId() {
        return itemWeightUnitMeasureId;
    }

    public void setItemWeightUnitMeasureId(LongFilter itemWeightUnitMeasureId) {
        this.itemWeightUnitMeasureId = itemWeightUnitMeasureId;
    }

    public StringFilter getItemWeightUnitMeasureCode() {
        return itemWeightUnitMeasureCode;
    }

    public void setItemWeightUnitMeasureCode(StringFilter itemWeightUnitMeasureCode) {
        this.itemWeightUnitMeasureCode = itemWeightUnitMeasureCode;
    }

    public LongFilter getItemPackageLengthUnitMeasureId() {
        return itemPackageLengthUnitMeasureId;
    }

    public void setItemPackageLengthUnitMeasureId(LongFilter itemPackageLengthUnitMeasureId) {
        this.itemPackageLengthUnitMeasureId = itemPackageLengthUnitMeasureId;
    }

    public StringFilter getItemPackageLengthUnitMeasureCode() {
        return itemPackageLengthUnitMeasureCode;
    }

    public void setItemPackageLengthUnitMeasureCode(StringFilter itemPackageLengthUnitMeasureCode) {
        this.itemPackageLengthUnitMeasureCode = itemPackageLengthUnitMeasureCode;
    }

    public LongFilter getItemPackageWidthUnitMeasureId() {
        return itemPackageWidthUnitMeasureId;
    }

    public void setItemPackageWidthUnitMeasureId(LongFilter itemPackageWidthUnitMeasureId) {
        this.itemPackageWidthUnitMeasureId = itemPackageWidthUnitMeasureId;
    }

    public StringFilter getItemPackageWidthUnitMeasureCode() {
        return itemPackageWidthUnitMeasureCode;
    }

    public void setItemPackageWidthUnitMeasureCode(StringFilter itemPackageWidthUnitMeasureCode) {
        this.itemPackageWidthUnitMeasureCode = itemPackageWidthUnitMeasureCode;
    }

    public LongFilter getItemPackageHeightUnitMeasureId() {
        return itemPackageHeightUnitMeasureId;
    }

    public void setItemPackageHeightUnitMeasureId(LongFilter itemPackageHeightUnitMeasureId) {
        this.itemPackageHeightUnitMeasureId = itemPackageHeightUnitMeasureId;
    }

    public StringFilter getItemPackageHeightUnitMeasureCode() {
        return itemPackageHeightUnitMeasureCode;
    }

    public void setItemPackageHeightUnitMeasureCode(StringFilter itemPackageHeightUnitMeasureCode) {
        this.itemPackageHeightUnitMeasureCode = itemPackageHeightUnitMeasureCode;
    }

    public LongFilter getItemPackageWeightUnitMeasureId() {
        return itemPackageWeightUnitMeasureId;
    }

    public void setItemPackageWeightUnitMeasureId(LongFilter itemPackageWeightUnitMeasureId) {
        this.itemPackageWeightUnitMeasureId = itemPackageWeightUnitMeasureId;
    }

    public StringFilter getItemPackageWeightUnitMeasureCode() {
        return itemPackageWeightUnitMeasureCode;
    }

    public void setItemPackageWeightUnitMeasureCode(StringFilter itemPackageWeightUnitMeasureCode) {
        this.itemPackageWeightUnitMeasureCode = itemPackageWeightUnitMeasureCode;
    }

    public IntegerFilter getNoOfPieces() {
        return noOfPieces;
    }

    public void setNoOfPieces(IntegerFilter noOfPieces) {
        this.noOfPieces = noOfPieces;
    }

    public IntegerFilter getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(IntegerFilter noOfItems) {
        this.noOfItems = noOfItems;
    }

    public StringFilter getManufacture() {
        return manufacture;
    }

    public void setManufacture(StringFilter manufacture) {
        this.manufacture = manufacture;
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

    public InstantFilter getSellStartDate() {
        return sellStartDate;
    }

    public void setSellStartDate(InstantFilter sellStartDate) {
        this.sellStartDate = sellStartDate;
    }

    public InstantFilter getSellEndDate() {
        return sellEndDate;
    }

    public void setSellEndDate(InstantFilter sellEndDate) {
        this.sellEndDate = sellEndDate;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
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

    public LongFilter getUploadTransactionId() {
        return uploadTransactionId;
    }

    public void setUploadTransactionId(LongFilter uploadTransactionId) {
        this.uploadTransactionId = uploadTransactionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockItemTempCriteria that = (StockItemTempCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stockItemName, that.stockItemName) &&
            Objects.equals(vendorCode, that.vendorCode) &&
            Objects.equals(vendorSKU, that.vendorSKU) &&
            Objects.equals(barcode, that.barcode) &&
            Objects.equals(barcodeTypeId, that.barcodeTypeId) &&
            Objects.equals(barcodeTypeName, that.barcodeTypeName) &&
            Objects.equals(productType, that.productType) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productCategoryName, that.productCategoryName) &&
            Objects.equals(productAttributeSetId, that.productAttributeSetId) &&
            Objects.equals(productAttributeId, that.productAttributeId) &&
            Objects.equals(productAttributeValue, that.productAttributeValue) &&
            Objects.equals(productOptionSetId, that.productOptionSetId) &&
            Objects.equals(productOptionId, that.productOptionId) &&
            Objects.equals(productOptionValue, that.productOptionValue) &&
            Objects.equals(modelName, that.modelName) &&
            Objects.equals(modelNumber, that.modelNumber) &&
            Objects.equals(materialId, that.materialId) &&
            Objects.equals(materialName, that.materialName) &&
            Objects.equals(productBrandId, that.productBrandId) &&
            Objects.equals(productBrandName, that.productBrandName) &&
            Objects.equals(dangerousGoods, that.dangerousGoods) &&
            Objects.equals(videoUrl, that.videoUrl) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(remommendedRetailPrice, that.remommendedRetailPrice) &&
            Objects.equals(currencyCode, that.currencyCode) &&
            Objects.equals(quantityOnHand, that.quantityOnHand) &&
            Objects.equals(warrantyPeriod, that.warrantyPeriod) &&
            Objects.equals(warrantyPolicy, that.warrantyPolicy) &&
            Objects.equals(warrantyTypeId, that.warrantyTypeId) &&
            Objects.equals(warrantyTypeName, that.warrantyTypeName) &&
            Objects.equals(itemLength, that.itemLength) &&
            Objects.equals(itemWidth, that.itemWidth) &&
            Objects.equals(itemHeight, that.itemHeight) &&
            Objects.equals(itemWeight, that.itemWeight) &&
            Objects.equals(itemPackageLength, that.itemPackageLength) &&
            Objects.equals(itemPackageWidth, that.itemPackageWidth) &&
            Objects.equals(itemPackageHeight, that.itemPackageHeight) &&
            Objects.equals(itemPackageWeight, that.itemPackageWeight) &&
            Objects.equals(itemLengthUnitMeasureId, that.itemLengthUnitMeasureId) &&
            Objects.equals(itemLengthUnitMeasureCode, that.itemLengthUnitMeasureCode) &&
            Objects.equals(itemWidthUnitMeasureId, that.itemWidthUnitMeasureId) &&
            Objects.equals(itemWidthUnitMeasureCode, that.itemWidthUnitMeasureCode) &&
            Objects.equals(itemHeightUnitMeasureId, that.itemHeightUnitMeasureId) &&
            Objects.equals(itemHeightUnitMeasureCode, that.itemHeightUnitMeasureCode) &&
            Objects.equals(itemWeightUnitMeasureId, that.itemWeightUnitMeasureId) &&
            Objects.equals(itemWeightUnitMeasureCode, that.itemWeightUnitMeasureCode) &&
            Objects.equals(itemPackageLengthUnitMeasureId, that.itemPackageLengthUnitMeasureId) &&
            Objects.equals(itemPackageLengthUnitMeasureCode, that.itemPackageLengthUnitMeasureCode) &&
            Objects.equals(itemPackageWidthUnitMeasureId, that.itemPackageWidthUnitMeasureId) &&
            Objects.equals(itemPackageWidthUnitMeasureCode, that.itemPackageWidthUnitMeasureCode) &&
            Objects.equals(itemPackageHeightUnitMeasureId, that.itemPackageHeightUnitMeasureId) &&
            Objects.equals(itemPackageHeightUnitMeasureCode, that.itemPackageHeightUnitMeasureCode) &&
            Objects.equals(itemPackageWeightUnitMeasureId, that.itemPackageWeightUnitMeasureId) &&
            Objects.equals(itemPackageWeightUnitMeasureCode, that.itemPackageWeightUnitMeasureCode) &&
            Objects.equals(noOfPieces, that.noOfPieces) &&
            Objects.equals(noOfItems, that.noOfItems) &&
            Objects.equals(manufacture, that.manufacture) &&
            Objects.equals(productComplianceCertificate, that.productComplianceCertificate) &&
            Objects.equals(genuineAndLegal, that.genuineAndLegal) &&
            Objects.equals(countryOfOrigin, that.countryOfOrigin) &&
            Objects.equals(sellStartDate, that.sellStartDate) &&
            Objects.equals(sellEndDate, that.sellEndDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(uploadTransactionId, that.uploadTransactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stockItemName,
        vendorCode,
        vendorSKU,
        barcode,
        barcodeTypeId,
        barcodeTypeName,
        productType,
        productCategoryId,
        productCategoryName,
        productAttributeSetId,
        productAttributeId,
        productAttributeValue,
        productOptionSetId,
        productOptionId,
        productOptionValue,
        modelName,
        modelNumber,
        materialId,
        materialName,
        productBrandId,
        productBrandName,
        dangerousGoods,
        videoUrl,
        unitPrice,
        remommendedRetailPrice,
        currencyCode,
        quantityOnHand,
        warrantyPeriod,
        warrantyPolicy,
        warrantyTypeId,
        warrantyTypeName,
        itemLength,
        itemWidth,
        itemHeight,
        itemWeight,
        itemPackageLength,
        itemPackageWidth,
        itemPackageHeight,
        itemPackageWeight,
        itemLengthUnitMeasureId,
        itemLengthUnitMeasureCode,
        itemWidthUnitMeasureId,
        itemWidthUnitMeasureCode,
        itemHeightUnitMeasureId,
        itemHeightUnitMeasureCode,
        itemWeightUnitMeasureId,
        itemWeightUnitMeasureCode,
        itemPackageLengthUnitMeasureId,
        itemPackageLengthUnitMeasureCode,
        itemPackageWidthUnitMeasureId,
        itemPackageWidthUnitMeasureCode,
        itemPackageHeightUnitMeasureId,
        itemPackageHeightUnitMeasureCode,
        itemPackageWeightUnitMeasureId,
        itemPackageWeightUnitMeasureCode,
        noOfPieces,
        noOfItems,
        manufacture,
        productComplianceCertificate,
        genuineAndLegal,
        countryOfOrigin,
        sellStartDate,
        sellEndDate,
        status,
        lastEditedBy,
        lastEditedWhen,
        uploadTransactionId
        );
    }

    @Override
    public String toString() {
        return "StockItemTempCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stockItemName != null ? "stockItemName=" + stockItemName + ", " : "") +
                (vendorCode != null ? "vendorCode=" + vendorCode + ", " : "") +
                (vendorSKU != null ? "vendorSKU=" + vendorSKU + ", " : "") +
                (barcode != null ? "barcode=" + barcode + ", " : "") +
                (barcodeTypeId != null ? "barcodeTypeId=" + barcodeTypeId + ", " : "") +
                (barcodeTypeName != null ? "barcodeTypeName=" + barcodeTypeName + ", " : "") +
                (productType != null ? "productType=" + productType + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productCategoryName != null ? "productCategoryName=" + productCategoryName + ", " : "") +
                (productAttributeSetId != null ? "productAttributeSetId=" + productAttributeSetId + ", " : "") +
                (productAttributeId != null ? "productAttributeId=" + productAttributeId + ", " : "") +
                (productAttributeValue != null ? "productAttributeValue=" + productAttributeValue + ", " : "") +
                (productOptionSetId != null ? "productOptionSetId=" + productOptionSetId + ", " : "") +
                (productOptionId != null ? "productOptionId=" + productOptionId + ", " : "") +
                (productOptionValue != null ? "productOptionValue=" + productOptionValue + ", " : "") +
                (modelName != null ? "modelName=" + modelName + ", " : "") +
                (modelNumber != null ? "modelNumber=" + modelNumber + ", " : "") +
                (materialId != null ? "materialId=" + materialId + ", " : "") +
                (materialName != null ? "materialName=" + materialName + ", " : "") +
                (productBrandId != null ? "productBrandId=" + productBrandId + ", " : "") +
                (productBrandName != null ? "productBrandName=" + productBrandName + ", " : "") +
                (dangerousGoods != null ? "dangerousGoods=" + dangerousGoods + ", " : "") +
                (videoUrl != null ? "videoUrl=" + videoUrl + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (remommendedRetailPrice != null ? "remommendedRetailPrice=" + remommendedRetailPrice + ", " : "") +
                (currencyCode != null ? "currencyCode=" + currencyCode + ", " : "") +
                (quantityOnHand != null ? "quantityOnHand=" + quantityOnHand + ", " : "") +
                (warrantyPeriod != null ? "warrantyPeriod=" + warrantyPeriod + ", " : "") +
                (warrantyPolicy != null ? "warrantyPolicy=" + warrantyPolicy + ", " : "") +
                (warrantyTypeId != null ? "warrantyTypeId=" + warrantyTypeId + ", " : "") +
                (warrantyTypeName != null ? "warrantyTypeName=" + warrantyTypeName + ", " : "") +
                (itemLength != null ? "itemLength=" + itemLength + ", " : "") +
                (itemWidth != null ? "itemWidth=" + itemWidth + ", " : "") +
                (itemHeight != null ? "itemHeight=" + itemHeight + ", " : "") +
                (itemWeight != null ? "itemWeight=" + itemWeight + ", " : "") +
                (itemPackageLength != null ? "itemPackageLength=" + itemPackageLength + ", " : "") +
                (itemPackageWidth != null ? "itemPackageWidth=" + itemPackageWidth + ", " : "") +
                (itemPackageHeight != null ? "itemPackageHeight=" + itemPackageHeight + ", " : "") +
                (itemPackageWeight != null ? "itemPackageWeight=" + itemPackageWeight + ", " : "") +
                (itemLengthUnitMeasureId != null ? "itemLengthUnitMeasureId=" + itemLengthUnitMeasureId + ", " : "") +
                (itemLengthUnitMeasureCode != null ? "itemLengthUnitMeasureCode=" + itemLengthUnitMeasureCode + ", " : "") +
                (itemWidthUnitMeasureId != null ? "itemWidthUnitMeasureId=" + itemWidthUnitMeasureId + ", " : "") +
                (itemWidthUnitMeasureCode != null ? "itemWidthUnitMeasureCode=" + itemWidthUnitMeasureCode + ", " : "") +
                (itemHeightUnitMeasureId != null ? "itemHeightUnitMeasureId=" + itemHeightUnitMeasureId + ", " : "") +
                (itemHeightUnitMeasureCode != null ? "itemHeightUnitMeasureCode=" + itemHeightUnitMeasureCode + ", " : "") +
                (itemWeightUnitMeasureId != null ? "itemWeightUnitMeasureId=" + itemWeightUnitMeasureId + ", " : "") +
                (itemWeightUnitMeasureCode != null ? "itemWeightUnitMeasureCode=" + itemWeightUnitMeasureCode + ", " : "") +
                (itemPackageLengthUnitMeasureId != null ? "itemPackageLengthUnitMeasureId=" + itemPackageLengthUnitMeasureId + ", " : "") +
                (itemPackageLengthUnitMeasureCode != null ? "itemPackageLengthUnitMeasureCode=" + itemPackageLengthUnitMeasureCode + ", " : "") +
                (itemPackageWidthUnitMeasureId != null ? "itemPackageWidthUnitMeasureId=" + itemPackageWidthUnitMeasureId + ", " : "") +
                (itemPackageWidthUnitMeasureCode != null ? "itemPackageWidthUnitMeasureCode=" + itemPackageWidthUnitMeasureCode + ", " : "") +
                (itemPackageHeightUnitMeasureId != null ? "itemPackageHeightUnitMeasureId=" + itemPackageHeightUnitMeasureId + ", " : "") +
                (itemPackageHeightUnitMeasureCode != null ? "itemPackageHeightUnitMeasureCode=" + itemPackageHeightUnitMeasureCode + ", " : "") +
                (itemPackageWeightUnitMeasureId != null ? "itemPackageWeightUnitMeasureId=" + itemPackageWeightUnitMeasureId + ", " : "") +
                (itemPackageWeightUnitMeasureCode != null ? "itemPackageWeightUnitMeasureCode=" + itemPackageWeightUnitMeasureCode + ", " : "") +
                (noOfPieces != null ? "noOfPieces=" + noOfPieces + ", " : "") +
                (noOfItems != null ? "noOfItems=" + noOfItems + ", " : "") +
                (manufacture != null ? "manufacture=" + manufacture + ", " : "") +
                (productComplianceCertificate != null ? "productComplianceCertificate=" + productComplianceCertificate + ", " : "") +
                (genuineAndLegal != null ? "genuineAndLegal=" + genuineAndLegal + ", " : "") +
                (countryOfOrigin != null ? "countryOfOrigin=" + countryOfOrigin + ", " : "") +
                (sellStartDate != null ? "sellStartDate=" + sellStartDate + ", " : "") +
                (sellEndDate != null ? "sellEndDate=" + sellEndDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (uploadTransactionId != null ? "uploadTransactionId=" + uploadTransactionId + ", " : "") +
            "}";
    }

}
