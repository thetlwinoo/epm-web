package com.epmweb.server.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.UploadTransactions} entity.
 */
public class UploadTransactionsDTO implements Serializable {

    private Long id;

    private String fileName;

    private String templateUrl;

    private Integer status;

    private String generatedCode;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long supplierId;

    private String supplierName;

    private Long actionTypeId;

    private String actionTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String suppliersName) {
        this.supplierName = suppliersName;
    }

    public Long getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(Long uploadActionTypesId) {
        this.actionTypeId = uploadActionTypesId;
    }

    public String getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(String uploadActionTypesName) {
        this.actionTypeName = uploadActionTypesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UploadTransactionsDTO uploadTransactionsDTO = (UploadTransactionsDTO) o;
        if (uploadTransactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uploadTransactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UploadTransactionsDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", templateUrl='" + getTemplateUrl() + "'" +
            ", status=" + getStatus() +
            ", generatedCode='" + getGeneratedCode() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", supplier=" + getSupplierId() +
            ", supplier='" + getSupplierName() + "'" +
            ", actionType=" + getActionTypeId() +
            ", actionType='" + getActionTypeName() + "'" +
            "}";
    }
}
