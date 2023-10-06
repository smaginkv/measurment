package com.cdek.international.customs.measurements.infrastructure.db;

import com.cdek.international.customs.measurements.core.application.PostamatCell;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostamatCellsRepository {
    public List<PostamatCell> getAllCells() {
        return List.of(
                new PostamatCell(List.of(200, 200, 200)),
                new PostamatCell(List.of(450, 300, 200)),
                new PostamatCell(List.of(150, 100, 100))
        );
    }
}
