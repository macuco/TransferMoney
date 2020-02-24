# Java Spring Boot Application / Accounts

Project to control accounts, the functionalities that it have are transfer, get all transactions, get all received transactions, get all sent transactions and get account balance. 

## Built With

* 	[Maven](https://maven.apache.org/) - Dependency Management
* 	[JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit 
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to easy the bootstrapping and development of new Spring Applications
* 	[git](https://git-scm.com/) - Free and Open-Source distributed version control system 

## External Tools Used

* [Postman](https://www.getpostman.com/) - API Development Environment (Testing Docmentation)


## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.trasnfermoney.TransferMoneyApplication` class from your IDE.

- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Eclipse 
   - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
   - Select the project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

### URLs

|  URL |  Method | Remarks |
|----------|--------------|--------------|
|`http://localhost:8080/api/transfer`                           | POST | Make a transfer between two accounts |
|`http://localhost:8080/api/{userId}/accounts/{accountId}/balance`                       | GET | |
|`http://localhost:8080/api/{userId}/accounts/{accountId}/transactions/all`                 | GET | |
|`http://localhost:8080/api/{userId}/accounts/{accountId}/transactions/received` | GET | |
|`http://localhost:8080/api/{userId}/accounts/{accountId}/transactions/sent`                             | GET | |
|`http://localhost:8080/api/create`                             | POST | Create a new user or account if doesn't exist, if exist don't change it |


## Documentation

The project has initialization information that is defined in the class com.trasnfermoney.config.DataLoader.java, the load information is:
* User id: 12345 - It has an account number 1000001 with $5000 and account number 1000003 with $500
* User id: 12346 - It has an account number 1000002 with $10000
* User id: 12347 - It has an account number 1000004 with $1000.50

After that are made the following transfers:
* From 1000001 to 1000003 with 1000
* From 1000002 to 1000004 with 1500
* From 1000004 to 1000003 with 10

And finally the final data are:	
* User id: 12345 - have account number 1000001 with $4000 and account number 1000003 with $1510
* User id: 12346 - have account number 1000002 with $8500
* User id: 12347 - have account number 1000004 with $2490.50



## Files and Directories

The project (project directory) has a particular directory structure. A representative project is shown below:

```
.
├── Spring Elements
├── src
│   └── main
│       └── java
│           ├── com.trasnfermoney
│           ├── com.trasnfermoney.config
│           ├── com.trasnfermoney.controller
│           ├── com.trasnfermoney.exception
│           ├── com.trasnfermoney.model
│           ├── com.trasnfermoney.dto
│           ├── com.trasnfermoney.repository
│           └── com.trasnfermoney.service
├── src
│   └── main
│       └── resources
│           ├── application.properties
├── src
│   └── test
│       └── java
│           └── com.trasnfermoney
│               ├── TransferMoneyApplicationTests.java
├── JRE System Library
├── Maven Dependencies
├── src
├── target
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## packages

- `config` — to configurate the API prefix and load the data with going to start the application ;
- `model` — to hold our entities;
- `dto` — to hold our data transfer objects;
- `repositoriy` — to communicate with memory data (Accounts and Users);
- `service` — to hold our business logic;
- `exception` — to hold our personalized exceptions;
- `controller` — to listen to the client;

- `resources/application.properties` - It contains application-wide properties. Spring reads the properties defined in this file to configure your application. You can define server’s default port, server’s context path, database URLs etc, in this file.

- `test/` - contains unit and integration tests

- `pom.xml` - contains all the project dependencies
 
 
 
# REST API

The REST API to the example app is described below.

## Create an account and user

### Request

`POST /create/`

    curl --location --request POST 'http://localhost:8080/api/create' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	"account": 123456789,
	"balance": 45450.00,
	"owner": 7612333392
	}'

### Response

    HTTP/1.1 201 CREATED

    []

## Make a transaction

### Request

`POST /transfer`

    curl --location --request POST 'http://localhost:8080/api/transfer' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	"fromAccount": 1000001,
	"toAccount": 1000003,
	"amount": 10.54
	}'

### Response

    HTTP/1.1 200 OK
    
    []

## Get a balance

### Request

`GET /{userId}/accounts/{accountNumber}/balance`

    curl --location --request GET 'http://localhost:8080/api/7612333392/accounts/123456789/balance'

### Response

    HTTP/1.1 200 OK
    
    {
	    "balance": {
	        "account": 123456789,
	        "balance": 45450.0,
	        "owner": 7612333392,
	        "createdAt": "2020-02-24T05:28:17.040+0000"
	    }
	}
	
## Get all transactions

### Request

`GET /{userId}/accounts/{accountNumber}/transactions/all`

    curl --location --request GET 'http://localhost:8080/api/12345/accounts/1000001/transactions/all'

### Response

    HTTP/1.1 200 OK
    
    {
	    "transactions": [
	        {
	            "fromAccount": 1000001,
	            "toAccount": 1000003,
	            "amount": 1000.0,
	            "sentAt": "2020-02-24T05:46:12.095+0000"
	        }
	    ]
	 }
	
## Get received 

### Request

`GET /{userId}/accounts/{accountId}/transactions/received`

    curl --location --request GET 'http://localhost:8080/api/12345/accounts/1000003/transactions/received'

### Response

    HTTP/1.1 200 OK
    
    {
		    "transactions": [
		        {
		            "fromAccount": 1000001,
		            "toAccount": 1000003,
		            "amount": 1000.0,
		            "sentAt": "2020-02-24T05:46:12.095+0000"
		        },
		        {
		            "fromAccount": 1000004,
		            "toAccount": 1000003,
		            "amount": 10.0,
		            "sentAt": "2020-02-24T05:46:12.095+0000"
		        }
		    ]
		}

 
## Get sent 

### Request

`GET /{userId}/accounts/{accountId}/transactions/sent`

    curl --location --request GET 'http://localhost:8080/api/12345/accounts/1000001/transactions/sent'

### Response

    HTTP/1.1 200 OK
    
	{
	    "transactions": [
	        {
	            "fromAccount": 1000001,
	            "toAccount": 1000003,
	            "amount": 1000.0,
	            "sentAt": "2020-02-24T05:46:12.095+0000"
	        }
	    ]
	}