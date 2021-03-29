package br.com.deliverit.instant.account.interest.calculator.account.service;

import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IReportAccountPayableService {

    Optional<AccountPayableModel> findById(UUID id);

    List<AccountPayableModel> findAll();

    Page<AccountPayableModel> findAllPageable(Pageable pageable);
}
