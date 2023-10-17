package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.CalcVolumeWeightUsecase;
import com.cdek.international.customs.measurements.infra.PostamatRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
    public BigDecimal getVolume() {
        final var volume = this.postamatRepository.getById().getVolume();
        return this.postamatWebConverter.toUserVolumeResponseDto(volume);
    }

    @GetMapping("volumeWeight")
    public BigDecimal getVolumeWeight() {
        return this.calcVolumeWeightUsecase.calcVolumeWeight();
    }
}