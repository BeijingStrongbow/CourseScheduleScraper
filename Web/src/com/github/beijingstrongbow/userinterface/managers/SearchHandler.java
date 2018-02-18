package com.github.beijingstrongbow.userinterface.managers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.beijingstrongbow.Course;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class SearchHandler implements HttpHandler {

	private enum SearchMethod{
		COURSE_NAME,
		COURSE_NUMBER,
		INVALID;
	}
	
	private HashMap<String, Course> database;
	
	/**
	 * Initializes the SearchHandler to search in a database for a specific semester
	 * 
	 * @param database
	 */
	public SearchHandler(HashMap<String, Course> database) {
		this.database = database;
	}
	
	@Override
	public void handle(HttpExchange e) throws IOException {
		if(!e.getRequestMethod().equalsIgnoreCase("GET")) {
			throw new IOException("Invalid request method");
		}
		
		String request = e.getRequestURI().toString();
		
		request = request.replace("/search/", "");
		//TODO: Make sure course name and number don't have = or &
		String courseNumber = request.substring(request.indexOf('=') + 1, request.indexOf('&'));
		request = request.substring(request.indexOf('&') + 1);
		
		String courseName = request.substring(request.indexOf('=') + 1);
		System.out.println(courseNumber);
		ArrayList<String> results = search(courseName, courseNumber, database);
		Headers headers = e.getResponseHeaders();
		headers.add("Access-Control-Allow-Origin", "*");

		int status = 200;
		
		if(results.size() <= 0) {
			status = 400;
		}
		
		e.sendResponseHeaders(status, 0); //0 = can send an arbitrary length of data
		
		PrintWriter output = new PrintWriter(e.getResponseBody());
		for(String s : results) {
			output.write(s + "\n");
			System.out.println(s);
		}
		output.flush();
		output.close();
	}
	private ArrayList<String> search(String courseName, String courseNumber, HashMap<String, Course> database) {
		SearchMethod searchMethod = getSearchMethod(courseName, courseNumber);
		ArrayList<Course> unsortedResults = new ArrayList<Course>();
		ArrayList<String> toReturn = new ArrayList<String>();
		
        boolean found = false;
        
        if(searchMethod == SearchMethod.COURSE_NAME){
        	courseName = courseName.replace("%20", " ");
        	
            for(String n : database.keySet()){
                if (courseName.toUpperCase().contains(n.toUpperCase())){
                    unsortedResults.add(database.get(courseName));
                    found = true;
                }
            }
        }
        else if(searchMethod == SearchMethod.COURSE_NUMBER){
            courseNumber = courseNumber.replaceAll("%20", "");
            
            for(Course c : database.values()){
                if(c.getNumber().toUpperCase().contains(courseNumber.toUpperCase())){
                	unsortedResults.add(c);
                	found = true;
                }
            }
        }
        else{
        	toReturn.add("invalid");
        	return toReturn;
        }
        
        Course.sort(unsortedResults);

    	for(Course c : unsortedResults){
        	toReturn.add(c.toString());
    	}
    	
    	return toReturn;
	}
	
	private SearchMethod getSearchMethod(String courseName, String courseNumber){
		if (courseName.length() > 0 && !courseName.equals("Course%20name") &&
                (courseNumber.length() == 0 || courseNumber.equals("Course%20number"))){
                return SearchMethod.COURSE_NAME;
            }
            else if (courseNumber.length() > 0 && !courseNumber.equals("Course%20number") &&
                (courseName.length() == 0 || courseName.equals("Course%20name"))){
                return SearchMethod.COURSE_NUMBER;
            }
            else{
                return SearchMethod.INVALID;
            }

	}
}
