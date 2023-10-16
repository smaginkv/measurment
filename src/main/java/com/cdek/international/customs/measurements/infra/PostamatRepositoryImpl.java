package com.cdek.international.customs.measurements.infra;

import com.cdek.international.customs.measurements.core.application.PostamatRepository;
import com.cdek.international.customs.measurements.core.domain.Postamat;
import com.cdek.international.customs.measurements.core.domain.PostamatCell;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostamatRepositoryImpl implements PostamatRepository {
    @Override
    public Postamat getById() {
        return new Postamat(
                List.of(
                        new PostamatCell(
                                List.of(150L, 150L, 150L),
                                1L
                        ),
                        new PostamatCell(
                                List.of(450L, 300L, 200L),
                                20L
                        ),
                        new PostamatCell(
                                List.of(150L, 100L, 100L),
                                10L
                        )
                )
        );
    }
}
