package com.cdek.international.customs.measurements.core;

import com.cdek.international.customs.measurements.core.domain.PostamatCell;
import org.junit.jupiter.api.Test;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.quantity.Volume;
import java.util.List;

import static javax.measure.MetricPrefix.KILO;
import static javax.measure.MetricPrefix.MILLI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PostamatCellTest {

    @Test
    void getCellVolume_expectEquals() {
        //given
        final var underTest = new PostamatCell(
                List.of(
                        Quantities.getQuantity(200, MILLI(Units.METRE)),
                        Quantities.getQuantity(255, MILLI(Units.METRE)),
                        Quantities.getQuantity(333, MILLI(Units.METRE))
                ),
                Quantities.getQuantity(18.87, Units.KILOGRAM)
        );
        final var expected = Quantities.getQuantity(0.016983000, Units.CUBIC_METRE);

        //when
        ComparableQuantity<Volume> actual = underTest.getVolume();

        assertNotEquals(actual, expected);
        assertTrue(actual.isEquivalentTo(expected));
        assertEquals(0, actual.compareTo(expected));
        assertTrue(actual.getUnit().isCompatible(expected.getUnit()));

        assertNotEquals(Units.KILOGRAM, KILO(Units.GRAM));
        assertTrue(Units.KILOGRAM.isCompatible(KILO(Units.KILOGRAM)));
    }
}