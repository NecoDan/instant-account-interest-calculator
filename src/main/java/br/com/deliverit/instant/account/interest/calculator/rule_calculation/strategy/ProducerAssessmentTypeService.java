package br.com.deliverit.instant.account.interest.calculator.rule_calculation.strategy;

import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public abstract class ProducerAssessmentTypeService {

    protected boolean isValid(AccountPayable accountPayable) {
        return (Objects.nonNull(accountPayable) && accountPayable.isValidTotalDaysLate());
    }

    protected boolean isUntil(AccountPayable accountPayable, int totalDays) {
        return (accountPayable.getTotalDaysLate() <= totalDays);
    }

    protected boolean isOver(AccountPayable accountPayable, int totalDays) {
        return (accountPayable.getTotalDaysLate() > totalDays);
    }
}
