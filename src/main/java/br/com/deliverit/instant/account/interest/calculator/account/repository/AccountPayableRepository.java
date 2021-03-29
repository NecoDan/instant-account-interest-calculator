package br.com.deliverit.instant.account.interest.calculator.account.repository;

import br.com.deliverit.instant.account.interest.calculator.account.model.AccountPayable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountPayableRepository extends JpaRepository<AccountPayable, UUID> {

}
