package com.trasnfermoney.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.trasnfermoney.service.AccountService;

/**
 * Load the initial data
 *
 */
@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
    private AccountService accountService;

    
    public void run(ApplicationArguments args) {
    	//Register accounts and users
    	this.accountService.register(1000001, 12345, 5000);
        this.accountService.register(1000003, 12345, 500);
        this.accountService.register(1000002, 12346, 10000);
        this.accountService.register(1000004, 12347, 1000.50F);
        
        //Make transfers
        this.accountService.transfer(1000001, 1000003, 1000);
        this.accountService.transfer(1000002, 1000004, 1500);
        this.accountService.transfer(1000004, 1000003, 10);
        
    }
}
