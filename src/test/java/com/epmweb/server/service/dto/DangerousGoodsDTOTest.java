package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class DangerousGoodsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DangerousGoodsDTO.class);
        DangerousGoodsDTO dangerousGoodsDTO1 = new DangerousGoodsDTO();
        dangerousGoodsDTO1.setId(1L);
        DangerousGoodsDTO dangerousGoodsDTO2 = new DangerousGoodsDTO();
        assertThat(dangerousGoodsDTO1).isNotEqualTo(dangerousGoodsDTO2);
        dangerousGoodsDTO2.setId(dangerousGoodsDTO1.getId());
        assertThat(dangerousGoodsDTO1).isEqualTo(dangerousGoodsDTO2);
        dangerousGoodsDTO2.setId(2L);
        assertThat(dangerousGoodsDTO1).isNotEqualTo(dangerousGoodsDTO2);
        dangerousGoodsDTO1.setId(null);
        assertThat(dangerousGoodsDTO1).isNotEqualTo(dangerousGoodsDTO2);
    }
}
