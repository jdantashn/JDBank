package com.JDBank;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;


public class Main {
	  public static void main(String[] args) {
		  	Client[] clients = generateClients(2); // Create # clients
		  	Account[] accounts = generateAccounts(clients); // Create accounts based on the # of clients
		  	Hashtable<Integer,Account> accountsHashtable = generateHashtable(accounts);
		  	Flow[] flows = loadFlows(accounts);
		  	updateAccountBalances(flows, accountsHashtable);
		  	printClients(clients);  	
		  	//printAccounts(accounts);
		  	printAccountsAscendingBalance(accountsHashtable);
		  	printFlows(flows);
		  	//checkNegativeBalances(accountsHashtable);
		  	
	  }
	  
	  
	  static Client[] generateClients(int numberOfClients) {
		  Client[] clients =  new Client[numberOfClients];
		  
		  for (int i = 1; i <= numberOfClients; i++) {
			  clients[i-1] = new Client("name" + i, "firstname" + i);
		  }
		  return clients;
		    
	  }
	  
	  static void printClients(Client[] clients) {
		  System.out.println("--------Clients--------");
		  Arrays.stream(clients).forEach(client -> System.out.println(client.toString()));
	  }  
	  
		static Account[] generateAccounts(Client[] clients) {
			int totalNumberOfAccounts = clients.length * 2;
			int accountIndex = 0; // keeps track of what index we are working on on the accounts array
			Account[] accounts = new Account[totalNumberOfAccounts];
			
				// create 1 CurrentAccount and 1 SavingsAccount per Client
			  for (int i = 0; i < clients.length; i++) {
				  accounts[accountIndex] = new CurrentAccount("Current", clients[i]);
				  accountIndex++;
				  accounts[accountIndex] = new SavingsAccount("Savings", clients[i]);
				  accountIndex++;
			  }
			
			
			return accounts;
		}
		
	    public static void printAccounts(Account[] accounts) {
	    	Arrays.stream(accounts).forEach(account -> System.out.println(account.toString()));
	    }
	    
	    public static Hashtable<Integer,Account> generateHashtable(Account[] accounts) {
	        Hashtable<Integer, Account> accountHashtable = new Hashtable<>();
	        for (Account account : accounts) {
	            accountHashtable.put(account.getAccountNumber(), account);
	        }
	        return accountHashtable;    
	    }
	    
	    public static void printAccountsAscendingBalance(Hashtable<Integer, Account> accountHashtable) {
	    	System.out.println("--------Accounts ascending balance--------");
	        accountHashtable.entrySet().stream()
	                .sorted(Map.Entry.comparingByValue((a1, a2) -> Double.compare(a1.getBalance(), a2.getBalance())))
	                .forEach(entry -> System.out.println(entry.getValue().toString()));
	    }
	    
	    public static Flow[] loadFlows(Account[] accounts) {	  
	    	// (debit from #1) + (credit & savings accounts) + (transform from #1 to #2)
	        Flow[] flows = new Flow[1 + accounts.length + 1]; 

	        // Calculate date 2 days from now
	        LocalDate currentDate = LocalDate.now().plusDays(2);
	        Date flowDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

	        // Debit of 50€ from account #1
	        flows[0] = new Debit("Debit of 50€", 1, 50.0, 1, false, flowDate);

	        
	        int flowIndex = 1; // Start index for credit flows
	        for (Account account : accounts) {
	        	// Credit of 100.50€ on all current accounts
	            if (account instanceof CurrentAccount) {
	                flows[flowIndex] = new Credit("Credit of 100.50€ on current account", flowIndex + 1, 100.50, account.getAccountNumber(), true, flowDate);
	                flowIndex++;
	                
	             // Credit of 1500€ on all savings accounts
	            } else if (account instanceof SavingsAccount) {
	                flows[flowIndex] = new Credit("Credit of 1500€ on savings account", flowIndex + 1, 1500.0, account.getAccountNumber(), true, flowDate);
	                flowIndex++;
	            }
	        }

	        // Transfer of 50€ from account #1 to account #2
	        flows[flowIndex] = new Transfer("Transfer of 50€ from account #1 to account #2", flowIndex + 1, 50.0, 2, false, flowDate, 1);

	        return flows;
	    }
	    
	    public static void printFlows(Flow[] flows) {
	    	System.out.println("--------Flows--------");
	        for (Flow flow : flows) {
	            System.out.println(flow.toString());
	        }
	    }
	    
	    public static void updateAccountBalances(Flow[] flows, Map<Integer, Account> accountMap) {
	        for (Flow flow : flows) {
	            Account account = accountMap.get(flow.getTargetAccountNumber());
	            if (account != null) {
	                account.setBalance(flow);
	            }
	            if (flow instanceof Transfer) {
	                Transfer transfer = (Transfer) flow;
	                Account sourceAccount = accountMap.get(transfer.getOriginAccountNumber());
	                if (sourceAccount != null) {
	                    sourceAccount.setBalance(flow);
	                }
	            }
	        }
	        checkNegativeBalances(accountMap);
	    }
	    
	    public static void checkNegativeBalances(Map<Integer, Account> accountMap) {
	        Predicate<Account> isNegativeBalance = account -> account.getBalance() < 0;
	        Optional<Account> negativeBalanceAccount = accountMap.values().stream()
	                .filter(isNegativeBalance)
	                .findFirst();

	        negativeBalanceAccount.ifPresent(account ->
	                System.out.println("Account " + account.getAccountNumber() + " has a negative balance."));
	    }
	    
	    
	    

}


