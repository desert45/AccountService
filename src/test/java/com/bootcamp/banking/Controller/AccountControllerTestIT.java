package com.bootcamp.banking.Controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bootcamp.AccountApplication;
import com.bootcamp.account.Model.document.Account;
import com.bootcamp.account.Model.document.TypeAccount;
import com.bootcamp.account.Model.service.AccountService;
import com.bootcamp.account.Util.AccountValidations;

import ch.qos.logback.core.subst.Token.Type;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

//@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
//@WebMvcTest({AccountControllerTestIT.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTestIT {

	@Autowired
	private WebTestClient accountServiceImp;
	@Autowired
	private AccountService service;
	
	// @Autowired
	// private MockMvc mockMvc;

	public AccountControllerTestIT() {
	}

	@Test
	void getAccount() throws Exception {
		accountServiceImp.get().uri("/accounts").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
				.expectBodyList(Account.class);
	}

	@Test
	void getAccountById() throws Exception {
		accountServiceImp.get().uri("/accounts/62ec35dc29944b2801de904f").accept(MediaType.APPLICATION_JSON).exchange()
				.expectStatus().isOk().expectBodyList(Account.class);
	}
	
	@Test
	void createAccount() throws Exception {
		
		List<TypeAccount> type = (List<TypeAccount>) new TypeAccount("1", null, null, null);
		Account account = new Account("62f43ffe4674c10151d3d3bf","71893700", "1234567890",type);
		
		accountServiceImp.post().uri("/accounts")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.body(Mono.just(account), Account.class)
		.exchange()
		.expectStatus().isCreated();
	}
	
	@Test
	public void eliminarTest() throws Exception {
		Account account = service.findById("62f43ffe4674c10151d3d3bf").block();
		//Account account = new Account();
		accountServiceImp.delete()
		.uri("accounts/{id}", Collections.singletonMap("id", account.getId()))
		.exchange()
		.expectStatus().isNoContent()
		.expectBody()
		.isEmpty();
		
		
	}

}
