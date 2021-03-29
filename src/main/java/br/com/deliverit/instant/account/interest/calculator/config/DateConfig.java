package br.com.deliverit.instant.account.interest.calculator.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
@Configuration
@AutoConfigureBefore({JacksonAutoConfiguration.class})
public class DateConfig {

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//
//        SimpleModule modulo = new SimpleModule();
//        modulo.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ofPattern("yyyy-MM-dd HH:mm:ss")));
//        modulo.addSerializer(LocalDate.class, new LocalDateSerializer(ofPattern("yyyy-MM-dd")));
//        modulo.addDeserializer(LocalDate.class, new LocalDateDeserializer(ofPattern("yyyy-MM-dd")));
//        modulo.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ofPattern("yyyy-MM-dd HH:mm:ss")));
//
//        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        mapper.registerModule(modulo);
//        return mapper;
//    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat("dd/MM/yyyy HH:mm:ss");
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            builder.serializers(new LocalDateTimeSerializer(ofPattern("yyyy-MM-dd HH:mm:ss")));
            builder.serializers(new LocalDateSerializer(ofPattern("yyyy-MM-dd")));
            builder.deserializers(new LocalDateDeserializer(ofPattern("yyyy-MM-dd")));
            builder.deserializers(new LocalDateTimeDeserializer(ofPattern("yyyy-MM-dd HH:mm:ss")));
        };
    }

//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
//        return builder -> {
//            builder.simpleDateFormat("dd/MM/yyyy HH:mm:ss");
//            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
//        };
//    }

    private void configurarVisibilidadeDasPropriedades(ObjectMapper mapper) {
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.DEFAULT)
                .withSetterVisibility(JsonAutoDetect.Visibility.DEFAULT)
                .withCreatorVisibility(JsonAutoDetect.Visibility.DEFAULT));
    }
//
//    private void configurarParsersDeData(ObjectMapper mapper) {
//        mapper.registerModule(new JavaTimeModule());
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//    }
}
