package com.bootcamp.account.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.bootcamp.account.Model.document.Account;
import com.bootcamp.account.Model.document.TypeAccount;
import com.bootcamp.account.Model.repository.AccountRepository;
import com.bootcamp.account.Model.service.AccountService;
import com.bootcamp.account.api.AccountController;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Validated
@Service
public class AccountServiceImp implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountEventsService accountEventsService;
	
	@Override
	public Flux<Account> getAll() throws Exception {
		return this.accountRepository.findAll();
	}

	@Override
	public Mono<Account> save(Account account) {
		System.out.println("Received " + account);
		this.accountEventsService.created(account);
		return this.accountRepository.save(account);
	}

	@Override
	public Mono<Account> findById(String documentNumber) {
		return this.accountRepository.findById(documentNumber);
	}

	@Override
	public Mono<Boolean> existsById(String documentNumber) {
		return this.accountRepository.existsById(documentNumber);
	}

	@Override
	public Mono<Void> deleteById(String id) throws Exception {
		System.out.println("Received " + id);
		this.accountEventsService.deleted(id);
		return this.accountRepository.deleteById(id);
	}


	@Override
	public Mono<Account> update(String documentNumber,Account account) {
	    return this.accountRepository.findById(documentNumber)
	            .flatMap(existingAccount-> accountRepository.save(account));
	}

	@Override
	public Flux<Account> findByDocumentNumber(String documentNumber) throws Exception {
		return this.accountRepository.findByDocumentNumber(documentNumber);
	}

	@Override
	public Mono<Void> delete(Account account) {
		return this.accountRepository.delete(account);
	}

}