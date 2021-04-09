package br.com.deliverit.instant.account.interest.calculator.controller.api;

import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableModel;
import br.com.deliverit.instant.account.interest.calculator.account.dto.AccountPayableRequest;
import br.com.deliverit.instant.account.interest.calculator.account.exceptions.AccountPayableNotFoundException;
import br.com.deliverit.instant.account.interest.calculator.account.service.IGenerateAccountPayableService;
import br.com.deliverit.instant.account.interest.calculator.account.service.IReportAccountPayableService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniel Santos
 * @since 28/03/2021
 */
@RestController
@RequestMapping("/v1/accounts")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class AccountPayableController {

    private final IGenerateAccountPayableService generateAccountPayableService;
    private final IReportAccountPayableService reportAccountPayableService;

    @GetMapping
    @ApiOperation(value = "Returns a list of all accounts payable.", tags = "accounts")
    public ResponseEntity<List<AccountPayableModel>> getAll() {
        log.info("Searching all existing Accounts Payable(s)...");
        log.info("Returning list of account(s) payable...");
        return new ResponseEntity<>(reportAccountPayableService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page")
    @ApiOperation(value = "Returns a list of all accounts payable pageable.", tags = "accounts")
    public ResponseEntity<Page<AccountPayableModel>> getAllPageable(@PageableDefault(page = 0, size = 10, sort = "payDay",
            direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("Searching all existing Accounts Payable(s)...");
        log.info("Returning list of account(s) payable pageable...");
        return new ResponseEntity<>(reportAccountPayableService.findAllPageable(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns a specific account payable by id", tags = "accounts")
    public ResponseEntity<AccountPayableModel> findById(@PathVariable("id") UUID id) {
        log.info("Searching for an existing accounts payable by Id ...");
        log.info("Returning a specific Account Payable by {}.", id);
        return new ResponseEntity<>(reportAccountPayableService.findById(id)
                .orElseThrow(() -> new AccountPayableNotFoundException(id.toString(), id)), HttpStatus.OK);
    }

    @ApiOperation(value = "Performs the vote on the specified account payable.", tags = "accounts")
    @PostMapping
    public ResponseEntity<AccountPayableModel> save(@Valid @RequestBody AccountPayableRequest accountPayableRequest) {
        return new ResponseEntity<>(generateAccountPayableService.create(generateAccountPayableService.toAccountPayableFrom(accountPayableRequest)), HttpStatus.CREATED);
    }
}
