package br.com.deliverit.instant.account.interest.calculator.account.service;


import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableRequest;
import br.com.deliverit.instant.account.interest.calculator.account.exceptions.AccountPayableUnProcessableEntityException;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.enums.TypeAssessment;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.factory.FactoryTypeAssessmentService;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.model.InterestCalculationRule;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.service.IInterestCalculationRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateAccountPayableService implements IGenerateAccountPayableService {

    private final IAccountPayableService accountPayableService;
    private final IInterestCalculationRuleService interestCalculationRuleService;
    private final FactoryTypeAssessmentService factoryTypeAssessmentService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(value = Transactional.TxType.NEVER)
    public AccountPayableModel create(AccountPayable accountPayable) {
        validateParams(accountPayable);
        try {
            accountPayable.generateId();
            accountPayable.generateTotalDaysLate();
            processInterestCalculationRuleBy(accountPayable);
            return this.accountPayableService.toAccountPayableModel(this.accountPayableService.save(accountPayable));
        } catch (Exception e) {
            throw new AccountPayableUnProcessableEntityException("GenerateVotingService - Error: Save AccountPayable.".concat(e.getMessage()));
        }
    }

    private void processInterestCalculationRuleBy(AccountPayable accountPayable) {
        Optional<TypeAssessment> optionalTypeAssessment = this.factoryTypeAssessmentService.get(accountPayable);
        Optional<InterestCalculationRule> optional = this.interestCalculationRuleService.findById((long) optionalTypeAssessment.get().getCode());

        accountPayable.setInterestCalculationRule(optional
                .orElseThrow(() ->
                        new AccountPayableUnProcessableEntityException("GenerateAccountPayableService - Error: Trying to create AccountPayable, interest calculation rule is invalid and/or nonexistent (null)."))
        );

        accountPayable.calculateAssessment();
    }

    @Override
    public AccountPayable toAccountPayableFrom(AccountPayableRequest accountPayableRequest) {
        AccountPayable accountPayable = this.modelMapper.map(accountPayableRequest, AccountPayable.class);

        LocalTime utcTime = LocalTime.now(ZoneId.of("America/Sao_Paulo"));
        accountPayable.setDueDate(LocalDateTime.of(accountPayableRequest.getDueDate(), utcTime));
        accountPayable.setPayDay(LocalDateTime.of(accountPayableRequest.getPayDayDate(), utcTime));

        return accountPayable;
    }

    @Override
    public void validateParams(AccountPayable accountPayable) {
        String msgValidation = "GenerateAccountPayableService - Error: Trying to create AccountPayable.";

        if (Objects.isNull(accountPayable)) {
            throw new AccountPayableUnProcessableEntityException(msgValidation.concat("AccountPayable is invalid and/or nonexistent (null)."));
        }

        if (StringUtils.isEmpty(accountPayable.getName())) {
            throw new AccountPayableUnProcessableEntityException(msgValidation.concat("The name is invalid and / or not informed."));
        }

        if (Objects.isNull(accountPayable.getOriginValue()) || Objects.equals(accountPayable.getOriginValue(), BigDecimal.ZERO)) {
            throw new AccountPayableUnProcessableEntityException(msgValidation.concat("The origin value is invalid and / or not informed."));
        }

        if (Objects.isNull(accountPayable.getDueDate())) {
            throw new AccountPayableUnProcessableEntityException(msgValidation.concat("The due date is invalid and / or not informed."));
        }

        if (Objects.isNull(accountPayable.getPayDay())) {
            throw new AccountPayableUnProcessableEntityException(msgValidation.concat("The pay day is invalid and / or not informed."));
        }
    }
}
