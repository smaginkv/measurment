package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.domain.PostamatCell;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class PostamatConverter {
    @NonNull
    public String toCellResponse(@NonNull PostamatCell cell) {
        return cell.dimensions().stream()
                .map(Object::toString)
                .collect(Collectors.joining("x", "", " cm"));
    }
}
