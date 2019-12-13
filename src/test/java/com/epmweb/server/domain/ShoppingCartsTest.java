package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ShoppingCartsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCarts.class);
        ShoppingCarts shoppingCarts1 = new ShoppingCarts();
        shoppingCarts1.setId(1L);
        ShoppingCarts shoppingCarts2 = new ShoppingCarts();
        shoppingCarts2.setId(shoppingCarts1.getId());
        assertThat(shoppingCarts1).isEqualTo(shoppingCarts2);
        shoppingCarts2.setId(2L);
        assertThat(shoppingCarts1).isNotEqualTo(shoppingCarts2);
        shoppingCarts1.setId(null);
        assertThat(shoppingCarts1).isNotEqualTo(shoppingCarts2);
    }
}
