package com.cdek.international.customs.measurements.core.application;

import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.quantity.Length;
import java.util.List;
import java.util.stream.IntStream;

import static javax.measure.MetricPrefix.CENTI;

public record PostamatCell(List<ComparableQuantity<Length>> dimensions) {
    public boolean isGreaterThanOrEqualTo(List<Integer> parcelDimensions) {
        return IntStream.range(0, 3)
                .allMatch(i -> dimensions.get(i).isGreaterThanOrEqualTo(Quantities.getQuantity(parcelDimensions.get(i), CENTI(Units.METRE))));
    }
}
