package com.sunman.org.restfulwebservices.socialmedia;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Size;

@JsonFilter("AccountFilter") //Name of filter used to Hide the fields 
public class Account {
	
	
	private Integer id;
	
	@Size(min = 5, max = 10, message = "Account number should be 5-10 characters")
	@JsonProperty("account-number")
	private int number;
	
	@JsonProperty("account-type")
	private String type;
	
	@JsonProperty("account-holder-name")
	private String holder_name;
	
	@JsonProperty("account-password")
	private String password;
	

	public Account(Integer id, int number, String type, String holder_name, String password) {
		super();
		this.id = id;
		this.number = number;
		this.type = type;
		this.holder_name = holder_name;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHolder_name() {
		return holder_name;
	}

	public void setHolder_name(String holder_name) {
		this.holder_name = holder_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", number=" + number + ", type=" + type + ", holder_name=" + holder_name
				+ ", password=" + password + "]";
	}
}
