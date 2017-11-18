package com.ccoew.finalproject;

public class PD {
	Double percent;
	int fraud;
	int nonFraud;
	
	
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public int getFraud() {
		return fraud;
	}
	public PD(Double percent, int fraud, int nonFraud) {
		super();
		this.percent = percent;
		this.fraud = fraud;
		this.nonFraud = nonFraud;
	}
	public void setFraud(int fraud) {
		this.fraud = fraud;
	}
	public int getNonFraud() {
		return nonFraud;
	}
	public void setNonFraud(int nonFraud) {
		this.nonFraud = nonFraud;
	}
	
}
