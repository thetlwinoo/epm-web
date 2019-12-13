package com.epmweb.server.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.StockItemTransactions} entity.
 */
public class StockItemTransactionsDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant transactionOccuredWhen;

    private BigDecimal quantity;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long stockItemId;

    private String stockItemName;

    private Long customerId;

    private Long invoiceId;

    private Long supplierId;

    private String supplierName;

    private Long transactionTypeId;

    private String transactionTypeName;

    private Long purchaseOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTransactionOccuredWhen() {
        return transactionOccuredWhen;
    }

    public void setTransactionOccuredWhen(Instant transactionOccuredWhen) {
        this.transactionOccuredWhen = transactionOccuredWhen;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoicesId) {
        this.invoiceId = invoicesId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String suppliersName) {
        this.supplierName = suppliersName;
    }

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypesId) {
        this.transactionTypeId = transactionTypesId;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypesName) {
        this.transactionTypeName = transactionTypesName;
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

        StockItemTransactionsDTO stockItemTransactionsDTO = (StockItemTransactionsDTO) o;
        if (stockItemTransactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockItemTransactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockItemTransactionsDTO{" +
            "id=" + getId() +
            ", transactionOccuredWhen='" + getTransactionOccuredWhen() + "'" +
            ", quantity=" + getQuantity() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", stockItem=" + getStockItemId() +
            ", stockItem='" + getStockItemName() + "'" +
            ", customer=" + getCustomerId() +
            ", invoice=" + getInvoiceId() +
            ", supplier=" + getSupplierId() +
            ", supplier='" + getSupplierName() + "'" +
            ", transactionType=" + getTransactionTypeId() +
            ", transactionType='" + getTransactionTypeName() + "'" +
            ", purchaseOrder=" + getPurchaseOrderId() +
            "}";
    }
}
