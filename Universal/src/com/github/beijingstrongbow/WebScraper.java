package com.github.beijingstrongbow;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.jar.Attributes.Name;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Contains functions related to scraping the course database
 * 
 * @author Eric
 */
public class WebScraper {
	
	//temporary
	public static void main(String[] args){
		populateDatabases("2017", "spring");
	}
	
	/**
	 * Populate the text database on the hard drive and the Course.courses hashmap with data from the
	 * online database
	 * 
	 * @param year
	 * @param semester
	 */
	public static void populateDatabases(String year, String semester){
		ArrayList<URL> urls = getUrls(year, semester);
		for(URL url : urls){
			scrapeUrl(url);
		}
		
		/*for(String key : Course.courses.keySet()){
			System.out.println(key + ", " + Course.courses.get(key));
			for(Section s : Course.courses.get(key).getSections()){
				if(s != null){
					System.out.println("\t" + s);
				}
			}
			if(Course.courses.get(key).getLab() != null){
				for(Section s : Course.courses.get(key).getLab().getSections()){
					if(s != null){
						System.out.println("\t" + s);
					}
				}
			}
			if(Course.courses.get(key).getQuiz() != null){
				for(Section s : Course.courses.get(key).getQuiz().getSections()){
					if(s != null){
						System.out.println("\t" + s);
					}
				}
			}
			if(Course.courses.get(key).getRec() != null){
				for(Section s : Course.courses.get(key).getRec().getSections()){
					if(s != null){
						System.out.println("\t" + s);
					}
				}
			}
		}*/
	}
	
	/**
	 * Get the URLs that need to be parsed
	 * 
	 * @param year The year to be parsed
	 * @param semester The semester to be parsed
	 * @return The list of all URLs that need to be parsed to input all courses
	 */
	private static ArrayList<URL> getUrls(String year, String semester){
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
					urls.add(new URL("https://courses.k-state.edu/" + semester + year + "/" + link));
				}
				
