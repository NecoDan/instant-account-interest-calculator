package br.com.deliverit.instant.account.interest.calculator.rule_calculation.strategy;

import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.enums.TypeAssessment;

public interface IProducerTypeAssessmentService {

    boolean isAppliable(AccountPayable accountPayable);

    TypeAssessment getTypeAssessmentFrom(AccountPayable accountPayable);

}
