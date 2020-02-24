package com.trasnfermoney.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
	
	private Long numberAccount;
	private Float balance;
	
	private User owner;
	private Date createdAt;
	private List<Transfer> transfers = new ArrayList<>();
	
	public Account() {}

	
	public Account(Long numberAccount) {
		this.numberAccount = numberAccount;
	}
	
	public Account(Long numberAccount, Float balance, Date createdAt, User owner) {
		this.numberAccount = numberAccount;
		this.balance = balance;
		this.createdAt = createdAt;
		this.owner = owner;
	}
	

	public Long getNumberAccount() {
		return numberAccount;
	}

	public void setNumberAccount(Long numberAccount) {
		this.numberAccount = numberAccount;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<Transfer> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}
	
		
	
}
