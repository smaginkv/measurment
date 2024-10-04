package com.cdek.international.customs.measurements.ui;

import org.springframework.lang.NonNull;

import java.util.List;

public record SuitableCellRequestDto(@NonNull List<Integer> parcelDimensions) {
}
