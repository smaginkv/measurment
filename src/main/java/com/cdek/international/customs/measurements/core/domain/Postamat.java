package com.cdek.international.customs.measurements.core.domain;

import java.math.BigDecimal;
import java.util.List;

public record Postamat(List<PostamatCell> cells) {
    public List<PostamatCell> getSuitableCells(List<Integer> parcelDimensions) {
        return this.cells.stream()
                .filter(cell -> cell.isGreaterThanOrEqualTo(parcelDimensions))
                .toList();
    }

    public BigDecimal getVolume() {
        return cells.stream()
                .map(PostamatCell::getVolume)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal::add)
                .orElseThrow(IllegalStateException::new);
    }
}
