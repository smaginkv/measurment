package com.cdek.international.customs.measurements.utils;

import com.cdek.international.customs.measurements.core.application.PostamatCell;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import java.util.List;

import static javax.measure.MetricPrefix.MILLI;

public class PostamatCellModelGenerator {

    public static PostamatCell getPostamatCell() {
        return new PostamatCell(
                List.of(
                        Quantities.getQuantity(200, MILLI(Units.METRE)),
                        Quantities.getQuantity(255, MILLI(Units.METRE)),
                        Quantities.getQuantity(333, MILLI(Units.METRE))
                ),
                Quantities.getQuantity(18.87, Units.KILOGRAM)
        );
    }

    public static List<Quantity<Length>> getPostamatCellDimensions(int x, int y, int z) {
        return List.of(
                Quantities.getQuantity(x, MILLI(Units.METRE)),
                Quantities.getQuantity(y, MILLI(Units.METRE)),
                Quantities.getQuantity(z, MILLI(Units.METRE))
        );
    }
}
