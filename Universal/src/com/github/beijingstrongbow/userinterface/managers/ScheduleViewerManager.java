package com.github.beijingstrongbow.userinterface.managers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.github.beijingstrongbow.Course;
import com.github.beijingstrongbow.Section;
import com.github.beijingstrongbow.Time;
import com.github.beijingstrongbow.userinterface.ScheduleViewerWindow;

public class ScheduleViewerManager {
	
	private ArrayList<ArrayList<Section>> validSchedules;
	
	private ScheduleViewerWindow window;
	
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
		window.setVisible(true);
	}
	
	public void generateSchedules(ArrayList<Course> selected){
		if(selected.size() <= 0){
			JOptionPane.showMessageDialog(null, "No courses were selected", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
        validSchedules.clear();

        iterateSections(selected, 0, new ArrayList<Section>());
        
        if(validSchedules.size() <= 0){
            JOptionPane.showMessageDialog(null, "No valid schedules found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        window.getSchedulesList().removeAllElements();
        for (int i = 1; i <= validSchedules.size(); i++){
            window.getSchedulesList().addElement(Integer.toString(i));
        }
        
        showWindow();
    }
	
	private void iterateSections(ArrayList<Course> courses, int index, ArrayList<Section> schedule){
        for(Section s : courses.get(index).getSections()){
            schedule.add(s);
            boolean valid = true;
            System.out.println(s);
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
		
		String sectionTypeText = window.getSectionTypeText();
		String startTimeText = window.getStartTimeText();
		String sectionNumberText = window.getSectionNumberText();
		String instructorText = window.getInstructorText();
		
        if (startTimeText.length() > 0 && startTimeText.charAt(0) != '(' &&
            (instructorText.length() == 0 || instructorText.charAt(0) == '(') &&
            sectionNumberText.equals("")){
            return SearchCriteria.START_TIME;
        }
        else if (instructorText.length() > 0 && instructorText.charAt(0) != '(' &&
            (startTimeText.length() == 0 || startTimeText.charAt(0) == '(') &&
            sectionNumberText.equals("")){
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
	
	public class ShowDetailsListener implements ActionListener, MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2){
				showDetails();
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

		@Override
		public void actionPerformed(ActionEvent e){
			showDetails();
		}
		
		private void showDetails(){
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
	}
	
	public class NarrowResultsListener implements ActionListener{

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
                    int minute = Integer.parseInt(time.substring(time.indexOf(':') + 1, time.indexOf(' ') - 2));
                    if (time.contains("p")){
                        hour += 12;
                    }

                    Time startTime = new Time(hour, minute);
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


            if(toRemove.size() == validSchedules.size()){
            	JOptionPane.showMessageDialog(null, "No schedules found", "Error", JOptionPane.ERROR_MESSAGE);

                return;
            }
            else{
                for(int i = toRemove.size() - 1; i >= 0; i--){
                    validSchedules.remove(toRemove.get(i).intValue());
                    window.getSchedulesList().remove(toRemove.get(i).intValue());
                }
            }
		}
	}
}
