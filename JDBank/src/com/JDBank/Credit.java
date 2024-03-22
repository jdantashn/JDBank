package com.JDBank;

import java.util.Date;

public class Credit extends Flow{

	protected Credit(String comment, int identifier, double amount, int targetAccountNumber, boolean effect, Date date) {
		super(comment, identifier, amount, targetAccountNumber, effect, date);
	}
}
