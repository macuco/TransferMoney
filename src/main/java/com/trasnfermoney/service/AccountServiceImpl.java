package com.trasnfermoney.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trasnfermoney.exception.BussinesLogicException;
import com.trasnfermoney.model.Account;
import com.trasnfermoney.model.Transfer;
import com.trasnfermoney.model.TransferType;
import com.trasnfermoney.model.User;
import com.trasnfermoney.repository.AccountRepository;
import com.trasnfermoney.repository.UserRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void register(long accountId, long ownerId, float amount) {
		User owner = null;
		if(ownerId == 0) {
			owner = userRepo.saveNewUser(new User());
		}else {
			owner = userRepo.getUser(new Long(ownerId)).orElse(userRepo.saveUser(new User(ownerId)));
		}
		Account account = new Account(accountId, amount, new Date(), owner);
		this.accountRepo.saveAccount(account);
		owner.getAccounts().add(account);
	}

	@Override
	public void transfer(long fromAccount, long toAccount, float amount) {
		
		Account from = this.accountRepo.getAccount(new Long(fromAccount)).orElseThrow(()->new BussinesLogicException("Not from account "+fromAccount));
		Account to = this.accountRepo.getAccount(new Long(toAccount)).orElseThrow(()->new BussinesLogicException("Not to account "+toAccount));
		
		if((from.getBalance()-amount)<-500) {
			throw new BussinesLogicException("Balance shouldn't be able below $-500");
		}
		
		from.setBalance(from.getBalance()-amount);
		to.setBalance(to.getBalance()+amount);
		
		Date currentDate = new Date();
		
		from.getTransfers().add(new Transfer(from, to,amount, TransferType.SENT, currentDate));
		to.getTransfers().add(new Transfer(from, to, amount, TransferType.RECEIVED, currentDate));
		
	}

	@Override
	public List<Transfer> getTransfers(long userId, long accountId) {
		User user = this.userRepo.getUser(new Long(userId)).orElseThrow(()->new BussinesLogicException("User not found"));
		Account account = user.getAccounts().stream().filter(a->a.getNumberAccount().equals(accountId)).findFirst().orElseThrow(()->new BussinesLogicException("Account not found"));
		return account.getTransfers(); 
	}

	@Override
	public List<Transfer> getTransfers(long userId, long accountId, TransferType type) {
		User user = this.userRepo.getUser(new Long(userId)).orElseThrow(()->new BussinesLogicException("User not found"));
		Account account = user.getAccounts().stream().filter(a->a.getNumberAccount().equals(accountId)).findFirst().orElseThrow(()->new BussinesLogicException("Account not found"));
		return account.getTransfers().stream().filter(a->a.getType()==type).collect(Collectors.toList());
	}

	@Override
	public Account getBalance(long userId, long accountId) {
		User user = this.userRepo.getUser(new Long(userId)).orElseThrow(()->new BussinesLogicException("User not found"));
		Account account = user.getAccounts().stream().filter(a->a.getNumberAccount().equals(accountId)).findFirst().orElseThrow(()->new BussinesLogicException("Account not found"));
		return account;
	}

}
