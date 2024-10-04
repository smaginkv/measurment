package com.cdek.international.customs.measurements.infrastructure.db;

import com.cdek.international.customs.measurements.core.domain.PostamatCell;
import com.cdek.international.customs.measurements.core.domain.Postamat;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostamatRepository {
    @NonNull
    public Postamat get() {
        return new Postamat(List.of(
                new PostamatCell(List.of(200, 200, 200)),
                new PostamatCell(List.of(450, 300, 200)),
                new PostamatCell(List.of(150, 100, 100))
        )
        );
    }
}
