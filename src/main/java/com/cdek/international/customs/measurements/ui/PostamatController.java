package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.infrastructure.db.PostamatRepository;
import lombok.NonNull;
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
@RequestMapping("/api/postamat")
@RequiredArgsConstructor
public class PostamatController {
    private final PostamatRepository postamatRepository;
    private final PostamatConverter postamatConverter;

    @NonNull
    @PostMapping("suitableCell")
    public List<String> getSuitableCell(@RequestBody @NonNull SuitableCellRequestDto requestDto) {
        return postamatRepository.get()
                .getSuitableCell(requestDto.parcelDimensions())
                .stream()
                .map(this.postamatConverter::toCellResponse)
                .toList();
    }
}
