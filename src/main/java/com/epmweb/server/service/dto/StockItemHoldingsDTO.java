package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.StockItemHoldings} entity.
 */
public class StockItemHoldingsDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantityOnHand;

    @NotNull
    private String binLocation;

    @NotNull
    private Integer lastStocktakeQuantity;

    @NotNull
    private BigDecimal lastCostPrice;

    @NotNull
    private Integer reorderLevel;

    @NotNull
    private Integer targerStockLevel;


    private Long stockItemHoldingOnStockItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public String getBinLocation() {
        return binLocation;
    }

    public void setBinLocation(String binLocation) {
        this.binLocation = binLocation;
    }

    public Integer getLastStocktakeQuantity() {
        return lastStocktakeQuantity;
    }

    public void setLastStocktakeQuantity(Integer lastStocktakeQuantity) {
        this.lastStocktakeQuantity = lastStocktakeQuantity;
    }

    public BigDecimal getLastCostPrice() {
        return lastCostPrice;
    }

    public void setLastCostPrice(BigDecimal lastCostPrice) {
        this.lastCostPrice = lastCostPrice;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Integer getTargerStockLevel() {
        return targerStockLevel;
    }

    public void setTargerStockLevel(Integer targerStockLevel) {
        this.targerStockLevel = targerStockLevel;
    }

    public Long getStockItemHoldingOnStockItemId() {
        return stockItemHoldingOnStockItemId;
    }

    public void setStockItemHoldingOnStockItemId(Long stockItemsId) {
        this.stockItemHoldingOnStockItemId = stockItemsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItemHoldingsDTO stockItemHoldingsDTO = (StockItemHoldingsDTO) o;
        if (stockItemHoldingsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockItemHoldingsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockItemHoldingsDTO{" +
            "id=" + getId() +
            ", quantityOnHand=" + getQuantityOnHand() +
            ", binLocation='" + getBinLocation() + "'" +
            ", lastStocktakeQuantity=" + getLastStocktakeQuantity() +
            ", lastCostPrice=" + getLastCostPrice() +
            ", reorderLevel=" + getReorderLevel() +
            ", targerStockLevel=" + getTargerStockLevel() +
            ", stockItemHoldingOnStockItem=" + getStockItemHoldingOnStockItemId() +
            "}";
    }
}
