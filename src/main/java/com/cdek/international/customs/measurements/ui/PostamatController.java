package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.application.PostamatCell;
import com.cdek.international.customs.measurements.core.application.PostamatCellService;
import com.cdek.international.customs.measurements.core.application.VolumeWeight;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.format.LocalUnitFormat;
import tech.units.indriya.format.NumberDelimiterQuantityFormat;

import javax.measure.Quantity;
import javax.measure.quantity.Volume;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

import static javax.measure.MetricPrefix.CENTI;
import static tech.units.indriya.unit.Units.METRE;

/**
 * Нужна зависимость web+lombok+indriya
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostamatController {
    private final PostamatCellService postamatCellService;
    private final PostamatConverter postamatConverter;

    @PostMapping("postamat/suitableCell")
    public List<String> getSuitableCell(@RequestBody SuitableCellRequestDto requestDto) {
        final var cells = postamatCellService.getSuitableCell(
                requestDto.parcelDimensions());

        return cells.stream()
                .map(postamatConverter::toCellResponse)
                .toList();
    }

    @GetMapping("postamat/volume")
    public String getPostamatVolume() {
        final var userConvertedVolume = postamatConverter.toUserVolumeResponseDto(
                postamatCellService.getVolume());

        return NumberDelimiterQuantityFormat.getInstance(
                        NumberFormat.getInstance(LocaleContextHolder.getLocale()),
                        LocalUnitFormat.getInstance(LocaleContextHolder.getLocale()))
                .format(userConvertedVolume);
    }

    @GetMapping("postamat/volumeWeight")
    public ComparableQuantity<VolumeWeight> getPostamatVolumeWeight() {
        return postamatCellService.getVolumeWeight();
    }
}
