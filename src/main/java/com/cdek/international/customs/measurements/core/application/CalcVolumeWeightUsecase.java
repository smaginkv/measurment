package com.cdek.international.customs.measurements.core.application;

import com.cdek.international.customs.measurements.core.domain.PostamatCell;
import com.cdek.international.customs.measurements.core.domain.VolumeWeight;
import com.cdek.international.customs.measurements.core.domain.VolumeWeightFactory;
import com.cdek.international.customs.measurements.infrastructure.db.PostamatCellsRepository;
import org.springframework.stereotype.Component;
import tech.units.indriya.ComparableQuantity;

import java.util.stream.Stream;

@Component
public class CalcVolumeWeightUsecase {
    private final PostamatCellsRepository postamatCellsRepository;
    private final VolumeWeightFactory volumeWeightFactory;

    public CalcVolumeWeightUsecase(
            PostamatCellsRepository postamatCellsRepository,
            VolumeWeightFactory volumeWeightFactory
    ) {
        this.postamatCellsRepository = postamatCellsRepository;
        this.volumeWeightFactory = volumeWeightFactory;
    }

    public ComparableQuantity<VolumeWeight> calcVolumeWeight() {
        return this.postamatCellsRepository.getAllCells().stream()
                .map(this::calcCellVolumeWeight)
                .reduce(ComparableQuantity::add)
                .orElseThrow(IllegalStateException::new);
    }

    private ComparableQuantity<VolumeWeight> calcCellVolumeWeight(PostamatCell cell) {
        return Stream.of(getCellWeight(cell), getCellVolumeWeight(cell))
                .max(Comparable::compareTo)
                .orElseThrow(IllegalStateException::new);
    }

    private ComparableQuantity<VolumeWeight> getCellWeight(PostamatCell cell) {
        return this.volumeWeightFactory.of(cell.weight());
    }

    private ComparableQuantity<VolumeWeight> getCellVolumeWeight(PostamatCell cell) {
        return this.volumeWeightFactory.of(cell.getCellVolume());
    }
}
