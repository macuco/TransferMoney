package com.trasnfermoney.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.trasnfermoney.dto.AccountDTO;
import com.trasnfermoney.dto.BalanceDTO;
import com.trasnfermoney.dto.TransactionsDTO;
import com.trasnfermoney.dto.TransferDTO;
import com.trasnfermoney.exception.BussinesLogicException;
import com.trasnfermoney.model.Account;
import com.trasnfermoney.model.Transfer;
import com.trasnfermoney.model.TransferType;
import com.trasnfermoney.service.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping("/create")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void registrar( @RequestBody AccountDTO accountDTO) {
		this.accountService.register(accountDTO.getAccount(), accountDTO.getOwner(), accountDTO.getBalance());
	}
	
	@GetMapping("/{userId}/accounts/{accountId}/transactions/all")
	public TransactionsDTO getAll(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId) {
		try {
			List<Transfer> transfers = this.accountService.getTransfers(userId, accountId);
			List<TransferDTO> transfersDTO = transfers.stream().map(t->new TransferDTO(t.getFromAccount().getNumberAccount(), t.getToAccount().getNumberAccount(), t.getAmount(), t.getSendAt())).collect(Collectors.toList());
			TransactionsDTO transactions = new TransactionsDTO();
			transactions.getTransactions().addAll(transfersDTO);
			return transactions;
		}catch(BussinesLogicException ble) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found user or account", ble);
		}
	}
	
	@GetMapping("/{userId}/accounts/{accountId}/transactions/received")
	public TransactionsDTO getReceived(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId) {
		try {
			List<Transfer> transfers = this.accountService.getTransfers(userId, accountId, TransferType.RECEIVED);
			List<TransferDTO> transfersDTO = transfers.stream().map(t->new TransferDTO(t.getFromAccount().getNumberAccount(), t.getToAccount().getNumberAccount(), t.getAmount(), t.getSendAt())).collect(Collectors.toList());
			TransactionsDTO transactions = new TransactionsDTO();
			transactions.getTransactions().addAll(transfersDTO);
			return transactions;
		}catch(BussinesLogicException ble) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found user or account", ble);
		}
	}
	
	@GetMapping("/{userId}/accounts/{accountId}/transactions/sent")
	public TransactionsDTO getSend(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId) {
		try {
			List<Transfer> transfers = this.accountService.getTransfers(userId, accountId, TransferType.SENT);
			List<TransferDTO> transfersDTO = transfers.stream().map(t->new TransferDTO(t.getFromAccount().getNumberAccount(), t.getToAccount().getNumberAccount(), t.getAmount(), t.getSendAt())).collect(Collectors.toList());
			TransactionsDTO transactions = new TransactionsDTO();
			transactions.getTransactions().addAll(transfersDTO);
			return transactions;
		}catch(BussinesLogicException ble) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found user or account", ble);
		}
	}
	
	@PostMapping("/transfer")
	@ResponseStatus(code = HttpStatus.OK)
	public void transfer( @RequestBody TransferDTO transfer) {
		try {
			this.accountService.transfer(transfer.getFromAccount(), transfer.getToAccount(), transfer.getAmount());
		}catch(BussinesLogicException ble) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient balance for the account "+transfer.getFromAccount(), ble);
		}
	}
	
	
	@GetMapping("/{userId}/accounts/{accountId}/balance")
	@ResponseStatus(code = HttpStatus.OK)
	public BalanceDTO getBanlance(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId) {
		try {
			Account account = this.accountService.getBalance(userId, accountId);
			BalanceDTO balanceDTO = new BalanceDTO(new AccountDTO(account.getNumberAccount(), account.getBalance(), account.getOwner().getId(), account.getCreatedAt()));
			return balanceDTO;
		}catch(BussinesLogicException ble) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account or User Not Found", ble);
		}
	}
}
