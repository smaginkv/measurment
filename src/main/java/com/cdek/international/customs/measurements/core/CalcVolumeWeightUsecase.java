package com.cdek.international.customs.measurements.core;

import com.cdek.international.customs.measurements.core.application.PostamatRepository;
import com.cdek.international.customs.measurements.core.domain.VolumeWeightFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CalcVolumeWeightUsecase {
    private final PostamatRepository postamatRepository;
    private final VolumeWeightFactory volumeWeightFactory;

    public BigDecimal calcVolumeWeight() {
        return this.postamatRepository.getById().cells().stream()
                .map(cell -> this.volumeWeightFactory.calcCellVolumeWeight(cell.getVolume(), cell.weight()))
                .reduce(BigDecimal::add)
                .orElseThrow(IllegalStateException::new);
    }
}
