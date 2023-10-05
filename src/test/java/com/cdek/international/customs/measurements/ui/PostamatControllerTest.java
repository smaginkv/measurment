package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.application.PostamatCellService;
import com.cdek.international.customs.measurements.infrastructure.conf.JacksonConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@WebMvcTest
@Import(JacksonConfiguration.class)
class PostamatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostamatCellService postamatCellService;
    @MockBean
    private PostamatConverter postamatConverter;

    @Test
    void suitableCell_whenOk_expectEquals() throws Exception {
        final var response = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/postamat/suitableCell")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "parcelDimensions": [
                                    {
                                      "unit": "cm",
                                      "scale": "ABSOLUTE",
                                      "value": 30
                                    }
                                    ]}""")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        verifyNoMoreInteractions(
                this.postamatCellService,
                this.postamatConverter
        );
    }

}