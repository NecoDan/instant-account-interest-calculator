package br.com.deliverit.instant.account.interest.calculator.rule_calculation.factory;

import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.enums.TypeAssessment;
import br.com.deliverit.instant.account.interest.calculator.rule_calculation.strategy.IProducerTypeAssessmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FactoryTypeAssessmentService {

    private final List<IProducerTypeAssessmentService> producerTypeAssessmentService;

    public FactoryTypeAssessmentService(List<IProducerTypeAssessmentService> producerTypeAssessmentService) {
        this.producerTypeAssessmentService = producerTypeAssessmentService;
    }

    public Optional<TypeAssessment> get(AccountPayable accountPayable) {
        return Optional.of(this.producerTypeAssessmentService
                .stream()
                .filter(Objects::nonNull)
                .filter(service -> service.isAppliable(accountPayable))
                .map(service -> service.getTypeAssessmentFrom(accountPayable))
                .findFirst()
                .orElse(TypeAssessment.NONE));
    }
}
