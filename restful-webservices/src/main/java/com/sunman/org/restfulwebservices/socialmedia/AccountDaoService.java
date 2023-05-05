package com.sunman.org.restfulwebservices.socialmedia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AccountDaoService {
	
	private static List<Account> accounts = new ArrayList<Account>();

	private static int accountCount = 0;

	static {
		accounts.add(new Account(++accountCount, 25345, "Savings", "Manjiri", "abc"));
		accounts.add(new Account(++accountCount, 4568745, "Checkin", "Shashank", "xyz"));
		accounts.add(new Account(++accountCount, 9855456, "RD", "Vedant", "pqr"));
	}

	public List<Account> getAllAccounts() {
		return accounts;
	}

	public Account getAccount(Integer id) {
		for (int i = 0; i < accounts.size(); i++) {
			if(accounts.get(i).getId().equals(id)) {
				return accounts.get(i);
			}
		}
		return null;
	}
}
