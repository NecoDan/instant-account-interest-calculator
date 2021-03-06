package br.com.deliverit.instant.account.interest.calculator.util;

import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableRequest;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.config.ModelMapperConfig;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.model.InterestCalculationRule;
import br.com.deliverit.instant.account.interest.calculator.util.useful.FormatterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Slf4j
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

    public static InterestCalculationRule createInterestCalculationRule() {
        return InterestCalculationRule.builder()
                .id(RandomUtil.generateRandomValueUntil(10000).longValue())
                .daysLate(RandomUtil.generateRandomValueUntil(365))
                .description(RandomUtil.generateIdentityNameRandom())
                .operator(">")
                .percentageInterest(RandomUtil.generateDecimalRandomValue(10.0))
                .percentageAssessment(RandomUtil.generateDecimalRandomValue(10.0))
                .build();
    }

    public static ObjectMapper getInicializeObjectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        module.addDeserializer(LocalDate.class, localDateDeserializer);
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

        return Jackson2ObjectMapperBuilder.json().modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();
    }

    public static ModelMapper getInicializeModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(AccountPayableRequest.class, AccountPayable.class);

        TypeMap<AccountPayable, AccountPayableModel> accountPayableToModelMapping = modelMapper.createTypeMap(AccountPayable.class, AccountPayableModel.class);
        accountPayableToModelMapping.addMappings(mapping -> mapping.using(ModelMapperConfig.LOCAL_DATE_TO_STRING_CONVERTER)
                .map(AccountPayable::getPayDay, AccountPayableModel::setPayDay)
        );

        return new ModelMapper();
    }

    public static void toStringEnd(ResultActions response) throws Exception {
        if (Objects.isNull(response)) {
            log.info("#TEST_RESULT: ".concat("Error generating output. There is no data..."));
            return;
        }

        String result = response.andReturn().getResponse().getContentAsString();
        log.info("#TEST_RESULT: ".concat(FormatterUtil.formatterContentJsonFrom(result)));
    }
}
