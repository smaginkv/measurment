package com.cdek.international.customs.measurements.core.domain;

import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;
import java.util.List;
import java.util.stream.IntStream;

public record PostamatCell(List<ComparableQuantity<Length>> dimensions, ComparableQuantity<Mass> weight) {

    public PostamatCell(
            List<ComparableQuantity<Length>> dimensions,
            ComparableQuantity<Mass> weight
    ) {
        this.dimensions = dimensions.stream()
                .sorted()
                .toList();
        this.weight = weight;

        if (dimensions.size() != 3) {
            throw new IllegalArgumentException("Expect exactly three dimensions");
        }
    }

    public boolean isGreaterThanOrEqualTo(List<Quantity<Length>> parcelDimensions) {
        return IntStream.range(0, 3)
                .allMatch(i -> dimensions.get(i).isGreaterThanOrEqualTo(
                        parcelDimensions.get(i)
                ));
    }

    public ComparableQuantity<Volume> getVolume() {
        return dimensions.get(0).multiply(dimensions.get(1)).multiply(dimensions.get(2))
                .asType(Volume.class);
    }
}
