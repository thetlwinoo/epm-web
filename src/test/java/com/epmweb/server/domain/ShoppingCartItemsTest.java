package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ShoppingCartItemsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartItems.class);
        ShoppingCartItems shoppingCartItems1 = new ShoppingCartItems();
        shoppingCartItems1.setId(1L);
        ShoppingCartItems shoppingCartItems2 = new ShoppingCartItems();
        shoppingCartItems2.setId(shoppingCartItems1.getId());
        assertThat(shoppingCartItems1).isEqualTo(shoppingCartItems2);
        shoppingCartItems2.setId(2L);
        assertThat(shoppingCartItems1).isNotEqualTo(shoppingCartItems2);
        shoppingCartItems1.setId(null);
        assertThat(shoppingCartItems1).isNotEqualTo(shoppingCartItems2);
    }
}
