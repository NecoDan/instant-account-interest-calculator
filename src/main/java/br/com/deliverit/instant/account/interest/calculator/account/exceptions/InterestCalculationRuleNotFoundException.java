package br.com.deliverit.instant.account.interest.calculator.account.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
public class InterestCalculationRuleNotFoundException extends HttpException {

    private static final long serialVersionUID = -7166035888009031051L;

    public InterestCalculationRuleNotFoundException(String message) {
        super(String.format("Interest calculation rule not found because '%s'.", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
