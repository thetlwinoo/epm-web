package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.ProductBrand} entity.
 */
public class ProductBrandDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String thumbnailUrl;


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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductBrandDTO productBrandDTO = (ProductBrandDTO) o;
        if (productBrandDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productBrandDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductBrandDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            "}";
    }
}
