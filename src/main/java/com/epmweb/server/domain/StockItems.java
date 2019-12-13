package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A StockItems.
 */
@Entity
@Table(name = "stock_items")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StockItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "vendor_code")
    private String vendorCode;

    @Column(name = "vendor_sku")
    private String vendorSKU;

    @Column(name = "generated_sku")
    private String generatedSKU;

    @Column(name = "barcode")
    private String barcode;

    @NotNull
    @Column(name = "unit_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "recommended_retail_price", precision = 21, scale = 2)
    private BigDecimal recommendedRetailPrice;

    @NotNull
    @Column(name = "quantity_on_hand", nullable = false)
    private Integer quantityOnHand;

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

    @Column(name = "no_of_pieces")
    private Integer noOfPieces;

    @Column(name = "no_of_items")
    private Integer noOfItems;

    @Column(name = "manufacture")
    private String manufacture;

    @Column(name = "marketing_comments")
    private String marketingComments;

    @Column(name = "internal_comments")
    private String internalComments;

    @Column(name = "sell_start_date")
    private Instant sellStartDate;

    @Column(name = "sell_end_date")
    private Instant sellEndDate;

    @Column(name = "sell_count")
    private Integer sellCount;

    @Column(name = "custom_fields")
    private String customFields;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "active_ind")
    private Boolean activeInd;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @OneToOne
    @JoinColumn(unique = true)
    private ReviewLines stockItemOnReviewLine;

    @OneToMany(mappedBy = "stockItem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Photos> photoLists = new HashSet<>();

    @OneToMany(mappedBy = "stockItem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DangerousGoods> dangerousGoodLists = new HashSet<>();

    @OneToMany(mappedBy = "stockItem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SpecialDeals> specialDiscounts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private UnitMeasure itemLengthUnit;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private UnitMeasure itemWidthUnit;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private UnitMeasure itemHeightUnit;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private UnitMeasure packageLengthUnit;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private UnitMeasure packageWidthUnit;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private UnitMeasure packageHeightUnit;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private UnitMeasure itemPackageWeightUnit;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private ProductAttribute productAttribute;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private ProductOption productOption;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private Materials material;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private Currency currency;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private BarcodeTypes barcodeType;

    @OneToOne(mappedBy = "stockItemHoldingOnStockItem")
    @JsonIgnore
    private StockItemHoldings stockItemHolding;

    @ManyToOne
    @JsonIgnoreProperties("stockItemLists")
    private Products product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public StockItems name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public StockItems vendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorSKU() {
        return vendorSKU;
    }

    public StockItems vendorSKU(String vendorSKU) {
        this.vendorSKU = vendorSKU;
        return this;
    }

    public void setVendorSKU(String vendorSKU) {
        this.vendorSKU = vendorSKU;
    }

    public String getGeneratedSKU() {
        return generatedSKU;
    }

    public StockItems generatedSKU(String generatedSKU) {
        this.generatedSKU = generatedSKU;
        return this;
    }

    public void setGeneratedSKU(String generatedSKU) {
        this.generatedSKU = generatedSKU;
    }

    public String getBarcode() {
        return barcode;
    }

    public StockItems barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public StockItems unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getRecommendedRetailPrice() {
        return recommendedRetailPrice;
    }

    public StockItems recommendedRetailPrice(BigDecimal recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
        return this;
    }

    public void setRecommendedRetailPrice(BigDecimal recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public StockItems quantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
        return this;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Integer getItemLength() {
        return itemLength;
    }

    public StockItems itemLength(Integer itemLength) {
        this.itemLength = itemLength;
        return this;
    }

    public void setItemLength(Integer itemLength) {
        this.itemLength = itemLength;
    }

    public Integer getItemWidth() {
        return itemWidth;
    }

    public StockItems itemWidth(Integer itemWidth) {
        this.itemWidth = itemWidth;
        return this;
    }

    public void setItemWidth(Integer itemWidth) {
        this.itemWidth = itemWidth;
    }

    public Integer getItemHeight() {
        return itemHeight;
    }

    public StockItems itemHeight(Integer itemHeight) {
        this.itemHeight = itemHeight;
        return this;
    }

    public void setItemHeight(Integer itemHeight) {
        this.itemHeight = itemHeight;
    }

    public BigDecimal getItemWeight() {
        return itemWeight;
    }

    public StockItems itemWeight(BigDecimal itemWeight) {
        this.itemWeight = itemWeight;
        return this;
    }

    public void setItemWeight(BigDecimal itemWeight) {
        this.itemWeight = itemWeight;
    }

    public Integer getItemPackageLength() {
        return itemPackageLength;
    }

    public StockItems itemPackageLength(Integer itemPackageLength) {
        this.itemPackageLength = itemPackageLength;
        return this;
    }

    public void setItemPackageLength(Integer itemPackageLength) {
        this.itemPackageLength = itemPackageLength;
    }

    public Integer getItemPackageWidth() {
        return itemPackageWidth;
    }

    public StockItems itemPackageWidth(Integer itemPackageWidth) {
        this.itemPackageWidth = itemPackageWidth;
        return this;
    }

    public void setItemPackageWidth(Integer itemPackageWidth) {
        this.itemPackageWidth = itemPackageWidth;
    }

    public Integer getItemPackageHeight() {
        return itemPackageHeight;
    }

    public StockItems itemPackageHeight(Integer itemPackageHeight) {
        this.itemPackageHeight = itemPackageHeight;
        return this;
    }

    public void setItemPackageHeight(Integer itemPackageHeight) {
        this.itemPackageHeight = itemPackageHeight;
    }

    public BigDecimal getItemPackageWeight() {
        return itemPackageWeight;
    }

    public StockItems itemPackageWeight(BigDecimal itemPackageWeight) {
        this.itemPackageWeight = itemPackageWeight;
        return this;
    }

    public void setItemPackageWeight(BigDecimal itemPackageWeight) {
        this.itemPackageWeight = itemPackageWeight;
    }

    public Integer getNoOfPieces() {
        return noOfPieces;
    }

    public StockItems noOfPieces(Integer noOfPieces) {
        this.noOfPieces = noOfPieces;
        return this;
    }

    public void setNoOfPieces(Integer noOfPieces) {
        this.noOfPieces = noOfPieces;
    }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    public StockItems noOfItems(Integer noOfItems) {
        this.noOfItems = noOfItems;
        return this;
    }

    public void setNoOfItems(Integer noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getManufacture() {
        return manufacture;
    }

    public StockItems manufacture(String manufacture) {
        this.manufacture = manufacture;
        return this;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getMarketingComments() {
        return marketingComments;
    }

    public StockItems marketingComments(String marketingComments) {
        this.marketingComments = marketingComments;
        return this;
    }

    public void setMarketingComments(String marketingComments) {
        this.marketingComments = marketingComments;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public StockItems internalComments(String internalComments) {
        this.internalComments = internalComments;
        return this;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public Instant getSellStartDate() {
        return sellStartDate;
    }

    public StockItems sellStartDate(Instant sellStartDate) {
        this.sellStartDate = sellStartDate;
        return this;
    }

    public void setSellStartDate(Instant sellStartDate) {
        this.sellStartDate = sellStartDate;
    }

    public Instant getSellEndDate() {
        return sellEndDate;
    }

    public StockItems sellEndDate(Instant sellEndDate) {
        this.sellEndDate = sellEndDate;
        return this;
    }

    public void setSellEndDate(Instant sellEndDate) {
        this.sellEndDate = sellEndDate;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public StockItems sellCount(Integer sellCount) {
        this.sellCount = sellCount;
        return this;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getCustomFields() {
        return customFields;
    }

    public StockItems customFields(String customFields) {
        this.customFields = customFields;
        return this;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public StockItems thumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public StockItems activeInd(Boolean activeInd) {
        this.activeInd = activeInd;
        return this;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public StockItems lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public StockItems lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public ReviewLines getStockItemOnReviewLine() {
        return stockItemOnReviewLine;
    }

    public StockItems stockItemOnReviewLine(ReviewLines reviewLines) {
        this.stockItemOnReviewLine = reviewLines;
        return this;
    }

    public void setStockItemOnReviewLine(ReviewLines reviewLines) {
        this.stockItemOnReviewLine = reviewLines;
    }

    public Set<Photos> getPhotoLists() {
        return photoLists;
    }

    public StockItems photoLists(Set<Photos> photos) {
        this.photoLists = photos;
        return this;
    }

    public StockItems addPhotoList(Photos photos) {
        this.photoLists.add(photos);
        photos.setStockItem(this);
        return this;
    }

    public StockItems removePhotoList(Photos photos) {
        this.photoLists.remove(photos);
        photos.setStockItem(null);
        return this;
    }

    public void setPhotoLists(Set<Photos> photos) {
        this.photoLists = photos;
    }

    public Set<DangerousGoods> getDangerousGoodLists() {
        return dangerousGoodLists;
    }

    public StockItems dangerousGoodLists(Set<DangerousGoods> dangerousGoods) {
        this.dangerousGoodLists = dangerousGoods;
        return this;
    }

    public StockItems addDangerousGoodList(DangerousGoods dangerousGoods) {
        this.dangerousGoodLists.add(dangerousGoods);
        dangerousGoods.setStockItem(this);
        return this;
    }

    public StockItems removeDangerousGoodList(DangerousGoods dangerousGoods) {
        this.dangerousGoodLists.remove(dangerousGoods);
        dangerousGoods.setStockItem(null);
        return this;
    }

    public void setDangerousGoodLists(Set<DangerousGoods> dangerousGoods) {
        this.dangerousGoodLists = dangerousGoods;
    }

    public Set<SpecialDeals> getSpecialDiscounts() {
        return specialDiscounts;
    }

    public StockItems specialDiscounts(Set<SpecialDeals> specialDeals) {
        this.specialDiscounts = specialDeals;
        return this;
    }

    public StockItems addSpecialDiscount(SpecialDeals specialDeals) {
        this.specialDiscounts.add(specialDeals);
        specialDeals.setStockItem(this);
        return this;
    }

    public StockItems removeSpecialDiscount(SpecialDeals specialDeals) {
        this.specialDiscounts.remove(specialDeals);
        specialDeals.setStockItem(null);
        return this;
    }

    public void setSpecialDiscounts(Set<SpecialDeals> specialDeals) {
        this.specialDiscounts = specialDeals;
    }

    public UnitMeasure getItemLengthUnit() {
        return itemLengthUnit;
    }

    public StockItems itemLengthUnit(UnitMeasure unitMeasure) {
        this.itemLengthUnit = unitMeasure;
        return this;
    }

    public void setItemLengthUnit(UnitMeasure unitMeasure) {
        this.itemLengthUnit = unitMeasure;
    }

    public UnitMeasure getItemWidthUnit() {
        return itemWidthUnit;
    }

    public StockItems itemWidthUnit(UnitMeasure unitMeasure) {
        this.itemWidthUnit = unitMeasure;
        return this;
    }

    public void setItemWidthUnit(UnitMeasure unitMeasure) {
        this.itemWidthUnit = unitMeasure;
    }

    public UnitMeasure getItemHeightUnit() {
        return itemHeightUnit;
    }

    public StockItems itemHeightUnit(UnitMeasure unitMeasure) {
        this.itemHeightUnit = unitMeasure;
        return this;
    }

    public void setItemHeightUnit(UnitMeasure unitMeasure) {
        this.itemHeightUnit = unitMeasure;
    }

    public UnitMeasure getPackageLengthUnit() {
        return packageLengthUnit;
    }

    public StockItems packageLengthUnit(UnitMeasure unitMeasure) {
        this.packageLengthUnit = unitMeasure;
        return this;
    }

    public void setPackageLengthUnit(UnitMeasure unitMeasure) {
        this.packageLengthUnit = unitMeasure;
    }

    public UnitMeasure getPackageWidthUnit() {
        return packageWidthUnit;
    }

    public StockItems packageWidthUnit(UnitMeasure unitMeasure) {
        this.packageWidthUnit = unitMeasure;
        return this;
    }

    public void setPackageWidthUnit(UnitMeasure unitMeasure) {
        this.packageWidthUnit = unitMeasure;
    }

    public UnitMeasure getPackageHeightUnit() {
        return packageHeightUnit;
    }

    public StockItems packageHeightUnit(UnitMeasure unitMeasure) {
        this.packageHeightUnit = unitMeasure;
        return this;
    }

    public void setPackageHeightUnit(UnitMeasure unitMeasure) {
        this.packageHeightUnit = unitMeasure;
    }

    public UnitMeasure getItemPackageWeightUnit() {
        return itemPackageWeightUnit;
    }

    public StockItems itemPackageWeightUnit(UnitMeasure unitMeasure) {
        this.itemPackageWeightUnit = unitMeasure;
        return this;
    }

    public void setItemPackageWeightUnit(UnitMeasure unitMeasure) {
        this.itemPackageWeightUnit = unitMeasure;
    }

    public ProductAttribute getProductAttribute() {
        return productAttribute;
    }

    public StockItems productAttribute(ProductAttribute productAttribute) {
        this.productAttribute = productAttribute;
        return this;
    }

    public void setProductAttribute(ProductAttribute productAttribute) {
        this.productAttribute = productAttribute;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public StockItems productOption(ProductOption productOption) {
        this.productOption = productOption;
        return this;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public Materials getMaterial() {
        return material;
    }

    public StockItems material(Materials materials) {
        this.material = materials;
        return this;
    }

    public void setMaterial(Materials materials) {
        this.material = materials;
    }

    public Currency getCurrency() {
        return currency;
    }

    public StockItems currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BarcodeTypes getBarcodeType() {
        return barcodeType;
    }

    public StockItems barcodeType(BarcodeTypes barcodeTypes) {
        this.barcodeType = barcodeTypes;
        return this;
    }

    public void setBarcodeType(BarcodeTypes barcodeTypes) {
        this.barcodeType = barcodeTypes;
    }

    public StockItemHoldings getStockItemHolding() {
        return stockItemHolding;
    }

    public StockItems stockItemHolding(StockItemHoldings stockItemHoldings) {
        this.stockItemHolding = stockItemHoldings;
        return this;
    }

    public void setStockItemHolding(StockItemHoldings stockItemHoldings) {
        this.stockItemHolding = stockItemHoldings;
    }

    public Products getProduct() {
        return product;
    }

    public StockItems product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockItems)) {
            return false;
        }
        return id != null && id.equals(((StockItems) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StockItems{" +
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
            "}";
    }
}
