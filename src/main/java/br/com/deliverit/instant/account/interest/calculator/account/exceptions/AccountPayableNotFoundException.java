package br.com.deliverit.instant.account.interest.calculator.account.exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
public class AccountPayableNotFoundException extends HttpException {

    private static final long serialVersionUID = -7166035888009031051L;

    public AccountPayableNotFoundException(String message, UUID id) {
        super(String.format("Account Payable '%s' not found by '%s'.", message, id));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
