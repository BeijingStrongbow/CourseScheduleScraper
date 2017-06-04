package com.github.beijingstrongbow;

/**
 * Represents a time on a 24 hour clock
 * @author Eric
 *
 */
public class Time implements Comparable<Time>{
	
	private int hour;
	
	private int minute;
	
	public Time(int hour, int minute){
		this.hour = hour;
		this.minute = minute;
	}
	
	public int getHour(){
		return hour;
	}
	
	public int getMinute(){
		return minute;
	}
	
	public void setHour(int hour){
		this.hour = hour;
	}
	
	public void setMinute(int minute){
		this.minute = minute;
	}
	
	@Override
	public int compareTo(Time other){
		if(this.hour != other.hour){
			return this.hour - other.hour;
		}
		else if(this.minute != other.minute){
			return this.minute - other.minute;
		}
		else{
			return 0;
		}
	}
}
