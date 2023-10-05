package com.cdek.international.customs.measurements.infrastructure.db;

import com.cdek.international.customs.measurements.core.application.PostamatCell;
import org.springframework.stereotype.Repository;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;
import java.util.List;

@Repository
public class PostamatCellsRepository {
    public List<PostamatCell> getAllCells() {
        return List.of(
                new PostamatCell(
                        List.of(
                                Quantities.getQuantity(200, MetricPrefix.MILLI(Units.METRE)),
                                Quantities.getQuantity(200, MetricPrefix.MILLI(Units.METRE)),
                                Quantities.getQuantity(200, MetricPrefix.MILLI(Units.METRE))
                        ),
                        Quantities.getQuantity(5, Units.KILOGRAM)
                ),
                new PostamatCell(
                        List.of(
                                Quantities.getQuantity(450, MetricPrefix.MILLI(Units.METRE)),
                                Quantities.getQuantity(300, MetricPrefix.MILLI(Units.METRE)),
                                Quantities.getQuantity(200, MetricPrefix.MILLI(Units.METRE))
                        ),
                        Quantities.getQuantity(20, Units.KILOGRAM)),
                new PostamatCell(
                        List.of(
                                Quantities.getQuantity(150, MetricPrefix.MILLI(Units.METRE)),
                                Quantities.getQuantity(100, MetricPrefix.MILLI(Units.METRE)),
                                Quantities.getQuantity(100, MetricPrefix.MILLI(Units.METRE))
                        ),
                        Quantities.getQuantity(10, Units.KILOGRAM))
        );
    }
}
