package com.cdek.international.customs.measurements.core.application;

import java.util.List;
import java.util.stream.IntStream;

public record PostamatCell(List<Integer> dimensions) {
    public boolean isGreaterThanOrEqualTo(List<Integer> parcelDimensions) {
        return IntStream.range(0, 3)
                .allMatch(i -> dimensions.get(i) >= parcelDimensions.get(i));
    }
}
