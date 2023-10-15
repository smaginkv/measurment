package com.cdek.international.customs.measurements.core.application;

import com.cdek.international.customs.measurements.core.domain.PostamatCell;
import com.cdek.international.customs.measurements.core.domain.VolumeWeight;
import com.cdek.international.customs.measurements.core.domain.VolumeWeightFactory;
import com.cdek.international.customs.measurements.infrastructure.db.PostamatRepository;
import org.springframework.stereotype.Component;
import tech.units.indriya.ComparableQuantity;

import java.util.stream.Stream;

@Component
public class CalcVolumeWeightUsecase {
    private final PostamatRepository postamatRepository;
    private final VolumeWeightFactory volumeWeightFactory;

    public CalcVolumeWeightUsecase(
            PostamatRepository postamatRepository,
            VolumeWeightFactory volumeWeightFactory
    ) {
        this.postamatRepository = postamatRepository;
        this.volumeWeightFactory = volumeWeightFactory;
    }

    public ComparableQuantity<VolumeWeight> calcVolumeWeight() {
        return this.postamatRepository.findById().cells().stream()
                .map(this::calcCellVolumeWeight)
                .reduce(ComparableQuantity::add)
                .orElseThrow(IllegalStateException::new);
    }

    private ComparableQuantity<VolumeWeight> calcCellVolumeWeight(PostamatCell cell) {
        return Stream.of(calcVolumeWeightByWeight(cell), calcVolumeWeightByVolume(cell))
                .max(Comparable::compareTo)
                .orElseThrow(IllegalStateException::new);
    }

    private ComparableQuantity<VolumeWeight> calcVolumeWeightByWeight(PostamatCell cell) {
        return this.volumeWeightFactory.of(cell.weight());
    }

    private ComparableQuantity<VolumeWeight> calcVolumeWeightByVolume(PostamatCell cell) {
        return this.volumeWeightFactory.of(cell.getCellVolume());
    }
}
