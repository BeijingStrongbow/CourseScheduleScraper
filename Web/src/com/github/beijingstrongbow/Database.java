package com.github.beijingstrongbow;

import java.util.HashMap;

public class Database {
	
	private HashMap<String, SemesterDatabase> database = new HashMap<String, SemesterDatabase>();
	
	public HashMap<String, Course> getDatabaseForSemester(String year, String semester) {
		return database.get(year + semester.toUpperCase()).getDatabase();
	}
	
	public void putDatabaseForSemester(HashMap<String, Course> database, String year, String semester) {
		this.database.put(year + semester.toUpperCase(), new SemesterDatabase(database));
	}
	
	/**
	 * The whole point of this class is just to make the code easier to read. I could just as 
	 * easily have a HashMap of HashMaps, but that seemed convoluted. Using a subclass seemed like
	 * a better method to me.
	 */
	private class SemesterDatabase {
		private HashMap<String, Course> database;
		
		private SemesterDatabase(HashMap<String, Course> database) {
			this.database = database;
		}
		
		private HashMap<String, Course> getDatabase() {
			return database;
		}
	}
	
}
