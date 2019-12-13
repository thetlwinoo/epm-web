package com.epmweb.server.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.Wishlists} entity.
 */
public class WishlistsDTO implements Serializable {

    private Long id;


    private Long wishlistUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWishlistUserId() {
        return wishlistUserId;
    }

    public void setWishlistUserId(Long peopleId) {
        this.wishlistUserId = peopleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WishlistsDTO wishlistsDTO = (WishlistsDTO) o;
        if (wishlistsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wishlistsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WishlistsDTO{" +
            "id=" + getId() +
            ", wishlistUser=" + getWishlistUserId() +
            "}";
    }
}
