package com.cdek.international.customs.measurements.core.application;

import tech.units.indriya.unit.AlternateUnit;

import javax.measure.Quantity;

import static tech.units.indriya.unit.Units.KILOGRAM;

public interface VolumeWeight extends Quantity<VolumeWeight> {
    AlternateUnit<VolumeWeight> VOLUME_WEIGHT = new AlternateUnit<>(KILOGRAM, "VW");
}
