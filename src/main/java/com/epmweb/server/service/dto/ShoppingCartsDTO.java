package com.epmweb.server.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.ShoppingCarts} entity.
 */
public class ShoppingCartsDTO implements Serializable {

    private Long id;

    private BigDecimal totalPrice;

    private BigDecimal totalCargoPrice;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long cartUserId;

    private Long customerId;

    private Long specialDealsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalCargoPrice() {
        return totalCargoPrice;
    }

    public void setTotalCargoPrice(BigDecimal totalCargoPrice) {
        this.totalCargoPrice = totalCargoPrice;
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

    public Long getCartUserId() {
        return cartUserId;
    }

    public void setCartUserId(Long peopleId) {
        this.cartUserId = peopleId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public Long getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(Long specialDealsId) {
        this.specialDealsId = specialDealsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShoppingCartsDTO shoppingCartsDTO = (ShoppingCartsDTO) o;
        if (shoppingCartsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shoppingCartsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShoppingCartsDTO{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            ", totalCargoPrice=" + getTotalCargoPrice() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", cartUser=" + getCartUserId() +
            ", customer=" + getCustomerId() +
            ", specialDeals=" + getSpecialDealsId() +
            "}";
    }
}
