package com.github.beijingstrongbow;

import java.util.HashMap;

import com.github.beijingstrongbow.userinterface.managers.SearchHandler;
import com.github.beijingstrongbow.userinterface.managers.WebServer;
import com.github.beijingstrongbow.webscraper.WebScraper;
import com.sun.net.httpserver.HttpHandler;

public class Main {
	
	private static final int BACKLOG = 5;
	private static final String[] SEMESTERS = {"Spring", "Summer", "Fall"};
	
	private static Database courseDatabase = new Database();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		WebScraper scraper = new WebScraper();
		
		scraper.populateAllDatabases(courseDatabase);
		
		HashMap<String, HttpHandler> handlers = new HashMap<String, HttpHandler>();
		handlers.put("/search/", new SearchHandler(courseDatabase.getDatabaseForSemester("2018", "Spring")));
		
		WebServer server = new WebServer(80, handlers); //starts an HTTP server on the default port for HTTP
	}
}
