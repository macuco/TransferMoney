package com.trasnfermoney.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private Long id;
	private List<Account> accounts = new ArrayList<>();
	
	public User() {}

	public User(Long id) {
		this.id = id;
		this.accounts = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	
	
	
}
