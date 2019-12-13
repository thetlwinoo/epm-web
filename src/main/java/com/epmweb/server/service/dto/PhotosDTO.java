package com.epmweb.server.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.epmweb.server.domain.Photos} entity.
 */
public class PhotosDTO implements Serializable {

    private Long id;

    private String thumbnailPhoto;

    private String originalPhoto;

    private String bannerTallPhoto;

    private String bannerWidePhoto;

    private String circlePhoto;

    private String sharpenedPhoto;

    private String squarePhoto;

    private String watermarkPhoto;

    @Lob
    private byte[] thumbnailPhotoBlob;

    private String thumbnailPhotoBlobContentType;
    @Lob
    private byte[] originalPhotoBlob;

    private String originalPhotoBlobContentType;
    @Lob
    private byte[] bannerTallPhotoBlob;

    private String bannerTallPhotoBlobContentType;
    @Lob
    private byte[] bannerWidePhotoBlob;

    private String bannerWidePhotoBlobContentType;
    @Lob
    private byte[] circlePhotoBlob;

    private String circlePhotoBlobContentType;
    @Lob
    private byte[] sharpenedPhotoBlob;

    private String sharpenedPhotoBlobContentType;
    @Lob
    private byte[] squarePhotoBlob;

    private String squarePhotoBlobContentType;
    @Lob
    private byte[] watermarkPhotoBlob;

    private String watermarkPhotoBlobContentType;
    private Integer priority;

    private Boolean defaultInd;


    private Long stockItemId;

    private Long productCategoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThumbnailPhoto() {
        return thumbnailPhoto;
    }

    public void setThumbnailPhoto(String thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
    }

    public String getOriginalPhoto() {
        return originalPhoto;
    }

    public void setOriginalPhoto(String originalPhoto) {
        this.originalPhoto = originalPhoto;
    }

    public String getBannerTallPhoto() {
        return bannerTallPhoto;
    }

    public void setBannerTallPhoto(String bannerTallPhoto) {
        this.bannerTallPhoto = bannerTallPhoto;
    }

    public String getBannerWidePhoto() {
        return bannerWidePhoto;
    }

    public void setBannerWidePhoto(String bannerWidePhoto) {
        this.bannerWidePhoto = bannerWidePhoto;
    }

    public String getCirclePhoto() {
        return circlePhoto;
    }

    public void setCirclePhoto(String circlePhoto) {
        this.circlePhoto = circlePhoto;
    }

    public String getSharpenedPhoto() {
        return sharpenedPhoto;
    }

    public void setSharpenedPhoto(String sharpenedPhoto) {
        this.sharpenedPhoto = sharpenedPhoto;
    }

    public String getSquarePhoto() {
        return squarePhoto;
    }

    public void setSquarePhoto(String squarePhoto) {
        this.squarePhoto = squarePhoto;
    }

    public String getWatermarkPhoto() {
        return watermarkPhoto;
    }

    public void setWatermarkPhoto(String watermarkPhoto) {
        this.watermarkPhoto = watermarkPhoto;
    }

    public byte[] getThumbnailPhotoBlob() {
        return thumbnailPhotoBlob;
    }

    public void setThumbnailPhotoBlob(byte[] thumbnailPhotoBlob) {
        this.thumbnailPhotoBlob = thumbnailPhotoBlob;
    }

    public String getThumbnailPhotoBlobContentType() {
        return thumbnailPhotoBlobContentType;
    }

    public void setThumbnailPhotoBlobContentType(String thumbnailPhotoBlobContentType) {
        this.thumbnailPhotoBlobContentType = thumbnailPhotoBlobContentType;
    }

    public byte[] getOriginalPhotoBlob() {
        return originalPhotoBlob;
    }

    public void setOriginalPhotoBlob(byte[] originalPhotoBlob) {
        this.originalPhotoBlob = originalPhotoBlob;
    }

    public String getOriginalPhotoBlobContentType() {
        return originalPhotoBlobContentType;
    }

    public void setOriginalPhotoBlobContentType(String originalPhotoBlobContentType) {
        this.originalPhotoBlobContentType = originalPhotoBlobContentType;
    }

