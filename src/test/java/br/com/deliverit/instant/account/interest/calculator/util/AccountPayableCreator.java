package br.com.deliverit.instant.account.interest.calculator.util;

import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.util.useful.FormatterUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountPayableCreator {

    public static AccountPayable getAccountPayable() {
        return AccountPayable.builder()
                .name(RandomUtil.generateIdentityNameRandom())
                .originValue(RandomUtil.generateDecimalRandomValue(1000.0D))
                .payValue(RandomUtil.generateDecimalRandomValue(1000.0D))
                .correctedValue(RandomUtil.generateDecimalRandomValue(1000.0D))
                .dueDate(LocalDateTime.now())
                .payDay(LocalDateTime.now())
                .totalDaysLate(RandomUtil.generateRandomValueUntil(30))
                .build()
                .generateIdRandom();
    }

    public static AccountPayableModel getAccountPayableModelFrom(AccountPayable accountPayable) {
        return AccountPayableModel.builder()
                .correctedValue(accountPayable.getCorrectedValue())
                .name(accountPayable.getName())
                .originValue(accountPayable.getOriginValue())
                .payDay(FormatterUtil.formatterLocalDateTimeBy(accountPayable.getPayDay()))
                .totalDaysLate(accountPayable.getTotalDaysLate())
                .build();
    }

    private static ObjectMapper inicializeObjectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        module.addDeserializer(LocalDate.class, localDateDeserializer);
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

        return Jackson2ObjectMapperBuilder.json().modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();
    }
}
