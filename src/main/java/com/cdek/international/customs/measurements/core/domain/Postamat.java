package com.cdek.international.customs.measurements.core.domain;

import lombok.NonNull;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import java.util.List;

public record Postamat(@NonNull List<PostamatCell> cells) {

    @NonNull
    public List<PostamatCell> getSuitableCell(List<Quantity<Length>> parcelDimensions) {
        return this.cells.stream()
                .filter(cell -> cell.isGreaterThanOrEqualTo(parcelDimensions))
                .toList();
    }
}
