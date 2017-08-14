package com.github.beijingstrongbow.webscraper;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.beijingstrongbow.Course;
import com.github.beijingstrongbow.Date;
import com.github.beijingstrongbow.Section;
import com.github.beijingstrongbow.Time;
import com.github.beijingstrongbow.userinterface.LoadingProgressWindow;

/**
 * Contains functions related to scraping the course database
 * 
 * @author Eric
 */
public class WebScraper {
		
	/**
	 * Populate the text database on the hard drive and the Course.courses hashmap with data from the
	 * online database
	 * 
	 * @param year
	 * @param semester
	 * @return Whether the operation was successful
	 */
	public boolean populateDatabases(String year, String semester, LoadingProgressWindow progress){
		ArrayList<URL> urls = getUrls(year, semester);
		if(urls == null) {
			return false;
		}
		
		double progressIncrement = 100.0 / urls.size();
		for(URL url : urls){
			scrapeUrl(url);
			progress.addProgress(progressIncrement);
		}
		
		return true;
	}
	
	/**
	 * Get the URLs that need to be parsed
	 * 
	 * @param year The year to be parsed
	 * @param semester The semester to be parsed
	 * @return The list of all URLs that need to be parsed to input all courses
	 */
	private ArrayList<URL> getUrls(String year, String semester){
		String url = "https://courses.k-state.edu/" + semester.toLowerCase() + year + "/schedule.html";
		//the URLs that need to be parsed to get all the courses
		ArrayList<URL> urls = new ArrayList<URL>();
		
		try{
			Document html = Jsoup.parse(new URL(url), 5000);
			Elements links = html.getElementsByAttribute("href");
			
			int i = 0;
			
			for(Element e : links){
				String link = e.attr("href");
				if(link.length() < 7 && !link.equals("/") && !link.equals("None")){
					urls.add(new URL("https://courses.k-state.edu/" + semester.toLowerCase() + year + "/" + link));
				}
				
				i++;
			}
			
		}
		catch(SocketTimeoutException ex) {
			JOptionPane.showMessageDialog(null, "Connection to the course database timed out. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		catch(IOException ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Couldn't process input " + semester + " " + year + ".", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		
		return urls;
	}
	
	/**
	 * Process a URL. This puts the data in Course.courses, but doesn't save the data to the file system
	 * 
	 * @param url The URL to process
	 */
	private void scrapeUrl(URL url){
		
		Document html;
		
		try{
			html = Jsoup.parse(url, 5000);
		}
		catch(IOException ex){
			JOptionPane.showMessageDialog(null, "Couldn't parse url " + url, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Elements courses = html.getElementsByTag("tbody");
		Elements headers = html.getElementsByClass("course-header");
		Elements closed = html.getElementsByClass("closed");
		Elements cancelled = html.getElementsByClass("cancelled");
		
		
		String section = "";
		String number = "";
		String name = "";
		String type = "";
		Element sessionLabel = null;
		Course current = null;
		
		for(Element e : courses){

			if(headers.contains(e)){
				number = e.getElementsByClass("number").text();
				name = e.getElementsByClass("name").text();
				current = addCourseToDatabase(number, name, Course.courses);
			}
			else {
				for(Element r : e.getElementsByTag("tr")){
					for(Element row : r.getElementsByClass("st")){
						Elements data = row.getElementsByTag("td");
						for(int i = 0; i < data.size(); i++){
							if(data.get(i).className().equals("session-label")){
								sessionLabel = data.get(i);
								data.remove(i);
							}
						}
						
						if(data.size() == 10 && (!data.get(5).text().equals("") || !data.get(6).text().equals(""))){
							
							if(!closed.contains(e) && !cancelled.contains(e)) {
								addSectionToDatabase(data.get(0).text(), data.get(1).text(), Integer.parseInt(data.get(2).text()), data.get(6).text(), sanitizeText(data.get(5).text(), "day"), data.get(8).text(), "", current, false);
							}
							
							current.setShouldHaveSection(data.get(1).text());
						}
						else if(data.size() == 11 && (!data.get(5).text().equals("") || !data.get(6).text().equals(""))){
							
							if(!closed.contains(e) && !cancelled.contains(e)) {
								addSectionToDatabase(data.get(0).text(), data.get(1).text(), Integer.parseInt(data.get(2).text()), data.get(6).text(), sanitizeText(data.get(5).text(), "day"), data.get(9).text(), "", current, false);
							}
							
							current.setShouldHaveSection(data.get(1).text());
						}
						else if(data.size() == 9){
							section = sanitizeText(data.get(0).text(), "something");
							type = sanitizeText(data.get(1).text(), "something");
							number = sanitizeText(data.get(2).text(), "something");
						}
						else if(data.size() == 6 && (!data.get(0).text().equals("") || !data.get(1).text().equals(""))){							
							if(!closed.contains(e) && !cancelled.contains(e)) {
								addSectionToDatabase(section, type, Integer.parseInt(number), data.get(1).text(), sanitizeText(data.get(0).text(), "day") + sessionLabel.text(), data.get(4).text(), sessionLabel.text(), current, false);
							}
							current.setShouldHaveSection(type);
						}
						else if(data.size() == 11 && data.get(0).text().contains("Z")){ //online classes
							if(!closed.contains(e) && !cancelled.contains(e)) {
								addSectionToDatabase(data.get(0).text(), data.get(1).text(), Integer.parseInt(data.get(2).text()), "Online", "Online", data.get(9).text(), "", current, true);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Adds a new Course to the database
	 * 
	 * @param number The number of the course to add (i.e. PHYS214)
	 * @param name The name of the course to add (i.e. Engineering Physics 2)
	 * @param database The database to add the Course to
	 */
	private Course addCourseToDatabase(String number, String name, HashMap<String, Course> database){
		if(!database.containsKey(number) || !database.get(number).getName().equals(name)){
			Course current = new Course(number, name);
			database.put(name, current);
			return current;
		}
		
		else return database.get(number);
	}
	
	/**
	 * Adds a new Section to the database. 
	 * 
	 * @param letter The letter of the section
	 * @param type The section's type (i.e. LAB)
	 * @param number The section's number (i.e. 13408)
	 * @param time The time of class for this section
	 * @param days The days of the week and starting and ending date (if applicable) for this section
	 * @param instructor The instructor for this section
	 * @param date The start and end dates for this Section, or "" if default
	 * @param course The course this section should be added to
	 * @param isOnline Whether the section is online
	 */
	private void addSectionToDatabase(String letter, String type, int number, String time, String days, String instructor, String date, Course course, boolean isOnline){
		Date startDate = new Date();
		Date endDate = new Date();
		
		if(!date.equals("")){
			String[] data = date.split("\\s+");
			
			String startMonth = data[1];
			if(data[2].contains(",")) data[2] = data[2].substring(0, data[2].indexOf(","));
			int startDay = Integer.parseInt(data[2]);
			
			String endMonth;
			int endDay;
			int endYear;
			int startYear;
			
			if(data.length == 7){
				endMonth = data[4];
				if(data[5].contains(",")) data[5] = data[5].substring(0, data[5].indexOf(","));
				endDay = Integer.parseInt(data[5]);
				
				if(data[6].contains(":")) data[6] = data[6].substring(0, data[6].indexOf(":"));
				startYear = Integer.parseInt(data[6]);
				endYear = startYear;
			}
			else{
				startYear = Integer.parseInt(data[3]);
				endMonth = data[5];
				if(data[6].contains(",")) data[6] = data[6].substring(0, data[6].indexOf(","));
				endDay = Integer.parseInt(data[6]);
				
				if(data[7].contains(":")) data[7] = data[7].substring(0, data[7].indexOf(":"));
				endYear = Integer.parseInt(data[7]);
			}
			
			startDate = new Date(startDay, startMonth, startYear);
			endDate = new Date(endDay, endMonth, endYear);
		}
		
		if (time.length() > 15)
        {
            String start = time.substring(0, time.indexOf("-") - 1);
            int startHour = Integer.parseInt(start.substring(0, start.indexOf(":")));
            int startMinute = Integer.parseInt(start.substring(start.indexOf(":") + 1, start.indexOf(":") + 3));

            String end = time.substring(time.indexOf("-") + 2);
            int endHour = Integer.parseInt(end.substring(0, end.indexOf(":")));
            int endMinute = Integer.parseInt(end.substring(end.indexOf(":") + 1, end.indexOf(":") + 3));

            if(end.contains("p") && !start.contains("a") && startHour != 12)
            {
                startHour += 12;
            }
            if(end.contains("p") && endHour != 12)
            {
                endHour += 12;
            }

            Time startTime = new Time(startHour, startMinute);
            Time endTime = new Time(endHour, endMinute);

            if(!startDate.equals(new Date()))
            {
                course.AddSection(new Section(letter, number, startTime, endTime, days, instructor, startDate, endDate, course), type);
            }
            else
            {
                course.AddSection(new Section(letter, number, startTime, endTime, days, instructor, course), type);
            }
        }
        else
        {
            if(!startDate.equals(new Date()) && !isOnline)
            {
                course.AddSection(new Section(letter, number, days, instructor, startDate, endDate, course, true), type);
            }
            else if(!isOnline)
            {
                course.AddSection(new Section(letter, number, days, instructor, course, true), type);
            }
            else {
            	course.AddSection(new Section(letter, number, days, instructor, course, false), type);
            }
        }
	}
	
	
	/**
	 * Sanitizes the input strings by removing any unicode characters and properly formatting the day string
	 * 
	 * @param text The text to sanitize
	 * @param unit The type of text that is being sanitized
	 * @return The sanitized text
	 */
	private String sanitizeText(String text, String unit){
		String cleanText = "";
		if(unit.equals("day")){
			if(text.equals("Appointment") && !Normalizer.isNormalized(text, Normalizer.Form.NFKD)){
				cleanText = Normalizer.normalize(text, Normalizer.Form.NFKD);
			}
			else if(text.equals("Appointment")){
				cleanText = text;
			}
			else{
				char c;
				
				for(int i = 0; i < text.length(); i++){
					c = text.charAt(i);
					switch(c){
						case 'M':
							cleanText += "M";
							break;
						case 'T':
							cleanText += "T";
							break;
						case 'W':
							cleanText += "W";
							break;
						case 'U':
							cleanText += "U";
							break;
						case 'F':
							cleanText += "F";
							break;
					}
				}
			}
		}
		else if (!Normalizer.isNormalized(text, Normalizer.Form.NFKD)){
			cleanText = Normalizer.normalize(text, Normalizer.Form.NFKD);
		}
		else{
			cleanText = text;
		}
		return cleanText;
	}
}
