package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.utils.PostamatCellModelGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.quantity.Volume;

import static javax.measure.MetricPrefix.CENTI;

class PostamatConverterTest {
    private PostamatConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new PostamatConverter();
    }

    @Test
    void toCellResponse_expectEquals() {
        //given
        final var cell = PostamatCellModelGenerator.getPostamatCell();

        //when
        final var actual = underTest.toCellResponse(cell);

        //then
        Assertions.assertThat(actual)
                .isEqualTo("20x25.5x33.3 cm");
    }

    @Test
    void toUserVolumeResponseDto_expectEquals() {
        //given
        final var volume = Quantities.getQuantity(8, Units.CUBIC_METRE).asType(Volume.class);
        final var expected = Quantities.getQuantity(8000000, CENTI(Units.METRE).pow(3)).asType(Volume.class);

        //when
        final var actual = underTest.toUserVolumeResponseDto(volume);

        //then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}