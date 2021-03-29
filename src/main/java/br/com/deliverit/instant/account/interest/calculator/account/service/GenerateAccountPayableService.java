package br.com.deliverit.instant.account.interest.calculator.account.service;


import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.exceptions.AccountPayableUnProcessableEntityException;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.model.InterestCalculationRule;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.service.IInterestCalculationRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

    @Override
    @Transactional(value = Transactional.TxType.NEVER)
    public AccountPayableModel create(AccountPayable accountPayable) {
        validateParams(accountPayable);
        try {
            accountPayable.generateId();
            accountPayable.setTotalDaysLate(2);
            accountPayable.setPayValue(accountPayable.getOriginValue());
            accountPayable.setCorrectedValue(accountPayable.getOriginValue());

            Optional<InterestCalculationRule> interestCalculationRule = interestCalculationRuleService.findById(1L);
            accountPayable.setInterestCalculationRule(interestCalculationRule
                    .orElseThrow(() -> new AccountPayableUnProcessableEntityException("GenerateAccountPayableService - Error: Trying to create AccountPayable, interest calculation rule is invalid and/or nonexistent (null).")));

            return this.accountPayableService.toAccountPayableModel(this.accountPayableService.save(accountPayable));
        } catch (Exception e) {
            throw new AccountPayableUnProcessableEntityException("GenerateVotingService - Error: Save AccountPayable.".concat(e.getMessage()));
        }
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
