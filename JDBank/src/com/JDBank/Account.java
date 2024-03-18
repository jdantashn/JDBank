package com.JDBank;

public abstract class Account {
	private static int nextId = 0;
	protected int accountNumber;
	protected String label;
	protected double balance;	
	protected Client client;
	
	  // Constructor
	  protected Account(String label, Client client) {
		  this.accountNumber = ++nextId;
		  this.label = label;
		  this.client = client;
		  this.balance = 0.0;
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
		
		public void setBalance(double amount) {
			// TODO: implement Flowtype logic
			this.balance += amount;
		}
		
	    @Override
	    public String toString() {
	        return "accountNumber=" + accountNumber +
	                ", label='" + label + '\'' +
	                ", balance=" + balance +
	                ", client=" + client;
	    }
		
}
