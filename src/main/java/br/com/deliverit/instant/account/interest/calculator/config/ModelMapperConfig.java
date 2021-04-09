package br.com.deliverit.instant.account.interest.calculator.config;


import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableRequest;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.util.useful.FormatterUtil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(AccountPayableRequest.class, AccountPayable.class);

        TypeMap<AccountPayable, AccountPayableModel> accountPayableToModelMapping = modelMapper.createTypeMap(AccountPayable.class, AccountPayableModel.class);
        accountPayableToModelMapping.addMappings(mapping -> mapping.using(LOCAL_DATE_TO_STRING_CONVERTER)
                .map(AccountPayable::getPayDay, AccountPayableModel::setPayDay)
        );

        return new ModelMapper();
    }

    public static final Converter<LocalDateTime, String> LOCAL_DATE_TO_STRING_CONVERTER = mappingContext -> {
        LocalDateTime source = mappingContext.getSource();
        return FormatterUtil.formatterLocalDateTimeBy(source);
    };
}
