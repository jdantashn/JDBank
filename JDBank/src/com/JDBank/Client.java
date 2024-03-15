package com.JDBank;

public class Client {

	private static int nextId = 0;
	private int clientNumber;
	private String name;
	private String firstName;
	
	  // Constructor
	  public Client(String name, String firstName) {
	    clientNumber = ++nextId;
	    this.name = name;
	    this.firstName = firstName;
	  }
	  
		public int getClientNumber() {
			return clientNumber;
		}
	  
		public String getName() {
			return name;
		}
		
		public String getFirstName() {
			return firstName;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		
	    @Override
	    public String toString() {
	        return "Client{" +
	                "Client Number=" + clientNumber +
	                ", Name=" + name +
	                ", First name=" + firstName +
	                '}';
	    }

}