				i++;
			}
			
		}
		catch(IOException ex){
			System.err.println("URL could not be parsed!");
		}
		
		
		return urls;
	}
	
	/**
	 * Process a URL. This puts the data in Course.courses, but doesn't save the data to the file system
	 * 
	 * @param url The URL to process
	 */
	private static void scrapeUrl(URL url){
		
		Document html;
		
		try{
			html = Jsoup.parse(url, 5000);
		}
		catch(IOException ex){
			System.err.print("Couldn't parse url " + url);
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
		
		for(Element e : courses){

			if(headers.contains(e)){
				number = e.getElementsByClass("number").text();
				name = e.getElementsByClass("name").text();
				addCourseToDatabase(number, name, Course.courses);
			}
			else if(!closed.contains(e) && !cancelled.contains(e)){
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
							addSectionToDatabase(data.get(0).text(), data.get(1).text(), Integer.parseInt(data.get(2).text()), data.get(6).text(), sanitizeText(data.get(5).text(), "day"), data.get(8).text());
						}
						else if(data.size() == 11 && (!data.get(5).text().equals("") || !data.get(6).text().equals(""))){
							addSectionToDatabase(data.get(0).text(), data.get(1).text(), Integer.parseInt(data.get(2).text()), data.get(6).text(), sanitizeText(data.get(5).text(), "day"), data.get(9).text());
						}
						else if(data.size() == 9){
							section = sanitizeText(data.get(0).text(), "something");
							type = sanitizeText(data.get(1).text(), "something");
							number = sanitizeText(data.get(2).text(), "something");
						}
						else if(data.size() == 6 && (!data.get(0).text().equals("") || !data.get(1).text().equals(""))){							
							addSectionToDatabase(section, type, Integer.parseInt(number), data.get(1).text(), sanitizeText(data.get(0).text(), "day") + sessionLabel.text(), data.get(4).text());
						}
					}
				}
			}
		}
	}
	
	/**
	 * Represents the current course being processed in addCourseToDatabase and addSectionToDatabase
	 */
	private static Course current = new Course();
	
	/**
	 * Adds a new Course to the database
	 * 
	 * @param number The number of the course to add (i.e. PHYS214)
	 * @param name The name of the course to add (i.e. Engineering Physics 2)
	 * @param database The database to add the Course to
	 */
	private static void addCourseToDatabase(String number, String name, HashMap<String, Course> database){
		if(!database.containsKey(number)){
			current = new Course(number, name);
			database.put(number, current);
		}
	}
	
	/**
	 * Adds a new Section to the database. This method call must directly follow the call to 
	 * addCourseToDatabase for the course this section belongs to!
	 * 
	 * @param letter The letter of the section
	 * @param type The section's type (i.e. LAB)
	 * @param number The section's number (i.e. 13408)
	 * @param time The time of class for this section
	 * @param daysAndDate The days of the week and starting and ending date (if applicable) for this section
	 * @param instructor The instructor for this section
	 */
	private static void addSectionToDatabase(String letter, String type, int number, String time, String daysAndDate, String instructor){
		GregorianCalendar startDate = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();
		
		String[] data = daysAndDate.split("\\s+");
		
		if (data.length > 7 && daysAndDate != "Appointment")
        {
            if (data.length == 12)
            {
                if (data[6].contains(","))
                {
                    startDate.set(Integer.parseInt(data[7]), monthStringToInt(data[5]), Integer.parseInt(data[6].substring(0, data[6].length() - 1)));
                }
                else
                {
                    startDate.set(Integer.parseInt(data[7]), monthStringToInt(data[5]), Integer.parseInt(data[6]));
                }
                endDate.set(Integer.parseInt(data[11].substring(0, data[11].length() - 1)), monthStringToInt(data[9]), Integer.parseInt(data[10].substring(0, data[10].length() - 1)));
            }
            else
            {
                if (data.length == 12)
                {
                    if (data[6].contains(","))
                    {
                        startDate.set(Integer.parseInt(data[10]), monthStringToInt(data[5]), Integer.parseInt(data[6].substring(0, data[6].length() - 1)));
                    }
                    else
                    {
                        startDate.set(Integer.parseInt(data[10]), monthStringToInt(data[5]), Integer.parseInt(data[6]));
                    }
                    endDate.set(Integer.parseInt(data[10].substring(0, data[11].length() - 1)), monthStringToInt(data[8]), Integer.parseInt(data[9].substring(0, data[9].length() - 1)));
                }
            }
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

            LocalTime startTime = LocalTime.of(startHour, startMinute);
            LocalTime endTime = LocalTime.of(endHour, endMinute);

            if(!startDate.equals(new GregorianCalendar()))
            {
                current.AddSection(new Section(letter, number, startTime, endTime, data[0], instructor, startDate, endDate, current), type);
            }
            else
            {
                current.AddSection(new Section(letter, number, startTime, endTime, data[0], instructor, current), type);
            }
        }
        else
        {
            if(!startDate.equals(new GregorianCalendar()))
            {
                current.AddSection(new Section(letter, number, data[0], instructor, startDate, endDate, current), type);
            }
            else
            {
                current.AddSection(new Section(letter, number, data[0], instructor, current), type);
            }
        }
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
                return 1;
            case "february":
                return 2;
            case "march":
                return 3;
            case "april":
                return 4;
            case "may":
                return 5;
            case "june":
                return 6;
            case "july":
                return 7;
            case "august":
                return 8;
            case "september":
                return 9;
            case "october":
                return 10;
            case "november":
                return 11;
            case "december":
                return 12;
            default:
                return -1;
        }
    }
	
	/**
	 * Sanitizes the input strings by removing any unicode characters and properly formatting the day string
	 * 
	 * @param text The text to sanitize
	 * @param unit The type of text that is being sanitized
	 * @return The sanitized text
	 */
	private static String sanitizeText(String text, String unit){
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
