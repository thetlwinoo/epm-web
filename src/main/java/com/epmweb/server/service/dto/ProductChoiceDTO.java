package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.ProductChoice} entity.
 */
public class ProductChoiceDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean isMultiply;


    private Long productCategoryId;

    private String productCategoryName;

    private Long productAttributeSetId;

    private String productAttributeSetName;

    private Long productOptionSetId;

    private String productOptionSetValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsMultiply() {
        return isMultiply;
    }

    public void setIsMultiply(Boolean isMultiply) {
        this.isMultiply = isMultiply;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Long getProductAttributeSetId() {
        return productAttributeSetId;
    }

    public void setProductAttributeSetId(Long productAttributeSetId) {
        this.productAttributeSetId = productAttributeSetId;
    }

    public String getProductAttributeSetName() {
        return productAttributeSetName;
    }

    public void setProductAttributeSetName(String productAttributeSetName) {
        this.productAttributeSetName = productAttributeSetName;
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

        ProductChoiceDTO productChoiceDTO = (ProductChoiceDTO) o;
        if (productChoiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productChoiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductChoiceDTO{" +
            "id=" + getId() +
            ", isMultiply='" + isIsMultiply() + "'" +
            ", productCategory=" + getProductCategoryId() +
            ", productCategory='" + getProductCategoryName() + "'" +
            ", productAttributeSet=" + getProductAttributeSetId() +
            ", productAttributeSet='" + getProductAttributeSetName() + "'" +
            ", productOptionSet=" + getProductOptionSetId() +
            ", productOptionSet='" + getProductOptionSetValue() + "'" +
            "}";
    }
}
