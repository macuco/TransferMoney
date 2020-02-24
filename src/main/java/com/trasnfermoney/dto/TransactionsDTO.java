package com.trasnfermoney.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransactionsDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5824032509825915988L;
	private List<TransferDTO> transactions;
	
	public TransactionsDTO() {
		this.transactions = new ArrayList<>();
	}

	public List<TransferDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransferDTO> transactions) {
		this.transactions = transactions;
	}
	
	

}
