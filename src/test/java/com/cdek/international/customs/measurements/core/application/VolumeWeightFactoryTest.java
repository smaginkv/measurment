package com.cdek.international.customs.measurements.core.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import static com.cdek.international.customs.measurements.core.application.VolumeWeight.VOLUME_WEIGHT;
import static tech.units.indriya.unit.Units.GRAM;

class VolumeWeightFactoryTest {
    private VolumeWeightFactory underTest;

    @BeforeEach
    void setUp() {
        underTest = new VolumeWeightFactory();
    }

    @Test
    void of_whenMass_expectVolumeWeight() {
        //given
        final var mass = Quantities.getQuantity(8000, GRAM);
        final var expected = Quantities.getQuantity(8, VOLUME_WEIGHT);

        //when
        final var actual = underTest.of(mass);

        //then
        Assertions.assertThat(actual)
                .usingComparator(Comparable::compareTo)
                .isEqualTo(expected);
    }

    @Test
    void of_whenVolume_expectKilo() {
        //given
        final var mass = Quantities.getQuantity(0.009, Units.CUBIC_METRE);
        final var expected = Quantities.getQuantity(10, VOLUME_WEIGHT);

        //when
        final var actual = underTest.of(mass);

        //then
        Assertions.assertThat(actual)
                .usingComparator(Comparable::compareTo)
                .isEqualTo(expected);
    }
}