package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.application.PostamatCell;
import org.springframework.stereotype.Component;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.quantity.Volume;
import java.util.stream.Collectors;

import static javax.measure.MetricPrefix.CENTI;
import static tech.units.indriya.unit.Units.METRE;

@Component
public class PostamatConverter {
    public String toCellResponse(PostamatCell cell) {
        return cell.dimensions().stream()
                .map(dimension -> dimension.to(CENTI(Units.METRE)))
                .map(Quantity::getValue)
                .map(Object::toString)
                .collect(Collectors.joining("x", "", " " + CENTI(METRE).toString()));
    }

    public ComparableQuantity<Volume> toUserVolumeResponseDto(ComparableQuantity<Volume> volume) {
        return volume.to(
                CENTI(METRE).multiply(CENTI(METRE)).multiply(CENTI(METRE)).asType(Volume.class));
    }
}
