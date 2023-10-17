package com.cdek.international.customs.measurements.infra.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.uom.lib.jackson.UnitJacksonModule;

import javax.measure.Quantity;

@Configuration
public class JacksonConfiguration {
    @Bean
    public Module units() {
        return new UnitJacksonModule();
    }

    @Bean
    public Module quantity() {
        return new SimpleModule()
                .addDeserializer(Quantity.class, new QuantityJsonDeserializer());
    }
}
