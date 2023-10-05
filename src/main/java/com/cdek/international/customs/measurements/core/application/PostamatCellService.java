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

    public List<PostamatCell> getSuitableCell(List<Quantity<Length>> parcelDimensions) {
        return postamatCellsRepository.getAllCells().stream()
                .filter(cell -> cell.isGreaterThanOrEqualTo(parcelDimensions))
                .toList();
    }

    public ComparableQuantity<Volume> getVolume() {
        return postamatCellsRepository.getAllCells().stream()
                .map(PostamatCell::getCellVolume)
                .reduce(ComparableQuantity::add)
                .orElseThrow(IllegalStateException::new);
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
        return volumeWeightFactory.of(cell.getCellVolume());
    }
}
