package com.trasnfermoney.model;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Transfer {
	
	@NotNull
	private Account fromAccount;
	@NotNull
	private Account toAccount;
	@Min(1)
	@NotNull
	private Float amount;
	private Date sendAt;
	private TransferType type;

	public Transfer() {}
	
	public Transfer(Account from, Account to, Float amount, TransferType type, Date sendAt) {
		this.fromAccount = from;
		this.toAccount = to;
		this.amount = amount;
		this.type = type;
		this.sendAt = sendAt;
	}
	
	public Account getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}
	public Account getToAccount() {
		return toAccount;
	}
	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	public Date getSendAt() {
		return sendAt;
	}

	public void setSendAt(Date sendAt) {
		this.sendAt = sendAt;
	}

	public TransferType getType() {
		return type;
	}

	public void setType(TransferType type) {
		this.type = type;
	}
	
	

}
