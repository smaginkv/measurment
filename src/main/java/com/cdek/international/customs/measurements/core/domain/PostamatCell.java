package com.cdek.international.customs.measurements.core.domain;

import lombok.NonNull;

import java.util.List;
import java.util.stream.IntStream;

public record PostamatCell(@NonNull List<Integer> dimensions) {

    public boolean isGreaterThanOrEqualTo(@NonNull List<Integer> parcelDimensions) {
        return IntStream.range(0, 3)
                .allMatch(i -> dimensions.get(i) >= parcelDimensions.get(i));
    }
}
