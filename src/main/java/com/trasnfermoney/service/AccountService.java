package com.trasnfermoney.service;

import java.util.List;

import com.trasnfermoney.model.Account;
import com.trasnfermoney.model.Transfer;
import com.trasnfermoney.model.TransferType;

public interface AccountService {

	/**
	 * Register a new account with a initialize amount and create a new user if isn't exist
	 * @param account
	 * @param owner
	 * @param amount
	 */
	public void register(long account, long owner, float amount);
	
	/**
	 * Make a transfer between two account, valid if the account exist and 
	 * account shouldn’t be able to have a balance below $-500
	 * @param fromAccount
	 * @param toAccount
	 * @param amount
	 */
	public void transfer(long fromAccount, long toAccount, float amount);
	
	/**
	 * Get all transactions
	 * @param userId
	 * @param accountId
	 * @return List of transfers
	 */
	public List<Transfer> getTransfers(long userId, long accountId);
	
	/**
	 * Get all transactions of a type
	 * @param userId
	 * @param accountId
	 * @param type
	 * @return List of transfer
	 */
	public List<Transfer> getTransfers(long userId, long accountId, TransferType type);
	
	/**
	 * Get the account of a user
	 * @param userId
	 * @param accountId
	 * @return Account with all the data of the account
	 */
	public Account getBalance(long userId, long accountId);
	
}
