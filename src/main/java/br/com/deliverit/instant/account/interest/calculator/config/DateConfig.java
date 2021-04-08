package br.com.deliverit.instant.account.interest.calculator.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
@Configuration
public class DateConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule modulo = new SimpleModule();
        modulo.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ofPattern("yyyy-MM-dd HH:mm:ss")));
        modulo.addSerializer(LocalDate.class, new LocalDateSerializer(ofPattern("yyyy-MM-dd")));
        modulo.addDeserializer(LocalDate.class, new LocalDateDeserializer(ofPattern("yyyy-MM-dd")));
        modulo.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ofPattern("yyyy-MM-dd HH:mm:ss")));

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(modulo);
        return mapper;
    }
}
