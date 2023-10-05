package com.cdek.international.customs.measurements.utils;


import com.cdek.international.customs.measurements.ui.SuitableCellRequestDto;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import java.util.List;

import static javax.measure.MetricPrefix.MILLI;

public class PostamatCellInfrastructureGenerator {

    public static SuitableCellRequestDto getSuitableCellRequestDto() {
        return new SuitableCellRequestDto(List.of(
                Quantities.getQuantity(200, MILLI(Units.METRE)),
                Quantities.getQuantity(255, MILLI(Units.METRE)),
                Quantities.getQuantity(333, MILLI(Units.METRE))
        ));
    }
}
