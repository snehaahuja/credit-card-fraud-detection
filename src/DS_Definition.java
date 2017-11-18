package com.ccoew.finalproject;
public class DS_Definition
{
	int seventyCross;
	int location;
	int period;
	int dist;
	int addChange;
	int time;
	int output;
	public int getSeventyCross() {
		return seventyCross;
	}
	public void setSeventyCross(int seventyCross) {
		this.seventyCross = seventyCross;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public int getDist() {
		return dist;
	}
	public void setDist(int dist) {
		this.dist = dist;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getOutput() {
		return output;
	}
	public void setOutput(int output) {
		this.output = output;
	}
	public DS_Definition(int seventyCross, int location, int period, int dist,
			/*int addChange,*/ int time,int output) {
		super();
		this.seventyCross = seventyCross;
		this.location = location;
		this.period = period;
		this.dist = dist;
		//this.addChange=addChange;
		this.time = time;
		this.output = output;
	}
}