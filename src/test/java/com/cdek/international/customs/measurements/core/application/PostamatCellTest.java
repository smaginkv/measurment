package com.cdek.international.customs.measurements.core.application;

import com.cdek.international.customs.measurements.utils.PostamatCellModelGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.quantity.Volume;

import static org.junit.jupiter.api.Assertions.*;

class PostamatCellTest {

    @Test
    void getCellVolume_expectEquals() {
        //given
        final var underTest = PostamatCellModelGenerator.getPostamatCell();
        final var expected = Quantities.getQuantity(0.016983000, Units.CUBIC_METRE);

        //when
        ComparableQuantity<Volume> actual = underTest.getCellVolume();

        //then
        Assertions.assertThat(actual)
                .isEqualByComparingTo(expected);
    }

    @Test
    void isGreaterThanOrEqualTo_whenLess_expectFalse() {
        //given
        final var underTest = PostamatCellModelGenerator.getPostamatCell();
        final var above1 = PostamatCellModelGenerator.getPostamatCellDimensions(201, 254, 332);
        final var above2 = PostamatCellModelGenerator.getPostamatCellDimensions(199, 256, 332);
        final var above3 = PostamatCellModelGenerator.getPostamatCellDimensions(199, 254, 334);

        //when, then
        assertFalse(underTest.isGreaterThanOrEqualTo(above1));
        assertFalse(underTest.isGreaterThanOrEqualTo(above2));
        assertFalse(underTest.isGreaterThanOrEqualTo(above3));
    }

    @Test
    void isGreaterThanOrEqualTo_whenGreater_expectTrue() {
        //given
        final var underTest = PostamatCellModelGenerator.getPostamatCell();
        final var under = PostamatCellModelGenerator.getPostamatCellDimensions(199, 254, 332);

        //when, then
        assertTrue(underTest.isGreaterThanOrEqualTo(under));
    }

    @Test
    void isGreaterThanOrEqualTo_whenEqual_expectTrue() {
        //given
        final var underTest = PostamatCellModelGenerator.getPostamatCell();
        final var under = PostamatCellModelGenerator.getPostamatCellDimensions(200, 255, 333);

        //when, then
        assertTrue(underTest.isGreaterThanOrEqualTo(under));
    }

}