    public byte[] getBannerTallPhotoBlob() {
        return bannerTallPhotoBlob;
    }

    public void setBannerTallPhotoBlob(byte[] bannerTallPhotoBlob) {
        this.bannerTallPhotoBlob = bannerTallPhotoBlob;
    }

    public String getBannerTallPhotoBlobContentType() {
        return bannerTallPhotoBlobContentType;
    }

    public void setBannerTallPhotoBlobContentType(String bannerTallPhotoBlobContentType) {
        this.bannerTallPhotoBlobContentType = bannerTallPhotoBlobContentType;
    }

    public byte[] getBannerWidePhotoBlob() {
        return bannerWidePhotoBlob;
    }

    public void setBannerWidePhotoBlob(byte[] bannerWidePhotoBlob) {
        this.bannerWidePhotoBlob = bannerWidePhotoBlob;
    }

    public String getBannerWidePhotoBlobContentType() {
        return bannerWidePhotoBlobContentType;
    }

    public void setBannerWidePhotoBlobContentType(String bannerWidePhotoBlobContentType) {
        this.bannerWidePhotoBlobContentType = bannerWidePhotoBlobContentType;
    }

    public byte[] getCirclePhotoBlob() {
        return circlePhotoBlob;
    }

    public void setCirclePhotoBlob(byte[] circlePhotoBlob) {
        this.circlePhotoBlob = circlePhotoBlob;
    }

    public String getCirclePhotoBlobContentType() {
        return circlePhotoBlobContentType;
    }

    public void setCirclePhotoBlobContentType(String circlePhotoBlobContentType) {
        this.circlePhotoBlobContentType = circlePhotoBlobContentType;
    }

    public byte[] getSharpenedPhotoBlob() {
        return sharpenedPhotoBlob;
    }

    public void setSharpenedPhotoBlob(byte[] sharpenedPhotoBlob) {
        this.sharpenedPhotoBlob = sharpenedPhotoBlob;
    }

    public String getSharpenedPhotoBlobContentType() {
        return sharpenedPhotoBlobContentType;
    }

    public void setSharpenedPhotoBlobContentType(String sharpenedPhotoBlobContentType) {
        this.sharpenedPhotoBlobContentType = sharpenedPhotoBlobContentType;
    }

    public byte[] getSquarePhotoBlob() {
        return squarePhotoBlob;
    }

    public void setSquarePhotoBlob(byte[] squarePhotoBlob) {
        this.squarePhotoBlob = squarePhotoBlob;
    }

    public String getSquarePhotoBlobContentType() {
        return squarePhotoBlobContentType;
    }

    public void setSquarePhotoBlobContentType(String squarePhotoBlobContentType) {
        this.squarePhotoBlobContentType = squarePhotoBlobContentType;
    }

    public byte[] getWatermarkPhotoBlob() {
        return watermarkPhotoBlob;
    }

    public void setWatermarkPhotoBlob(byte[] watermarkPhotoBlob) {
        this.watermarkPhotoBlob = watermarkPhotoBlob;
    }

    public String getWatermarkPhotoBlobContentType() {
        return watermarkPhotoBlobContentType;
    }

    public void setWatermarkPhotoBlobContentType(String watermarkPhotoBlobContentType) {
        this.watermarkPhotoBlobContentType = watermarkPhotoBlobContentType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhotosDTO photosDTO = (PhotosDTO) o;
        if (photosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), photosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PhotosDTO{" +
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
            ", originalPhotoBlob='" + getOriginalPhotoBlob() + "'" +
            ", bannerTallPhotoBlob='" + getBannerTallPhotoBlob() + "'" +
            ", bannerWidePhotoBlob='" + getBannerWidePhotoBlob() + "'" +
            ", circlePhotoBlob='" + getCirclePhotoBlob() + "'" +
            ", sharpenedPhotoBlob='" + getSharpenedPhotoBlob() + "'" +
            ", squarePhotoBlob='" + getSquarePhotoBlob() + "'" +
            ", watermarkPhotoBlob='" + getWatermarkPhotoBlob() + "'" +
            ", priority=" + getPriority() +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", stockItem=" + getStockItemId() +
            ", productCategory=" + getProductCategoryId() +
            "}";
    }
}
