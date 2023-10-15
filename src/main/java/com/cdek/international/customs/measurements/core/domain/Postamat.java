package com.cdek.international.customs.measurements.core.domain;

import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Volume;
import java.util.List;

public record Postamat(List<PostamatCell> cells) {

    public ComparableQuantity<Volume> getVolume() {
        return this.cells.stream()
                .map(PostamatCell::getCellVolume)
                .reduce(ComparableQuantity::add)
                .orElseThrow(IllegalStateException::new);

    }

    public List<PostamatCell> getSuitableCell(List<Quantity<Length>> parcelDimensions) {
        return this.cells.stream()
                .filter(cell -> cell.isGreaterThanOrEqualTo(parcelDimensions))
                .toList();
    }
}
