package com.example.demo.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.pojo.Customer;
import com.example.demo.service.BankService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bankManagement")
public class BankController {
//	@Autowired
//	BankService service;

	WebClient webClient;

	@PostConstruct
	public void init() {
		webClient = WebClient.builder().baseUrl("http://localhost:9091")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

	}

	@PostMapping("/insertCustomer")
	public Mono<String> bankAdmin(@RequestBody Customer customer) {
		return webClient.post().uri("/createCustomer").syncBody(customer).retrieve().bodyToMono(String.class);

	}

	@GetMapping("/allCustomerDetails")
	public Flux<Customer> getAllCustomer() {
		return webClient.get().uri("/customer").retrieve().bodyToFlux(Customer.class);

	}

	@GetMapping("/customerById/{id}")
	public Mono<Customer> getAllCustomerById(@PathVariable int id) {
		return webClient.get().uri("/findCustomerById/{id}" + id).retrieve().bodyToMono(Customer.class);
	}

	@DeleteMapping("/removeCustomer/{id}")
	public Mono<String> deleteCustomer(@PathVariable int id) {
		return webClient.delete().uri("/deleteCustomer/{id}" + id).retrieve().bodyToMono(String.class);
	}

	@PutMapping("/changeCustomer/{id}")
	public Mono<Customer> updateCustomer(@PathVariable int id, @PathVariable Customer customer) {
		return webClient.put().uri("/updateCustomer" + id).syncBody(customer).retrieve().bodyToMono(Customer.class);
	}

}
