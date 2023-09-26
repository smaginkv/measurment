package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.application.PostamatCellService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.units.indriya.ComparableQuantity;

import javax.measure.quantity.Length;
import java.util.List;

/**
 * Нужна зависимость web+lombok+indriya
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {
    private final PostamatCellService postamatCellService;

    @PostMapping("postamat/suitableCell")
    public List<ComparableQuantity<Length>> getSuitableCell(@RequestBody SuitableCellRequestDto requestDto) {
        final var parcelDimensions = requestDto.parcelDimensions();
        return postamatCellService.getSuitableCell(parcelDimensions);
    }
}
