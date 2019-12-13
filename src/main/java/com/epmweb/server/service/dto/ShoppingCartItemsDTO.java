package com.epmweb.server.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.ShoppingCartItems} entity.
 */
public class ShoppingCartItemsDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long stockItemId;

    private String stockItemName;

    private Long cartId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    public String getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(String stockItemsName) {
        this.stockItemName = stockItemsName;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long shoppingCartsId) {
        this.cartId = shoppingCartsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShoppingCartItemsDTO shoppingCartItemsDTO = (ShoppingCartItemsDTO) o;
        if (shoppingCartItemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shoppingCartItemsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShoppingCartItemsDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", stockItem=" + getStockItemId() +
            ", stockItem='" + getStockItemName() + "'" +
            ", cart=" + getCartId() +
            "}";
    }
}
