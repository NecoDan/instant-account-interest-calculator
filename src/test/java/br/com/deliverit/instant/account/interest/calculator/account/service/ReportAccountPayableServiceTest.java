package br.com.deliverit.instant.account.interest.calculator.account.service;

import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.account.repository.AccountPayableRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
class ReportAccountPayableServiceTest {

    @Mock
    private AccountPayableService accountPayableServiceMock;
    @Spy
    @InjectMocks
    private ReportAccountPayableService reportAccountPayableServiceMock;

    private AccountPayableRepository accountPayableRepository;
    private AccountPayableService accountPayableService;
    private ReportAccountPayableService reportAccountPayableService;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.accountPayableRepository = mock(AccountPayableRepository.class);
        this.accountPayableService = new AccountPayableService(accountPayableRepository, mock(ModelMapper.class));
        this.reportAccountPayableService = new ReportAccountPayableService(accountPayableService);
    }

    private void resetarMocks() {
        reset(accountPayableServiceMock);
        reset(reportAccountPayableServiceMock);
    }

    @Test
    void testFindByIdReturnAccountPayableModel() {
        log.info("{} ", "#TEST: testFindByIdReturnAccountPayableModel: ");

        // -- 01_Cenário
        resetarMocks();
        UUID id = UUID.randomUUID();
        AccountPayableModel accountPayableModel = mock(AccountPayableModel.class);

        // -- 02_Ação
        doCallRealMethod().when(reportAccountPayableServiceMock).findById(any(UUID.class));
        when(reportAccountPayableServiceMock.findById(id)).thenReturn(Optional.of(accountPayableModel));

        // -- 03_Verificação_Validação
        assertTrue(reportAccountPayableServiceMock.findById(id).isPresent());
    }

    @Test
    void testFindAllReturnAccountPayableModelListEmpty() {
        log.info("{} ", "#TEST: testFindAllReturnAccountPayableModel: ");

        // -- 01_Cenário
        resetarMocks();
        List<AccountPayableModel> accountPayableModelList = Collections.emptyList();

        // -- 02_Ação
        doCallRealMethod().when(reportAccountPayableServiceMock).findAll();
        when(reportAccountPayableServiceMock.findAll()).thenReturn(accountPayableModelList);

        // -- 03_Verificação_Validação
        assertTrue(reportAccountPayableServiceMock.findAll().isEmpty());
        log.info("{} ", "-------------------------------------------------------------");
    }

    @Test
    void testFindAllPageableEmptyReturnAccountPayableModel() {
        log.info("{} ", "#TEST: testFindAllPageableEmptyReturnAccountPayableModel: ");

        // -- 01_Cenário
        resetarMocks();

        Pageable pageable = PageRequest.of(0, 8);
        Page<AccountPayableModel> accountPayableModelPage = new PageImpl<>(Collections.emptyList());

        // -- 02_Ação
        doCallRealMethod().when(reportAccountPayableServiceMock).findAllPageable(isA(Pageable.class));
        when(reportAccountPayableServiceMock.findAllPageable(pageable)).thenReturn(accountPayableModelPage);

        // -- 03_Verificação_Validação
        assertTrue(reportAccountPayableServiceMock.findAllPageable(pageable).isEmpty());
    }

    @Test
    void testFindAllPageableReturnAccountPayableModel() {
        log.info("{} ", "#TEST: testFindAllPageableReturnAccountPayableModel: ");

        // -- 01_Cenário
        resetarMocks();
        Pageable pageable = PageRequest.of(0, 8);

        List<AccountPayable> accountPayableList = Arrays.asList(mock(AccountPayable.class),
                mock(AccountPayable.class), mock(AccountPayable.class), mock(AccountPayable.class),
                mock(AccountPayable.class), mock(AccountPayable.class));

        Page<AccountPayable> accountPayablePage = new PageImpl<>(accountPayableList);

        // -- 02_Ação
        when(accountPayableRepository.findAll(pageable)).thenReturn(accountPayablePage);
        reportAccountPayableService.findAllPageable(pageable);

        // -- 03_Verificação_Validação
        verify(accountPayableRepository).findAll(any(Pageable.class));
    }

    @Test
    void testFindAllReturnAccountPayableModelList() {
        log.info("{} ", "#TEST: testFindAllReturnAccountPayableModelList: ");

        // -- 01_Cenário
        resetarMocks();
        List<AccountPayable> accountPayableList = Arrays.asList(mock(AccountPayable.class),
                mock(AccountPayable.class), mock(AccountPayable.class), mock(AccountPayable.class),
                mock(AccountPayable.class), mock(AccountPayable.class), mock(AccountPayable.class)
        );

        // -- 02_Ação
        when(accountPayableRepository.findAll()).thenReturn(accountPayableList);
        reportAccountPayableService.findAll();

        // -- 03_Verificação_Validação
        verify(accountPayableRepository).findAll();
    }
}
