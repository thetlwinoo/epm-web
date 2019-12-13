package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.ProductOption} entity.
 */
public class ProductOptionDTO implements Serializable {

    private Long id;

    @NotNull
    private String value;


    private Long productOptionSetId;

    private String productOptionSetValue;

    private Long supplierId;

    private String supplierName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getProductOptionSetId() {
        return productOptionSetId;
    }

    public void setProductOptionSetId(Long productOptionSetId) {
        this.productOptionSetId = productOptionSetId;
    }

    public String getProductOptionSetValue() {
        return productOptionSetValue;
    }

    public void setProductOptionSetValue(String productOptionSetValue) {
        this.productOptionSetValue = productOptionSetValue;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductOptionDTO productOptionDTO = (ProductOptionDTO) o;
        if (productOptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productOptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductOptionDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", productOptionSet=" + getProductOptionSetId() +
            ", productOptionSet='" + getProductOptionSetValue() + "'" +
            ", supplier=" + getSupplierId() +
            ", supplier='" + getSupplierName() + "'" +
            "}";
    }
}
