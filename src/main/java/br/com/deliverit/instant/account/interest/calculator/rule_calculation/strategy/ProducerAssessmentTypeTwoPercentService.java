package br.com.deliverit.instant.account.interest.calculator.rule_calculation.strategy;

import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.enums.TypeAssessment;
import org.springframework.stereotype.Service;

@Service
public class ProducerAssessmentTypeTwoPercentService extends ProducerAssessmentTypeService implements IProducerTypeAssessmentService {

    private static final int UP_TO_3_DAYS = 3;

    @Override
    public boolean isAppliable(AccountPayable accountPayable) {
        return (isValid(accountPayable) && isUntil(accountPayable, UP_TO_3_DAYS));
    }

    @Override
    public TypeAssessment getTypeAssessmentFrom(AccountPayable accountPayable) {
        return TypeAssessment.TWO_PERCENT;
    }
}
