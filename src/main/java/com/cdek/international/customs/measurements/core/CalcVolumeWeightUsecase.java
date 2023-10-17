package com.cdek.international.customs.measurements.core;

import com.cdek.international.customs.measurements.core.application.PostamatRepository;
import com.cdek.international.customs.measurements.core.domain.VolumeWeight;
import com.cdek.international.customs.measurements.core.domain.VolumeWeightFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.units.indriya.ComparableQuantity;

@Component
@RequiredArgsConstructor
public class CalcVolumeWeightUsecase {
    private final PostamatRepository postamatRepository;
    private final VolumeWeightFactory volumeWeightFactory;

    public ComparableQuantity<VolumeWeight> calcVolumeWeight() {
        return this.postamatRepository.getById().cells().stream()
                .map(cell -> this.volumeWeightFactory.calcCellVolumeWeight(cell.getVolume(), cell.weight()))
                .reduce(ComparableQuantity::add)
                .orElseThrow(IllegalStateException::new);
    }
}
