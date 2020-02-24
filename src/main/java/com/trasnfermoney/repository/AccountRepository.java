package com.trasnfermoney.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.trasnfermoney.model.Account;

@Repository
public class AccountRepository {

	private static long MAX_ACCOUNT_ID;
	public static Map<Long, Account> ACCOUNTS;
	
	static {
		MAX_ACCOUNT_ID=1;
		ACCOUNTS = new HashMap<>();
	}
	
	public Account saveAccount(Account account) {
		if(account.getNumberAccount()==null || account.getNumberAccount()==0) {
			account = this.saveNewAccount(account);
		}else {
			if(ACCOUNTS.get(account.getNumberAccount()) == null) {
				ACCOUNTS.put(account.getNumberAccount(), account);
			}
		}
		
		return ACCOUNTS.get(account.getNumberAccount());
	}
	
	public Account saveNewAccount(Account account) {
		account.setNumberAccount(MAX_ACCOUNT_ID++);
		ACCOUNTS.put(MAX_ACCOUNT_ID-1, account);
		return account;
	}
	
	public Optional<Account> getAccount(Long accountId) {
		return Optional.ofNullable(ACCOUNTS.get(accountId));
	}
}
