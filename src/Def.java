
package com.ccoew.finalproject;

public class Def {
	
	public Def(double in, double out, int epoch, int instanceNum) {
		super();
		this.in = in;
		this.out = out;
		this.epoch = epoch;
		this.instanceNum = instanceNum;
	}

	

	double in, out;
	int epoch,instanceNum;

	public double getIn() {
		return in;
	}

	public void setIn(double in) {
		this.in = in;
	}

	public double getOut() {
		return out;
	}

	public void setOut(double out) {
		this.out = out;
	}

	public int getEpoch() {
		return epoch;
	}

	public void setEpoch(int epoch) {
		this.epoch = epoch;
	}

	public int getInstanceNum() {
		return instanceNum;
	}

	public void setInstanceNum(int instanceNum) {
		this.instanceNum = instanceNum;
	}
}
