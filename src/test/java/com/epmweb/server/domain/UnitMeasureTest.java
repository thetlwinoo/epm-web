package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class UnitMeasureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitMeasure.class);
        UnitMeasure unitMeasure1 = new UnitMeasure();
        unitMeasure1.setId(1L);
        UnitMeasure unitMeasure2 = new UnitMeasure();
        unitMeasure2.setId(unitMeasure1.getId());
        assertThat(unitMeasure1).isEqualTo(unitMeasure2);
        unitMeasure2.setId(2L);
        assertThat(unitMeasure1).isNotEqualTo(unitMeasure2);
        unitMeasure1.setId(null);
        assertThat(unitMeasure1).isNotEqualTo(unitMeasure2);
    }
}
