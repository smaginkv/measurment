package com.cdek.international.customs.measurements.core.application;

import com.cdek.international.customs.measurements.infrastructure.db.PostamatCellsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Volume;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PostamatCellService {
    private final PostamatCellsRepository postamatCellsRepository;
    private final VolumeWeightFactory volumeWeightFactory;

    public List<List<ComparableQuantity<Length>>> getSuitableCell(List<Quantity<Length>> parcelDimensions) {

        return postamatCellsRepository.getAllCells().stream()
                .map(PostamatCell::dimensions)
                .filter(cellDimensions -> cellDimensions.get(0).isGreaterThanOrEqualTo(parcelDimensions.get(0)))
                .filter(cellDimensions -> cellDimensions.get(1).isGreaterThanOrEqualTo(parcelDimensions.get(1)))
                .filter(cellDimensions -> cellDimensions.get(2).isGreaterThanOrEqualTo(parcelDimensions.get(2)))
                .toList();
    }

    public ComparableQuantity<Volume> getVolume() {
        return postamatCellsRepository.getAllCells().stream()
                .map(PostamatCell::dimensions)
                .map(this::getCellVolume)
                .reduce(ComparableQuantity::add)
                .orElseThrow(IllegalStateException::new);
    }

    private ComparableQuantity<Volume> getCellVolume(List<ComparableQuantity<Length>> cells) {
        return cells.get(0).multiply(cells.get(1)).multiply(cells.get(2)).asType(Volume.class);
    }

    public ComparableQuantity<VolumeWeight> getVolumeWeight() {
        return postamatCellsRepository.getAllCells().stream()
                .map(cell ->
                        Stream.of(getCellWeight(cell), getCellVolumeWeight(cell))
                                .max(Comparable::compareTo)
                                .get()
                )
                .reduce(ComparableQuantity::add)
                .orElseThrow(IllegalStateException::new);
    }

    private ComparableQuantity<VolumeWeight> getCellWeight(PostamatCell cell) {
        return volumeWeightFactory.of(cell.weight());
    }

    private ComparableQuantity<VolumeWeight> getCellVolumeWeight(PostamatCell cell) {
        final var volume = getCellVolume(cell.dimensions());
        return volumeWeightFactory.of(volume);
    }
}
