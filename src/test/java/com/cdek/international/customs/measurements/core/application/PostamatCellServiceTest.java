package com.cdek.international.customs.measurements.core.application;

import com.cdek.international.customs.measurements.infrastructure.db.PostamatCellsRepository;
import com.cdek.international.customs.measurements.utils.PostamatCellModelGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import java.util.List;

import static com.cdek.international.customs.measurements.core.application.VolumeWeight.VOLUME_WEIGHT;
import static javax.measure.MetricPrefix.CENTI;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(VolumeWeightFactory.class)
class PostamatCellServiceTest {
    private PostamatCellService underTest;

    @MockBean
    private PostamatCellsRepository postamatCellsRepository;

    @Autowired
    private VolumeWeightFactory volumeWeightFactory;

    @BeforeEach
    void setUp() {
        underTest = new PostamatCellService(postamatCellsRepository, volumeWeightFactory);
    }

    @Test
    void getSuitableCell_whenOk_expectEquals() {
        //given
        final var parcelDimensions = PostamatCellModelGenerator.getPostamatCellDimensions(199, 254, 332);
        final var postamatCell = PostamatCellModelGenerator.getPostamatCell();

        when(postamatCellsRepository.getAllCells())
                .thenReturn(List.of(postamatCell));

        //when
        final var actual = underTest.getSuitableCell(parcelDimensions);

        //then
        Assertions.assertThat(actual)
                .hasSize(1)
                .element(0)
                .isSameAs(postamatCell);
    }

    @Test
    void getSuitableCell_whenNoSuitableCell_expectEmpty() {
        //given
        final var parcelDimensions = PostamatCellModelGenerator.getPostamatCellDimensions(201, 254, 332);
        final var postamatCell = PostamatCellModelGenerator.getPostamatCell();

        when(postamatCellsRepository.getAllCells())
                .thenReturn(List.of(postamatCell));

        //when
        final var actual = underTest.getSuitableCell(parcelDimensions);

        //then
        Assertions.assertThat(actual)
                .isEmpty();
    }

    @Test
    void getVolume_whenOk_expectEquals() {
        //given
        final var postamatCell1 = PostamatCellModelGenerator.getPostamatCell();
        final var postamatCell2 = PostamatCellModelGenerator.getPostamatCell();
        final var expected = Quantities.getQuantity(0.033966, Units.CUBIC_METRE);
        when(postamatCellsRepository.getAllCells())
                .thenReturn(List.of(postamatCell1, postamatCell2));

        //when
        final var actual = underTest.getVolume();

        Assertions.assertThat(actual)
                .isEqualByComparingTo(expected);
    }

    @Test
    void getVolumeWeight_whenOk_expectEquals() {
        //given
        final var postamatCell1 = new PostamatCell(
                List.of(
                        Quantities.getQuantity(10, CENTI(Units.METRE)),
                        Quantities.getQuantity(10, CENTI(Units.METRE)),
                        Quantities.getQuantity(10, CENTI(Units.METRE))
                ),
                Quantities.getQuantity(10, Units.KILOGRAM)
        );

        final var postamatCell2 = new PostamatCell(
                List.of(
                        Quantities.getQuantity(15, CENTI(Units.METRE)),
                        Quantities.getQuantity(15, CENTI(Units.METRE)),
                        Quantities.getQuantity(15, CENTI(Units.METRE))
                ),
                Quantities.getQuantity(3.75, Units.KILOGRAM)
        );

        final var postamatCell3 = new PostamatCell(
                List.of(
                        Quantities.getQuantity(30, CENTI(Units.METRE)),
                        Quantities.getQuantity(30, CENTI(Units.METRE)),
                        Quantities.getQuantity(30, CENTI(Units.METRE))
                ),
                Quantities.getQuantity(20, Units.KILOGRAM)
        );

        when(postamatCellsRepository.getAllCells())
                .thenReturn(List.of(postamatCell1, postamatCell2, postamatCell3));

        //when
        final var actual = underTest.getVolumeWeight();

        //then
        Assertions.assertThat(actual)
                .isEqualByComparingTo(Quantities.getQuantity(43.75, VOLUME_WEIGHT));
    }
}