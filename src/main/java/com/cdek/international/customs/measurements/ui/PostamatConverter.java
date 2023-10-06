package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.application.PostamatCell;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class PostamatConverter {
    public String toCellResponse(PostamatCell cell) {
        return cell.dimensions().stream()
                .map(Object::toString)
                .collect(Collectors.joining("x", "", " cm"));
    }
}
