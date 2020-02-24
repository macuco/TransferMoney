package com.trasnfermoney.dto;

import java.io.Serializable;
import java.util.Date;

public class AccountDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2393804937408961198L;
	private long account;
	private float balance;
	private long owner;
	private Date createdAt;
	
	public AccountDTO() {}
	
	public AccountDTO(long account, float balance, long owner, Date creadtedAt ) {
		this.account = account;
		this.balance = balance;
		this.owner = owner;
		this.createdAt = creadtedAt;
	}
	
	public long getAccount() {
		return account;
	}
	public void setAccount(long account) {
		this.account = account;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	

}
