package br.com.deliverit.instant.account.interest.calculator.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class AppControllerWebClientTest {

    private static final String BASE_URI = "/v1";
    private static final String MSG = "This is the property file to the ${spring.application.name} specific to DEFAULT Environment";

    private WebTestClient webTestClient;
    private AppController appController;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        this.appController = new AppController();
        this.appController.setAppMessage(MSG);
        this.webTestClient = WebTestClient.bindToController(appController).build();
    }

    @Test
    void testGetAppMessage() {
        log.info("{} ", "\n#TEST: testGetAppMessage: ");

        // -- 01_Cenário
        AtomicReference<String> resultJson = new AtomicReference<>("");

        // -- 03_Verificação_Validação
        webTestClient
                .get()
                .uri(BASE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .consumeWith(exchangeResult -> {
                    resultJson.set(Objects.requireNonNull(exchangeResult.getResponseBody()));
                    assertThat(Objects.requireNonNull(exchangeResult.getResponseBody()).contains(MSG));
                });

        log.info("{} ", "\n#TEST: testGetAppMessage: ");
    }
}
