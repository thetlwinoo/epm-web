package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductOption.
 */
@Entity
@Table(name = "product_option")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne
    @JsonIgnoreProperties("productOptions")
    private ProductOptionSet productOptionSet;

    @ManyToOne
    @JsonIgnoreProperties("productOptions")
    private Suppliers supplier;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public ProductOption value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ProductOptionSet getProductOptionSet() {
        return productOptionSet;
    }

    public ProductOption productOptionSet(ProductOptionSet productOptionSet) {
        this.productOptionSet = productOptionSet;
        return this;
    }

    public void setProductOptionSet(ProductOptionSet productOptionSet) {
        this.productOptionSet = productOptionSet;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public ProductOption supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOption)) {
            return false;
        }
        return id != null && id.equals(((ProductOption) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductOption{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
