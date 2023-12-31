package com.cdek.international.customs.measurements.infrastructure.db;

import com.cdek.international.customs.measurements.core.application.PostamatCell;
import org.springframework.stereotype.Repository;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import java.util.List;

import static javax.measure.MetricPrefix.MILLI;

@Repository
public class PostamatCellsRepository {
    public List<PostamatCell> getAllCells() {
        return List.of(
                new PostamatCell(
                        List.of(
                                Quantities.getQuantity(200, MILLI(Units.METRE)),
                                Quantities.getQuantity(200, MILLI(Units.METRE)),
                                Quantities.getQuantity(200, MILLI(Units.METRE))
                        ),
                        Quantities.getQuantity(5, Units.KILOGRAM)
                ),
                new PostamatCell(
                        List.of(
                                Quantities.getQuantity(450, MILLI(Units.METRE)),
                                Quantities.getQuantity(300, MILLI(Units.METRE)),
                                Quantities.getQuantity(200, MILLI(Units.METRE))
                        ),
                        Quantities.getQuantity(20, Units.KILOGRAM)),
                new PostamatCell(
                        List.of(
                                Quantities.getQuantity(150, MILLI(Units.METRE)),
                                Quantities.getQuantity(100, MILLI(Units.METRE)),
                                Quantities.getQuantity(100, MILLI(Units.METRE))
                        ),
                        Quantities.getQuantity(10, Units.KILOGRAM))
        );
    }
}
