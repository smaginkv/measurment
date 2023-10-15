package com.cdek.international.customs.measurements.core.domain;

import org.springframework.stereotype.Service;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.AlternateUnit;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import static javax.measure.MetricPrefix.CENTI;
import static tech.units.indriya.unit.Units.KILOGRAM;

@Service
public class VolumeWeightFactory {
    private static final int VOLUMETRIC_WEIGHT_COEFFICIENT = 900;
    private static final AlternateUnit<VolumeWeight> VOLUME_WEIGHT_KG = new AlternateUnit<>(KILOGRAM, "VW");

    public ComparableQuantity<VolumeWeight> of(Quantity<Mass> weight) {
        final var inKilo = weight.to(KILOGRAM).getValue();
        return Quantities.getQuantity(inKilo, VOLUME_WEIGHT_KG);
    }

    public ComparableQuantity<VolumeWeight> of(ComparableQuantity<Volume> volume) {
        final var volumeCm3 = volume.to(CENTI(Units.METRE).pow(3).asType(Volume.class));
        final var volumeWeight = volumeCm3.divide(VOLUMETRIC_WEIGHT_COEFFICIENT).getValue();
        return Quantities.getQuantity(volumeWeight, VOLUME_WEIGHT_KG);
    }
}
