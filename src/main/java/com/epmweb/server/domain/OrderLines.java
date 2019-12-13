package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import com.epmweb.server.domain.enumeration.OrderLineStatus;

/**
 * A OrderLines.
 */
@Entity
@Table(name = "order_lines")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderLines implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "carrier_tracking_number")
    private String carrierTrackingNumber;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", precision = 21, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "unit_price_discount", precision = 21, scale = 2)
    private BigDecimal unitPriceDiscount;

    @Column(name = "line_total", precision = 21, scale = 2)
    private BigDecimal lineTotal;

    @Column(name = "tax_rate", precision = 21, scale = 2)
    private BigDecimal taxRate;

    @Column(name = "picked_quantity")
    private Integer pickedQuantity;

    @Column(name = "picking_completed_when")
    private Instant pickingCompletedWhen;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderLineStatus status;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @ManyToOne
    @JsonIgnoreProperties("orderLines")
    private StockItems stockItem;

    @ManyToOne
    @JsonIgnoreProperties("orderLines")
    private PackageTypes packageType;

    @ManyToOne
    @JsonIgnoreProperties("orderLineLists")
    private Orders order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarrierTrackingNumber() {
        return carrierTrackingNumber;
    }

    public OrderLines carrierTrackingNumber(String carrierTrackingNumber) {
        this.carrierTrackingNumber = carrierTrackingNumber;
        return this;
    }

    public void setCarrierTrackingNumber(String carrierTrackingNumber) {
        this.carrierTrackingNumber = carrierTrackingNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderLines quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public OrderLines unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPriceDiscount() {
        return unitPriceDiscount;
    }

    public OrderLines unitPriceDiscount(BigDecimal unitPriceDiscount) {
        this.unitPriceDiscount = unitPriceDiscount;
        return this;
    }

    public void setUnitPriceDiscount(BigDecimal unitPriceDiscount) {
        this.unitPriceDiscount = unitPriceDiscount;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public OrderLines lineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
        return this;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public OrderLines taxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getPickedQuantity() {
        return pickedQuantity;
    }

    public OrderLines pickedQuantity(Integer pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
        return this;
    }

    public void setPickedQuantity(Integer pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
    }

    public Instant getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public OrderLines pickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
        return this;
    }

    public void setPickingCompletedWhen(Instant pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public OrderLineStatus getStatus() {
        return status;
    }

    public OrderLines status(OrderLineStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderLineStatus status) {
        this.status = status;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public OrderLines lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public OrderLines lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public OrderLines stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public PackageTypes getPackageType() {
        return packageType;
    }

    public OrderLines packageType(PackageTypes packageTypes) {
        this.packageType = packageTypes;
        return this;
    }

    public void setPackageType(PackageTypes packageTypes) {
        this.packageType = packageTypes;
    }

    public Orders getOrder() {
        return order;
    }

    public OrderLines order(Orders orders) {
        this.order = orders;
        return this;
    }

    public void setOrder(Orders orders) {
        this.order = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderLines)) {
            return false;
        }
        return id != null && id.equals(((OrderLines) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderLines{" +
            "id=" + getId() +
            ", carrierTrackingNumber='" + getCarrierTrackingNumber() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", unitPriceDiscount=" + getUnitPriceDiscount() +
            ", lineTotal=" + getLineTotal() +
            ", taxRate=" + getTaxRate() +
            ", pickedQuantity=" + getPickedQuantity() +
            ", pickingCompletedWhen='" + getPickingCompletedWhen() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
