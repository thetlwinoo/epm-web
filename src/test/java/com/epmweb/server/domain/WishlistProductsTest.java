package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class WishlistProductsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WishlistProducts.class);
        WishlistProducts wishlistProducts1 = new WishlistProducts();
        wishlistProducts1.setId(1L);
        WishlistProducts wishlistProducts2 = new WishlistProducts();
        wishlistProducts2.setId(wishlistProducts1.getId());
        assertThat(wishlistProducts1).isEqualTo(wishlistProducts2);
        wishlistProducts2.setId(2L);
        assertThat(wishlistProducts1).isNotEqualTo(wishlistProducts2);
        wishlistProducts1.setId(null);
        assertThat(wishlistProducts1).isNotEqualTo(wishlistProducts2);
    }
}
