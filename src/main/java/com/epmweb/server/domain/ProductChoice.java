package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductChoice.
 */
@Entity
@Table(name = "product_choice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductChoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "is_multiply", nullable = false)
    private Boolean isMultiply;

    @ManyToOne
    @JsonIgnoreProperties("productChoices")
    private ProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties("productChoices")
    private ProductAttributeSet productAttributeSet;

    @ManyToOne
    @JsonIgnoreProperties("productChoices")
    private ProductOptionSet productOptionSet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsMultiply() {
        return isMultiply;
    }

    public ProductChoice isMultiply(Boolean isMultiply) {
        this.isMultiply = isMultiply;
        return this;
    }

    public void setIsMultiply(Boolean isMultiply) {
        this.isMultiply = isMultiply;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public ProductChoice productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductAttributeSet getProductAttributeSet() {
        return productAttributeSet;
    }

    public ProductChoice productAttributeSet(ProductAttributeSet productAttributeSet) {
        this.productAttributeSet = productAttributeSet;
        return this;
    }

    public void setProductAttributeSet(ProductAttributeSet productAttributeSet) {
        this.productAttributeSet = productAttributeSet;
    }

    public ProductOptionSet getProductOptionSet() {
        return productOptionSet;
    }

    public ProductChoice productOptionSet(ProductOptionSet productOptionSet) {
        this.productOptionSet = productOptionSet;
        return this;
    }

    public void setProductOptionSet(ProductOptionSet productOptionSet) {
        this.productOptionSet = productOptionSet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductChoice)) {
            return false;
        }
        return id != null && id.equals(((ProductChoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductChoice{" +
            "id=" + getId() +
            ", isMultiply='" + isIsMultiply() + "'" +
            "}";
    }
}
