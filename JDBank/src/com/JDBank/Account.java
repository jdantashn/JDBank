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
		
		public void setBalance(Flow flow) {
			
			double amount = flow.getAmount();
	        
	        // Determine flow type and update balance accordingly
	        if (flow instanceof Credit) {
	            this.balance += amount;
	        } else if (flow instanceof Debit) {
	            this.balance -= amount;
	        } else if (flow instanceof Transfer) {
	            Transfer transfer = (Transfer) flow;
	            if (this.accountNumber == transfer.getTargetAccountNumber()) {
	                this.balance += amount;
	            } else if (this.accountNumber == transfer.getOriginAccountNumber()) {
	                this.balance -= amount;
	            }
	        }
	    }
		
	    @Override
	    public String toString() {
	        return "accountNumber=" + accountNumber +
	                ", label='" + label + '\'' +
	                ", balance=" + balance +
	                ", client=" + client;
	    }
		
}
