package com.cdek.international.customs.measurements.infrastructure.conf;

import com.fasterxml.jackson.core.JsonParseException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.MeasurementException;
import javax.measure.MetricPrefix;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;
import java.io.IOException;
import java.math.BigDecimal;

@JsonTest(properties = "spring.cloud.kubernetes.enabled = false")
@Import(JacksonConfiguration.class)
class QuantityJsonDeserializerTest {
    @Autowired
    private JacksonTester<QuantityTestDto<Length>> jacksonLengthTester;
    @Autowired
    private JacksonTester<QuantityTestDto<Volume>> jacksonVolumeTester;
    @Autowired
    private JacksonTester<QuantityTestDto<Mass>> jacksonMassTester;

    @Test
    void deserialize_whenQuantityAsObject_expectEquals() throws IOException {
        // given
        //@formatter:off
        final var json =
                "{" +
                        "\"quantity\": {" +
                        "\"value\":100.01," +
                        "\"scale\":\"ABSOLUTE\"," +
                        "\"unit\":\"cm\"" +
                        "}" +
                        "}";
        //@formatter:off
        final var length = Quantities.getQuantity(
                BigDecimal.valueOf(100.01d),
                MetricPrefix.CENTI(Units.METRE)
        );
        final var expected = new QuantityTestDto<>(length);
        // when
        final var actual = this.jacksonLengthTester.parseObject(json);
        // then
        Assertions.assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deserialize_whenVolumeAsObject_expectEquals() throws IOException {
        // given
        //@formatter:off
        final var json = "{" +
                "\"quantity\":{" +
                "\"value\":100.01," +
                "\"scale\":\"ABSOLUTE\"," +
                "\"unit\":\"m3\"" +
                "}" +
                "}";
        //@formatter:off
        final var volume = Quantities.getQuantity(
                BigDecimal.valueOf(100.01d),
                Units.CUBIC_METRE
        );
        final var expected = new QuantityTestDto<>(volume);
        // when
        final var actual = this.jacksonVolumeTester.parseObject(json);
        // then
        Assertions.assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deserialize_whenMassAsObject_expectEquals() throws IOException {
        // given
        //@formatter:off
        final var json =
                "{" +
                        "\"quantity\": {" +
                        "\"value\":100.01," +
                        "\"scale\":\"ABSOLUTE\"," +
                        "\"unit\":\"kg\"" +
                        "}" +
                        "}";
        //@formatter:off
        final var mass = Quantities.getQuantity(
                BigDecimal.valueOf(100.01d),
                MetricPrefix.KILO(Units.GRAM)
        );
        final var expected = new QuantityTestDto<>(mass);
        // when
        final var actual = this.jacksonMassTester.parseObject(json);
        // then
        Assertions.assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deserialize_whenQuantityAsObjectWithScale_expectEquals() throws IOException {
        // given
        //@formatter:off
        final var json =
                "{" +
                        "\"quantity\": {" +
                        "\"value\":100.01," +
                        "\"unit\":\"km\"," +
                        "\"scale\":\"ABSOLUTE\"" +
                        "}" +
                        "}";
        //@formatter:on
        final var length = Quantities.getQuantity(
                BigDecimal.valueOf(100.01d),
                MetricPrefix.KILO(Units.METRE)
        );
        final var expected = new QuantityTestDto<>(length);
        // when
        final var actual = this.jacksonLengthTester.parseObject(json);
        // then
        Assertions.assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    // NEGATIVE

    @Test
    void deserialize_whenValueIsNull_expectLengthIsNull() throws IOException {
        // given
        //@formatter:off
        final var json =
                "{" +
                        "\"quantity\": null" +
                        "}";
        //@formatter:on

        // when
        final var actual = this.jacksonLengthTester.parseObject(json);
        // then
        Assertions.assertThat(actual)
                .isNotNull()
                .extracting("quantity")
                .isNull();
    }

    @Test
    void deserialize_whenValueIsEmpty_expectThrows() {
        // given
        //@formatter:off
        final var json =
                "{" +
                        "\"quantity\": \" \"" +
                        "}";
        //@formatter:on

        // when then
        Assertions.assertThatThrownBy(() -> this.jacksonLengthTester.parseObject(json))
                .hasCauseInstanceOf(JsonParseException.class);
    }

    @Test
    void deserialize_whenValueIsArray_expectThrows() {
        // given
        //@formatter:off
        final var json =
                "{" +
                        "\"quantity\": []" +
                        "}";
        //@formatter:on

        // when then
        Assertions.assertThatThrownBy(() -> this.jacksonLengthTester.parseObject(json))
                .hasCauseInstanceOf(JsonParseException.class);
    }

    @Test
    void deserialize_whenMassUnitForLength_expectThrows() {
        // given
        //@formatter:off
        final var json =
                "{" +
                        "\"quantity\": {" +
                        "\"value\": 100.01," +
                        "\"unit\":\"kg\"" +
                        "}" +
                        "}";
        //@formatter:off

        // when then
        Assertions.assertThatThrownBy(() -> this.jacksonLengthTester.parseObject(json))
                .hasCauseInstanceOf(JsonParseException.class);
    }

    @Test
    void deserialize_whenNonexistentUnit_expectThrows() {
        // given
        //@formatter:off
        final var json =
                "{" +
                        "\"quantity\": {" +
                        "\"value\": 100.01," +
                        "\"scale\":\"ABSOLUTE\"," +
                        "\"unit\": \"cmt\"" +
                        "}" +
                        "}";
        //@formatter:off

        // when then
        Assertions.assertThatThrownBy(() -> this.jacksonLengthTester.parseObject(json))
                .hasCauseInstanceOf(MeasurementException.class);
    }

    @Test
    void deserialize_whenValueNotANumber_expectThrows() {
        // given
        //@formatter:off
        final var json =
                "{" +
                        "\"quantity\": {" +
                        "\"value\": \"100.01\"," +
                        "\"scale\":\"ABSOLUTE\"," +
                        "\"unit\": \"cmt\"" +
                        "}" +
                        "}";
        //@formatter:off

        // when then
        Assertions.assertThatThrownBy(() -> this.jacksonLengthTester.parseObject(json))
                .hasCauseInstanceOf(MeasurementException.class);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuantityTestDto<T extends Quantity<T>> {
        private Quantity<T> quantity;
    }
}