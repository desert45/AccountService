package com.bootcamp.account.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.account.Model.document.Account;
import com.bootcamp.account.Model.document.TypeAccount;
import com.bootcamp.account.Model.service.AccountService;
import com.bootcamp.account.Response.CustomerResponse;
import com.bootcamp.account.Service.webClientService;
import com.bootcamp.account.Util.AccountValidations;
import com.bootcamp.account.dto.customerDto;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller for accounts
 * 
 * @author aafernandez
 *
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private webClientService webClientService;

	@Autowired
	private AccountValidations accountValidations;

	private String documentNumber;

	List<TypeAccount> listTypeAccountsFinal = new ArrayList<>();

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping()
	@Operation(summary = "Get list of account")
	public ResponseEntity<Flux<Account>> getAccount() throws Exception {
		Flux<Account> response = accountService.getAll();
		return new ResponseEntity<Flux<Account>>(response, HttpStatus.OK);

	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping(path = { "{id}" })
	@Operation(summary = "Get list of account by ID")
	public Mono<Account> getAccountByID(@PathVariable("id") String id) throws Exception {
		return this.accountService.findById(id);
		// return new Mono<Account>(response, HttpStatus.OK);
	}

	@GetMapping(path = { "/document/{documentNumber}" })
	@Operation(summary = "Get list of account by document number")
	public Flux<Account> getAccountByDocumentNumber(@PathVariable("documentNumber") String documentNumber)
			throws Exception {
		return this.accountService.findByDocumentNumber(documentNumber);
		// return new Mono<Account>(response, HttpStatus.OK);
	}

	/**
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	@Operation(summary = "Register new account")
	public Mono<Account> register(@RequestBody Account account) throws Exception {

		List<customerDto> customerList = new ArrayList<>();
		List<TypeAccount> typeAccounts = new ArrayList<>();
		List<Account> accountRegistred = new ArrayList<>();
		long temporizador = (3 * 1000);

		// Validar si existe el cliente
		Flux<customerDto> customerResponse = findCustomersbyID(account.getDocumentNumber());
		customerResponse.collectList().subscribe(customerList::addAll);
		Thread.sleep(temporizador);

		if (customerList.isEmpty()) {
			documentNumber = "";
		} else {
			documentNumber = customerList.get(0).getDocumentNumber();
		}

		log.info("Validar Codigo Repetido --->" + documentNumber);

		if (documentNumber.equals("")) {
			return Mono.error(new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "El Usuario no existe"));
		}

		// Validacion del cliente con sus cuentas
		Flux<Account> AccountResponse = getAccountByDocumentNumber(account.getDocumentNumber());
		AccountResponse.collectList().subscribe(accountRegistred::addAll);
		Thread.sleep(temporizador);

		boolean existAccountCustomer = this.accountValidations.ValidationAccountCustomer(accountRegistred, account);

		if (existAccountCustomer) {
			return Mono.error(new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
					"El usuario y el tipo de cuenta seleccionado ya existen"));
		}

		// Obtiene los tipos de cuentas con sus caracteristicas segun el tipo ingresado
		typeAccounts = this.accountValidations.comunicationWebClientObtenerTypeBankAccountsMock();

		// Carga el objeto tipo de cuentas con los nuevos valores obtenidos
		listTypeAccountsFinal = this.accountValidations.validacionTypeBankAccounts(typeAccounts, account);

		if (listTypeAccountsFinal.isEmpty()) {
			return Mono.error(
					new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Ingrese un tipo de cuenta valido"));
		}

		return accountService.save(account);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(path = { "{id}" })
	@Operation(summary = "Delete a account by Id")
	public Mono<ResponseEntity<Account>> delete(@PathVariable("id") String id) throws Exception {
		return accountService.findById(id).flatMap(existingCustomer-> {
			return accountService.delete(existingCustomer).then(Mono.just(new ResponseEntity<Account>(HttpStatus.NO_CONTENT)));})
				.defaultIfEmpty(new ResponseEntity<Account>(HttpStatus.NOT_FOUND));
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PutMapping(path = { "{id}" }, produces = { "application/json" })
	@Operation(summary = "Update a account by Id")
	public Mono<Account> update(@PathVariable("id") String id, @Valid @RequestBody Account account) throws Exception {
		return accountService.update(id, account);
	}

	/**
	 * 
	 * @param documentNumber
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping(value = "/customers/{documentNumber}")
	@Operation(summary = "Find Customer by ID")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Flux<customerDto> findCustomersbyID(@PathVariable("documentNumber") String documentNumber)
			throws InterruptedException {
		Flux<customerDto> customerResponse = this.webClientService.findCustomersByID(documentNumber);
		return customerResponse;
	}

}
