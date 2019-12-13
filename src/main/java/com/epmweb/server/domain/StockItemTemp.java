package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A StockItemTemp.
 */
@Entity
@Table(name = "stock_item_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StockItemTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "stock_item_name", nullable = false)
    private String stockItemName;

    @NotNull
    @Column(name = "vendor_code", nullable = false)
    private String vendorCode;

    @NotNull
    @Column(name = "vendor_sku", nullable = false)
    private String vendorSKU;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "barcode_type_id")
    private Long barcodeTypeId;

    @Column(name = "barcode_type_name")
    private String barcodeTypeName;

    @Column(name = "product_type")
    private String productType;

    @NotNull
    @Column(name = "product_category_id", nullable = false)
    private Long productCategoryId;

    @Column(name = "product_category_name")
    private String productCategoryName;

    @Column(name = "product_attribute_set_id")
    private Long productAttributeSetId;

    @Column(name = "product_attribute_id")
    private Long productAttributeId;

    @Column(name = "product_attribute_value")
    private String productAttributeValue;

    @Column(name = "product_option_set_id")
    private Long productOptionSetId;

    @Column(name = "product_option_id")
    private Long productOptionId;

    @Column(name = "product_option_value")
    private String productOptionValue;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "material_name")
    private String materialName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "short_description")
    private String shortDescription;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "product_brand_id")
    private Long productBrandId;

    @Column(name = "product_brand_name")
    private String productBrandName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "highlights")
    private String highlights;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "search_details")
    private String searchDetails;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "care_instructions")
    private String careInstructions;

    @Column(name = "dangerous_goods")
    private String dangerousGoods;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "unit_price", precision = 21, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "remommended_retail_price", precision = 21, scale = 2)
    private BigDecimal remommendedRetailPrice;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "quantity_on_hand")
    private Integer quantityOnHand;

    @Column(name = "warranty_period")
    private String warrantyPeriod;

    @Column(name = "warranty_policy")
    private String warrantyPolicy;

    @Column(name = "warranty_type_id")
    private Long warrantyTypeId;

    @Column(name = "warranty_type_name")
    private String warrantyTypeName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "what_in_the_box")
    private String whatInTheBox;

    @Column(name = "item_length")
    private Integer itemLength;

    @Column(name = "item_width")
    private Integer itemWidth;

    @Column(name = "item_height")
    private Integer itemHeight;

    @Column(name = "item_weight", precision = 21, scale = 2)
    private BigDecimal itemWeight;

    @Column(name = "item_package_length")
    private Integer itemPackageLength;

    @Column(name = "item_package_width")
    private Integer itemPackageWidth;

    @Column(name = "item_package_height")
    private Integer itemPackageHeight;

    @Column(name = "item_package_weight", precision = 21, scale = 2)
    private BigDecimal itemPackageWeight;

    @Column(name = "item_length_unit_measure_id")
    private Long itemLengthUnitMeasureId;

    @Column(name = "item_length_unit_measure_code")
    private String itemLengthUnitMeasureCode;

    @Column(name = "item_width_unit_measure_id")
    private Long itemWidthUnitMeasureId;

    @Column(name = "item_width_unit_measure_code")
    private String itemWidthUnitMeasureCode;

    @Column(name = "item_height_unit_measure_id")
    private Long itemHeightUnitMeasureId;

    @Column(name = "item_height_unit_measure_code")
    private String itemHeightUnitMeasureCode;

    @Column(name = "item_weight_unit_measure_id")
    private Long itemWeightUnitMeasureId;

    @Column(name = "item_weight_unit_measure_code")
    private String itemWeightUnitMeasureCode;

    @Column(name = "item_package_length_unit_measure_id")
    private Long itemPackageLengthUnitMeasureId;

    @Column(name = "item_package_length_unit_measure_code")
    private String itemPackageLengthUnitMeasureCode;

    @Column(name = "item_package_width_unit_measure_id")
    private Long itemPackageWidthUnitMeasureId;

    @Column(name = "item_package_width_unit_measure_code")
    private String itemPackageWidthUnitMeasureCode;

    @Column(name = "item_package_height_unit_measure_id")
    private Long itemPackageHeightUnitMeasureId;

    @Column(name = "item_package_height_unit_measure_code")
    private String itemPackageHeightUnitMeasureCode;

    @Column(name = "item_package_weight_unit_measure_id")
    private Long itemPackageWeightUnitMeasureId;

    @Column(name = "item_package_weight_unit_measure_code")
    private String itemPackageWeightUnitMeasureCode;

    @Column(name = "no_of_pieces")
    private Integer noOfPieces;

    @Column(name = "no_of_items")
    private Integer noOfItems;

    @Column(name = "manufacture")
    private String manufacture;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "special_feactures")
    private String specialFeactures;

    @Column(name = "product_compliance_certificate")
    private String productComplianceCertificate;

    @Column(name = "genuine_and_legal")
    private Boolean genuineAndLegal;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "usage_and_side_effects")
    private String usageAndSideEffects;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "safety_warnning")
    private String safetyWarnning;

    @Column(name = "sell_start_date")
    private Instant sellStartDate;

    @Column(name = "sell_end_date")
    private Instant sellEndDate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @ManyToOne
    @JsonIgnoreProperties("stockItemTempLists")
    private UploadTransactions uploadTransaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockItemName() {
        return stockItemName;
    }

    public StockItemTemp stockItemName(String stockItemName) {
        this.stockItemName = stockItemName;
        return this;
    }

    public void setStockItemName(String stockItemName) {
        this.stockItemName = stockItemName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public StockItemTemp vendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorSKU() {
        return vendorSKU;
    }

    public StockItemTemp vendorSKU(String vendorSKU) {
        this.vendorSKU = vendorSKU;
        return this;
    }

    public void setVendorSKU(String vendorSKU) {
        this.vendorSKU = vendorSKU;
    }

    public String getBarcode() {
        return barcode;
    }

    public StockItemTemp barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Long getBarcodeTypeId() {
        return barcodeTypeId;
    }

    public StockItemTemp barcodeTypeId(Long barcodeTypeId) {
        this.barcodeTypeId = barcodeTypeId;
        return this;
    }

    public void setBarcodeTypeId(Long barcodeTypeId) {
        this.barcodeTypeId = barcodeTypeId;
    }

    public String getBarcodeTypeName() {
        return barcodeTypeName;
    }

    public StockItemTemp barcodeTypeName(String barcodeTypeName) {
        this.barcodeTypeName = barcodeTypeName;
        return this;
    }

    public void setBarcodeTypeName(String barcodeTypeName) {
        this.barcodeTypeName = barcodeTypeName;
    }

    public String getProductType() {
        return productType;
    }

    public StockItemTemp productType(String productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public StockItemTemp productCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
        return this;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public StockItemTemp productCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
        return this;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Long getProductAttributeSetId() {
        return productAttributeSetId;
    }

    public StockItemTemp productAttributeSetId(Long productAttributeSetId) {
        this.productAttributeSetId = productAttributeSetId;
        return this;
    }

    public void setProductAttributeSetId(Long productAttributeSetId) {
        this.productAttributeSetId = productAttributeSetId;
    }

    public Long getProductAttributeId() {
        return productAttributeId;
    }

    public StockItemTemp productAttributeId(Long productAttributeId) {
        this.productAttributeId = productAttributeId;
        return this;
    }

    public void setProductAttributeId(Long productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getProductAttributeValue() {
        return productAttributeValue;
    }

    public StockItemTemp productAttributeValue(String productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
        return this;
    }

    public void setProductAttributeValue(String productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
    }

    public Long getProductOptionSetId() {
        return productOptionSetId;
    }

    public StockItemTemp productOptionSetId(Long productOptionSetId) {
        this.productOptionSetId = productOptionSetId;
        return this;
    }

    public void setProductOptionSetId(Long productOptionSetId) {
        this.productOptionSetId = productOptionSetId;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public StockItemTemp productOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
        return this;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getProductOptionValue() {
        return productOptionValue;
    }

    public StockItemTemp productOptionValue(String productOptionValue) {
        this.productOptionValue = productOptionValue;
        return this;
    }

    public void setProductOptionValue(String productOptionValue) {
        this.productOptionValue = productOptionValue;
    }

    public String getModelName() {
        return modelName;
    }

    public StockItemTemp modelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public StockItemTemp modelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
        return this;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public StockItemTemp materialId(Long materialId) {
        this.materialId = materialId;
        return this;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public StockItemTemp materialName(String materialName) {
        this.materialName = materialName;
        return this;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public StockItemTemp shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public StockItemTemp description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductBrandId() {
        return productBrandId;
    }

    public StockItemTemp productBrandId(Long productBrandId) {
        this.productBrandId = productBrandId;
        return this;
    }

    public void setProductBrandId(Long productBrandId) {
        this.productBrandId = productBrandId;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public StockItemTemp productBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
        return this;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

    public String getHighlights() {
        return highlights;
    }

    public StockItemTemp highlights(String highlights) {
        this.highlights = highlights;
        return this;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getSearchDetails() {
        return searchDetails;
    }

    public StockItemTemp searchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
        return this;
    }

    public void setSearchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
    }

    public String getCareInstructions() {
        return careInstructions;
    }

    public StockItemTemp careInstructions(String careInstructions) {
        this.careInstructions = careInstructions;
        return this;
    }

    public void setCareInstructions(String careInstructions) {
        this.careInstructions = careInstructions;
    }

    public String getDangerousGoods() {
        return dangerousGoods;
    }

    public StockItemTemp dangerousGoods(String dangerousGoods) {
        this.dangerousGoods = dangerousGoods;
        return this;
    }

    public void setDangerousGoods(String dangerousGoods) {
        this.dangerousGoods = dangerousGoods;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public StockItemTemp videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public StockItemTemp unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getRemommendedRetailPrice() {
        return remommendedRetailPrice;
    }

    public StockItemTemp remommendedRetailPrice(BigDecimal remommendedRetailPrice) {
        this.remommendedRetailPrice = remommendedRetailPrice;
        return this;
    }

    public void setRemommendedRetailPrice(BigDecimal remommendedRetailPrice) {
        this.remommendedRetailPrice = remommendedRetailPrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public StockItemTemp currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public StockItemTemp quantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
        return this;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public StockItemTemp warrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
        return this;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyPolicy() {
        return warrantyPolicy;
    }

    public StockItemTemp warrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
        return this;
    }

    public void setWarrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
    }

    public Long getWarrantyTypeId() {
        return warrantyTypeId;
    }

    public StockItemTemp warrantyTypeId(Long warrantyTypeId) {
        this.warrantyTypeId = warrantyTypeId;
        return this;
    }

    public void setWarrantyTypeId(Long warrantyTypeId) {
        this.warrantyTypeId = warrantyTypeId;
    }

    public String getWarrantyTypeName() {
        return warrantyTypeName;
    }

    public StockItemTemp warrantyTypeName(String warrantyTypeName) {
        this.warrantyTypeName = warrantyTypeName;
        return this;
    }

    public void setWarrantyTypeName(String warrantyTypeName) {
        this.warrantyTypeName = warrantyTypeName;
    }

    public String getWhatInTheBox() {
        return whatInTheBox;
    }

    public StockItemTemp whatInTheBox(String whatInTheBox) {
        this.whatInTheBox = whatInTheBox;
        return this;
    }

    public void setWhatInTheBox(String whatInTheBox) {
        this.whatInTheBox = whatInTheBox;
    }

    public Integer getItemLength() {
        return itemLength;
    }

    public StockItemTemp itemLength(Integer itemLength) {
        this.itemLength = itemLength;
        return this;
    }

    public void setItemLength(Integer itemLength) {
        this.itemLength = itemLength;
    }

    public Integer getItemWidth() {
        return itemWidth;
    }

    public StockItemTemp itemWidth(Integer itemWidth) {
        this.itemWidth = itemWidth;
        return this;
    }

    public void setItemWidth(Integer itemWidth) {
        this.itemWidth = itemWidth;
    }

    public Integer getItemHeight() {
        return itemHeight;
    }

    public StockItemTemp itemHeight(Integer itemHeight) {
        this.itemHeight = itemHeight;
        return this;
    }

    public void setItemHeight(Integer itemHeight) {
        this.itemHeight = itemHeight;
    }

    public BigDecimal getItemWeight() {
        return itemWeight;
    }

    public StockItemTemp itemWeight(BigDecimal itemWeight) {
        this.itemWeight = itemWeight;
        return this;
    }

    public void setItemWeight(BigDecimal itemWeight) {
        this.itemWeight = itemWeight;
    }

    public Integer getItemPackageLength() {
        return itemPackageLength;
    }

    public StockItemTemp itemPackageLength(Integer itemPackageLength) {
        this.itemPackageLength = itemPackageLength;
        return this;
    }

    public void setItemPackageLength(Integer itemPackageLength) {
        this.itemPackageLength = itemPackageLength;
    }

    public Integer getItemPackageWidth() {
        return itemPackageWidth;
    }

    public StockItemTemp itemPackageWidth(Integer itemPackageWidth) {
        this.itemPackageWidth = itemPackageWidth;
        return this;
    }

    public void setItemPackageWidth(Integer itemPackageWidth) {
        this.itemPackageWidth = itemPackageWidth;
    }

    public Integer getItemPackageHeight() {
        return itemPackageHeight;
    }

    public StockItemTemp itemPackageHeight(Integer itemPackageHeight) {
        this.itemPackageHeight = itemPackageHeight;
        return this;
    }

    public void setItemPackageHeight(Integer itemPackageHeight) {
        this.itemPackageHeight = itemPackageHeight;
    }

    public BigDecimal getItemPackageWeight() {
        return itemPackageWeight;
    }

    public StockItemTemp itemPackageWeight(BigDecimal itemPackageWeight) {
        this.itemPackageWeight = itemPackageWeight;
        return this;
    }

    public void setItemPackageWeight(BigDecimal itemPackageWeight) {
        this.itemPackageWeight = itemPackageWeight;
    }

    public Long getItemLengthUnitMeasureId() {
        return itemLengthUnitMeasureId;
    }

    public StockItemTemp itemLengthUnitMeasureId(Long itemLengthUnitMeasureId) {
        this.itemLengthUnitMeasureId = itemLengthUnitMeasureId;
        return this;
    }

    public void setItemLengthUnitMeasureId(Long itemLengthUnitMeasureId) {
        this.itemLengthUnitMeasureId = itemLengthUnitMeasureId;
    }

    public String getItemLengthUnitMeasureCode() {
        return itemLengthUnitMeasureCode;
    }

    public StockItemTemp itemLengthUnitMeasureCode(String itemLengthUnitMeasureCode) {
        this.itemLengthUnitMeasureCode = itemLengthUnitMeasureCode;
        return this;
    }

    public void setItemLengthUnitMeasureCode(String itemLengthUnitMeasureCode) {
        this.itemLengthUnitMeasureCode = itemLengthUnitMeasureCode;
    }

    public Long getItemWidthUnitMeasureId() {
        return itemWidthUnitMeasureId;
    }

    public StockItemTemp itemWidthUnitMeasureId(Long itemWidthUnitMeasureId) {
        this.itemWidthUnitMeasureId = itemWidthUnitMeasureId;
        return this;
    }

    public void setItemWidthUnitMeasureId(Long itemWidthUnitMeasureId) {
        this.itemWidthUnitMeasureId = itemWidthUnitMeasureId;
    }

    public String getItemWidthUnitMeasureCode() {
        return itemWidthUnitMeasureCode;
    }

    public StockItemTemp itemWidthUnitMeasureCode(String itemWidthUnitMeasureCode) {
        this.itemWidthUnitMeasureCode = itemWidthUnitMeasureCode;
        return this;
    }

    public void setItemWidthUnitMeasureCode(String itemWidthUnitMeasureCode) {
        this.itemWidthUnitMeasureCode = itemWidthUnitMeasureCode;
    }

    public Long getItemHeightUnitMeasureId() {
        return itemHeightUnitMeasureId;
    }

    public StockItemTemp itemHeightUnitMeasureId(Long itemHeightUnitMeasureId) {
        this.itemHeightUnitMeasureId = itemHeightUnitMeasureId;
        return this;
    }

    public void setItemHeightUnitMeasureId(Long itemHeightUnitMeasureId) {
        this.itemHeightUnitMeasureId = itemHeightUnitMeasureId;
    }

    public String getItemHeightUnitMeasureCode() {
        return itemHeightUnitMeasureCode;
    }

    public StockItemTemp itemHeightUnitMeasureCode(String itemHeightUnitMeasureCode) {
        this.itemHeightUnitMeasureCode = itemHeightUnitMeasureCode;
        return this;
    }

    public void setItemHeightUnitMeasureCode(String itemHeightUnitMeasureCode) {
        this.itemHeightUnitMeasureCode = itemHeightUnitMeasureCode;
    }

    public Long getItemWeightUnitMeasureId() {
        return itemWeightUnitMeasureId;
    }

    public StockItemTemp itemWeightUnitMeasureId(Long itemWeightUnitMeasureId) {
        this.itemWeightUnitMeasureId = itemWeightUnitMeasureId;
        return this;
    }

    public void setItemWeightUnitMeasureId(Long itemWeightUnitMeasureId) {
        this.itemWeightUnitMeasureId = itemWeightUnitMeasureId;
    }

    public String getItemWeightUnitMeasureCode() {
        return itemWeightUnitMeasureCode;
    }

    public StockItemTemp itemWeightUnitMeasureCode(String itemWeightUnitMeasureCode) {
        this.itemWeightUnitMeasureCode = itemWeightUnitMeasureCode;
        return this;
    }

    public void setItemWeightUnitMeasureCode(String itemWeightUnitMeasureCode) {
        this.itemWeightUnitMeasureCode = itemWeightUnitMeasureCode;
    }

    public Long getItemPackageLengthUnitMeasureId() {
        return itemPackageLengthUnitMeasureId;
    }

    public StockItemTemp itemPackageLengthUnitMeasureId(Long itemPackageLengthUnitMeasureId) {
        this.itemPackageLengthUnitMeasureId = itemPackageLengthUnitMeasureId;
        return this;
    }

    public void setItemPackageLengthUnitMeasureId(Long itemPackageLengthUnitMeasureId) {
        this.itemPackageLengthUnitMeasureId = itemPackageLengthUnitMeasureId;
    }

    public String getItemPackageLengthUnitMeasureCode() {
        return itemPackageLengthUnitMeasureCode;
    }

    public StockItemTemp itemPackageLengthUnitMeasureCode(String itemPackageLengthUnitMeasureCode) {
        this.itemPackageLengthUnitMeasureCode = itemPackageLengthUnitMeasureCode;
        return this;
    }

    public void setItemPackageLengthUnitMeasureCode(String itemPackageLengthUnitMeasureCode) {
        this.itemPackageLengthUnitMeasureCode = itemPackageLengthUnitMeasureCode;
    }

    public Long getItemPackageWidthUnitMeasureId() {
        return itemPackageWidthUnitMeasureId;
    }

    public StockItemTemp itemPackageWidthUnitMeasureId(Long itemPackageWidthUnitMeasureId) {
        this.itemPackageWidthUnitMeasureId = itemPackageWidthUnitMeasureId;
        return this;
    }

    public void setItemPackageWidthUnitMeasureId(Long itemPackageWidthUnitMeasureId) {
        this.itemPackageWidthUnitMeasureId = itemPackageWidthUnitMeasureId;
    }

    public String getItemPackageWidthUnitMeasureCode() {
        return itemPackageWidthUnitMeasureCode;
    }

    public StockItemTemp itemPackageWidthUnitMeasureCode(String itemPackageWidthUnitMeasureCode) {
        this.itemPackageWidthUnitMeasureCode = itemPackageWidthUnitMeasureCode;
        return this;
    }

    public void setItemPackageWidthUnitMeasureCode(String itemPackageWidthUnitMeasureCode) {
        this.itemPackageWidthUnitMeasureCode = itemPackageWidthUnitMeasureCode;
    }

    public Long getItemPackageHeightUnitMeasureId() {
        return itemPackageHeightUnitMeasureId;
    }

    public StockItemTemp itemPackageHeightUnitMeasureId(Long itemPackageHeightUnitMeasureId) {
        this.itemPackageHeightUnitMeasureId = itemPackageHeightUnitMeasureId;
        return this;
    }

    public void setItemPackageHeightUnitMeasureId(Long itemPackageHeightUnitMeasureId) {
        this.itemPackageHeightUnitMeasureId = itemPackageHeightUnitMeasureId;
    }

    public String getItemPackageHeightUnitMeasureCode() {
        return itemPackageHeightUnitMeasureCode;
    }

    public StockItemTemp itemPackageHeightUnitMeasureCode(String itemPackageHeightUnitMeasureCode) {
        this.itemPackageHeightUnitMeasureCode = itemPackageHeightUnitMeasureCode;
        return this;
    }

    public void setItemPackageHeightUnitMeasureCode(String itemPackageHeightUnitMeasureCode) {
        this.itemPackageHeightUnitMeasureCode = itemPackageHeightUnitMeasureCode;
    }

    public Long getItemPackageWeightUnitMeasureId() {
        return itemPackageWeightUnitMeasureId;
    }

    public StockItemTemp itemPackageWeightUnitMeasureId(Long itemPackageWeightUnitMeasureId) {
        this.itemPackageWeightUnitMeasureId = itemPackageWeightUnitMeasureId;
        return this;
    }

    public void setItemPackageWeightUnitMeasureId(Long itemPackageWeightUnitMeasureId) {
        this.itemPackageWeightUnitMeasureId = itemPackageWeightUnitMeasureId;
    }

    public String getItemPackageWeightUnitMeasureCode() {
        return itemPackageWeightUnitMeasureCode;
    }

    public StockItemTemp itemPackageWeightUnitMeasureCode(String itemPackageWeightUnitMeasureCode) {
        this.itemPackageWeightUnitMeasureCode = itemPackageWeightUnitMeasureCode;
        return this;
    }

    public void setItemPackageWeightUnitMeasureCode(String itemPackageWeightUnitMeasureCode) {
        this.itemPackageWeightUnitMeasureCode = itemPackageWeightUnitMeasureCode;
    }

    public Integer getNoOfPieces() {
        return noOfPieces;
    }

    public StockItemTemp noOfPieces(Integer noOfPieces) {
        this.noOfPieces = noOfPieces;
        return this;
    }

    public void setNoOfPieces(Integer noOfPieces) {
        this.noOfPieces = noOfPieces;
    }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    public StockItemTemp noOfItems(Integer noOfItems) {
        this.noOfItems = noOfItems;
        return this;
    }

    public void setNoOfItems(Integer noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getManufacture() {
        return manufacture;
    }

    public StockItemTemp manufacture(String manufacture) {
        this.manufacture = manufacture;
        return this;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getSpecialFeactures() {
        return specialFeactures;
    }

    public StockItemTemp specialFeactures(String specialFeactures) {
        this.specialFeactures = specialFeactures;
        return this;
    }

    public void setSpecialFeactures(String specialFeactures) {
        this.specialFeactures = specialFeactures;
    }

    public String getProductComplianceCertificate() {
        return productComplianceCertificate;
    }

    public StockItemTemp productComplianceCertificate(String productComplianceCertificate) {
        this.productComplianceCertificate = productComplianceCertificate;
        return this;
    }

    public void setProductComplianceCertificate(String productComplianceCertificate) {
        this.productComplianceCertificate = productComplianceCertificate;
    }

    public Boolean isGenuineAndLegal() {
        return genuineAndLegal;
    }

    public StockItemTemp genuineAndLegal(Boolean genuineAndLegal) {
        this.genuineAndLegal = genuineAndLegal;
        return this;
    }

    public void setGenuineAndLegal(Boolean genuineAndLegal) {
        this.genuineAndLegal = genuineAndLegal;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public StockItemTemp countryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
        return this;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getUsageAndSideEffects() {
        return usageAndSideEffects;
    }

    public StockItemTemp usageAndSideEffects(String usageAndSideEffects) {
        this.usageAndSideEffects = usageAndSideEffects;
        return this;
    }

    public void setUsageAndSideEffects(String usageAndSideEffects) {
        this.usageAndSideEffects = usageAndSideEffects;
    }

    public String getSafetyWarnning() {
        return safetyWarnning;
    }

    public StockItemTemp safetyWarnning(String safetyWarnning) {
        this.safetyWarnning = safetyWarnning;
        return this;
    }

    public void setSafetyWarnning(String safetyWarnning) {
        this.safetyWarnning = safetyWarnning;
    }

    public Instant getSellStartDate() {
        return sellStartDate;
    }

    public StockItemTemp sellStartDate(Instant sellStartDate) {
        this.sellStartDate = sellStartDate;
        return this;
    }

    public void setSellStartDate(Instant sellStartDate) {
        this.sellStartDate = sellStartDate;
    }

    public Instant getSellEndDate() {
        return sellEndDate;
    }

    public StockItemTemp sellEndDate(Instant sellEndDate) {
        this.sellEndDate = sellEndDate;
        return this;
    }

    public void setSellEndDate(Instant sellEndDate) {
        this.sellEndDate = sellEndDate;
    }

    public Integer getStatus() {
        return status;
    }

    public StockItemTemp status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public StockItemTemp lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public StockItemTemp lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public UploadTransactions getUploadTransaction() {
        return uploadTransaction;
    }

    public StockItemTemp uploadTransaction(UploadTransactions uploadTransactions) {
        this.uploadTransaction = uploadTransactions;
        return this;
    }

    public void setUploadTransaction(UploadTransactions uploadTransactions) {
        this.uploadTransaction = uploadTransactions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockItemTemp)) {
            return false;
        }
        return id != null && id.equals(((StockItemTemp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StockItemTemp{" +
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
            "}";
    }
}
