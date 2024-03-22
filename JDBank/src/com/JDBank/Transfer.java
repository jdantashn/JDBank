package com.JDBank;

import java.util.Date;

public class Transfer extends Flow{

	private int originAccountNumber;
	
	protected Transfer(String comment, int identifier, double amount, int targetAccountNumber, boolean effect, Date date, int originAccountNumber) {
		super(comment, identifier, amount, targetAccountNumber, effect, date);
		this.originAccountNumber = originAccountNumber;
	}
	

	public int getOriginAccountNumber() {
        return originAccountNumber;
    }
    
    public void setOriginAccountNumber(int originAccountNumber) {
    	this.originAccountNumber = originAccountNumber;
    }
    
    

}
