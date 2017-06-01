package com.github.beijingstrongbow;

import java.time.LocalTime;
import java.util.GregorianCalendar;

/**
 * Represents a specitic section of a course
 * 
 * @author Eric
 */
public class Section implements Comparable<Section>{
	
	/*
	 * The letter representing this Section
	 */
	private String _letter;
	
	/**
	 * The number representing this section, for example 10707
	 */
	private int _number; 
	
	/**
	 * The time at which class begins for this Section
	 */
	private LocalTime _startTime;
	
	/**
	 * The time at which class ends for this Section
	 */
	private LocalTime _endTime;
	
	/**
	 * Whether this class is scheduled on an appointment basis
	 */
	private boolean _isAppointment;
	
	/**
	 * The days of the week this Section is held on
	 */
	private String _weekDays;
	
	/**
	 * The instructor's name for this Section
	 */
	private String _instructor;
	
	/**
	 * The type of this section, for example, LEC (lecture) or LAB (lab)
	 */
	private String _type;
	
	/**
	 * The Course that is taught in this Section
	 */
	private Course _course;
	
	/**
	 * The date of the first day of class for this Section
	 */
	private GregorianCalendar _fromDate;
	
	/**
	 * The date of the last day of class for this Section
	 */
	private GregorianCalendar _toDate;
	
	/**
	 * Initialize the Section as an appointment with the default start and end date
	 * 
	 * @param letter The letter representing this Section
	 * @param number The number representing this Section (i.e. 11407)
	 * @param days The days of the week this Section is held on
	 * @param instructor The instructor for this Section
	 * @param course The Course that is taught in this Section
	 */
	public Section(String letter, int number, String days, String instructor, Course course){
		
		_letter = letter;
		_number = number;
		_isAppointment = true;
		_weekDays = days;
		_instructor = instructor;
		_course = course;
		
	}
	
	/**
	 * Initialize the Section as an appointment with a non-default start and end date
	 * 
	 * @param letter The letter representing this Section
	 * @param number The number representing this Section (i.e. 11407)
	 * @param days The days of the week this Section is held on
	 * @param instructor The instructor for this Section
	 * @param fromDate The date of the first day of class for this Section
	 * @param toDate The date of the last day of class for this Section
	 * @param course The Course taught in this Section
	 */
	public Section(String letter, int number, String days, String instructor, GregorianCalendar fromDate, GregorianCalendar toDate, Course course){
		
		_letter = letter;
		_number = number;
		_weekDays = days;
		_instructor = instructor;
		_fromDate = fromDate;
		_toDate = toDate;
		_isAppointment = true;
		_course = course;
	}
	
	/**
	 * Initialize the Section as a standard class (not an appointment) with the default start and end date
	 * 
	 * @param letter The letter representing this Section
	 * @param number The number representing this Section (i.e. 11407)
	 * @param startTime The time at which class begins for this Section 
	 * @param endTime The time at which class ends for this Section
	 * @param days The days  of the week this Section is held on
	 * @param instructor The instructor for this Section
	 * @param course The Course taught in this Section
	 */
	public Section(String letter, int number, LocalTime startTime, LocalTime endTime, String days, String instructor, Course course){
		
		_letter = letter;
		_number = number;
		_startTime = startTime;
		_endTime = endTime;
		_weekDays = days;
		_instructor = instructor;
		_isAppointment = false;
		_course = course;
		
	}
	
	/**
	 * Initialize the Section as a standard class (not an appointment) with the a non-default start and end date
	 * 
	 * @param letter The letter representing this Section
	 * @param number The number representing this Section (i.e. 11407)
	 * @param startTime The time at which class begins for this Section
	 * @param endTime The time at which class ends for this Section
	 * @param days The days of the week this Section is held on
	 * @param instructor The instructor for this Section
	 * @param fromDate The date of the first day of class for this Section
	 * @param toDate The date of the last day of class for this Section
	 * @param course The Course taught in this Section
	 */
	public Section(String letter, int number, LocalTime startTime, LocalTime endTime, String days, String instructor, GregorianCalendar fromDate, GregorianCalendar toDate, Course course){
		
		_letter = letter;
		_number = number;
		_startTime = startTime;
		_endTime = endTime;
		_weekDays = days;
		_instructor = instructor;
		_fromDate = fromDate;
		_toDate = toDate;
		_isAppointment = false;
		_course = course;
		
	}
	
	/**
	 * Get the letter representing this Section
	 * 
	 * @return The letter representing this Section
	 */
	public String getLetter(){
		return _letter;
	}
	
	/**
	 * Get the number representing this Section (i.e. 11407)
	 * 
	 * @return The number representing this Section
	 */
	public int getNumber(){
		return _number;
	}
	
	/**
	 * Get the time at which class begins for this Section
	 * 
	 * @return The time at which class begins
	 */
	public LocalTime getStartTime(){
		return _startTime;
	}
	
	
	/**
	 * Get the time at which class ends for this Section
	 * 
	 * @return The time at which class ends
	 */
	public LocalTime getEndTime(){
		return _endTime;
	}
	
