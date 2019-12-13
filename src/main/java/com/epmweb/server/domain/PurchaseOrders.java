package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A PurchaseOrders.
 */
@Entity
@Table(name = "purchase_orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private Instant orderDate;

    @Column(name = "expected_delivery_date")
    private Instant expectedDeliveryDate;

    @Column(name = "supplier_reference")
    private String supplierReference;

    @NotNull
    @Column(name = "is_order_finalized", nullable = false)
    private Integer isOrderFinalized;

    @Column(name = "comments")
    private String comments;

    @Column(name = "internal_comments")
    private String internalComments;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @OneToMany(mappedBy = "purchaseOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchaseOrderLines> purchaseOrderLineLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("purchaseOrders")
    private People contactPerson;

    @ManyToOne
    @JsonIgnoreProperties("purchaseOrders")
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties("purchaseOrders")
    private DeliveryMethods deliveryMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public PurchaseOrders orderDate(Instant orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Instant getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public PurchaseOrders expectedDeliveryDate(Instant expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public void setExpectedDeliveryDate(Instant expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getSupplierReference() {
        return supplierReference;
    }

    public PurchaseOrders supplierReference(String supplierReference) {
        this.supplierReference = supplierReference;
        return this;
    }

    public void setSupplierReference(String supplierReference) {
        this.supplierReference = supplierReference;
    }

    public Integer getIsOrderFinalized() {
        return isOrderFinalized;
    }

    public PurchaseOrders isOrderFinalized(Integer isOrderFinalized) {
        this.isOrderFinalized = isOrderFinalized;
        return this;
    }

    public void setIsOrderFinalized(Integer isOrderFinalized) {
        this.isOrderFinalized = isOrderFinalized;
    }

    public String getComments() {
        return comments;
    }

    public PurchaseOrders comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public PurchaseOrders internalComments(String internalComments) {
        this.internalComments = internalComments;
        return this;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public PurchaseOrders lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public PurchaseOrders lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Set<PurchaseOrderLines> getPurchaseOrderLineLists() {
        return purchaseOrderLineLists;
    }

    public PurchaseOrders purchaseOrderLineLists(Set<PurchaseOrderLines> purchaseOrderLines) {
        this.purchaseOrderLineLists = purchaseOrderLines;
        return this;
    }

    public PurchaseOrders addPurchaseOrderLineList(PurchaseOrderLines purchaseOrderLines) {
        this.purchaseOrderLineLists.add(purchaseOrderLines);
        purchaseOrderLines.setPurchaseOrder(this);
        return this;
    }

    public PurchaseOrders removePurchaseOrderLineList(PurchaseOrderLines purchaseOrderLines) {
        this.purchaseOrderLineLists.remove(purchaseOrderLines);
        purchaseOrderLines.setPurchaseOrder(null);
        return this;
    }

    public void setPurchaseOrderLineLists(Set<PurchaseOrderLines> purchaseOrderLines) {
        this.purchaseOrderLineLists = purchaseOrderLines;
    }

    public People getContactPerson() {
        return contactPerson;
    }

    public PurchaseOrders contactPerson(People people) {
        this.contactPerson = people;
        return this;
    }

    public void setContactPerson(People people) {
        this.contactPerson = people;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public PurchaseOrders supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public DeliveryMethods getDeliveryMethod() {
        return deliveryMethod;
    }

    public PurchaseOrders deliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
        return this;
    }

    public void setDeliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseOrders)) {
            return false;
        }
        return id != null && id.equals(((PurchaseOrders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PurchaseOrders{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", supplierReference='" + getSupplierReference() + "'" +
            ", isOrderFinalized=" + getIsOrderFinalized() +
            ", comments='" + getComments() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
