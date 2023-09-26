package com.cdek.international.customs.measurements.core.application;

import com.cdek.international.customs.measurements.infrastructure.db.PostamatCellDbo;
import com.cdek.international.customs.measurements.infrastructure.db.PostamatCellsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostamatCellService {

    private final PostamatCellsRepository postamatCellsRepository;

    public List<ComparableQuantity<Length>> getSuitableCell(List<Quantity<Length>> parcelDimensions) {

        return postamatCellsRepository.getAllCell().stream()
                .map(PostamatCellDbo::dimensions)
                .filter(cellDimensions -> cellDimensions.get(0).isGreaterThanOrEqualTo(parcelDimensions.get(0)))
                .filter(cellDimensions -> cellDimensions.get(1).isGreaterThanOrEqualTo(parcelDimensions.get(1)))
                .filter(cellDimensions -> cellDimensions.get(1).isGreaterThanOrEqualTo(parcelDimensions.get(2)))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
