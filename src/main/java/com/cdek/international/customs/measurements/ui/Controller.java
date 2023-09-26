package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.application.PostamatCellService;
import com.cdek.international.customs.measurements.infrastructure.db.PostamatCellsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Нужна зависимость web+lombok+indriya
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {
    private final PostamatCellsRepository postamatCellsRepository;
    private final PostamatCellService postamatCellService;

    @PostMapping("postamat/suitableCell")
    public List<Integer> getSuitableCell(@RequestBody SuitableCellRequestDto requestDto) {
        final var parcelDimensions = requestDto.parcelDimensions();
        final var cells = postamatCellsRepository.getAllCell();
        return postamatCellService.getSuitableCell(parcelDimensions, cells);
    }
}
