package com.bootcamp.account.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.account.Response.CustomerResponse;
import com.bootcamp.account.dto.customerDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class webClientService {

	private final WebClient webclient;

	public webClientService(WebClient.Builder webClientBuilder) {
		this.webclient = webClientBuilder.baseUrl("http://localhost:8080/").build();
	}

	public Flux<customerDto> findCustomers() throws InterruptedException{

		Flux<customerDto> customerServiceClientResponse = this.webclient.get().uri("/customers").retrieve()
				.bodyToFlux(customerDto.class).log().doOnError(ex -> {
					throw new RuntimeException("the exception message is - " + ex.getMessage());
				});
		//long temporizador = (5 * 1000);
		//Thread.sleep(temporizador);

		return customerServiceClientResponse;

		//return this.webclient.get().uri("/customers").retrieve().bodyToFlux(customerDto.class);

	}

	public Flux<customerDto> findCustomersByID(String id) throws InterruptedException {
		Flux<customerDto> customerServiceClientResponse = this.webclient.get().uri("/customers/{id}", id).retrieve()
				.bodyToFlux(customerDto.class).log().doOnError(ex -> {
					throw new RuntimeException("the exception message is - " + ex.getMessage());
				});
		return customerServiceClientResponse;

	}
}
