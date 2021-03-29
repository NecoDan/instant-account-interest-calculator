package br.com.deliverit.instant.account.interest.calculator.account.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
public abstract class HttpException extends RuntimeException {

    private static final long serialVersionUID = 1367514783325148120L;

    HttpException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}
