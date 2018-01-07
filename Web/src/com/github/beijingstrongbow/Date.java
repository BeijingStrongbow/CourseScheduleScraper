package com.github.beijingstrongbow;

/**
 * Represents a date. There is probably a way to do this with the native Java libraries,
 * but this solution was easier.
 * 
 * @author Eric
 *
 */
public class Date implements Comparable<Date>{
	
	/**
	 * The year this date is in
	 */
	private int year;
	
	/**
	 * The month this date is in
	 */
	private int month;
	
	/**
	 * The day of the month of this date
	 */
	private int day;
	
	/**
	 * Construct a date
	 * 
	 * @param day The day of the month for this date
	 * @param month The name of the month for this date
	 * @param year The year for this date
	 */
	public Date(int day, String month, int year){
		this.day = day;
		this.year = year;
		this.month = monthStringToInt(month);
	}
	
	/**
	 * Initialize the date as default
	 */
	public Date(){
		this.day = 0;
		this.month = 0;
		this.year = 0;
	}
	
	/**
	 * Get the day for this date
	 * 
	 * @return The day
	 */
	public int getDay(){
		return day;
	}
	
	/**
	 * Get the integer representation of the month for this date. This starts at 0 for January.
	 * 
	 * @return The integer representation of the month
	 */
	public int getMonth(){
		return month;
	}
	
	/**
	 * Get the name of the month for this date (i.e. September)
	 * 
	 * @return The name of the month
	 */
	public String getMonthName(){
		return monthIntToString(month);
	}
	
	/**
	 * Get the year for this date
	 * 
	 * @return The year for this date
	 */
	public int getYear(){
		return year;
	}
	
	/**
	 * Change the day stored in this date
	 * 
	 * @param day The new day to store
	 */
	public void setDay(int day){
		this.day = day;
	}
	
	/**
	 * Change the month stored by this date
	 * 
	 * @param month The new month to store
	 */
	public void setMonth(String month){
		this.month = monthStringToInt(month);
	}
	
	/**
	 * Change the year stored by this date
	 * 
	 * @param year The new year to store
	 */
	public void setYear(int year){
		this.year = year;
	}
	
	/**
	 * Compare this date to another date
	 * 
	 * @param other The date to compare to
	 * @return Negative if this date is before Date other, positive if it is after, 
	 * and 0 if they are the same.
	 */
	@Override
	public int compareTo(Date other){
		if(this.year != other.year){
			return this.year - other.year;
		}
		else if(this.month != other.month){
			return this.month - other.month;
		}
		else if(this.day != other.day){
			return this.day - other.day;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * Compare two dates to see if they are equal
	 * 
	 * @param other The Object (an instance of Date) to compare to
	 * @return Whether the two objects are equal
	 */
	@Override
	public boolean equals(Object other){
		if(!(other instanceof Date)){
			throw new RuntimeException("Can't compare " + other.getClass().getName() + " to Date!");
		}
		else{
			Date d = (Date) other;
			
			return this.day == d.day && this.month == d.month && this.year == d.year;
		}
	}
	
	@Override
	public String toString(){
		return monthIntToString(month) + " " + day + ", " + year;
	}

	/**
	 * Convert the name of a month to an integer representation of that month
	 * 
	 * @param month The month to convert
	 * @return The integer representation of month
	 */
	private static int monthStringToInt(String month)
    {
        month = month.toLowerCase();
        switch (month)
        {
            case "january":
                return 0;
            case "february":
                return 1;
            case "march":
                return 2;
            case "april":
                return 3;
            case "may":
                return 4;
            case "june":
                return 5;
            case "july":
                return 6;
            case "august":
                return 7;
            case "september":
                return 8;
            case "october":
                return 9;
            case "november":
                return 10;
            case "december":
                return 11;
            default:
                return -1;
        }
    }
	
	/**
	 * Convert the integer representation of a month (starting at 0 for January) to the name of the month.
	 * 
	 * @param month The month to convert
	 * @return The name of the month
	 */
	private static String monthIntToString(int month){
		switch(month){
			case 0:
				return "January";
			case 1:
				return "February";
			case 2:
				return "March";
			case 3:
				return "April";
			case 4:
				return "May";
			case 5:
				return "June";
			case 6:
				return "July";
			case 7:
				return "August";
			case 8:
				return "September";
			case 9:
				return "October";
			case 10:
				return "November";
			case 11:
				return "December";
			default:
				return "";
		}
	}
}
