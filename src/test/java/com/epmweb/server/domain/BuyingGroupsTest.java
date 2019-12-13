package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class BuyingGroupsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyingGroups.class);
        BuyingGroups buyingGroups1 = new BuyingGroups();
        buyingGroups1.setId(1L);
        BuyingGroups buyingGroups2 = new BuyingGroups();
        buyingGroups2.setId(buyingGroups1.getId());
        assertThat(buyingGroups1).isEqualTo(buyingGroups2);
        buyingGroups2.setId(2L);
        assertThat(buyingGroups1).isNotEqualTo(buyingGroups2);
        buyingGroups1.setId(null);
        assertThat(buyingGroups1).isNotEqualTo(buyingGroups2);
    }
}
