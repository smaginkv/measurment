package com.cdek.international.customs.measurements.core.application;

import com.cdek.international.customs.measurements.infrastructure.db.PostamatCellDbo;
import com.cdek.international.customs.measurements.infrastructure.db.PostamatCellsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Volume;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostamatCellService {

    private final PostamatCellsRepository postamatCellsRepository;

    public List<List<ComparableQuantity<Length>>> getSuitableCell(List<Quantity<Length>> parcelDimensions) {

        return postamatCellsRepository.getAllCells().stream()
                .map(PostamatCellDbo::dimensions)
                .filter(cellDimensions -> cellDimensions.get(0).isGreaterThanOrEqualTo(parcelDimensions.get(0)))
                .filter(cellDimensions -> cellDimensions.get(1).isGreaterThanOrEqualTo(parcelDimensions.get(1)))
                .filter(cellDimensions -> cellDimensions.get(1).isGreaterThanOrEqualTo(parcelDimensions.get(2)))
                .toList();
    }

    public ComparableQuantity<Volume> getVolume() {
        return postamatCellsRepository.getAllCells().stream()
                .map(PostamatCellDbo::dimensions)
                .map(this::getCellVolume)
                .reduce(ComparableQuantity::add)
                .orElseThrow(IllegalStateException::new);
    }

    private ComparableQuantity<Volume> getCellVolume(List<ComparableQuantity<Length>> cells) {
        return cells.get(0).multiply(cells.get(1)).multiply(cells.get(2)).asType(Volume.class);
    }
}
