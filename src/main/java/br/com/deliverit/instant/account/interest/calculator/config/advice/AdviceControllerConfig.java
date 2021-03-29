package br.com.deliverit.instant.account.interest.calculator.config.advice;

import br.com.deliverit.instant.account.interest.calculator.account.exceptions.HttpException;
import br.com.deliverit.instant.account.interest.calculator.util.useful.FormatterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebInputException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
@ControllerAdvice
@Slf4j
public class AdviceControllerConfig {

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(HttpException throwable) {
        log.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(throwable.getHttpStatus())
                .body(getErrorApiResponse(throwable.getMessage(), throwable.getHttpStatus().getReasonPhrase(), throwable.getHttpStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlerServerInputException(ServerWebInputException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(getErrorApiResponse("missing a parameter.", e.getMessage(), e.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        String mensagem = String.format("Argument '% s' must be valid '% s' but it is '% s'.",
                e.getName(), e.getRequiredType().getSimpleName(), e.getValue());
        return new ResponseEntity<>(getErrorApiResponse(mensagem, e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerInternalServerErrorFromExceptions(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(getBody(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(getErrorApiResponse("Invalid input JSON.", e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse(e.getMessage());
        return new ResponseEntity<>(getErrorApiResponse(message, e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    private ErrorApiResponse getErrorApiResponse(String message, String details, HttpStatus httpStatus) {
        return ErrorApiResponse.builder()
                .message(message)
                .details(details)
                .timestamp(FormatterUtil.formatterLocalDateTimeBy(LocalDateTime.now()))
                .statusCode(String.valueOf(httpStatus.value()))
                .status(httpStatus.toString())
                .build();
    }

    private Map<String, Object> getBody(String message, HttpStatus httpStatus) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", FormatterUtil.formatterLocalDateTimeBy(LocalDateTime.now()));
        body.put("message", message);
        body.put("status_code", httpStatus.value());
        body.put("status", httpStatus.toString());

        return body;
    }
}
