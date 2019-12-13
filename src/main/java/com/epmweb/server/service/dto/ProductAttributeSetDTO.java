package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.ProductAttributeSet} entity.
 */
public class ProductAttributeSetDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    private Long productOptionSetId;

    private String productOptionSetValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductAttributeSetDTO productAttributeSetDTO = (ProductAttributeSetDTO) o;
        if (productAttributeSetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productAttributeSetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductAttributeSetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", productOptionSet=" + getProductOptionSetId() +
            ", productOptionSet='" + getProductOptionSetValue() + "'" +
            "}";
    }
}
