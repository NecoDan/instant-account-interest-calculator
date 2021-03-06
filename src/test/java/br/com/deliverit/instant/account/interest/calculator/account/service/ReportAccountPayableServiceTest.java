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

        // -- 01_Cen??rio
        resetarMocks();
        UUID id = UUID.randomUUID();
        AccountPayableModel accountPayableModel = mock(AccountPayableModel.class);

        // -- 02_A????o
        doCallRealMethod().when(reportAccountPayableServiceMock).findById(any(UUID.class));
        when(reportAccountPayableServiceMock.findById(id)).thenReturn(Optional.of(accountPayableModel));

        // -- 03_Verifica????o_Valida????o
        assertTrue(reportAccountPayableServiceMock.findById(id).isPresent());
    }

    @Test
    void testFindAllReturnAccountPayableModelListEmpty() {
        log.info("{} ", "#TEST: testFindAllReturnAccountPayableModel: ");

        // -- 01_Cen??rio
        resetarMocks();
        List<AccountPayableModel> accountPayableModelList = Collections.emptyList();

        // -- 02_A????o
        doCallRealMethod().when(reportAccountPayableServiceMock).findAll();
        when(reportAccountPayableServiceMock.findAll()).thenReturn(accountPayableModelList);

        // -- 03_Verifica????o_Valida????o
        assertTrue(reportAccountPayableServiceMock.findAll().isEmpty());
        log.info("{} ", "-------------------------------------------------------------");
    }

    @Test
    void testFindAllPageableEmptyReturnAccountPayableModel() {
        log.info("{} ", "#TEST: testFindAllPageableEmptyReturnAccountPayableModel: ");

        // -- 01_Cen??rio
        resetarMocks();

        Pageable pageable = PageRequest.of(0, 8);
        Page<AccountPayableModel> accountPayableModelPage = new PageImpl<>(Collections.emptyList());

        // -- 02_A????o
        doCallRealMethod().when(reportAccountPayableServiceMock).findAllPageable(isA(Pageable.class));
        when(reportAccountPayableServiceMock.findAllPageable(pageable)).thenReturn(accountPayableModelPage);

        // -- 03_Verifica????o_Valida????o
        assertTrue(reportAccountPayableServiceMock.findAllPageable(pageable).isEmpty());
    }

    @Test
    void testFindAllPageableReturnAccountPayableModel() {
        log.info("{} ", "#TEST: testFindAllPageableReturnAccountPayableModel: ");

        // -- 01_Cen??rio
        resetarMocks();
        Pageable pageable = PageRequest.of(0, 8);

        List<AccountPayable> accountPayableList = Arrays.asList(mock(AccountPayable.class),
                mock(AccountPayable.class), mock(AccountPayable.class), mock(AccountPayable.class),
                mock(AccountPayable.class), mock(AccountPayable.class));

        Page<AccountPayable> accountPayablePage = new PageImpl<>(accountPayableList);

        // -- 02_A????o
        when(accountPayableRepository.findAll(pageable)).thenReturn(accountPayablePage);
        reportAccountPayableService.findAllPageable(pageable);

        // -- 03_Verifica????o_Valida????o
        verify(accountPayableRepository).findAll(any(Pageable.class));
    }

    @Test
    void testFindAllReturnAccountPayableModelList() {
        log.info("{} ", "#TEST: testFindAllReturnAccountPayableModelList: ");

        // -- 01_Cen??rio
        resetarMocks();
        List<AccountPayable> accountPayableList = Arrays.asList(mock(AccountPayable.class),
                mock(AccountPayable.class), mock(AccountPayable.class), mock(AccountPayable.class),
                mock(AccountPayable.class), mock(AccountPayable.class), mock(AccountPayable.class)
        );

        // -- 02_A????o
        when(accountPayableRepository.findAll()).thenReturn(accountPayableList);
        reportAccountPayableService.findAll();

        // -- 03_Verifica????o_Valida????o
        verify(accountPayableRepository).findAll();
    }
}
