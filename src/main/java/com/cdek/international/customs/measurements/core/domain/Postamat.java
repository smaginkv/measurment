package com.cdek.international.customs.measurements.core.domain;

import lombok.NonNull;

import java.util.List;

public record Postamat(@NonNull List<PostamatCell> cells) {

    @NonNull
    public List<PostamatCell> getSuitableCell(@NonNull List<Integer> parcelDimensions) {
        return this.cells.stream()
                .filter(cell -> cell.isGreaterThanOrEqualTo(parcelDimensions))
                .toList();
    }
}
