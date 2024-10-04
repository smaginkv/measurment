package com.cdek.international.customs.measurements.infrastructure.conf;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.uom.lib.jackson.UnitJacksonModule;

import javax.measure.Quantity;

@Configuration
public class JacksonConfiguration {
    /**
     * Регистрируем модуль сериализации систем единиц СИ - JSR-385
     */
    @Bean
    public Module unitsOfMeasureModule() {
        return new UnitJacksonModule();
    }

    /**
     * Регистрируем недостающие сериализаторы/десериализаторы для типа {@link Quantity} систем единиц СИ
     */
    @Bean
    public Module unitsOfMeasureQuantityModule() {
        return new SimpleModule()
                .addDeserializer(Quantity.class, new QuantityJsonDeserializer());
    }
}
