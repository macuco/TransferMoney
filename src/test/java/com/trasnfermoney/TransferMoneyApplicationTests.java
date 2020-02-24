package com.trasnfermoney;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trasnfermoney.dto.BalanceDTO;
import com.trasnfermoney.dto.TransactionsDTO;
import com.trasnfermoney.dto.TransferDTO;

@RunWith(JUnit4.class)
@SpringBootTest
@AutoConfigureMockMvc
class TransferMoneyApplicationTests {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    ObjectMapper objectmapper;
	
	private String apiRootPath = "http://localhost:8080/api/";
	
	
	@Test
	void trasnfer() throws Exception {
		
		Long userId = 12345L;
		Long accountId1 = 1000001L;
		Long accountId2 = 1000003L;
		Float amount = 500F;
		
		// Get the balance
		MvcResult result1 = this.mockMvc.perform(get(apiRootPath+"/"+userId+"/accounts/"+accountId1+"/balance"))
				.andReturn();
		BalanceDTO balance1 = objectmapper.readValue(result1.getResponse().getContentAsString(), new TypeReference<BalanceDTO>() {});
		
		MvcResult result3 = this.mockMvc.perform(get(apiRootPath+"/"+userId+"/accounts/"+accountId2+"/balance"))
				.andReturn();
		BalanceDTO balance3 = objectmapper.readValue(result3.getResponse().getContentAsString(), new TypeReference<BalanceDTO>() {});
		
		//Make the transfer
		TransferDTO transfer = new TransferDTO();
		transfer.setAmount(amount);
		transfer.setFromAccount(1000001);
		transfer.setToAccount(1000003);
		
		//Apply the transfer
		this.mockMvc.perform(post(apiRootPath+"/transfer")
				.content(objectmapper.writeValueAsString(transfer))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.OK.value()))
				.andReturn();
		
		//Get the result balance
		MvcResult result1Final = this.mockMvc.perform(get(apiRootPath+"/"+userId+"/accounts/"+accountId1+"/balance"))
				.andReturn();
		BalanceDTO balance1Final = objectmapper.readValue(result1Final.getResponse().getContentAsString(), new TypeReference<BalanceDTO>() {});
		
		MvcResult result3Final = this.mockMvc.perform(get(apiRootPath+"/"+userId+"/accounts/"+accountId2+"/balance"))
				.andReturn();
		BalanceDTO balance3Final = objectmapper.readValue(result3Final.getResponse().getContentAsString(), new TypeReference<BalanceDTO>() {});
		
		//Check the new balance of the two accounts
		assertEquals(balance1Final.getBalance().getBalance(), balance1.getBalance().getBalance()-amount);
		assertEquals(balance3Final.getBalance().getBalance(), balance3.getBalance().getBalance()+amount);
	}
	
	@Test
	void minBalanceAccount() throws Exception {
		MvcResult result = this.mockMvc.perform(get(apiRootPath+"/12345/accounts/1000001/balance")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		BalanceDTO currentBalance = objectmapper.readValue(result.getResponse().getContentAsString(), new TypeReference<BalanceDTO>() {});
		
		//Make the transfer
		TransferDTO transfer = new TransferDTO();
		transfer.setAmount(currentBalance.getBalance().getBalance()+501);//The transfer with the current balance plus 501
		transfer.setFromAccount(1000001);
		transfer.setToAccount(1000003);
				
		//Apply the transfer with error
		this.mockMvc.perform(post(apiRootPath+"/transfer")
			.content(objectmapper.writeValueAsString(transfer))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(HttpStatus.FORBIDDEN.value()))
			.andReturn();
		
		//Check the current balance
		result = this.mockMvc.perform(get(apiRootPath+"/12345/accounts/1000001/balance")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		BalanceDTO currentBalanceFinal = objectmapper.readValue(result.getResponse().getContentAsString(), new TypeReference<BalanceDTO>() {});
		
		assertEquals(currentBalance.getBalance().getBalance(), currentBalanceFinal.getBalance().getBalance());
	}
	
	@Test
	void historical() throws Exception {
		
		
		MvcResult result = this.mockMvc.perform(get(apiRootPath+"/12345/accounts/1000001/transactions/all"))
				.andExpect(status().isOk())
				.andReturn();
		TransactionsDTO transactionsInitial = objectmapper.readValue(result.getResponse().getContentAsString(), new TypeReference<TransactionsDTO>() {});
		
		result = this.mockMvc.perform(get(apiRootPath+"/12345/accounts/1000003/transactions/received"))
				.andExpect(status().isOk())
				.andReturn();
		TransactionsDTO receivedTransactionsInitial = objectmapper.readValue(result.getResponse().getContentAsString(), new TypeReference<TransactionsDTO>() {});
		
		
		result = this.mockMvc.perform(get(apiRootPath+"/12345/accounts/1000001/transactions/sent"))
				.andExpect(status().isOk())
				.andReturn();
		TransactionsDTO sendTransactionsIntial = objectmapper.readValue(result.getResponse().getContentAsString(), new TypeReference<TransactionsDTO>() {});
		
		//Make the transfer
		TransferDTO transfer = new TransferDTO();
		transfer.setAmount(500F);
		transfer.setFromAccount(1000001);
		transfer.setToAccount(1000003);
				
		//Apply the transfer
		this.mockMvc.perform(post(apiRootPath+"/transfer")
			.content(objectmapper.writeValueAsString(transfer))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(HttpStatus.OK.value()))
			.andReturn();	
		
		
		result = this.mockMvc.perform(get(apiRootPath+"/12345/accounts/1000001/transactions/all"))
				.andExpect(status().isOk())
				.andReturn();
		TransactionsDTO transactionsFinal = objectmapper.readValue(result.getResponse().getContentAsString(), new TypeReference<TransactionsDTO>() {});
		
		
		result = this.mockMvc.perform(get(apiRootPath+"/12345/accounts/1000003/transactions/received"))
				.andExpect(status().isOk())
				.andReturn();
		TransactionsDTO receivedTransactionsFinal = objectmapper.readValue(result.getResponse().getContentAsString(), new TypeReference<TransactionsDTO>() {});
		
		
		result = this.mockMvc.perform(get(apiRootPath+"/12345/accounts/1000001/transactions/sent"))
				.andExpect(status().isOk())
				.andReturn();
		TransactionsDTO sendTransactionsFinal = objectmapper.readValue(result.getResponse().getContentAsString(), new TypeReference<TransactionsDTO>() {});
		
		//Check the number of transactions
		assertEquals(transactionsInitial.getTransactions().size()+1, transactionsFinal.getTransactions().size());
		assertEquals(sendTransactionsIntial.getTransactions().size()+1, sendTransactionsFinal.getTransactions().size());
		assertEquals(receivedTransactionsInitial.getTransactions().size()+1, receivedTransactionsFinal.getTransactions().size());
		 
	}
	
	

}
