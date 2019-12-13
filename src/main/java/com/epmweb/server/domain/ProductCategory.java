package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProductCategory.
 */
@Entity
@Table(name = "product_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_label")
    private String shortLabel;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @OneToMany(mappedBy = "productCategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Photos> photoLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("productCategories")
    private ProductCategory parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProductCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public ProductCategory shortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
        return this;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public ProductCategory thumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Set<Photos> getPhotoLists() {
        return photoLists;
    }

    public ProductCategory photoLists(Set<Photos> photos) {
        this.photoLists = photos;
        return this;
    }

    public ProductCategory addPhotoList(Photos photos) {
        this.photoLists.add(photos);
        photos.setProductCategory(this);
        return this;
    }

    public ProductCategory removePhotoList(Photos photos) {
        this.photoLists.remove(photos);
        photos.setProductCategory(null);
        return this;
    }

    public void setPhotoLists(Set<Photos> photos) {
        this.photoLists = photos;
    }

    public ProductCategory getParent() {
        return parent;
    }

    public ProductCategory parent(ProductCategory productCategory) {
        this.parent = productCategory;
        return this;
    }

    public void setParent(ProductCategory productCategory) {
        this.parent = productCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategory)) {
            return false;
        }
        return id != null && id.equals(((ProductCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            "}";
    }
}
