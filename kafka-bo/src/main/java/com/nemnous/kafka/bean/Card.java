package com.nemnous.kafka.bean;

import lombok.Data;

@Data
public class Card {
	private String cardNumber;
	private String cvv;
	private String expiryDate;
}
