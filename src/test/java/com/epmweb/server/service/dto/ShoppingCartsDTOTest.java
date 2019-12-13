package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ShoppingCartsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartsDTO.class);
        ShoppingCartsDTO shoppingCartsDTO1 = new ShoppingCartsDTO();
        shoppingCartsDTO1.setId(1L);
        ShoppingCartsDTO shoppingCartsDTO2 = new ShoppingCartsDTO();
        assertThat(shoppingCartsDTO1).isNotEqualTo(shoppingCartsDTO2);
        shoppingCartsDTO2.setId(shoppingCartsDTO1.getId());
        assertThat(shoppingCartsDTO1).isEqualTo(shoppingCartsDTO2);
        shoppingCartsDTO2.setId(2L);
        assertThat(shoppingCartsDTO1).isNotEqualTo(shoppingCartsDTO2);
        shoppingCartsDTO1.setId(null);
        assertThat(shoppingCartsDTO1).isNotEqualTo(shoppingCartsDTO2);
    }
}
