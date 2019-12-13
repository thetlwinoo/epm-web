package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class WishlistsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wishlists.class);
        Wishlists wishlists1 = new Wishlists();
        wishlists1.setId(1L);
        Wishlists wishlists2 = new Wishlists();
        wishlists2.setId(wishlists1.getId());
        assertThat(wishlists1).isEqualTo(wishlists2);
        wishlists2.setId(2L);
        assertThat(wishlists1).isNotEqualTo(wishlists2);
        wishlists1.setId(null);
        assertThat(wishlists1).isNotEqualTo(wishlists2);
    }
}
