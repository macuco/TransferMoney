package com.trasnfermoney.dto;

import java.io.Serializable;
import java.util.Date;

public class TransferDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5689381918041501489L;
	private long fromAccount;
	private long toAccount;
	private float amount;
	private Date sentAt;
	
	public TransferDTO() {}
	
	
	public TransferDTO(long fromAccount, long toAccount, float amount, Date sentAt) {
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
		this.sentAt = sentAt;
	}


	public long getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(long fromAccount) {
		this.fromAccount = fromAccount;
	}
	public long getToAccount() {
		return toAccount;
	}
	public void setToAccount(long toAccount) {
		this.toAccount = toAccount;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Date getSentAt() {
		return sentAt;
	}
	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}
	
}
