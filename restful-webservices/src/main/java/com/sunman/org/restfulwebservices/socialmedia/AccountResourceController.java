package com.sunman.org.restfulwebservices.socialmedia;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class AccountResourceController {

	
	private AccountDaoService service;
	
	public AccountResourceController(AccountDaoService service) {
		this.service = service;
	}
	
	@GetMapping("/accounts")
	public List<Account> getAllAccounts() {
		return service.getAllAccounts();
	}
	
	@GetMapping("/accounts/{id}")
	public Account getAccount(@PathVariable Integer id) {
		return service.getAccount(id);
	}
	
	@GetMapping("/accounts/filtering/{id}") //Hiding password in JSON Response.
	public MappingJacksonValue getAccountWithFilteringPassword(@PathVariable Integer id) {
		Account account =  service.getAccount(id);
		
		//MappingJacksonValue is used Serialization logic data to send the response back.
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(account); //Want to do filtering on account object by mapping
		
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("account-number", "account-type", "account-holder-name" ,"id"); // How you want to filter
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("AccountFilter", filter);
		
		mappingJacksonValue.setFilters(filters); //Add multiple filers to mapping
		
		return mappingJacksonValue;
		
	}
	
	@GetMapping("/accounts/filtering")
	public MappingJacksonValue getAccountWithFilteringList() {
		List<Account> accountList = Arrays.asList(new Account(12, 25345, "Savings", "Sunetra", "a1bc"),new Account(15, 2455345, "FD", "Advait", "ddk")); 
		
		//MappingJacksonValue is used Serialization logic data to send the response back.
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(accountList); //Want to do filtering on list of multiple accounts object by mapping
		
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "account-number", "account-holder-name"); // How you want to filter
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("PasswordFilter", filter);
		
		mappingJacksonValue.setFilters(filters); //Add multiple filers to mapping
		
		return mappingJacksonValue;
		
	}
	
	
}
