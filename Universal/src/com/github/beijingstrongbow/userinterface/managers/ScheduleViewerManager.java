package com.github.beijingstrongbow.userinterface.managers;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.github.beijingstrongbow.Course;
import com.github.beijingstrongbow.Section;
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
		START_TIME,
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
	
	public boolean generateSchedules(ArrayList<Course> selected){
		if(selected.size() <= 0){
			JOptionPane.showMessageDialog(null, "No courses were selected", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
        validSchedules.clear();
        
        iterateSections(selected, 0, new ArrayList<Section>());
        
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
	
	private void iterateSections(ArrayList<Course> courses, int index, ArrayList<Section> schedule){
        for(Section s : courses.get(index).getSections()){
            schedule.add(s);
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
                iterateSections(courses, index + 1, schedule);
            }
            schedule.remove(s);
        }
    }
	
	private SearchCriteria getSearchCriteria(){
		
		String startTimeText = window.getStartTimeText();
		String sectionNumberText = window.getSectionNumberText();
		String instructorText = window.getInstructorText();
		
        if (startTimeText.length() > 0 && startTimeText.charAt(0) != '(' &&
            (instructorText.length() == 0 || instructorText.charAt(0) == '(') &&
            (sectionNumberText.equals("") || sectionNumberText.charAt(0) == '(')){
            return SearchCriteria.START_TIME;
        }
        else if (instructorText.length() > 0 && instructorText.charAt(0) != '(' &&
            (startTimeText.length() == 0 || startTimeText.charAt(0) == '(') &&
            (sectionNumberText.equals("") || sectionNumberText.charAt(0) == '(')){
            return SearchCriteria.INSTRUCTOR;
        }
        else if(!sectionNumberText.equals("") &&
            (startTimeText.length() == 0 || startTimeText.charAt(0) == '(') &&
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
			
			for(Section s : validSchedules.get(selectedIndex)){
				if(s.isAppointment()) numAppointments++;
			}
			
			currentCalendar = new CalendarPanel(validSchedules.get(selectedIndex));
			currentCalendar.addMouseListener(new CalendarClickListener());
			window.viewCalendar(currentCalendar, numAppointments);
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
            window.setDefaultText();
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
            String sectionType = window.getSectionTypeText().trim();
            ArrayList<Integer> toRemove = new ArrayList<Integer>();

            if(!sectionType.equals("")){
                if (sectionType.toUpperCase().contains("Q")){
                    sectionType = "QZ";
                }
                else if (sectionType.toUpperCase().contains("R")){
                    sectionType = "REC";
                }
                else if (sectionType.toUpperCase().contains("L") || sectionType.toUpperCase().contains("D")){
                    sectionType = "LAB";
                }
                else{
                    JOptionPane.showMessageDialog(null, "Invalid section type! Valid types are Lab, Std, Rec, and Quiz", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            else{
                sectionType = "LEC";
            }

            if(searchType == SearchCriteria.START_TIME){
                try{
                    String time = window.getStartTimeText();
                    int hour = Integer.parseInt(time.substring(0, time.indexOf(':')));
                    int minute = Integer.parseInt(time.substring(time.indexOf(':') + 1, time.indexOf(':') + 3));
                    if (time.contains("p")){
                        hour += 12;
                    }

                    Time startTime = new Time(hour, minute);
                    System.out.println(hour + " " + minute);
                    for(int i = 0; i < validSchedules.size(); i++){
                        for (Section s : validSchedules.get(i)){
                            if (s.getCourse().getNumber().equals(courseNumber) && s.getType().equals(sectionType) && !startTime.equals(s.getStartTime())){
                                toRemove.add(i);
                            }
                        }
                    }
                }
                catch (IndexOutOfBoundsException ex){
                	JOptionPane.showMessageDialog(null, "Time should be formatted as hh:mm/h:mm a.m./p.m. (e.g. 4:30 p.m.)", "Error", JOptionPane.ERROR_MESSAGE);
                	return;
                }
                catch(NumberFormatException ex){
                	JOptionPane.showMessageDialog(null, "Time should be formatted as hh:mm/h:mm a.m./p.m. (e.g. 4:30 p.m.)", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(searchType == SearchCriteria.INSTRUCTOR){
                String instructor = window.getInstructorText();

                for(int i = 0; i < validSchedules.size(); i++){
                    for(Section s : validSchedules.get(i)){
                        if(s.getCourse().getNumber().equals(courseNumber) && s.getType().equals(sectionType) && !s.getInstructor().toLowerCase().equals(instructor.toLowerCase())){
                            toRemove.add(i);
                        }
                    }
                }
            }
            else if(searchType == SearchCriteria.SECTION_NUMBER){
                try{
                    int number = Integer.parseInt(window.getSectionNumberText());

                    for (int i = 0; i < validSchedules.size(); i++){
                        for(Section s : validSchedules.get(i)){
                            if (s.getCourse().getNumber().equals(courseNumber) && s.getType().equals(sectionType) && s.getNumber() != number){
                                toRemove.add(i);
                            }
                        }
                    }
                }
                catch (NumberFormatException ex){
                	JOptionPane.showMessageDialog(null, "Invalid section number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
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
			System.out.println(index + 1);
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
}
