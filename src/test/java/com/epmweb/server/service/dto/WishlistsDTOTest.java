package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class WishlistsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WishlistsDTO.class);
        WishlistsDTO wishlistsDTO1 = new WishlistsDTO();
        wishlistsDTO1.setId(1L);
        WishlistsDTO wishlistsDTO2 = new WishlistsDTO();
        assertThat(wishlistsDTO1).isNotEqualTo(wishlistsDTO2);
        wishlistsDTO2.setId(wishlistsDTO1.getId());
        assertThat(wishlistsDTO1).isEqualTo(wishlistsDTO2);
        wishlistsDTO2.setId(2L);
        assertThat(wishlistsDTO1).isNotEqualTo(wishlistsDTO2);
        wishlistsDTO1.setId(null);
        assertThat(wishlistsDTO1).isNotEqualTo(wishlistsDTO2);
    }
}
