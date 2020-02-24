package com.trasnfermoney.dto;

import java.io.Serializable;

public class BalanceDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6708086853205652887L;
	private AccountDTO balance;
	
	public BalanceDTO() {}
	public BalanceDTO(AccountDTO account) {
		this.balance = account;
	}

	public AccountDTO getBalance() {
		return balance;
	}

	public void setBalance(AccountDTO balance) {
		this.balance = balance;
	}
	
	
}
