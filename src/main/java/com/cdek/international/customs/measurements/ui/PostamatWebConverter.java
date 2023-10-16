package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.domain.PostamatCell;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

@Component
public class PostamatWebConverter {
    public String toSuitableCellsResponseDto(PostamatCell cell) {
        return cell.dimensions().stream()
                .map(Object::toString)
                .collect(Collectors.joining("x", "", " cm"));
    }

    public BigDecimal toUserVolumeResponseDto(BigDecimal volume) {
        final var divisor = BigDecimal.valueOf(1000);
        return volume.divide(divisor, 2, RoundingMode.HALF_UP);
    }
}
