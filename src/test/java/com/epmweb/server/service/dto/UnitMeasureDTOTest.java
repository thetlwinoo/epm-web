package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class UnitMeasureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitMeasureDTO.class);
        UnitMeasureDTO unitMeasureDTO1 = new UnitMeasureDTO();
        unitMeasureDTO1.setId(1L);
        UnitMeasureDTO unitMeasureDTO2 = new UnitMeasureDTO();
        assertThat(unitMeasureDTO1).isNotEqualTo(unitMeasureDTO2);
        unitMeasureDTO2.setId(unitMeasureDTO1.getId());
        assertThat(unitMeasureDTO1).isEqualTo(unitMeasureDTO2);
        unitMeasureDTO2.setId(2L);
        assertThat(unitMeasureDTO1).isNotEqualTo(unitMeasureDTO2);
        unitMeasureDTO1.setId(null);
        assertThat(unitMeasureDTO1).isNotEqualTo(unitMeasureDTO2);
    }
}
