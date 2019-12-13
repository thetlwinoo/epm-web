package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class BuyingGroupsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyingGroupsDTO.class);
        BuyingGroupsDTO buyingGroupsDTO1 = new BuyingGroupsDTO();
        buyingGroupsDTO1.setId(1L);
        BuyingGroupsDTO buyingGroupsDTO2 = new BuyingGroupsDTO();
        assertThat(buyingGroupsDTO1).isNotEqualTo(buyingGroupsDTO2);
        buyingGroupsDTO2.setId(buyingGroupsDTO1.getId());
        assertThat(buyingGroupsDTO1).isEqualTo(buyingGroupsDTO2);
        buyingGroupsDTO2.setId(2L);
        assertThat(buyingGroupsDTO1).isNotEqualTo(buyingGroupsDTO2);
        buyingGroupsDTO1.setId(null);
        assertThat(buyingGroupsDTO1).isNotEqualTo(buyingGroupsDTO2);
    }
}
