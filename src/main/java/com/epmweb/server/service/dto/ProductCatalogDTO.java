package com.epmweb.server.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.ProductCatalog} entity.
 */
public class ProductCatalogDTO implements Serializable {

    private Long id;


    private Long productCategoryId;

    private String productCategoryName;

    private Long productId;

    private String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productsId) {
        this.productId = productsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productsName) {
        this.productName = productsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductCatalogDTO productCatalogDTO = (ProductCatalogDTO) o;
        if (productCatalogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productCatalogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductCatalogDTO{" +
            "id=" + getId() +
            ", productCategory=" + getProductCategoryId() +
            ", productCategory='" + getProductCategoryName() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductName() + "'" +
            "}";
    }
}
