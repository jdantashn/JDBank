package com.JDBank;

import java.util.Date;

public abstract class Flow {
	private String comment;
	private int identifier;
	private double amount;
	private int targetAccountNumber;
	private boolean effect;
	private Date date;
	
    protected Flow(String comment, int identifier, double amount, int targetAccountNumber, boolean effect, Date date) {
        this.comment = comment;
        this.identifier = identifier;
        this.amount = amount;
        this.targetAccountNumber = targetAccountNumber;
        this.effect = effect;
        this.date = date;
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public void setTargetAccountNumber(int targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public boolean getEffect() {
        return effect;
    }

    public void setEffect(boolean effect) {
        this.effect = effect;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    @Override
    public String toString() {
        return "comment=" + comment +
                ", identifier='" + identifier + '\'' +
                ", amount=" + amount +
                ", targetAccountNumber=" + targetAccountNumber +
                ", effect=" + effect +
                ", date=" + date;
    }
    
    
    
    
}
