package com.cdek.international.customs.measurements.ui;

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

import java.text.NumberFormat;
import java.util.List;

/**
 * Нужна зависимость web+lombok+indriya
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostamatController {
    private final PostamatCellService postamatCellService;
    private final PostamatConverter postamatConverter;

    @PostMapping("/postamat/suitableCell")
    public List<String> suitableCell(@RequestBody SuitableCellRequestDto requestDto) {
        final var cells = postamatCellService.getSuitableCell(
                requestDto.parcelDimensions());

        return cells.stream()
                .map(postamatConverter::toCellResponse)
                .toList();
    }

    @GetMapping("/postamat/volume")
    public String getPostamatVolume() {
        final var userConvertedVolume = postamatConverter.toUserVolumeResponseDto(
                postamatCellService.getVolume());

        return NumberDelimiterQuantityFormat.getInstance(
                        NumberFormat.getInstance(LocaleContextHolder.getLocale()),
                        LocalUnitFormat.getInstance(LocaleContextHolder.getLocale()))
                .format(userConvertedVolume);
    }

    @GetMapping("/postamat/volumeWeight")
    public ComparableQuantity<VolumeWeight> getPostamatVolumeWeight() {
        return postamatCellService.getVolumeWeight();
    }
}
