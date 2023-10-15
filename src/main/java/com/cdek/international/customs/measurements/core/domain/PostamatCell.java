package com.cdek.international.customs.measurements.core.domain;

import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;
import java.util.List;
import java.util.stream.IntStream;

public record PostamatCell(
        List<ComparableQuantity<Length>> dimensions,
        Quantity<Mass> weight
) {

    public boolean isGreaterThanOrEqualTo(List<Quantity<Length>> parcelDimensions) {
        return IntStream.range(0, 3)
                .allMatch(i -> dimensions.get(i).isGreaterThanOrEqualTo(parcelDimensions.get(i)));
    }

    public ComparableQuantity<Volume> getCellVolume() {
        return dimensions.get(0).multiply(dimensions.get(1)).multiply(dimensions.get(2)).asType(Volume.class);
    }
}
