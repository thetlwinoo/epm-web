package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class DangerousGoodsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DangerousGoods.class);
        DangerousGoods dangerousGoods1 = new DangerousGoods();
        dangerousGoods1.setId(1L);
        DangerousGoods dangerousGoods2 = new DangerousGoods();
        dangerousGoods2.setId(dangerousGoods1.getId());
        assertThat(dangerousGoods1).isEqualTo(dangerousGoods2);
        dangerousGoods2.setId(2L);
        assertThat(dangerousGoods1).isNotEqualTo(dangerousGoods2);
        dangerousGoods1.setId(null);
        assertThat(dangerousGoods1).isNotEqualTo(dangerousGoods2);
    }
}
