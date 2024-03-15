package com.JDBank;

import java.util.Arrays;

public class Main {
	  public static void main(String[] args) {
		  	Client[] clients = generateClients(5); // Create clients
		  	printClients(clients);
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
	        Arrays.stream(clients).forEach(account -> System.out.println(account.toString()));
	  }  
}
