package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ShoppingCartItemsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartItemsDTO.class);
        ShoppingCartItemsDTO shoppingCartItemsDTO1 = new ShoppingCartItemsDTO();
        shoppingCartItemsDTO1.setId(1L);
        ShoppingCartItemsDTO shoppingCartItemsDTO2 = new ShoppingCartItemsDTO();
        assertThat(shoppingCartItemsDTO1).isNotEqualTo(shoppingCartItemsDTO2);
        shoppingCartItemsDTO2.setId(shoppingCartItemsDTO1.getId());
        assertThat(shoppingCartItemsDTO1).isEqualTo(shoppingCartItemsDTO2);
        shoppingCartItemsDTO2.setId(2L);
        assertThat(shoppingCartItemsDTO1).isNotEqualTo(shoppingCartItemsDTO2);
        shoppingCartItemsDTO1.setId(null);
        assertThat(shoppingCartItemsDTO1).isNotEqualTo(shoppingCartItemsDTO2);
    }
}
