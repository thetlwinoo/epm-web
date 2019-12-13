package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.ProductTags} entity.
 */
public class ProductTagsDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    private Long productId;

    private String productName;

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

        ProductTagsDTO productTagsDTO = (ProductTagsDTO) o;
        if (productTagsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productTagsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductTagsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductName() + "'" +
            "}";
    }
}
