package com.github.beijingstrongbow.userinterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import com.github.beijingstrongbow.Section;
import com.github.beijingstrongbow.Time;
import com.github.beijingstrongbow.Section.SectionBasis;

public class CalendarPanel extends JPanel{
	
	private Graphics2D graphics;
	
	private FontMetrics metrics;
	
	private Row[] rows;
	
	private Column[] columns;
	
	private Color[] colors;
	
	private final int width = 137;
	
	private final int height = 27;
	
	private final int leftEdge = 108;
	
	private final int topEdge = 35;
	
	private int currentColor = 0;
	
	private ArrayList<Section> sections;
	
	private static HashMap<CalendarBlock, Section> sectionsAdded = new HashMap<CalendarBlock, Section>();
	
	private static ArrayList<Color> usedColors = new ArrayList<Color>();
	
	public CalendarPanel(ArrayList<Section> sections){
		setLayout(new GridLayout(1, 1));
		
		this.sections = sections;
		
		Row[] rows = {
				new Row(topEdge, new Time(7, 0)),
				new Row(topEdge + height, new Time(8, 0)),
				new Row(topEdge + 2 * height, new Time(9, 0)),
				new Row(topEdge + 3 * height, new Time(10, 0)),
				new Row(topEdge + 4 * height, new Time(11, 0)),
				new Row(topEdge + 5 * height, new Time(12, 0)),
				new Row(topEdge + 6 * height, new Time(13, 0)),
				new Row(topEdge + 7 * height, new Time(14, 0)),
				new Row(topEdge + 8 * height, new Time(15, 0)),
				new Row(topEdge + 9 * height, new Time(16, 0)),
				new Row(topEdge + 10 * height, new Time(17, 0)),
				new Row(topEdge + 11 * height, new Time(18, 0)),
				new Row(topEdge + 12 * height, new Time(19, 0)),
				new Row(topEdge + 13 * height, new Time(20, 0))
		};
		
		this.rows = rows;
		
		Column[] columns = {
				new Column(leftEdge, "Monday"),
				new Column(leftEdge + width, "Tuesday"),
				new Column(leftEdge + 2 * width, "Wednesday"),
				new Column(leftEdge + 3 * width, "Thursday"),
				new Column(leftEdge + 4 * width, "Friday")
		};
		
		this.columns = columns;
		
		Color[] colors = {
			new Color(213, 0, 0), //tomato
			new Color(142, 36, 170), //grape
			new Color(11, 128, 67), //basil
			new Color(244, 81, 30), //tangerine
			new Color(63, 81, 181), //blueberry
			new Color(51, 182, 121), //sage
			new Color(3, 155, 229), //peacock
			new Color(246, 191, 38), //banana
			new Color(121, 134, 203), //lavender
			new Color(230, 124, 115), //flamingo
		};
		
		this.colors = colors;
	}
	
	@Override
	public void paintComponent(Graphics g){		
		super.paintComponent(g);
		this.validate();
		this.setBackground(Color.WHITE);
		Font textFont = new Font("Tahoma", Font.PLAIN, 20);
		currentColor = 0;
		
		graphics = (Graphics2D) g;
		graphics.setStroke(new BasicStroke(1));
		graphics.setPaint(Color.GRAY);
		graphics.setFont(textFont);
				
		int xCoord = leftEdge;
		int yCoord = topEdge;
		
		//iterate through the columns
		int textX;
		int textY;
		metrics = graphics.getFontMetrics(textFont);
		
		for(int column = 0; column < columns.length; column++){
			textY = (int) (rows[0].getY() - (0.25 * metrics.getHeight()));
			
			//center the text in the column
			textX = (int) (columns[column].getX() + 0.5 * (width - metrics.stringWidth(columns[column].getDay())));
			
			drawString(columns[column].getDay(), textX, textY);
			
			for(int row = 0; row < rows.length; row++){
				if(column == 0){
					textX = leftEdge - metrics.stringWidth(rows[row].getTime().toString()) - 3;
					
					textY = (int) (rows[row].getY() + 0.25 * metrics.getHeight());
					
					drawString(rows[row].getTime().toString(), textX, textY);
				}
				
				graphics.draw(new Rectangle(columns[column].getX(), rows[row].getY(), width, height));
			}
		}
		
		textX = leftEdge - metrics.stringWidth(rows[rows.length - 1].getTime().toString()) - 3;
		textY = (int) (rows[rows.length - 1].getY() + height + 0.25 * metrics.getHeight());
		
		drawString("9:00 p.m.", textX, textY);
		
		for(Section s : sections){
			addSection(s);
		}
	}
	
	private void drawString(String s, int xCoord, int yCoord){
		Paint p = graphics.getPaint();
		graphics.setPaint(Color.BLACK);
		graphics.drawString(s, xCoord, yCoord);
		graphics.setPaint(p);
	}
		
