package com.cdek.international.customs.measurements.core.domain;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

@Service
public class VolumeWeightFactory {
    private static final BigDecimal VOLUMETRIC_WEIGHT_COEFFICIENT = BigDecimal.valueOf(900L);

    public BigDecimal calcCellVolumeWeight(Long volume, Long weight) {
        return Stream.of(calcVolumeWeightByVolume(volume), calcVolumeWeightByWeight(weight))
                .max(Comparable::compareTo)
                .orElseThrow(IllegalStateException::new);
    }

    private BigDecimal calcVolumeWeightByVolume(Long volume) {
        final var scale = BigDecimal.valueOf(1000);
        final var volumeCm3 = BigDecimal.valueOf(volume).divide(scale, 2, RoundingMode.HALF_UP);
        return volumeCm3.divide(VOLUMETRIC_WEIGHT_COEFFICIENT, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcVolumeWeightByWeight(Long weight) {
        return BigDecimal.valueOf(weight);
    }
}
