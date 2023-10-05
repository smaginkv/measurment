package com.cdek.international.customs.measurements.core.application;

import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import java.util.List;

public record PostamatCell(
        List<ComparableQuantity<Length>> dimensions,
        Quantity<Mass> weight
) {
}
