package com.github.beijingstrongbow.userinterface.managers;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.github.beijingstrongbow.Course;
import com.github.beijingstrongbow.Section;
import com.github.beijingstrongbow.Section.SectionBasis;
import com.github.beijingstrongbow.update.UpdateHandler;
import com.github.beijingstrongbow.Time;
import com.github.beijingstrongbow.userinterface.CalendarPanel;
import com.github.beijingstrongbow.userinterface.ScheduleViewerWindow;

public class ScheduleViewerManager {
	
	private ArrayList<ArrayList<Section>> validSchedules;
	
	private ScheduleViewerWindow window;
	
	private boolean isCalendarMode = true;
	
	private int selectedIndex = -1;
	
	private CalendarPanel currentCalendar;
	
	private enum SearchCriteria{
		INSTRUCTOR,
		SECTION_NUMBER,
		INVALID;
	}
	
	public ScheduleViewerManager(){
		validSchedules = new ArrayList<ArrayList<Section>>();
		window = new ScheduleViewerWindow(this);
	}
	
	public void showWindow(){
		window.getDetailsList().removeAllElements();
		window.viewDetails();
		window.setVisible(true);
	}
	
	public boolean generateSchedules(ArrayList<Course> selected, boolean showOnline){
		if(selected.size() <= 0){
			JOptionPane.showMessageDialog(null, "No courses were selected", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
        validSchedules.clear();
        
        iterateSections(selected, showOnline, 0, new ArrayList<Section>());
        
        if(validSchedules.size() <= 0){
            JOptionPane.showMessageDialog(null, "No valid schedules found", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        window.getSchedulesList().removeAllElements();
        for (int i = 1; i <= validSchedules.size(); i++){
            window.getSchedulesList().addElement(Integer.toString(i));
        }
        return true;
    }
	
	private void iterateSections(ArrayList<Course> courses, boolean showOnline, int index, ArrayList<Section> schedule){
        for(Section s : courses.get(index).getSections()){
            if (showOnline || s.getSectionBasis() != SectionBasis.ONLINE) {
            	schedule.add(s);
            }
            else {
            	continue;
            }

            boolean valid = true;
            if(index == courses.size() - 1){
                for(int i = 0; i < schedule.size(); i++){
                    for(int j = i+1; j < schedule.size(); j++){
                        if (schedule.get(i).overlaps(schedule.get(j))){
                            valid = false;
                            break;
                        }
                    }
                    if (!valid){
                        break;
                    }
                }
                if (valid){
                    ArrayList<Section> temp = new ArrayList<Section>();
                    for(Section v : schedule){
                    	temp.add(v);
                    }
                    validSchedules.add(temp);
                }
            }
            else{
                iterateSections(courses, showOnline, index + 1, schedule);
            }
            schedule.remove(s);
        }
    }
	
	private SearchCriteria getSearchCriteria(){
		
		String sectionNumberText = window.getSectionNumberText();
		String instructorText = window.getInstructorText();
		
        if (instructorText.length() > 0 && instructorText.charAt(0) != '(' &&
            (sectionNumberText.equals("") || sectionNumberText.charAt(0) == '(')){
            return SearchCriteria.INSTRUCTOR;
        }
        else if(!sectionNumberText.equals("") &&
            (instructorText.length() == 0 || instructorText.charAt(0) == '(')){
            return SearchCriteria.SECTION_NUMBER;
        }
        else{
            return SearchCriteria.INVALID;
        }
    }
	
	private void showDetails(int selectedIndex){
		window.viewDetails();
        window.getDetailsList().removeAllElements();
        int index = window.getSelectedSchedule();
        
        if(index >= 0){
        	ArrayList<Section> schedule = validSchedules.get(window.getSelectedSchedule());
            Section.sort(schedule);
            for(Section s : schedule){
                window.getDetailsList().addElement(s);
            }
        }
    }

	private void showCalendar(int selectedIndex) {
		
		if(selectedIndex < 0){
			return;
		}
		else{
			int numAppointments = 0;
			int numOnline = 0;
			
			for(Section s : validSchedules.get(selectedIndex)){
				if(s.getSectionBasis() == SectionBasis.APPOINTMENT) numAppointments++;
				else if(s.getSectionBasis() == SectionBasis.ONLINE) numOnline++;
			}
			
			currentCalendar = new CalendarPanel(validSchedules.get(selectedIndex));
			currentCalendar.addMouseListener(new CalendarClickListener());
			window.viewCalendar(currentCalendar, numAppointments, numOnline);
		}
	}
	
	/**
	 * Remove a list of schedules from the list of possible schedules on the UI
	 * 
	 * @param toRemove The list of the indices of the schedules to remove
	 */
	private void removeSchedules(ArrayList<Integer> toRemove) {
		if(toRemove.size() == validSchedules.size()){
        	JOptionPane.showMessageDialog(null, "No schedules found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            for(int i = toRemove.size() - 1; i >= 0; i--){
                validSchedules.remove(toRemove.get(i).intValue());
                window.getSchedulesList().remove(toRemove.get(i).intValue());
            }
            window.setDefaultText(null);
        }
	}
	
	public class CalendarClickListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			Section clicked = currentCalendar.getSectionClicked(e);
			
			int choice = JOptionPane.showOptionDialog(null, "Are you sure you want to narrow results by requiring " + clicked.getCourse().getNumber() + " to be at " + clicked.getStartTime() + " on " + clicked.getDays() + "?", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						
			ArrayList<Integer> toRemove = new ArrayList<Integer>();
			
			if(clicked != null && choice == 0) {
                for (int i = 0; i < validSchedules.size(); i++){
                    for(Section s : validSchedules.get(i)){
                        if (s.getCourse().getNumber().equals(clicked.getCourse().getNumber()) && s.getType().equals(clicked.getType()) && s.getNumber() != clicked.getNumber()){
                            toRemove.add(i);
                        }
                    }
                }
                
                removeSchedules(toRemove);
                JOptionPane.showMessageDialog(null, "Results narrowed. All schedules now have section number " + clicked.getNumber() + " for " + clicked.getCourse().getNumber());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	
	public class NarrowResultsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SearchCriteria searchType = getSearchCriteria();
            String courseNumber = window.getCourseNumberText().trim();
            courseNumber = courseNumber.replace(" ", "");
            ArrayList<Integer> toRemove = new ArrayList<Integer>();

            if(searchType == SearchCriteria.INSTRUCTOR){
                String instructor = window.getInstructorText();

                for(int i = 0; i < validSchedules.size(); i++){
                    for(Section s : validSchedules.get(i)){
                        if(s.getCourse().getNumber().equals(courseNumber) && s.getType().equals("LEC") && !s.getInstructor().toLowerCase().equals(instructor.toLowerCase())){
                            toRemove.add(i);
                        }
                    }
                }
            }
            else if(searchType == SearchCriteria.SECTION_NUMBER){
            	Course course = null;
            	String sectionType = "";
            	int sectionNumber;
            	
            	try {
                	sectionNumber = Integer.parseInt(window.getSectionNumberText());
            	}
            	catch(NumberFormatException ex) {
                	JOptionPane.showMessageDialog(null, "Section number must be an integer", "Error", JOptionPane.ERROR_MESSAGE);
                	return;
            	}
            	
            	for(Course c : Course.courses.values()) {
            		if(c.getNumber().toUpperCase().equals(courseNumber.toUpperCase())) {
            			course = c;
            			break;
            		}
            	}
            	
            	if(course == null) {
                	JOptionPane.showMessageDialog(null, "Invalid course number", "Error", JOptionPane.ERROR_MESSAGE);
                	return;
            	}
            	
            	for(Section s : course.getSections()) {
            		if(s.getNumber() == sectionNumber) sectionType = "LEC";
            	}
            	if(course.getLab() != null && sectionType.equals("")) {
            		for(Section s : course.getLab().getSections()) {
            			if(s.getNumber() == sectionNumber) sectionType = "LAB";
            		}
            	}
            	if(course.getQuiz() != null && sectionType.equals("")) {
            		for(Section s : course.getQuiz().getSections()) {
            			if(s.getNumber() == sectionNumber) sectionType = "QZ";
            		}
            	}
            	if(course.getRec() != null && sectionType.equals("")) {
            		for(Section s : course.getRec().getSections()) {
            			if(s.getNumber() == sectionNumber) sectionType = "REC";
            		}
            	}
            	
            	for (int i = 0; i < validSchedules.size(); i++){
                    for(Section s : validSchedules.get(i)){
                        if (s.getCourse().getNumber().equals(courseNumber) && s.getType().equals(sectionType) && s.getNumber() != sectionNumber){
                            toRemove.add(i);
                        }
                    }
                }
            }
            else{
            	JOptionPane.showMessageDialog(null, "Only one search criterion is allowed", "Error", JOptionPane.ERROR_MESSAGE);

                return;
            }


            removeSchedules(toRemove);
		}
	}
	
	public class ShowCalendarListener implements ActionListener, MouseListener, KeyListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			isCalendarMode = true;
			showCalendar(window.getSelectedSchedule());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			changeScheduleView(window.getSelectedSchedule());
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				if(window.getSelectedSchedule() != selectedIndex - 1 && selectedIndex > 0) {
					changeScheduleView(selectedIndex - 1);
				}
				else {
					changeScheduleView(window.getSelectedSchedule());
				}
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				if(window.getSelectedSchedule() != selectedIndex + 1 && selectedIndex < window.getSchedulesList().size() - 1) {
					changeScheduleView(selectedIndex + 1);
				}
				else {
					changeScheduleView(window.getSelectedSchedule());
				}
			}
		}
		
		@Override
		public void keyTyped(KeyEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
		
		/**
		 * Change the calendar or details view to match the selected index in the schedules list
		 */
		private void changeScheduleView(int index) {
			if(selectedIndex != index) {
				selectedIndex = index;
				if(isCalendarMode) {
					showCalendar(index);
				}
				else {
					showDetails(index);
				}
			}
		}
	}
	
	public class ShowDetailsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e){
			showDetails(window.getSelectedSchedule());
			isCalendarMode = false;
		}
	}
	
