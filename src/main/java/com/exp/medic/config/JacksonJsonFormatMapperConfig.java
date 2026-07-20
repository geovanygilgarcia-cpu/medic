package com.exp.medic.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.type.format.jackson.JacksonJsonFormatMapper;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer; // ← paquete nuevo
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonJsonFormatMapperConfig {

    @Bean
    public HibernatePropertiesCustomizer hibernateJsonFormatMapperCustomizer() {
        ObjectMapper hibernateObjectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());

        return props -> props.put(
                "hibernate.type.json_format_mapper",
                new JacksonJsonFormatMapper(hibernateObjectMapper)
        );
    }
}