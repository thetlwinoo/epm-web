package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.ProductCategory} entity.
 */
public class ProductCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String shortLabel;

    private String thumbnailUrl;


    private Long parentId;

    private String parentName;

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

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long productCategoryId) {
        this.parentId = productCategoryId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String productCategoryName) {
        this.parentName = productCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductCategoryDTO productCategoryDTO = (ProductCategoryDTO) o;
        if (productCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            ", parent=" + getParentId() +
            ", parent='" + getParentName() + "'" +
            "}";
    }
}
