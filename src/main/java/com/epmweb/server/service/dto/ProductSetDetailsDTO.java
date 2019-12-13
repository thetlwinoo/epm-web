package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.ProductSetDetails} entity.
 */
public class ProductSetDetailsDTO implements Serializable {

    private Long id;

    private Integer subGroupNo;

    private Integer subGroupMinCount;

    @NotNull
    private BigDecimal subGroupMinTotal;

    private Integer minCount;

    private Integer maxCount;

    private Boolean isOptional;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSubGroupNo() {
        return subGroupNo;
    }

    public void setSubGroupNo(Integer subGroupNo) {
        this.subGroupNo = subGroupNo;
    }

    public Integer getSubGroupMinCount() {
        return subGroupMinCount;
    }

    public void setSubGroupMinCount(Integer subGroupMinCount) {
        this.subGroupMinCount = subGroupMinCount;
    }

    public BigDecimal getSubGroupMinTotal() {
        return subGroupMinTotal;
    }

    public void setSubGroupMinTotal(BigDecimal subGroupMinTotal) {
        this.subGroupMinTotal = subGroupMinTotal;
    }

    public Integer getMinCount() {
        return minCount;
    }

    public void setMinCount(Integer minCount) {
        this.minCount = minCount;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Boolean isIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductSetDetailsDTO productSetDetailsDTO = (ProductSetDetailsDTO) o;
        if (productSetDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productSetDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductSetDetailsDTO{" +
            "id=" + getId() +
            ", subGroupNo=" + getSubGroupNo() +
            ", subGroupMinCount=" + getSubGroupMinCount() +
            ", subGroupMinTotal=" + getSubGroupMinTotal() +
            ", minCount=" + getMinCount() +
            ", maxCount=" + getMaxCount() +
            ", isOptional='" + isIsOptional() + "'" +
            "}";
    }
}
