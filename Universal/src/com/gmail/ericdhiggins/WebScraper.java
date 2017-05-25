package com.gmail.ericdhiggins;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
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
	
	/**
	 * Populate the text database on the hard drive and the Course.courses hashmap with data from the
	 * online database
	 * 
	 * @param year
	 * @param semester
	 */
	public void populateDatabases(String year, String semester){
		ArrayList<URL> urls = getUrls(year, semester);
		
		Thread[] threads = new Thread[urls.size()];
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
			
			for(Element e : links){
				String link = e.attr("href");
				
				if(link.length() < 7 && link != "/" && link != "None"){
					urls.add(new URL("https://courses.k-state.edu/" + semester + year + "/" + link));
				}
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
	private void proccessUrl(URL url){
		
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
		
		for(Element e : courses){
			if(headers.contains(e)){
				number = e.getElementsByClass("number").text();
				name = e.getElementsByClass("name").text();
				//write this header to the file
			}
			else if(!closed.contains(e) && !cancelled.contains(e)){
				for(Element r : e.getElementsByTag("tr")){
					for(Element row : e.getElementsByClass("st")){
						int counter = 0;
						
						Elements data = row.getElementsByTag("td");
						for(Element f : data){
							Element[] g = (Element[]) f.getElementsByAttributeValueNot("class", "section-label").toArray();
							
							if(g.length == 10 && g[5].text() != "" || g[6].text() != ""){
								//Write this section to the file
							}
							else if(g.length == 11 && g[5].text() != "" || g[6].text() != ""){
								//Write this section to the file
							}
							else if(g.length == 9){
								section = sanitizeText(g[8].text(), "something");
								type = sanitizeText(g[1].text(), "something");
								number = sanitizeText(g[2].text(), "something");
							}
							else if(g.length == 6 && (g[0].text() != "" || g[1].text() != "")){
								//Write this section to the file
							}
						}
					}
				}
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
	
	/**
	 * Whether an element should be loaded into the database, based on its CSS class
	 * 
	 * @param cssClass The CSS class of the element
	 * @return Whether the element should be loaded
	 */
	private boolean shouldLoad(String cssClass){
		return cssClass.equals("section");
	}
	
	/**
	 * Run by the individual threads to parse each URL
	 * 
	 * @author Eric
	 */
	private class UrlParser implements Runnable{

		@Override
		public void run() {
			
		}
		
	}
}
