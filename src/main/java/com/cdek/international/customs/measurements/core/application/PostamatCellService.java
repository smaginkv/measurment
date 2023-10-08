package com.cdek.international.customs.measurements.core.application;

import com.cdek.international.customs.measurements.infrastructure.db.PostamatCellsRepository;
import org.springframework.stereotype.Service;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import java.util.List;

@Service
public class PostamatCellService {
    private final PostamatCellsRepository postamatCellsRepository;

    public PostamatCellService(PostamatCellsRepository postamatCellsRepository) {
        this.postamatCellsRepository = postamatCellsRepository;
    }

    public List<PostamatCell> getSuitableCell(List<Quantity<Length>> parcelDimensions) {
        return this.postamatCellsRepository.getAllCells().stream()
                .filter(cell -> cell.isGreaterThanOrEqualTo(parcelDimensions))
                .toList();
    }
}
