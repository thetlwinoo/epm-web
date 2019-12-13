package com.epmweb.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link com.epmweb.server.domain.StockItemHoldings} entity. This class is used
 * in {@link com.epmweb.server.web.rest.StockItemHoldingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stock-item-holdings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockItemHoldingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter quantityOnHand;

    private StringFilter binLocation;

    private IntegerFilter lastStocktakeQuantity;

    private BigDecimalFilter lastCostPrice;

    private IntegerFilter reorderLevel;

    private IntegerFilter targerStockLevel;

    private LongFilter stockItemHoldingOnStockItemId;

    public StockItemHoldingsCriteria(){
    }

    public StockItemHoldingsCriteria(StockItemHoldingsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.quantityOnHand = other.quantityOnHand == null ? null : other.quantityOnHand.copy();
        this.binLocation = other.binLocation == null ? null : other.binLocation.copy();
        this.lastStocktakeQuantity = other.lastStocktakeQuantity == null ? null : other.lastStocktakeQuantity.copy();
        this.lastCostPrice = other.lastCostPrice == null ? null : other.lastCostPrice.copy();
        this.reorderLevel = other.reorderLevel == null ? null : other.reorderLevel.copy();
        this.targerStockLevel = other.targerStockLevel == null ? null : other.targerStockLevel.copy();
        this.stockItemHoldingOnStockItemId = other.stockItemHoldingOnStockItemId == null ? null : other.stockItemHoldingOnStockItemId.copy();
    }

    @Override
    public StockItemHoldingsCriteria copy() {
        return new StockItemHoldingsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(IntegerFilter quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public StringFilter getBinLocation() {
        return binLocation;
    }

    public void setBinLocation(StringFilter binLocation) {
        this.binLocation = binLocation;
    }

    public IntegerFilter getLastStocktakeQuantity() {
        return lastStocktakeQuantity;
    }

    public void setLastStocktakeQuantity(IntegerFilter lastStocktakeQuantity) {
        this.lastStocktakeQuantity = lastStocktakeQuantity;
    }

    public BigDecimalFilter getLastCostPrice() {
        return lastCostPrice;
    }

    public void setLastCostPrice(BigDecimalFilter lastCostPrice) {
        this.lastCostPrice = lastCostPrice;
    }

    public IntegerFilter getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(IntegerFilter reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public IntegerFilter getTargerStockLevel() {
        return targerStockLevel;
    }

    public void setTargerStockLevel(IntegerFilter targerStockLevel) {
        this.targerStockLevel = targerStockLevel;
    }

    public LongFilter getStockItemHoldingOnStockItemId() {
        return stockItemHoldingOnStockItemId;
    }

    public void setStockItemHoldingOnStockItemId(LongFilter stockItemHoldingOnStockItemId) {
        this.stockItemHoldingOnStockItemId = stockItemHoldingOnStockItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockItemHoldingsCriteria that = (StockItemHoldingsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantityOnHand, that.quantityOnHand) &&
            Objects.equals(binLocation, that.binLocation) &&
            Objects.equals(lastStocktakeQuantity, that.lastStocktakeQuantity) &&
            Objects.equals(lastCostPrice, that.lastCostPrice) &&
            Objects.equals(reorderLevel, that.reorderLevel) &&
            Objects.equals(targerStockLevel, that.targerStockLevel) &&
            Objects.equals(stockItemHoldingOnStockItemId, that.stockItemHoldingOnStockItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantityOnHand,
        binLocation,
        lastStocktakeQuantity,
        lastCostPrice,
        reorderLevel,
        targerStockLevel,
        stockItemHoldingOnStockItemId
        );
    }

    @Override
    public String toString() {
        return "StockItemHoldingsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantityOnHand != null ? "quantityOnHand=" + quantityOnHand + ", " : "") +
                (binLocation != null ? "binLocation=" + binLocation + ", " : "") +
                (lastStocktakeQuantity != null ? "lastStocktakeQuantity=" + lastStocktakeQuantity + ", " : "") +
                (lastCostPrice != null ? "lastCostPrice=" + lastCostPrice + ", " : "") +
                (reorderLevel != null ? "reorderLevel=" + reorderLevel + ", " : "") +
                (targerStockLevel != null ? "targerStockLevel=" + targerStockLevel + ", " : "") +
                (stockItemHoldingOnStockItemId != null ? "stockItemHoldingOnStockItemId=" + stockItemHoldingOnStockItemId + ", " : "") +
            "}";
    }

}