	/**
	 * Get the days of the week this Section is held on
	 * 
	 * @return The days of the week this Section is held on
	 */
	public String getDays(){
		return _weekDays;
	}
	
	/**
	 * Get the instructor for this Section
	 * 
	 * @return The instructor
	 */
	public String getInstructor(){
		return _instructor;
	}
	
	/**
	 * Get the date of the first day of class for this Section
	 * 
	 * @return The date of the first day of class
	 */
	public GregorianCalendar getFromDate(){
		return _fromDate;
	}
	
	/**
	 * Get the date for the last day of class for this Section
	 * 
	 * @return The date of the last day of class
	 */
	public GregorianCalendar getToDate(){
		return _toDate;
	}
	
	/**
	 * Whether this Section is held on an appointment basis
	 * 
	 * @return Whether this Section is held on an appointment basis
	 */
	public boolean isAppointment(){
		return _isAppointment;
	}
	
	/**
	 * Get the Course taught in this Section
	 * 
	 * @return The Course taught in this Section
	 */
	public Course getCourse(){
		return _course;
	}
	
	/**
	 * Set the Course taught in this Section
	 * 
	 * @param course The Course taught in this Section
	 */
	public void setCourse(Course course){
		this._course = course;
	}
	
	/**
	 * Set the type of this Section (i.e. LEC (lecture))
	 * 
	 * @return The type of this Section
	 */
	public String getType(){
		return _type;
	}
	
	/**
	 * Set the type of this Section (i.e. LEC (lecture))
	 * 
	 * @param Type The type of this Section
	 */
	public void setType(String type){
		this._type = type;
	}
	
	/**
	 * Processes two Sections and whether they overlap each other.
	 * 
	 * @param s The section to compare with this Section
	 * @return True if there is any time when these two Sections are on the same day at the same time, and false otherwise.
	 */
	public boolean overlaps(Section s){
		if(s.isAppointment() || _isAppointment){
			return false;
		}
		
		for(int i = 0; i < _weekDays.length(); i++){
			for(int j = 0; j < s._weekDays.length(); j++){
				
				if(_weekDays.charAt(i) == s._weekDays.charAt(j)){
					
					if(_endTime.compareTo(s._startTime) < 0 || _startTime.compareTo(s._endTime) > 0){
						return false;
					}
					else if(_fromDate != null && s._fromDate != null && _toDate != null && s._toDate != null &&
							!_fromDate.equals(new GregorianCalendar()) && !s._fromDate.equals(new GregorianCalendar()) &&
							(_toDate.compareTo(s._fromDate) < 0 || _fromDate.compareTo(s._toDate) > 0)){
						return false;
						
					}
					else{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Compares the start times of two sections
	 * 
	 * @param arg0 The Section to compare this Section to
	 * @return Negative if this Section starts before Section arg0, or positive if this Section starts after Section arg0
	 */
	@Override
	public int compareTo(Section arg0) {
		return _startTime.compareTo(arg0._startTime);
	}

	/**
	 * Convert this Section to a String representation
	 * 
	 * @return The String representation of this Section
	 */
	@Override
	public String toString(){
		int startHour;
		int startMinute;
		int endHour;
		int endMinute;
		
		String time;
		
		if(_isAppointment){
			time = "";
		}
		else{

			startHour = _startTime.getHour();
			startMinute = _startTime.getMinute();
			endHour = _endTime.getHour();
			endMinute = _endTime.getMinute();
						
			String stringStartMinute = Integer.toString(startMinute);
			String stringEndMinute = Integer.toString(endMinute);
			
			if(startMinute < 10){
				stringStartMinute = "0" + stringStartMinute;
			}
			if(endMinute < 10){
				stringEndMinute = "0" + stringEndMinute;
			}
			
			if(startHour < 12 && endHour < 12){
				time = startHour + ":" + stringStartMinute + " - " + endHour + ":" + stringEndMinute + " a.m.";
			}
			else if(startHour < 12 && endHour >= 12){
				if(endHour >= 13) endHour -= 12;
				
				time = startHour + ":" + stringStartMinute + " a.m. - " + endHour + ":" + stringEndMinute + " p.m.";
			}
			else{
				if(startHour >= 13) startHour -= 12;
				if(endHour >= 13) endHour -= 12;
				
				time = startHour + ":" + stringStartMinute + " - " + endHour + ":" + stringEndMinute + " p.m.";
			}
		}
		
		return _course.getNumber() + ": " + _course.getName() + " (" + _number + ") " + _weekDays + " " + time + " " + _instructor;
	}
	
	@Override
	public boolean equals(Object other){
		if(other instanceof Section){
			Section s = (Section) other;
			return s._number == this._number && s._fromDate == this._fromDate && s._toDate == this._toDate;
		}
		else{
			return false;
		}
	}
}
