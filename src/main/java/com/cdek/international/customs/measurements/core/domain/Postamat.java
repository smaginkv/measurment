package com.cdek.international.customs.measurements.core.domain;

import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Volume;
import java.util.List;

public record Postamat(List<PostamatCell> cells) {
    public List<PostamatCell> getSuitableCells(List<Quantity<Length>> parcelDimensions) {
        return this.cells.stream()
                .filter(cell -> cell.isGreaterThanOrEqualTo(parcelDimensions))
                .toList();
    }

    public ComparableQuantity<Volume> getVolume() {
        return cells.stream()
                .map(PostamatCell::getVolume)
                .reduce(ComparableQuantity::add)
                .orElseThrow(IllegalStateException::new);
    }
}
