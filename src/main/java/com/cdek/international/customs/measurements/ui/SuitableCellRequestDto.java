package com.cdek.international.customs.measurements.ui;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import java.util.List;

public record SuitableCellRequestDto(List<Quantity<Length>> parcelDimensions) {

}
