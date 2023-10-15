package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.application.CalcVolumeWeightUsecase;
import com.cdek.international.customs.measurements.core.application.PostamatCellService;
import com.cdek.international.customs.measurements.core.domain.VolumeWeight;
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
@RequestMapping("/api/postamat")
public class PostamatController {
    private final PostamatCellService postamatCellService;
    private final PostamatConverter postamatConverter;
    private final CalcVolumeWeightUsecase calcVolumeWeightUsecase;

    public PostamatController(
            PostamatCellService postamatCellService,
            PostamatConverter postamatConverter,
            CalcVolumeWeightUsecase calcVolumeWeightUsecase
    ) {
        this.postamatCellService = postamatCellService;
        this.postamatConverter = postamatConverter;
        this.calcVolumeWeightUsecase = calcVolumeWeightUsecase;
    }

    @PostMapping("suitableCell")
    public List<String> getSuitableCell(@RequestBody SuitableCellRequestDto requestDto) {
        final var cells = this.postamatCellService.getSuitableCell(
                requestDto.parcelDimensions());

        return cells.stream()
                .map(this.postamatConverter::toCellResponse)
                .toList();
    }

    @GetMapping("volume")
    public String getPostamatVolume() {
        final var userConvertedVolume = this.postamatConverter.toUserVolumeResponseDto(
                this.postamatCellService.getVolume());

        return NumberDelimiterQuantityFormat.getInstance(
                        NumberFormat.getInstance(LocaleContextHolder.getLocale()),
                        LocalUnitFormat.getInstance(LocaleContextHolder.getLocale()))
                .format(userConvertedVolume);
    }

    @GetMapping("volumeWeight")
    public ComparableQuantity<VolumeWeight> calcPostamatVolumeWeight() {
        return this.calcVolumeWeightUsecase.calcVolumeWeight();
    }
}
