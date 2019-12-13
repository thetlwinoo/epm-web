package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A StockItemTransactions.
 */
@Entity
@Table(name = "stock_item_transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StockItemTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "transaction_occured_when", nullable = false)
    private Instant transactionOccuredWhen;

    @Column(name = "quantity", precision = 21, scale = 2)
    private BigDecimal quantity;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @ManyToOne
    @JsonIgnoreProperties("stockItemTransactions")
    private StockItems stockItem;

    @ManyToOne
    @JsonIgnoreProperties("stockItemTransactions")
    private Customers customer;

    @ManyToOne
    @JsonIgnoreProperties("stockItemTransactions")
    private Invoices invoice;

    @ManyToOne
    @JsonIgnoreProperties("stockItemTransactions")
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties("stockItemTransactions")
    private TransactionTypes transactionType;

    @ManyToOne
    @JsonIgnoreProperties("stockItemTransactions")
    private PurchaseOrders purchaseOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTransactionOccuredWhen() {
        return transactionOccuredWhen;
    }

    public StockItemTransactions transactionOccuredWhen(Instant transactionOccuredWhen) {
        this.transactionOccuredWhen = transactionOccuredWhen;
        return this;
    }

    public void setTransactionOccuredWhen(Instant transactionOccuredWhen) {
        this.transactionOccuredWhen = transactionOccuredWhen;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public StockItemTransactions quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public StockItemTransactions lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public StockItemTransactions lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public StockItemTransactions stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public Customers getCustomer() {
        return customer;
    }

    public StockItemTransactions customer(Customers customers) {
        this.customer = customers;
        return this;
    }

    public void setCustomer(Customers customers) {
        this.customer = customers;
    }

    public Invoices getInvoice() {
        return invoice;
    }

    public StockItemTransactions invoice(Invoices invoices) {
        this.invoice = invoices;
        return this;
    }

    public void setInvoice(Invoices invoices) {
        this.invoice = invoices;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public StockItemTransactions supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public TransactionTypes getTransactionType() {
        return transactionType;
    }

    public StockItemTransactions transactionType(TransactionTypes transactionTypes) {
        this.transactionType = transactionTypes;
        return this;
    }

    public void setTransactionType(TransactionTypes transactionTypes) {
        this.transactionType = transactionTypes;
    }

    public PurchaseOrders getPurchaseOrder() {
        return purchaseOrder;
    }

    public StockItemTransactions purchaseOrder(PurchaseOrders purchaseOrders) {
        this.purchaseOrder = purchaseOrders;
        return this;
    }

    public void setPurchaseOrder(PurchaseOrders purchaseOrders) {
        this.purchaseOrder = purchaseOrders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockItemTransactions)) {
            return false;
        }
        return id != null && id.equals(((StockItemTransactions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StockItemTransactions{" +
            "id=" + getId() +
            ", transactionOccuredWhen='" + getTransactionOccuredWhen() + "'" +
            ", quantity=" + getQuantity() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
