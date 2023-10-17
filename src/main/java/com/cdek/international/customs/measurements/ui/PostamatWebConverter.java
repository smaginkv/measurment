package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.domain.PostamatCell;
import org.springframework.stereotype.Component;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.quantity.Volume;
import java.util.stream.Collectors;

import static javax.measure.MetricPrefix.CENTI;

@Component
public class PostamatWebConverter {
    public String toSuitableCellsResponseDto(PostamatCell cell) {
        return cell.dimensions().stream()
                .map(dimensions -> dimensions.to(CENTI(Units.METRE)))
                .map(Quantity::getValue)
                .map(Object::toString)
                .collect(Collectors.joining("x", "", " " + CENTI(Units.METRE)));
    }

    public ComparableQuantity<Volume> toUserVolumeResponseDto(ComparableQuantity<Volume> volume) {
        return volume.to(CENTI(Units.METRE).pow(3).asType(Volume.class));
    }
}
