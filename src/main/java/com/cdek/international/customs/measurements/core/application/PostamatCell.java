package com.cdek.international.customs.measurements.core.application;

import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import java.util.List;
import java.util.stream.IntStream;

public record PostamatCell(List<ComparableQuantity<Length>> dimensions) {
    public boolean isGreaterThanOrEqualTo(List<Quantity<Length>> parcelDimensions) {
        return IntStream.range(0, 3)
                .allMatch(i -> dimensions.get(i).isGreaterThanOrEqualTo(parcelDimensions.get(i)));
    }
}
