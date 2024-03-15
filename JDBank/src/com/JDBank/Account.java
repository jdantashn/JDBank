package com.JDBank;

public abstract class Account {
	private static int nextId = 0;
	protected int accountNumber;
	protected String label;
	protected double balance;	
	protected Client client;
	
	  // Constructor
	  protected Account(String label, Client client) {
		  accountNumber = ++nextId;
		  this.label = label;
		  this.client = client;
	  }
	  
	  
		public int getAccountNumber() {
			return accountNumber;
		}
		
		public String getLabel() {
			return label;
		}
		
		public double getBalance() {
			return balance;
		}
		
		public Client getClient() {
			return client;
		}
		
		public void setLabel(String label) {
			this.label = label;
		}
		
		
		public void setClient(Client client) {
			this.client = client;
		}
		
}
