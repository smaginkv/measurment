package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.application.PostamatCell;
import com.cdek.international.customs.measurements.core.application.PostamatCellService;
import com.cdek.international.customs.measurements.infrastructure.db.PostamatCellsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest
@AutoConfigureJsonTesters
@Import({ PostamatCellService.class, PostamatConverter.class })
class PostamatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostamatCellsRepository postamatCellsRepository;

    @Autowired
    private JacksonTester<List<String>> listStringJacksonTester;

    @Autowired
    private JacksonTester<SuitableCellRequestDto> suitableCellRequestDtoJacksonTester;

    @Test
    void suitableCell_whenOk_expectEquals() throws Exception {
        //given
        final var requestDto = new SuitableCellRequestDto(List.of(20, 25, 33));
        final var postamatCell1_1 = new PostamatCell(List.of(21, 26, 34));
        final var postamatCell1_2 = new PostamatCell(List.of(21, 25, 33));
        final var postamatCell1_3 = new PostamatCell(List.of(20, 26, 33));
        final var postamatCell1_4 = new PostamatCell(List.of(20, 25, 34));
        final var postamatCell2 = new PostamatCell(List.of(20, 25, 33));
        final var postamatCell3_1 = new PostamatCell(List.of(21, 24, 32));
        final var postamatCell3_2 = new PostamatCell(List.of(19, 26, 32));
        final var postamatCell3_3 = new PostamatCell(List.of(19, 24, 34));

        when(postamatCellsRepository.getAllCells())
                .thenReturn(List.of(postamatCell1_1, postamatCell1_2, postamatCell1_3, postamatCell1_4, postamatCell2,
                        postamatCell3_1, postamatCell3_2, postamatCell3_3));

        //when
        final var response = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/postamat/suitableCell")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(suitableCellRequestDtoJacksonTester.write(requestDto).getJson())
        ).andReturn().getResponse();

        //then
        assertThat(response.getStatus())

                .isEqualTo(HttpStatus.OK.value());

        List<String> actual = listStringJacksonTester.parseObject(response.getContentAsString());
        assertThat(actual)
                .containsExactlyInAnyOrder(
                        "20x25x33 cm",
                        "21x26x34 cm",
                        "21x25x33 cm",
                        "20x26x33 cm",
                        "20x25x34 cm");

    }
}