	/**
	 * Handles showing the default text in each text field (the example text)
	 * 
	 * @author ericd
	 */
	public class TextFieldDefaultHandler implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			if(e.getComponent() instanceof JTextField) {
				JTextField field = (JTextField) e.getComponent();
				
				if(field.getForeground().equals(Color.GRAY)) {
					field.setForeground(Color.BLACK);
					field.setText("");
				}
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if(e.getComponent() instanceof JTextField) {
				JTextField field = (JTextField) e.getComponent();
				if(field.getText().equals("")) {
					field.setForeground(Color.GRAY);
					window.setDefaultText(field);
				}
			}
		}
	}
	
	public class WindowCloseListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {}
		@Override
		public void windowClosing(WindowEvent e) {
			selectedIndex = -1;
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}
		@Override
		public void windowIconified(WindowEvent e) {	}
		@Override
		public void windowDeiconified(WindowEvent e) {	}
		@Override
		public void windowActivated(WindowEvent e) {}
		@Override
		public void windowDeactivated(WindowEvent e) {}
	}
	
	/**
	 * Saves the schedule the user has selected to a CSV file. Specifically, this saves the
	 * details view.
	 * 
	 * @author ericd
	 */
	public class SaveScheduleListener implements ActionListener {

		/**
		 * Called when the user clicks the Save Schedule button
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(selectedIndex < 0) {
				JOptionPane.showMessageDialog(null, "No schedule was selected to save", "Error", JOptionPane.ERROR_MESSAGE, null);
				return;
			}
			
			File currentLoc;
			try {
				currentLoc = new File(UpdateHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().substring(1));
			} catch (URISyntaxException ex) {
				ex.printStackTrace();
				return;
			}
			
			currentLoc = currentLoc.getParentFile();
			JFileChooser saveLocChooser = new JFileChooser();
			saveLocChooser.setCurrentDirectory(currentLoc);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv", "txt");
			saveLocChooser.setFileFilter(filter);
			saveLocChooser.showSaveDialog(null);
			
			if(saveLocChooser.getSelectedFile() == null) {
				return;
			}
			
			try {
				File saveFile = processFileExtension(saveLocChooser.getSelectedFile());
				if(!saveFile.exists()) {
					saveFile.createNewFile();
				}
		
				writeFile(new PrintWriter(new FileOutputStream(saveFile), true), validSchedules.get(window.getSelectedSchedule()));
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
		/**
		 * Adds a file extension if the user didn't provide one. If the user did give an
		 * extension, that extension is kept - even if it isn't .csv
		 * 
		 * @param f The file chosen by the user
		 * @return The new file, with a proper extension.
		 */
		public File processFileExtension(File f) {
			int extensionIndex = f.getPath().lastIndexOf('.');
			File newFile;
			
			if(extensionIndex < 0) {
				newFile = new File(f.getPath() + ".csv");
			}
			else {
				newFile = f;
			}
			return newFile;
		}
		
		/**
		 * Write the data to the file
		 * 
		 * @param output The file to write to
		 */
		public void writeFile(PrintWriter output, ArrayList<Section> schedule) {
			Section.sort(schedule);
			
			output.write("Course Number,Course Name,Section Number,Days,Time,Instructor\n");
			
			for(Section s : schedule) {
		
				output.write(s.getCourse().getNumber() + "," + 
						s.getCourse().getName() + "," + 
						s.getNumber() + "," +
						s.getDays() + "," +
						s.timeAsString() + "," + 
						s.getInstructor().replaceAll(",", "") + "\n");
			}
			
			output.flush();
			output.close();
			
			JOptionPane.showMessageDialog(null, "Schedule saved", "Info", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
}
