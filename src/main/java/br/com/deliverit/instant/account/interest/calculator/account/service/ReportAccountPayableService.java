package br.com.deliverit.instant.account.interest.calculator.account.service;

import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportAccountPayableService implements IReportAccountPayableService {

    private final IAccountPayableService accountPayableService;

    @Override
    public Optional<AccountPayableModel> findById(UUID id) {
        return this.accountPayableService.findById(id).map(this::toAccountPayableModel);
    }

    @Override
    public List<AccountPayableModel> findAll() {
        List<AccountPayable> accountPayableList = this.accountPayableService.findAll();
        return (accountPayableList.isEmpty()) ? Collections.emptyList() : getListAccountPayableModelFrom(accountPayableList);
    }

    @Override
    public Page<AccountPayableModel> findAllPageable(Pageable pageable) {
        Page<AccountPayable> accountPayablePage = this.accountPayableService.findAllPageable(pageable);
        return (Objects.isNull(accountPayablePage) || accountPayablePage.isEmpty())
                ? new PageImpl<>(Collections.emptyList())
                : getPageAccountPayableModel(accountPayablePage, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
    }

    private List<AccountPayableModel> getListAccountPayableModelFrom(List<AccountPayable> accountPayableList) {
        return accountPayableList.stream().map(this::toAccountPayableModel).collect(Collectors.toList());
    }

    private Page<AccountPayableModel> getPageAccountPayableModel(Page<AccountPayable> accountPayablePage, int page,
                                                                 int size, Sort sort) {
        List<AccountPayableModel> modelList = accountPayablePage.stream().map(this::toAccountPayableModel).collect(Collectors.toList());
        return new PageImpl<>(modelList, PageRequest.of(page, size, sort), modelList.size());
    }

    private AccountPayableModel toAccountPayableModel(AccountPayable accountPayable) {
        return this.accountPayableService.toAccountPayableModel(accountPayable);
    }
}
