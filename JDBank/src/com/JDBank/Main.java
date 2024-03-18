package com.JDBank;

import java.util.Arrays;

public class Main {
	  public static void main(String[] args) {
		  	Client[] clients = generateClients(5); // Create # clients
		  	printClients(clients);
		  	Account[] accounts = generateAccounts(clients); // Create accounts based on the # of clients
		  	printAccounts(accounts);
		  	
	  }
	  
	  
	  
	  
	  static Client[] generateClients(int numberOfClients) {
		  Client[] clients =  new Client[numberOfClients];
		  
		  for (int i = 1; i <= numberOfClients; i++) {
			  clients[i-1] = new Client("name" + i, "firstname" + i);
		  }
		  return clients;
		    
	  }
	  
	  static void printClients(Client[] clients) {
	        // Get the stream 
	        Arrays.stream(clients).forEach(client -> System.out.println(client.toString()));
	  }  
	  
		static Account[] generateAccounts(Client[] clients) {
			int totalNumberOfAccounts = clients.length * 2;
			int accountIndex = 0; // keeps track of what index we are working on on the accounts array
			Account[] accounts = new Account[totalNumberOfAccounts];
			
				// create 1 CurrentAccount and 1 SavingsAccount for each Client
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
}


