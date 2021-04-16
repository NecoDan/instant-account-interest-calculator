package br.com.deliverit.instant.account.interest.calculator.controller.api;

import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.service.GenerateAccountPayableService;
import br.com.deliverit.instant.account.interest.calculator.account.service.ReportAccountPayableService;
import br.com.deliverit.instant.account.interest.calculator.config.advice.AdviceControllerConfig;
import br.com.deliverit.instant.account.interest.calculator.util.AccountPayableCreator;
import br.com.deliverit.instant.account.interest.calculator.util.useful.FormatterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@Slf4j
class AccountPayableControllerWebClientTest {

    private static final String URI = "/v1/accounts";

    private GenerateAccountPayableService generateAccountPayableService;
    private ReportAccountPayableService reportAccountPayableService;
    private AccountPayableController accountPayableController;
    private WebTestClient webTestClient;
    private ModelMapper modelMapperFinal;
    private ObjectMapper objectMapper;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        this.generateAccountPayableService = Mockito.mock(GenerateAccountPayableService.class);
        this.reportAccountPayableService = Mockito.mock(ReportAccountPayableService.class);
        this.accountPayableController = new AccountPayableController(this.generateAccountPayableService, this.reportAccountPayableService);

        this.webTestClient = WebTestClient
                .bindToController(accountPayableController)
                .controllerAdvice(AdviceControllerConfig.class)
                .build();

        this.modelMapperFinal = AccountPayableCreator.getInicializeModelMapper();
        this.objectMapper = AccountPayableCreator.getInicializeObjectMapper();
    }

    @Test
    void findByIdReturnStatus200OkFromWebClientTest() {
        log.info("{} ", "\n#TEST: findByIdReturnStatus200OkFromWebClientTest: ");

        // -- 01_Cenário
        AtomicReference<String> responseResultJSON = new AtomicReference<>("");
        UUID id = UUID.randomUUID();
        AccountPayableModel accountPayableModel = AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable());

        // -- 02_Ação
        when(reportAccountPayableService.findById(isA(UUID.class))).thenReturn(Optional.of(accountPayableModel));

        // -- 03_Verificação_Validação
        webTestClient.get()
                .uri(URI + "/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AccountPayableModel.class)
                .consumeWith(result -> {
                    try {
                        responseResultJSON.set(this.objectMapper.writeValueAsString(Objects.requireNonNull(result.getResponseBody())));

                        assertThat(Objects.requireNonNull(result.getResponseBody()).getName()).isEqualTo(accountPayableModel.getName());
                        assertThat(Objects.requireNonNull(result.getResponseBody()).getTotalDaysLate()).isEqualTo(accountPayableModel.getTotalDaysLate());

                    } catch (JsonProcessingException e) {
                        log.info(e.getLocalizedMessage());
                    }
                });

        toStringEnd(responseResultJSON);
    }

    @Test
    void testGetAllPageableReturnStatus200OkFromWebClientTest() {
        log.info("{} ", "\n#TEST: testGetAllPageableReturnStatus200OkFromWebClientTest: ");

        // -- 01_Cenário
        AtomicReference<String> responseJSON = new AtomicReference<>("");

        List<AccountPayableModel> accountPayableModelList = Arrays.asList(
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable()),
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable()),
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable()),
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable()),
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable())
        );

        int sizePageable = 10;
        Pageable pageable = PageRequest.of(0, sizePageable);
        Page<AccountPayableModel> pageAccountPayableModel = new PageImpl<>(accountPayableModelList, pageable, accountPayableModelList.size());

        // -- 02_Ação
        when(reportAccountPayableService.findAllPageable(isA(Pageable.class))).thenReturn(pageAccountPayableModel);

        // -- 03_Verificação_Validação
//        webTestClient.get()
//                .uri(URI + "/page")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectBody(Page.class);
//                .jsonPath("$.numberOfElements").isEqualTo(accountPayableModelList.size())
//                .jsonPath("$.content[0].name").isNotEmpty();


        webTestClient.get()
                .uri(URI + "/page")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
//                .expectStatus()
//                .isOk()
                .expectBody(Page.class)
                .consumeWith(result -> {
                    try {
                        responseJSON.set(this.objectMapper.writeValueAsString(Objects.requireNonNull(result.getResponseBody())));
                        Assertions.assertTrue(Objects.nonNull(result.getResponseBody()));
                    } catch (JsonProcessingException e) {
                        log.info(e.getLocalizedMessage());
                    }
                });

        //toStringEnd(responseJSON);
    }

    private void toStringEnd(AtomicReference<String> resultJson) {
        if (Objects.isNull(resultJson) || StringUtils.isBlank(resultJson.get())) {
            log.info("#TEST_RESULT: ".concat("Error generating output. There is no data..."));
            return;
        }
        log.info("{} ", "#TEST_RESULT: ".concat(FormatterUtil.formatterContentJsonFrom(resultJson.get())));
    }
}
