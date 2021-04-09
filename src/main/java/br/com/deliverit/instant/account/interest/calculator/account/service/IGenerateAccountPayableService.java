package br.com.deliverit.instant.account.interest.calculator.account.service;

import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableRequest;
import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;

public interface IGenerateAccountPayableService {

    AccountPayableModel create(AccountPayable accountPayable);

    AccountPayable toAccountPayableFrom(AccountPayableRequest accountPayableRequest);

    void validateParams(AccountPayable accountPayable);
}
