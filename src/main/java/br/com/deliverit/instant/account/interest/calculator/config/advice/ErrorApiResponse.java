package br.com.deliverit.instant.account.interest.calculator.config.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorApiResponse {
    private String message;
    private String details;
    private String timestamp;
    private String statusCode;
    private String status;
}
