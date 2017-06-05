package com.github.beijingstrongbow;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Course that can be taken
 * 
 * @author Eric
 */
public class Course implements Comparable<Course>{
	
	/**
	 * The list of all courses
	 */
	public static HashMap<String, Course> courses = new HashMap<String, Course>();
	
	/**
	 * The sections in which this Course is taught
	 */
	private ArrayList<Section> _sections;
	
	/**
	 * The course number (i.e. CIS300)
	 */
	private String _number;
	
	/**
	 * The course name (i.e. Chemistry 1)
	 */
	private String _name;
	
	/**
	 * The quiz sections for this Course. If this is not null, 
	 * it is required that each student enroll in one of these sections
	 */
	private Course _qz;
	
	/**
	 * The recitation sections for this Course. If this is not null,
	 * it is required that the each student enroll in one of these sections
	 */
	private Course _rec;
	
	/**
	 * The lab sections for this Course. If this is not null,
	 * it is required that each student enroll in one of these sections
	 */
	private Course _lab;
	
	/**
	 * Get the course number (i.e. CIS300)
	 * 
	 * @return The course number
	 */
	public String getNumber(){
		return _number;
	}
	
	/**
	 * Get the course name
	 *
	 * @return The name of this course
	 */
	public String getName(){
		return _name;
	}
	
	/**
	 * Get the list of Sections for this Course
	 * 
	 * @return The list of Sections in which this Course is taught
	 */
	public ArrayList<Section> getSections(){
		return _sections;
	}
	
	/**
	 * Get the number of Sections
	 * 
	 * @return The number of Sections
	 */
	public int getNumberOfSections(){
		return _sections.size();
	}
	
	/**
	 * Get the lab sections for this Course
	 * 
	 * @return The lab sections for this Course, or null if there are none
	 */
	public Course getLab(){
		return _lab;
	}
	
	/**
	 * Get the recitation sections for this Course
	 * 
	 * @return The recitation sections for this Course, or null if there are none
	 */
	public Course getRec(){
		return _rec;
	}
	
	/**
	 * Get the quiz sections for this Course
	 * 
	 * @return The quiz sections for this Course, or null if there are none
	 */
	public Course getQuiz(){
		return _qz;
	}
	
	/**
	 * Initialize the Course with no name, number, or Sections
	 */
	public Course(){
		_number = "";
		_name = "";
	}
	
	/**
	 * Initialize the Course with a name, number, and an empty list of Sections
	 * 
	 * @param number The Course number
	 * @param name The Course name
	 */
	public Course(String number, String name){
		_number = number;
		_name = name;
		_sections = new ArrayList<Section>();
	}
	
	/**
	 * Add a Section for this Course
	 * 
	 * @param s The Section to add
	 * @param type The type of this Section (i.e. LEC (lecture))
	 */
	public void AddSection(Section s, String type){
		if(contains(s, type)){
			return;
		}
		
		switch(type){
			case "LAB":
			case "STD":
				if(_lab == null) _lab = new Course(_number, _name + " Lab");
				
				for(Section sec : _lab.getSections()){
					if(sec.equals(s)){
						return;
					}
				}
				
				type = "LAB";
				s.setCourse(_lab);
				_lab._sections.add(s);
				break;
			case "REC":
				if(_rec == null) _rec = new Course(_number, _name + " Recitation");
				
				for(Section sec : _rec.getSections()){
					if(sec.equals(s)){
						return;
					}
				}
				
				s.setCourse(_rec);
				_rec._sections.add(s);
				break;
			case "QZ":
				if(_qz == null) _qz = new Course(_number, _name + " Quiz");
				
				for(Section sec : _qz.getSections()){
					if(sec.equals(s)){
						return;
					}
				}
				
				s.setCourse(_qz);
				_qz._sections.add(s);
				break;
			default:
				type = "LEC";
				
				for(Section sec : getSections()){
					if(sec.equals(s)){
						return;
					}
				}
				
				_sections.add(s);
				break;
		}
		s.setType(type);
	}
	
	/**
	 * Get a String representation of this Course
	 * 
	 * @return The String representation of this Course
	 */
	@Override
	public String toString(){
		return _number + ": " + _name;
	}
	
	/**
	 * Whether this course contains a section with the same number as s
	 * 
	 * @param s The section to check
	 * @return Whether this course contains a section with the same number as s
	 */
	public boolean contains(Section s, String type){
		if(type.equalsIgnoreCase("rec") && _rec != null){
			for(Section e : _rec._sections){
				if(e.getNumber() == s.getNumber()){
					return true;
				}
			}
		}
		else if((type.equalsIgnoreCase("lab") || type.equalsIgnoreCase("std")) && _lab != null){
			for(Section e : _lab._sections){
				if(e.getNumber() == s.getNumber()){
					return true;
				}
			}
		}
		else if(type.equalsIgnoreCase("qz") && _qz != null){
			for(Section e : _qz._sections){
				if(e.getNumber() == s.getNumber()){
					return true;
				}
			}
		}
		else if(type.equalsIgnoreCase("lec") && _sections != null){
			for(Section e : _sections){
				if(e.getNumber() == s.getNumber()){
					return true;
				}
			}
		}
		return false;
	}
	
	public static void sort(ArrayList<Course> list){
		Course element;
		for(int i = 0; i < list.size(); i++){
			element = list.get(i);
			int j;
			for(j = i-1; j >= 0; j--){
				if(list.get(j).compareTo(element) > 0){
					list.set(j+1, list.get(j));
				}
				else{
					break;
				}
			}
			list.set(j+1, element);
		}
	}

	@Override
	public int compareTo(Course other) {
		String thisLetters;
		String otherLetters;
		int thisNumbers;
		int otherNumbers;
		
		int index = indexOfFirstNumber(_number);
		thisLetters = _number.substring(0, index);
		thisNumbers = Integer.parseInt(_number.substring(index));
		
		index = indexOfFirstNumber(other._number);
		otherLetters = other._number.substring(0, index);
		otherNumbers = Integer.parseInt(other._number.substring(index));
		
		if(!thisLetters.equalsIgnoreCase(otherLetters)){
			int[] thisLettersAscii = stringToAsciiValues(thisLetters);
			int[] otherLettersAscii = stringToAsciiValues(otherLetters);
			
			for(int i = 0; i < Math.min(thisLettersAscii.length, otherLettersAscii.length); i++){
				if(thisLettersAscii[i] != otherLettersAscii[i]){
					return thisLettersAscii[i] - otherLettersAscii[i];
				}
			}
			
			return thisLettersAscii.length - otherLettersAscii.length;
		}
		else{
			return thisNumbers - otherNumbers;
		}
	}
	
	/**
	 * Get the index of the first numerical value in the string
	 * 
	 * @param s The string to process
	 * @return The index of the first numerical value
	 */
	private int indexOfFirstNumber(String s){
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == '0' ||
					s.charAt(i) == '1' ||
					s.charAt(i) == '2' ||
					s.charAt(i) == '3' ||
					s.charAt(i) == '4' ||
					s.charAt(i) == '5' ||
					s.charAt(i) == '6' ||
					s.charAt(i) == '7' ||
					s.charAt(i) == '8' ||
					s.charAt(i) == '9'){
				return i;
			}
		}
		return -1;
	}
	
	private int[] stringToAsciiValues(String s){
		char[] chars = s.toCharArray();
		int[] ints = new int[chars.length];
		
		for(int i = 0; i < chars.length; i++){
			ints[i] = (int) chars[i];
		}
		
		return ints;
	}
}
