package br.com.deliverit.instant.account.interest.calculator.account.service;


import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import br.com.deliverit.instant.account.interest.calculator.account.repository.AccountPayableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountPayableService implements IAccountPayableService {

    private final AccountPayableRepository accountPayableRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<AccountPayable> findById(UUID id) {
        return accountPayableRepository.findById(id);
    }

    public List<AccountPayable> findAll() {
        return this.accountPayableRepository.findAll();
    }

    @Override
    public Page<AccountPayable> findAllPageable(Pageable pageable) {
        return this.accountPayableRepository.findAll(pageable);
    }

    @Override
    @Transactional(value = Transactional.TxType.NEVER)
    public AccountPayable save(AccountPayable accountPayable) {
        return this.accountPayableRepository.save(accountPayable);
    }

    @Override
    public AccountPayableModel toAccountPayableModel(AccountPayable accountPayable) {
        return this.modelMapper.map(accountPayable, AccountPayableModel.class);
    }
}
