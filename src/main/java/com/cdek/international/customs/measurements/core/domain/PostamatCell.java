package com.cdek.international.customs.measurements.core.domain;

import java.util.List;
import java.util.stream.IntStream;

public record PostamatCell(List<Long> dimensions, Long weight) {

    public PostamatCell(List<Long> dimensions, Long weight) {
        this.dimensions = dimensions.stream()
                .sorted()
                .toList();
        this.weight = weight;

        if (dimensions.size() != 3) {
            throw new IllegalArgumentException("Expect exactly three dimensions");
        }
    }

    public boolean isGreaterThanOrEqualTo(List<Integer> parcelDimensions) {
        return IntStream.range(0, 3)
                .allMatch(i -> dimensions.get(i) >= parcelDimensions.get(i));
    }

    public Long getVolume() {
        return dimensions.get(0) * dimensions.get(1) * dimensions.get(2);
    }
}
