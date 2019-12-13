package com.epmweb.server.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.CompareProducts} entity.
 */
public class CompareProductsDTO implements Serializable {

    private Long id;


    private Long productId;

    private String productName;

    private Long compareId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCompareId() {
        return compareId;
    }

    public void setCompareId(Long comparesId) {
        this.compareId = comparesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompareProductsDTO compareProductsDTO = (CompareProductsDTO) o;
        if (compareProductsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compareProductsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompareProductsDTO{" +
            "id=" + getId() +
            ", product=" + getProductId() +
            ", product='" + getProductName() + "'" +
            ", compare=" + getCompareId() +
            "}";
    }
}
