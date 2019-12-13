package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class WishlistProductsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WishlistProductsDTO.class);
        WishlistProductsDTO wishlistProductsDTO1 = new WishlistProductsDTO();
        wishlistProductsDTO1.setId(1L);
        WishlistProductsDTO wishlistProductsDTO2 = new WishlistProductsDTO();
        assertThat(wishlistProductsDTO1).isNotEqualTo(wishlistProductsDTO2);
        wishlistProductsDTO2.setId(wishlistProductsDTO1.getId());
        assertThat(wishlistProductsDTO1).isEqualTo(wishlistProductsDTO2);
        wishlistProductsDTO2.setId(2L);
        assertThat(wishlistProductsDTO1).isNotEqualTo(wishlistProductsDTO2);
        wishlistProductsDTO1.setId(null);
        assertThat(wishlistProductsDTO1).isNotEqualTo(wishlistProductsDTO2);
    }
}
