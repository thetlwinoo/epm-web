package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.DangerousGoods} entity.
 */
public class DangerousGoodsDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    private Long stockItemId;

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

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DangerousGoodsDTO dangerousGoodsDTO = (DangerousGoodsDTO) o;
        if (dangerousGoodsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dangerousGoodsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DangerousGoodsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", stockItem=" + getStockItemId() +
            "}";
    }
}
