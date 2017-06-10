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
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Time){
			Time other = (Time) o;
			return other.getHour() == hour && other.getMinute() == minute;
		}
		else{
			return false;
		}
	}
	
	@Override
	public String toString(){
		int hour = this.hour;
		
		String time;
		
		String minute;
		
		if(this.minute < 10){
			minute = "0" + this.minute;
		}
		else{
			minute = Integer.toString(this.minute);
		}
		
		if(hour == 12) time = hour + ":" + minute + " p.m.";
		
		else if(hour < 12) time = hour + ":" + minute + " a.m.";
		
		else time = (hour - 12) + ":" + minute + " p.m.";
		
		return time;
	}
}
