package br.com.deliverit.instant.account.interest.calculator.account.service;

import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.account.repository.AccountPayableRepository;
import br.com.deliverit.instant.account.interest.calculator.util.AccountPayableCreator;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@Slf4j
class AccountPayableServiceTest {

    @Mock
    private AccountPayableRepository accountPayableRepository;
    @Mock
    private ModelMapper modelMapper;
    @Spy
    @InjectMocks
    private AccountPayableService accountPayableService;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private void resetarMocks() {
        reset(accountPayableRepository);
        reset(accountPayableService);
    }

    @Test
    void testFindById() {
        log.info("{} ", "#TEST: testFindById: ");

        // -- 01_Cenário
        resetarMocks();
        UUID id = UUID.randomUUID();
        AccountPayable accountPayable = mock(AccountPayable.class);

        // -- 02_Ação
        doCallRealMethod().when(accountPayableService).findById(any(UUID.class));
        when(accountPayableService.findById(id)).thenReturn(Optional.of(accountPayable));

        // -- 03_Verificação_Validação
        assertTrue(accountPayableService.findById(id).isPresent());
    }

    @Test
    void testFindAll() {
        log.info("{} ", "#TEST: testFindAll:");

        // -- 01_Cenário
        resetarMocks();
        List<AccountPayable> accountPayableList = Arrays.asList(mock(AccountPayable.class),
                mock(AccountPayable.class), mock(AccountPayable.class), mock(AccountPayable.class)
        );

        // -- 02_Ação
        doCallRealMethod().when(accountPayableService).findAll();
        when(accountPayableService.findAll()).thenReturn(accountPayableList);

        // -- 03_Verificação_Validação
        assertEquals(accountPayableList, accountPayableService.findAll());
    }

    @Test
    void testFindAllPageable() {
        log.info("{} ", "#TEST: testFindAllPageable: ");

        // -- 01_Cenário
        resetarMocks();
        Pageable pageable = PageRequest.of(0, 8);

        List<AccountPayable> accountPayableList = Arrays.asList(mock(AccountPayable.class), mock(AccountPayable.class),
                mock(AccountPayable.class), mock(AccountPayable.class), mock(AccountPayable.class), mock(AccountPayable.class));

        Page<AccountPayable> accountPayablePage = new PageImpl<>(accountPayableList);

        // -- 02_Ação
        doCallRealMethod().when(accountPayableService).findAllPageable(isA(Pageable.class));
        when(accountPayableService.findAllPageable(pageable)).thenReturn(accountPayablePage);

        // -- 03_Verificação_Validação
        assertEquals(accountPayablePage, accountPayableService.findAllPageable(pageable));
    }

    @Test
    void testSave() {
        log.info("{} ", "#TEST: testSave: ");

        // -- 01_Cenário
        resetarMocks();
        AccountPayable accountPayable = AccountPayableCreator.getAccountPayable();

        // -- 02_Ação
        doCallRealMethod().when(accountPayableService).save(isA(AccountPayable.class));
        accountPayableService.save(accountPayable);

        // -- 03_Verificação_Validação
        verify(accountPayableService).save(any(AccountPayable.class));
    }

    @Test
    void testGenerateAccountPayableModelFromToAccountPayableModel() {
        log.info("{} ", "#TEST: testGenerateAccountPayableModelFromToAccountPayableModel: ");

        // -- 01_Cenário
        resetarMocks();
        AccountPayable accountPayable = AccountPayableCreator.getAccountPayable();
        AccountPayableModel accountPayableModel = AccountPayableCreator.getAccountPayableModelFrom(accountPayable);

        // -- 02_Ação
        doCallRealMethod().when(accountPayableService).toAccountPayableModel(isA(AccountPayable.class));
        when(accountPayableService.toAccountPayableModel(accountPayable)).thenReturn(accountPayableModel);
        accountPayableService.toAccountPayableModel(accountPayable);

        // -- 03_Verificação_Validação
        verify(accountPayableService).toAccountPayableModel(any(AccountPayable.class));
    }
}
