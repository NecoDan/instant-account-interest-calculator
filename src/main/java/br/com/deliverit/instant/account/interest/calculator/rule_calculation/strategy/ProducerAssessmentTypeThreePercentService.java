package br.com.deliverit.instant.account.interest.calculator.rule_calculation.strategy;

import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.enums.TypeAssessment;
import org.springframework.stereotype.Service;

@Service
public class ProducerAssessmentTypeThreePercentService extends ProducerAssessmentTypeService implements IProducerTypeAssessmentService {

    private static final int MORE_THAN_3_DAYS = 3;

    @Override
    public boolean isAppliable(AccountPayable accountPayable) {
        return (isValid(accountPayable) && isOver(accountPayable, MORE_THAN_3_DAYS));
    }

    @Override
    public TypeAssessment getTypeAssessmentFrom(AccountPayable accountPayable) {
        return TypeAssessment.THREE_PERCENT;
    }
}
