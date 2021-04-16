package br.com.deliverit.instant.account.interest.calculator.controller.api;

import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableRequest;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.account.service.GenerateAccountPayableService;
import br.com.deliverit.instant.account.interest.calculator.account.service.ReportAccountPayableService;
import br.com.deliverit.instant.account.interest.calculator.util.AccountPayableCreator;
import br.com.deliverit.instant.account.interest.calculator.util.RandomUtil;
import br.com.deliverit.instant.account.interest.calculator.util.useful.FormatterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(AccountPayableController.class)
public class AccountPayableControllerWebMvcTest {

    private static final String URI = "/v1/accounts";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GenerateAccountPayableService generateAccountPayableService;
    @MockBean
    private ReportAccountPayableService reportAccountPayableService;

    private ModelMapper modelMapperFinal;
    private ObjectMapper objectMapper;

    @Before
    @SneakyThrows
    public void setUp() {
        this.modelMapperFinal = AccountPayableCreator.getInicializeModelMapper();
        this.objectMapper = AccountPayableCreator.getInicializeObjectMapper();
    }

    @Test
    @SneakyThrows
    public void testGetAllReturnStatus200() {
        log.info("\n#TEST: testGetAllReturnStatus200: ");

        // -- 01_Cenário
        List<AccountPayableModel> accountPayableModelList = Arrays.asList(
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable()),
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable()),
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable()),
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable()),
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable())
        );

        // -- 02_Ação
        given(reportAccountPayableService.findAll()).willReturn(accountPayableModelList);
        ResultActions response = getResponseEntityEndPointsMethodGET(URI, MediaType.APPLICATION_JSON);

        // -- 03_Verificação_Validação
        response.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].name").exists())
                .andExpect(jsonPath("$.[*].name").isNotEmpty())
                .andExpect(jsonPath("$.[*].payDay").isNotEmpty())
                .andExpect(jsonPath("$.[*].originValue").exists())
                .andExpect(jsonPath("$.[*].originValue").isNotEmpty())
                .andExpect(jsonPath("$.[*].correctedValue").exists())
                .andExpect(jsonPath("$.[*].correctedValue").isNotEmpty())
                .andExpect(jsonPath("$.[*].totalDaysLate").exists())
                .andExpect(jsonPath("$.[*].totalDaysLate").isNotEmpty());

        assertNotNull(response.andReturn().getResponse().getContentAsString());
        AccountPayableCreator.toStringEnd(response);
    }

    @Test
    @SneakyThrows
    public void testGetAllPageableReturnStatus200() {
        log.info("\n#TEST: testGetAllPageableReturnStatus200: ");

        // -- 01_Cenário
        List<AccountPayableModel> accountPayableModelList = Arrays.asList(
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable()),
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable()),
                AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable())
        );

        int sizePageable = 10;
        Pageable pageable = PageRequest.of(0, sizePageable);
        Page<AccountPayableModel> pageAccountPayableModel = new PageImpl<>(accountPayableModelList, pageable, accountPayableModelList.size());

        // -- 02_Ação
        given(reportAccountPayableService.findAllPageable(isA(Pageable.class))).willReturn(pageAccountPayableModel);
        ResultActions response = getResponseEntityEndPointsMethodGET(URI + "/page", MediaType.APPLICATION_JSON);

        // -- 03_Verificação_Validação
        response.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].[*].name").exists())
                .andExpect(jsonPath("$.[*].[*].name").isNotEmpty())
                .andExpect(jsonPath("$.[*].[*].payDay").isNotEmpty())
                .andExpect(jsonPath("$.[*].[*].originValue").exists())
                .andExpect(jsonPath("$.[*].[*].originValue").isNotEmpty())
                .andExpect(jsonPath("$.[*].[*].correctedValue").exists())
                .andExpect(jsonPath("$.[*].[*].correctedValue").isNotEmpty())
                .andExpect(jsonPath("$.[*].[*].totalDaysLate").exists())
                .andExpect(jsonPath("$.[*].[*].totalDaysLate").isNotEmpty())
                .andExpect(jsonPath("$.totalPages").exists())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").exists())
                .andExpect(jsonPath("$.totalElements").value(accountPayableModelList.size()))
                .andExpect(jsonPath("$.numberOfElements").exists())
                .andExpect(jsonPath("$.numberOfElements").value(accountPayableModelList.size()))
                .andExpect(jsonPath("$.size").exists())
                .andExpect(jsonPath("$.size").value(sizePageable));

        assertNotNull(response.andReturn().getResponse().getContentAsString());
        AccountPayableCreator.toStringEnd(response);
    }

    @Test
    @SneakyThrows
    public void findByIdReturnStatus200() {
        log.info("\n#TEST: findByIdReturnStatus200: ");

        // -- 01_Cenário
        UUID id = UUID.randomUUID();
        AccountPayableModel accountPayableModel = AccountPayableCreator.getAccountPayableModelFrom(AccountPayableCreator.getAccountPayable());

        // -- 02_Ação
        given(reportAccountPayableService.findById(isA(UUID.class))).willReturn(Optional.of(accountPayableModel));
        ResultActions response = getResponseEntityEndPointsMethodGET(URI + "/" + id.toString(), MediaType.APPLICATION_JSON);

        // -- 03_Verificação_Validação
        response.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").value(accountPayableModel.getName()))
                .andExpect(jsonPath("$.payDay").exists())
                .andExpect(jsonPath("$.payDay").isNotEmpty())
                .andExpect(jsonPath("$.payDay").isString())
                .andExpect(jsonPath("$.originValue").exists())
                .andExpect(jsonPath("$.originValue").isNotEmpty())
                .andExpect(jsonPath("$.originValue").isNumber())
                .andExpect(jsonPath("$.correctedValue").exists())
                .andExpect(jsonPath("$.correctedValue").isNotEmpty())
                .andExpect(jsonPath("$.correctedValue").isNumber())
                .andExpect(jsonPath("$.totalDaysLate").exists())
                .andExpect(jsonPath("$.totalDaysLate").isNotEmpty())
                .andExpect(jsonPath("$.totalDaysLate").isNumber())
                .andExpect(jsonPath("$.totalDaysLate").value(accountPayableModel.getTotalDaysLate()));

        assertNotNull(response.andReturn().getResponse().getContentAsString());
        AccountPayableCreator.toStringEnd(response);
    }

    @Test
    @SneakyThrows
    public void saveReturnStatus201() {
        log.info("\n#TEST: saveReturnStatus201: ");

        // -- 01_Cenário
        AccountPayableRequest accountPayableRequest = AccountPayableRequest.builder()
                .name(RandomUtil.generateIdentityNameRandom())
                .originValue(RandomUtil.generateDecimalRandomValue(1000))
                .dueDate(LocalDate.now().plusDays(5))
                .payDayDate(LocalDate.now())
                .build();

        AccountPayable accountPayable = this.modelMapperFinal.map(accountPayableRequest, AccountPayable.class);

        LocalTime utcTime = LocalTime.now(ZoneId.of("America/Sao_Paulo"));
        accountPayable.setDueDate(LocalDateTime.of(accountPayableRequest.getDueDate(), utcTime));
        accountPayable.setPayDay(LocalDateTime.of(accountPayableRequest.getPayDayDate(), utcTime));
        accountPayable.generateTotalDaysLate();
        accountPayable.setPayValue(accountPayableRequest.getOriginValue());
        accountPayable.setCorrectedValue(accountPayableRequest.getOriginValue());

        AccountPayableModel accountPayableModel = AccountPayableCreator.getAccountPayableModelFrom(accountPayable);

        // -- 02_Ação
        when(generateAccountPayableService.toAccountPayableFrom(isA(AccountPayableRequest.class))).thenReturn(accountPayable);
        given(generateAccountPayableService.create(isA(AccountPayable.class))).willReturn(accountPayableModel);

        ResultActions response = this.mockMvc.perform(post(URI)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonValueAccountPayableRequestFrom(accountPayableRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        );

        // -- 03_Verificação_Validação
        response.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").value(accountPayableModel.getName()))
                .andExpect(jsonPath("$.payDay").exists())
                .andExpect(jsonPath("$.payDay").isNotEmpty())
                .andExpect(jsonPath("$.payDay").isString())
                .andExpect(jsonPath("$.originValue").exists())
                .andExpect(jsonPath("$.originValue").isNotEmpty())
                .andExpect(jsonPath("$.originValue").isNumber())
                .andExpect(jsonPath("$.correctedValue").exists())
                .andExpect(jsonPath("$.correctedValue").isNotEmpty())
                .andExpect(jsonPath("$.correctedValue").isNumber())
                .andExpect(jsonPath("$.totalDaysLate").exists())
                .andExpect(jsonPath("$.totalDaysLate").isNotEmpty())
                .andExpect(jsonPath("$.totalDaysLate").isNumber())
                .andExpect(jsonPath("$.totalDaysLate").value(accountPayableModel.getTotalDaysLate()));

        verify(generateAccountPayableService).create(any(AccountPayable.class));

        String statusResponse = String.valueOf(response.andReturn().getResponse().getStatus());
        log.info("#TEST_RESULT_STATUS: ".concat((statusResponse.isEmpty()) ? " " : HttpStatus.valueOf(Integer.parseInt(statusResponse)).toString()));
        AccountPayableCreator.toStringEnd(response);
    }

    private ResultActions getResponseEntityEndPointsMethodGET(String url, MediaType mediaType) throws Exception {
        return this.mockMvc.perform(get(url).accept(mediaType));
    }

    private String getJsonValueAccountPayableRequestFrom(AccountPayableRequest accountPayableRequest) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(accountPayableRequest);
    }
}
