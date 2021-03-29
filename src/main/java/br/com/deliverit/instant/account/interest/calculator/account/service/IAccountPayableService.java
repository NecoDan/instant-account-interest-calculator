package br.com.deliverit.instant.account.interest.calculator.account.service;


import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountPayableService {

    Optional<AccountPayable> findById(UUID id);

    List<AccountPayable> findAll();

    Page<AccountPayable> findAllPageable(Pageable pageable);

    AccountPayable save(AccountPayable accountPayable);

    AccountPayableModel toAccountPayableModel(AccountPayable accountPayable);
}
