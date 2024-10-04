package com.cdek.international.customs.measurements.ui;

import org.springframework.lang.NonNull;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import java.util.List;

public record SuitableCellRequestDto(@NonNull List<Quantity<Length>> parcelDimensions) {
}
