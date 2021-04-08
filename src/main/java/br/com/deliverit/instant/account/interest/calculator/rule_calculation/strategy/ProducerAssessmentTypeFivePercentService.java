package br.com.deliverit.instant.account.interest.calculator.rule_calculation.strategy;

import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.enums.TypeAssessment;
import org.springframework.stereotype.Service;

@Service
public class ProducerAssessmentTypeFivePercentService extends ProducerAssessmentTypeService implements IProducerTypeAssessmentService {

    private static final int MORE_THAN_5_DAYS = 5;

    @Override
    public boolean isAppliable(AccountPayable accountPayable) {
        return (isValid(accountPayable) && isOver(accountPayable, MORE_THAN_5_DAYS));
    }

    @Override
    public TypeAssessment getTypeAssessmentFrom(AccountPayable accountPayable) {
        return TypeAssessment.FIVE_PERCENT;
    }
}
