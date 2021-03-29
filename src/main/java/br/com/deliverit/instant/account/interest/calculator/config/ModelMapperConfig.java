package br.com.deliverit.instant.account.interest.calculator.config;


import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableRequest;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(AccountPayable.class, AccountPayableModel.class);
        modelMapper.createTypeMap(AccountPayableRequest.class, AccountPayable.class);
        return new ModelMapper();
    }
}
