package com.cdek.international.customs.measurements.core.application;

import com.cdek.international.customs.measurements.infrastructure.db.PostamatCellDbo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostamatCellService {
    public List<Integer> getSuitableCell(List<Integer> parcelDimensions, List<PostamatCellDbo> cells) {
        return cells.stream()
                .map(PostamatCellDbo::dimensions)
                .filter(cellDimensions -> cellDimensions.get(0) >= parcelDimensions.get(0))
                .filter(cellDimensions -> cellDimensions.get(1) >= parcelDimensions.get(1))
                .filter(cellDimensions -> cellDimensions.get(2) >= parcelDimensions.get(2))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
