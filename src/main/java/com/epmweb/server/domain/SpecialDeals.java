package com.epmweb.server.domain;
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
 * A SpecialDeals.
 */
@Entity
@Table(name = "special_deals")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SpecialDeals implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "deal_description", nullable = false)
    private String dealDescription;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Column(name = "discount_amount", precision = 21, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "discount_percentage", precision = 21, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "unit_price", precision = 21, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @OneToMany(mappedBy = "specialDeals",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShoppingCarts> cartDiscounts = new HashSet<>();

    @OneToMany(mappedBy = "specialDeals",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Orders> orderDiscounts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("specialDeals")
    private BuyingGroups buyingGroup;

    @ManyToOne
    @JsonIgnoreProperties("specialDeals")
    private CustomerCategories customerCategory;

    @ManyToOne
    @JsonIgnoreProperties("specialDeals")
    private Customers customer;

    @ManyToOne
    @JsonIgnoreProperties("specialDeals")
    private ProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties("specialDiscounts")
    private StockItems stockItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public SpecialDeals dealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
        return this;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public SpecialDeals startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public SpecialDeals endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public SpecialDeals discountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public SpecialDeals discountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
        return this;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public SpecialDeals discountCode(String discountCode) {
        this.discountCode = discountCode;
        return this;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public SpecialDeals unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public SpecialDeals lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public SpecialDeals lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Set<ShoppingCarts> getCartDiscounts() {
        return cartDiscounts;
    }

    public SpecialDeals cartDiscounts(Set<ShoppingCarts> shoppingCarts) {
        this.cartDiscounts = shoppingCarts;
        return this;
    }

    public SpecialDeals addCartDiscount(ShoppingCarts shoppingCarts) {
        this.cartDiscounts.add(shoppingCarts);
        shoppingCarts.setSpecialDeals(this);
        return this;
    }

    public SpecialDeals removeCartDiscount(ShoppingCarts shoppingCarts) {
        this.cartDiscounts.remove(shoppingCarts);
        shoppingCarts.setSpecialDeals(null);
        return this;
    }

    public void setCartDiscounts(Set<ShoppingCarts> shoppingCarts) {
        this.cartDiscounts = shoppingCarts;
    }

    public Set<Orders> getOrderDiscounts() {
        return orderDiscounts;
    }

    public SpecialDeals orderDiscounts(Set<Orders> orders) {
        this.orderDiscounts = orders;
        return this;
    }

    public SpecialDeals addOrderDiscount(Orders orders) {
        this.orderDiscounts.add(orders);
        orders.setSpecialDeals(this);
        return this;
    }

    public SpecialDeals removeOrderDiscount(Orders orders) {
        this.orderDiscounts.remove(orders);
        orders.setSpecialDeals(null);
        return this;
    }

    public void setOrderDiscounts(Set<Orders> orders) {
        this.orderDiscounts = orders;
    }

    public BuyingGroups getBuyingGroup() {
        return buyingGroup;
    }

    public SpecialDeals buyingGroup(BuyingGroups buyingGroups) {
        this.buyingGroup = buyingGroups;
        return this;
    }

    public void setBuyingGroup(BuyingGroups buyingGroups) {
        this.buyingGroup = buyingGroups;
    }

    public CustomerCategories getCustomerCategory() {
        return customerCategory;
    }

    public SpecialDeals customerCategory(CustomerCategories customerCategories) {
        this.customerCategory = customerCategories;
        return this;
    }

    public void setCustomerCategory(CustomerCategories customerCategories) {
        this.customerCategory = customerCategories;
    }

    public Customers getCustomer() {
        return customer;
    }

    public SpecialDeals customer(Customers customers) {
        this.customer = customers;
        return this;
    }

    public void setCustomer(Customers customers) {
        this.customer = customers;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public SpecialDeals productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public SpecialDeals stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpecialDeals)) {
            return false;
        }
        return id != null && id.equals(((SpecialDeals) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SpecialDeals{" +
            "id=" + getId() +
            ", dealDescription='" + getDealDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", discountAmount=" + getDiscountAmount() +
            ", discountPercentage=" + getDiscountPercentage() +
            ", discountCode='" + getDiscountCode() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
