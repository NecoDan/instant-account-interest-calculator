package br.com.deliverit.instant.account.interest.calculator.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Daniel Santos
 * Home redirection to swagger api documentation
 */
@RestController
@Slf4j
@Data
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AppController {

    @Value("${app.message}")
    private String appMessage;

    @GetMapping
    public ResponseEntity<String> getAppMessageCall() {
        log.info("Call to request app: {}.", appMessage);
        return ResponseEntity.ok(appMessage);
    }
}