	private void addSection(Section s){
		
		if(s.getSectionBasis() == SectionBasis.APPOINTMENT || s.getSectionBasis() == SectionBasis.ONLINE){
			return;
		}
		
		int x = 0;
		int y = 0;
		
		int width = 0;
		int height = 0;
		
		int textX;
		int textY;
		
		for(char day : s.getDays().toCharArray()){
			for(int i = 0; i < columns.length; i++){
				if(columns[i].getDayLetter() == day){
					x = (int) (columns[i].getX());
					width = (int) (this.width);
				}
			}
		
			for(int i = 0; i < rows.length; i++){
				if(s.getStartTime().getHour() == rows[i].getTime().getHour()){
					y = (int) (rows[i].getY() + this.height * (s.getStartTime().getMinute() / 60.0));
				}
			}

			if(s.getEndTime().getHour() > 21 || (s.getEndTime().getHour() == 21 && s.getEndTime().getMinute() > 0)){
				height = (int) (rows[rows.length - 1].getY() + this.height + 15 - y);
			}
			else if(s.getEndTime().getHour() == 21 && s.getEndTime().getMinute() == 0){
				height = (int) (rows[rows.length - 1].getY() + this.height - y);
			}
			else{
				for(int i = 0; i < rows.length; i++){
					if(s.getEndTime().getHour() == rows[i].getTime().getHour()){
						height = (int) (rows[i].getY() + this.height * (s.getEndTime().getMinute() / 60.0)) - y;
					}
				}
			}
			
			textX = (int) (x + 0.5 * (width - metrics.stringWidth(s.getCourse().getNumber())));
			textY = (int) (y + metrics.getHeight() - 5);
			
			boolean colorSet = false;
			CalendarBlock block = new CalendarBlock(new Rectangle(x, y, width, height));
			
			for(CalendarBlock b : sectionsAdded.keySet()){
				if(sectionsAdded.get(b).getCourse().equals(s.getCourse())){
					block.setColor(b.getColor());
					colorSet = true;
					break;
				}
			}
			
			if(!colorSet){
				while(usedColors.contains(colors[currentColor])) {
					if(currentColor < colors.length - 1) {
						currentColor++;
					}
					else currentColor = 0;
				}
				
				block.setColor(colors[currentColor]);
				usedColors.add(colors[currentColor]);
			}
			
			sectionsAdded.put(block, s);
			block.paint(graphics);
			drawString(s.getCourse().getNumber(), textX, textY);
		}
	}
	
	/**
	 * Get the Section that was clicked
	 * 
	 * @param e The MouseEvent describing the click event
	 * @return The Section that was clicked
	 */
	public Section getSectionClicked(MouseEvent e) {
		Point click = new Point(e.getX(), e.getY());
		
		for(CalendarBlock b : sectionsAdded.keySet()) {
			Rectangle r = b.getRectangle();

			if(b.getRectangle().contains(click)) {
				return sectionsAdded.get(b);
			}
		}
		
		return null;
	}
	
	private class Row {
		private int y;
		
		private Time time;
		
		private Row(int y, Time time){
			this.y = y;
			this.time = time;
		}
		
		private int getY(){
			return y;
		}
		
		private Time getTime(){
			return time;
		}
	}
	
	private class Column {
		private int x;
		
		private String day;
		
		private Column(int x, String day){
			this.x = x;
			
			this.day = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();
		}
		
		private int getX(){
			return x;
		}
		
		private String getDay(){
			return day;
		}
		
		private char getDayLetter(){
			if(day.equals("Thursday")){
				return 'U';
			}
			else{
				return day.charAt(0);
			}
		}
	}
	
	/**
	 * Represents a rectangle on the calendar
	 * 
	 * @author ericd
	 */
	private class CalendarBlock {
		
		/**
		 * The Rectangle instance defining the bounds of the block
		 */
		private Rectangle rectangle;
		
		/**
		 * The color the block should be drawn in
		 */
		private Color color;
		
		/**
		 * Initialize this CalendarBlock with only a bounds, and no color
		 * 
		 * @param rectangle The Rectangle instance defining the bounds of the block
		 */
		private CalendarBlock(Rectangle rectangle) {
			this.rectangle = rectangle;
		}
		
		/**
		 * Initialize this CalendarBlock with only a color, and no bounds
		 * 
		 * @param color The color the block should be drawn in
		 */
		private CalendarBlock(Color color) {
			this.color = color;
		}
		
		/**
		 * Initialize a new block on the calendar
		 * 
		 * @param rectangle The Rectangle instance defining the bounds of the block
		 * @param color The color the block should be drawn in
		 */
		private CalendarBlock(Rectangle rectangle, Color color) {
			this.rectangle = rectangle;
			this.color = color;
		}
		
		/**
		 * Get the color of this CalendarBlock
		 * 
		 * @return The color of this CalendarBlock
		 */
		private Color getColor() {
			return color;
		}
		
		/**
		 * Get the Rectangle that defines the bounds of this CalendarBlock
		 * 
		 * @return The Rectangle that defines the bounds of this CalendarBlock
		 */
		private Rectangle getRectangle() {
			return rectangle;
		}
		
		/**
		 * Set the Color this block should be drawn in
		 * 
		 * @param color The Color this block should be drawn in
		 */
		private void setColor(Color color) {
			this.color = color;
		}
		
		/**
		 * Set the Rectangle defining the bounds of this block
		 * 
		 * @param rectangle The Rectangle defining the bounds of this block
		 */
		private void setRectangle(Rectangle rectangle) {
			this.rectangle = rectangle;
		}
		
		/**
		 * Draw the block on the calendar
		 * 
		 * @param graphics The Graphics2D instance that should be used to draw the block
		 */
		private void paint(Graphics2D graphics) {
			graphics.setPaint(color);
			graphics.fill(rectangle);
		}
		
	}
}
