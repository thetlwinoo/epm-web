package com.epmweb.server.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.PurchaseOrderLines} entity.
 */
public class PurchaseOrderLinesDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer orderedOuters;

    @NotNull
    private String description;

    @NotNull
    private Integer receivedOuters;

    private BigDecimal expectedUnitPricePerOuter;

    private Instant lastReceiptDate;

    @NotNull
    private Boolean isOrderLineFinalized;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long packageTypeId;

    private String packageTypeName;

    private Long stockItemId;

    private String stockItemName;

    private Long purchaseOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderedOuters() {
        return orderedOuters;
    }

    public void setOrderedOuters(Integer orderedOuters) {
        this.orderedOuters = orderedOuters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReceivedOuters() {
        return receivedOuters;
    }

    public void setReceivedOuters(Integer receivedOuters) {
        this.receivedOuters = receivedOuters;
    }

    public BigDecimal getExpectedUnitPricePerOuter() {
        return expectedUnitPricePerOuter;
    }

    public void setExpectedUnitPricePerOuter(BigDecimal expectedUnitPricePerOuter) {
        this.expectedUnitPricePerOuter = expectedUnitPricePerOuter;
    }

    public Instant getLastReceiptDate() {
        return lastReceiptDate;
    }

    public void setLastReceiptDate(Instant lastReceiptDate) {
        this.lastReceiptDate = lastReceiptDate;
    }

    public Boolean isIsOrderLineFinalized() {
        return isOrderLineFinalized;
    }

    public void setIsOrderLineFinalized(Boolean isOrderLineFinalized) {
        this.isOrderLineFinalized = isOrderLineFinalized;
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

    public Long getPackageTypeId() {
        return packageTypeId;
    }

    public void setPackageTypeId(Long packageTypesId) {
        this.packageTypeId = packageTypesId;
    }

    public String getPackageTypeName() {
        return packageTypeName;
    }

    public void setPackageTypeName(String packageTypesName) {
        this.packageTypeName = packageTypesName;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    public String getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(String stockItemsName) {
        this.stockItemName = stockItemsName;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrdersId) {
        this.purchaseOrderId = purchaseOrdersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PurchaseOrderLinesDTO purchaseOrderLinesDTO = (PurchaseOrderLinesDTO) o;
        if (purchaseOrderLinesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseOrderLinesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseOrderLinesDTO{" +
            "id=" + getId() +
            ", orderedOuters=" + getOrderedOuters() +
            ", description='" + getDescription() + "'" +
            ", receivedOuters=" + getReceivedOuters() +
            ", expectedUnitPricePerOuter=" + getExpectedUnitPricePerOuter() +
            ", lastReceiptDate='" + getLastReceiptDate() + "'" +
            ", isOrderLineFinalized='" + isIsOrderLineFinalized() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", packageType=" + getPackageTypeId() +
            ", packageType='" + getPackageTypeName() + "'" +
            ", stockItem=" + getStockItemId() +
            ", stockItem='" + getStockItemName() + "'" +
            ", purchaseOrder=" + getPurchaseOrderId() +
            "}";
    }
}
