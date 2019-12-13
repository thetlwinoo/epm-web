package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Photos.
 */
@Entity
@Table(name = "photos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Photos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "thumbnail_photo")
    private String thumbnailPhoto;

    @Column(name = "original_photo")
    private String originalPhoto;

    @Column(name = "banner_tall_photo")
    private String bannerTallPhoto;

    @Column(name = "banner_wide_photo")
    private String bannerWidePhoto;

    @Column(name = "circle_photo")
    private String circlePhoto;

    @Column(name = "sharpened_photo")
    private String sharpenedPhoto;

    @Column(name = "square_photo")
    private String squarePhoto;

    @Column(name = "watermark_photo")
    private String watermarkPhoto;

    @Lob
    @Column(name = "thumbnail_photo_blob")
    private byte[] thumbnailPhotoBlob;

    @Column(name = "thumbnail_photo_blob_content_type")
    private String thumbnailPhotoBlobContentType;

    @Lob
    @Column(name = "original_photo_blob")
    private byte[] originalPhotoBlob;

    @Column(name = "original_photo_blob_content_type")
    private String originalPhotoBlobContentType;

    @Lob
    @Column(name = "banner_tall_photo_blob")
    private byte[] bannerTallPhotoBlob;

    @Column(name = "banner_tall_photo_blob_content_type")
    private String bannerTallPhotoBlobContentType;

    @Lob
    @Column(name = "banner_wide_photo_blob")
    private byte[] bannerWidePhotoBlob;

    @Column(name = "banner_wide_photo_blob_content_type")
    private String bannerWidePhotoBlobContentType;

    @Lob
    @Column(name = "circle_photo_blob")
    private byte[] circlePhotoBlob;

    @Column(name = "circle_photo_blob_content_type")
    private String circlePhotoBlobContentType;

    @Lob
    @Column(name = "sharpened_photo_blob")
    private byte[] sharpenedPhotoBlob;

    @Column(name = "sharpened_photo_blob_content_type")
    private String sharpenedPhotoBlobContentType;

    @Lob
    @Column(name = "square_photo_blob")
    private byte[] squarePhotoBlob;

    @Column(name = "square_photo_blob_content_type")
    private String squarePhotoBlobContentType;

    @Lob
    @Column(name = "watermark_photo_blob")
    private byte[] watermarkPhotoBlob;

    @Column(name = "watermark_photo_blob_content_type")
    private String watermarkPhotoBlobContentType;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "default_ind")
    private Boolean defaultInd;

    @ManyToOne
    @JsonIgnoreProperties("photoLists")
    private StockItems stockItem;

    @ManyToOne
    @JsonIgnoreProperties("photoLists")
    private ProductCategory productCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThumbnailPhoto() {
        return thumbnailPhoto;
    }

    public Photos thumbnailPhoto(String thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
        return this;
    }

    public void setThumbnailPhoto(String thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
    }

    public String getOriginalPhoto() {
        return originalPhoto;
    }

    public Photos originalPhoto(String originalPhoto) {
        this.originalPhoto = originalPhoto;
        return this;
    }

    public void setOriginalPhoto(String originalPhoto) {
        this.originalPhoto = originalPhoto;
    }

    public String getBannerTallPhoto() {
        return bannerTallPhoto;
    }

    public Photos bannerTallPhoto(String bannerTallPhoto) {
        this.bannerTallPhoto = bannerTallPhoto;
        return this;
    }

    public void setBannerTallPhoto(String bannerTallPhoto) {
        this.bannerTallPhoto = bannerTallPhoto;
    }

    public String getBannerWidePhoto() {
        return bannerWidePhoto;
    }

    public Photos bannerWidePhoto(String bannerWidePhoto) {
        this.bannerWidePhoto = bannerWidePhoto;
        return this;
    }

    public void setBannerWidePhoto(String bannerWidePhoto) {
        this.bannerWidePhoto = bannerWidePhoto;
    }

    public String getCirclePhoto() {
        return circlePhoto;
    }

    public Photos circlePhoto(String circlePhoto) {
        this.circlePhoto = circlePhoto;
        return this;
    }

    public void setCirclePhoto(String circlePhoto) {
        this.circlePhoto = circlePhoto;
    }

    public String getSharpenedPhoto() {
        return sharpenedPhoto;
    }

    public Photos sharpenedPhoto(String sharpenedPhoto) {
        this.sharpenedPhoto = sharpenedPhoto;
        return this;
    }

    public void setSharpenedPhoto(String sharpenedPhoto) {
        this.sharpenedPhoto = sharpenedPhoto;
    }

    public String getSquarePhoto() {
        return squarePhoto;
    }

    public Photos squarePhoto(String squarePhoto) {
        this.squarePhoto = squarePhoto;
        return this;
    }

    public void setSquarePhoto(String squarePhoto) {
        this.squarePhoto = squarePhoto;
    }

    public String getWatermarkPhoto() {
        return watermarkPhoto;
    }

    public Photos watermarkPhoto(String watermarkPhoto) {
        this.watermarkPhoto = watermarkPhoto;
        return this;
    }

    public void setWatermarkPhoto(String watermarkPhoto) {
        this.watermarkPhoto = watermarkPhoto;
    }

    public byte[] getThumbnailPhotoBlob() {
        return thumbnailPhotoBlob;
    }

    public Photos thumbnailPhotoBlob(byte[] thumbnailPhotoBlob) {
        this.thumbnailPhotoBlob = thumbnailPhotoBlob;
        return this;
    }

    public void setThumbnailPhotoBlob(byte[] thumbnailPhotoBlob) {
        this.thumbnailPhotoBlob = thumbnailPhotoBlob;
    }

    public String getThumbnailPhotoBlobContentType() {
        return thumbnailPhotoBlobContentType;
    }

    public Photos thumbnailPhotoBlobContentType(String thumbnailPhotoBlobContentType) {
        this.thumbnailPhotoBlobContentType = thumbnailPhotoBlobContentType;
        return this;
    }

    public void setThumbnailPhotoBlobContentType(String thumbnailPhotoBlobContentType) {
        this.thumbnailPhotoBlobContentType = thumbnailPhotoBlobContentType;
    }

    public byte[] getOriginalPhotoBlob() {
        return originalPhotoBlob;
    }

    public Photos originalPhotoBlob(byte[] originalPhotoBlob) {
        this.originalPhotoBlob = originalPhotoBlob;
        return this;
    }

    public void setOriginalPhotoBlob(byte[] originalPhotoBlob) {
        this.originalPhotoBlob = originalPhotoBlob;
    }

    public String getOriginalPhotoBlobContentType() {
        return originalPhotoBlobContentType;
    }

    public Photos originalPhotoBlobContentType(String originalPhotoBlobContentType) {
        this.originalPhotoBlobContentType = originalPhotoBlobContentType;
        return this;
    }

    public void setOriginalPhotoBlobContentType(String originalPhotoBlobContentType) {
        this.originalPhotoBlobContentType = originalPhotoBlobContentType;
    }

    public byte[] getBannerTallPhotoBlob() {
        return bannerTallPhotoBlob;
    }

    public Photos bannerTallPhotoBlob(byte[] bannerTallPhotoBlob) {
        this.bannerTallPhotoBlob = bannerTallPhotoBlob;
        return this;
    }

    public void setBannerTallPhotoBlob(byte[] bannerTallPhotoBlob) {
        this.bannerTallPhotoBlob = bannerTallPhotoBlob;
    }

    public String getBannerTallPhotoBlobContentType() {
        return bannerTallPhotoBlobContentType;
    }

    public Photos bannerTallPhotoBlobContentType(String bannerTallPhotoBlobContentType) {
        this.bannerTallPhotoBlobContentType = bannerTallPhotoBlobContentType;
        return this;
    }

    public void setBannerTallPhotoBlobContentType(String bannerTallPhotoBlobContentType) {
        this.bannerTallPhotoBlobContentType = bannerTallPhotoBlobContentType;
    }

    public byte[] getBannerWidePhotoBlob() {
        return bannerWidePhotoBlob;
    }

    public Photos bannerWidePhotoBlob(byte[] bannerWidePhotoBlob) {
        this.bannerWidePhotoBlob = bannerWidePhotoBlob;
        return this;
    }

    public void setBannerWidePhotoBlob(byte[] bannerWidePhotoBlob) {
        this.bannerWidePhotoBlob = bannerWidePhotoBlob;
    }

    public String getBannerWidePhotoBlobContentType() {
        return bannerWidePhotoBlobContentType;
    }

    public Photos bannerWidePhotoBlobContentType(String bannerWidePhotoBlobContentType) {
        this.bannerWidePhotoBlobContentType = bannerWidePhotoBlobContentType;
        return this;
    }

    public void setBannerWidePhotoBlobContentType(String bannerWidePhotoBlobContentType) {
        this.bannerWidePhotoBlobContentType = bannerWidePhotoBlobContentType;
    }

    public byte[] getCirclePhotoBlob() {
        return circlePhotoBlob;
    }

    public Photos circlePhotoBlob(byte[] circlePhotoBlob) {
        this.circlePhotoBlob = circlePhotoBlob;
        return this;
    }

    public void setCirclePhotoBlob(byte[] circlePhotoBlob) {
        this.circlePhotoBlob = circlePhotoBlob;
    }

    public String getCirclePhotoBlobContentType() {
        return circlePhotoBlobContentType;
    }

    public Photos circlePhotoBlobContentType(String circlePhotoBlobContentType) {
        this.circlePhotoBlobContentType = circlePhotoBlobContentType;
        return this;
    }

    public void setCirclePhotoBlobContentType(String circlePhotoBlobContentType) {
        this.circlePhotoBlobContentType = circlePhotoBlobContentType;
    }

    public byte[] getSharpenedPhotoBlob() {
        return sharpenedPhotoBlob;
    }

    public Photos sharpenedPhotoBlob(byte[] sharpenedPhotoBlob) {
        this.sharpenedPhotoBlob = sharpenedPhotoBlob;
        return this;
    }

    public void setSharpenedPhotoBlob(byte[] sharpenedPhotoBlob) {
        this.sharpenedPhotoBlob = sharpenedPhotoBlob;
    }

    public String getSharpenedPhotoBlobContentType() {
        return sharpenedPhotoBlobContentType;
    }

    public Photos sharpenedPhotoBlobContentType(String sharpenedPhotoBlobContentType) {
        this.sharpenedPhotoBlobContentType = sharpenedPhotoBlobContentType;
        return this;
    }

    public void setSharpenedPhotoBlobContentType(String sharpenedPhotoBlobContentType) {
        this.sharpenedPhotoBlobContentType = sharpenedPhotoBlobContentType;
    }

    public byte[] getSquarePhotoBlob() {
        return squarePhotoBlob;
    }

    public Photos squarePhotoBlob(byte[] squarePhotoBlob) {
        this.squarePhotoBlob = squarePhotoBlob;
        return this;
    }

    public void setSquarePhotoBlob(byte[] squarePhotoBlob) {
        this.squarePhotoBlob = squarePhotoBlob;
    }

    public String getSquarePhotoBlobContentType() {
        return squarePhotoBlobContentType;
    }

    public Photos squarePhotoBlobContentType(String squarePhotoBlobContentType) {
        this.squarePhotoBlobContentType = squarePhotoBlobContentType;
        return this;
    }

    public void setSquarePhotoBlobContentType(String squarePhotoBlobContentType) {
        this.squarePhotoBlobContentType = squarePhotoBlobContentType;
    }

    public byte[] getWatermarkPhotoBlob() {
        return watermarkPhotoBlob;
    }

    public Photos watermarkPhotoBlob(byte[] watermarkPhotoBlob) {
        this.watermarkPhotoBlob = watermarkPhotoBlob;
        return this;
    }

    public void setWatermarkPhotoBlob(byte[] watermarkPhotoBlob) {
        this.watermarkPhotoBlob = watermarkPhotoBlob;
    }

    public String getWatermarkPhotoBlobContentType() {
        return watermarkPhotoBlobContentType;
    }

    public Photos watermarkPhotoBlobContentType(String watermarkPhotoBlobContentType) {
        this.watermarkPhotoBlobContentType = watermarkPhotoBlobContentType;
        return this;
    }

    public void setWatermarkPhotoBlobContentType(String watermarkPhotoBlobContentType) {
        this.watermarkPhotoBlobContentType = watermarkPhotoBlobContentType;
    }

    public Integer getPriority() {
        return priority;
    }

    public Photos priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public Photos defaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
        return this;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public Photos stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public Photos productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Photos)) {
            return false;
        }
        return id != null && id.equals(((Photos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Photos{" +
            "id=" + getId() +
            ", thumbnailPhoto='" + getThumbnailPhoto() + "'" +
            ", originalPhoto='" + getOriginalPhoto() + "'" +
            ", bannerTallPhoto='" + getBannerTallPhoto() + "'" +
            ", bannerWidePhoto='" + getBannerWidePhoto() + "'" +
            ", circlePhoto='" + getCirclePhoto() + "'" +
            ", sharpenedPhoto='" + getSharpenedPhoto() + "'" +
            ", squarePhoto='" + getSquarePhoto() + "'" +
            ", watermarkPhoto='" + getWatermarkPhoto() + "'" +
            ", thumbnailPhotoBlob='" + getThumbnailPhotoBlob() + "'" +
            ", thumbnailPhotoBlobContentType='" + getThumbnailPhotoBlobContentType() + "'" +
            ", originalPhotoBlob='" + getOriginalPhotoBlob() + "'" +
            ", originalPhotoBlobContentType='" + getOriginalPhotoBlobContentType() + "'" +
            ", bannerTallPhotoBlob='" + getBannerTallPhotoBlob() + "'" +
            ", bannerTallPhotoBlobContentType='" + getBannerTallPhotoBlobContentType() + "'" +
            ", bannerWidePhotoBlob='" + getBannerWidePhotoBlob() + "'" +
            ", bannerWidePhotoBlobContentType='" + getBannerWidePhotoBlobContentType() + "'" +
            ", circlePhotoBlob='" + getCirclePhotoBlob() + "'" +
            ", circlePhotoBlobContentType='" + getCirclePhotoBlobContentType() + "'" +
            ", sharpenedPhotoBlob='" + getSharpenedPhotoBlob() + "'" +
            ", sharpenedPhotoBlobContentType='" + getSharpenedPhotoBlobContentType() + "'" +
            ", squarePhotoBlob='" + getSquarePhotoBlob() + "'" +
            ", squarePhotoBlobContentType='" + getSquarePhotoBlobContentType() + "'" +
            ", watermarkPhotoBlob='" + getWatermarkPhotoBlob() + "'" +
            ", watermarkPhotoBlobContentType='" + getWatermarkPhotoBlobContentType() + "'" +
            ", priority=" + getPriority() +
            ", defaultInd='" + isDefaultInd() + "'" +
            "}";
    }
}
