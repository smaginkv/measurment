package com.cdek.international.customs.measurements.infrastructure.db;

import tech.units.indriya.ComparableQuantity;

import javax.measure.quantity.Length;
import java.util.List;

public record PostamatCellDbo(List<ComparableQuantity<Length>> dimensions) {
}
