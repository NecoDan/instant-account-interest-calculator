package br.com.deliverit.instant.account.interest.calculator.account.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
public class AccountPayableUnProcessableEntityException extends HttpException {

    private static final long serialVersionUID = -3605370362890111560L;

    public AccountPayableUnProcessableEntityException(String message) {
        super(String.format("Could not process the AccountPayable: %s.", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

}
