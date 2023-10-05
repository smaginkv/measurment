package com.cdek.international.customs.measurements.ui;

import com.cdek.international.customs.measurements.core.application.PostamatCellService;
import com.cdek.international.customs.measurements.infrastructure.conf.JacksonConfiguration;
import com.cdek.international.customs.measurements.utils.PostamatCellInfrastructureGenerator;
import com.cdek.international.customs.measurements.utils.PostamatCellModelGenerator;
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
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.quantity.Volume;
import java.util.List;

import static com.cdek.international.customs.measurements.core.application.VolumeWeight.VOLUME_WEIGHT;
import static javax.measure.MetricPrefix.CENTI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static tech.units.indriya.unit.Units.METRE;

@WebMvcTest
@Import(JacksonConfiguration.class)
@AutoConfigureJsonTesters
class PostamatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostamatCellService postamatCellService;
    @MockBean
    private PostamatConverter postamatConverter;

    @Autowired
    private JacksonTester<List<String>> listStringJacksonTester;

    @Autowired
    private JacksonTester<SuitableCellRequestDto> suitableCellRequestDtoJacksonTester;

    @Test
    void suitableCell_whenOk_expectEquals() throws Exception {
        //given
        final var requestDto = PostamatCellInfrastructureGenerator.getSuitableCellRequestDto();
        final var postamatCell = PostamatCellModelGenerator.getPostamatCell();
        final var postamatCellRepresentation = "20.0x25.5x33.3 cm";

        when(postamatCellService.getSuitableCell(requestDto.parcelDimensions()))
                .thenReturn(List.of(postamatCell));
        when(postamatConverter.toCellResponse(postamatCell))
                .thenReturn(postamatCellRepresentation);

        //when
        final var response = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/postamat/suitableCell")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(suitableCellRequestDtoJacksonTester.write(requestDto).getJson())
        ).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString())
                .isEqualTo(listStringJacksonTester.write(List.of(postamatCellRepresentation)).getJson());
    }

    @Test
    void getPostamatVolume_whenOk_expectEquals() throws Exception {
        //given
        final var volume = Quantities.getQuantity(1, Units.CUBIC_METRE);
        final var userVolume = Quantities.getQuantity(1000000, CENTI(METRE).pow(3)).asType(Volume.class);
        when(postamatCellService.getVolume())
                .thenReturn(volume);

        when(postamatConverter.toUserVolumeResponseDto(volume))
                .thenReturn(userVolume);

        //when
        final var response = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/postamat/volume")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString())
                .isEqualTo("1,000,000 cm³");
    }

    @Test
    void getPostamatVolumeWeight_whenOk_expectEquals() throws Exception {
        //given
        final var volumeWeight = Quantities.getQuantity(1, VOLUME_WEIGHT);
        when(postamatCellService.getVolumeWeight())
                .thenReturn(volumeWeight);

        //when
        final var response = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/postamat/volumeWeight")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString())
                .isEqualTo("{\"unit\":\"VW\",\"scale\":\"ABSOLUTE\",\"value\":1}");
    }

}