package com.cdek.international.customs.measurements.infrastructure.db;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostamatCellsRepository {
    public List<PostamatCellDbo> getAllCell() {
        return List.of(
                new PostamatCellDbo(List.of(200, 200, 200)),
                new PostamatCellDbo(List.of(450, 300, 200)),
                new PostamatCellDbo(List.of(150, 100, 100))
        );
    }
}
