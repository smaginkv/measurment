package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.CalcVolumeWeightUsecase;
import com.cdek.international.customs.measurements.core.domain.VolumeWeight;
import com.cdek.international.customs.measurements.infra.PostamatRepositoryImpl;
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

@RestController
@RequestMapping("/api/postamat")
@RequiredArgsConstructor
public class PostamatController {
    private final PostamatRepositoryImpl postamatRepository;
    private final PostamatWebConverter postamatWebConverter;
    private final CalcVolumeWeightUsecase calcVolumeWeightUsecase;

    @PostMapping("suitableCells")
    public List<String> getSuitableCells(@RequestBody SuitableCellsRequestDto requestDto) {
        final var parcelDimensions = requestDto.parcelDimensions().stream()
                .sorted()
                .toList();

        final var postamat = this.postamatRepository.getById();
        final var cells = postamat.getSuitableCells(parcelDimensions);

        return cells.stream()
                .map(this.postamatWebConverter::toSuitableCellsResponseDto)
                .toList();
    }

    @GetMapping("volume")
    public String getVolume() {
        final var volume = this.postamatRepository.getById().getVolume();
        final var userConvertedVolume = this.postamatWebConverter.toUserVolumeResponseDto(volume);

        return NumberDelimiterQuantityFormat.getInstance(
                        NumberFormat.getInstance(LocaleContextHolder.getLocale()),
                        LocalUnitFormat.getInstance(LocaleContextHolder.getLocale()))
                .format(userConvertedVolume);
    }

    @GetMapping("volumeWeight")
    public ComparableQuantity<VolumeWeight> getVolumeWeight() {
        return this.calcVolumeWeightUsecase.calcVolumeWeight();
    }
}