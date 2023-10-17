package com.cdek.international.customs.measurements.core.domain;

import org.springframework.stereotype.Service;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.AlternateUnit;
import tech.units.indriya.unit.Units;

import javax.measure.Unit;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;
import java.math.BigDecimal;
import java.util.stream.Stream;

import static javax.measure.MetricPrefix.CENTI;

@Service
public class VolumeWeightFactory {
    private static final BigDecimal VOLUMETRIC_WEIGHT_COEFFICIENT = BigDecimal.valueOf(900L);
    private static final Unit<VolumeWeight> VOLUME_WEIGHT_KG = new AlternateUnit<>(Units.KILOGRAM, "VW kg");

    public ComparableQuantity<VolumeWeight> calcCellVolumeWeight(
            ComparableQuantity<Volume> volume,
            ComparableQuantity<Mass> weight) {

        return Stream.of(calcVolumeWeightByVolume(volume), calcVolumeWeightByWeight(weight))
                .max(Comparable::compareTo)
                .orElseThrow(IllegalStateException::new);
    }

    private ComparableQuantity<VolumeWeight> calcVolumeWeightByVolume(ComparableQuantity<Volume> volume) {
        final var inCm3 = volume.to(CENTI(Units.METRE).pow(3).asType(Volume.class));
        final var volumeWeight = inCm3.divide(VOLUMETRIC_WEIGHT_COEFFICIENT);
        return Quantities.getQuantity(volumeWeight.getValue(), VOLUME_WEIGHT_KG);
    }

    private ComparableQuantity<VolumeWeight> calcVolumeWeightByWeight(ComparableQuantity<Mass> weight) {
        final var volumeWeightKg = weight.to(Units.KILOGRAM).getValue();
        return Quantities.getQuantity(volumeWeightKg, VOLUME_WEIGHT_KG);
    }
}
