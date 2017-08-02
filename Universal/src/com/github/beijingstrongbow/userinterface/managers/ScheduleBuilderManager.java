package com.github.beijingstrongbow.userinterface.managers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.github.beijingstrongbow.Course;
import com.github.beijingstrongbow.Main;
import com.github.beijingstrongbow.Main.ProgramState;
import com.github.beijingstrongbow.Section;
import com.github.beijingstrongbow.userinterface.ScheduleBuilderWindow;

public class ScheduleBuilderManager {
	
	private ScheduleBuilderWindow window;
	
	public ScheduleBuilderManager(){
		window = new ScheduleBuilderWindow(this);
	}
	
	public void showWindow(){
		window.setVisible(true);
	}
	
	public class SearchButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			SearchMethod searchMethod = getSearchMethod();
			DefaultListModel<Course> searchResults = window.getSearchResults();
			ArrayList<Course> unsortedResults = new ArrayList<Course>();
			
            boolean found = false;
            searchResults.removeAllElements();
            
            if(searchMethod == SearchMethod.COURSE_NAME){
                String name = window.getCourseNameSearchText().trim();
                for(String courseName : Course.courses.keySet()){
                    if (courseName.toUpperCase().contains(name.toUpperCase())){
                        unsortedResults.add(Course.courses.get(courseName));
                        found = true;
                    }
                }
                if (!found){
                    JOptionPane.showMessageDialog(null, "No course called " + name + " was found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(searchMethod == SearchMethod.COURSE_NUMBER){
                String number = window.getCourseNumSearchText().replaceAll("\\s+", "");
                
                for(Course c : Course.courses.values()){
                    if(c.getNumber().toUpperCase().contains(number.toUpperCase())){
                    	unsortedResults.add(c);
                    	found = true;
                    }
                }
                if (!found){
                    JOptionPane.showMessageDialog(null, "No course numbered " + number + " was found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Invalid search criteria", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            Course.sort(unsortedResults);
            
            for(Course c : unsortedResults){
            	searchResults.addElement(c);
            }
            
            window.setCourseNameSearchText("");
            window.setCourseNumSearchText("");
		}
	}
	
	public class ResultsDoubleClickListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2){
				if(e.getSource() instanceof JList){
					JList<Course> j = (JList<Course>) e.getSource();
					
					if((DefaultListModel<Course>) j.getModel() == window.getSearchResults()){
						
						Course selected = j.getSelectedValue();
						boolean present = false;
						
						for(int i = 0; i < window.getSelectedCourses().size(); i++){
							if(selected.equals(window.getSelectedCourses().getElementAt(i))){
								present = true;
								break;
							}
						}
						
						if(!present && checkSectionsPresent(selected)){
							window.getSearchResults().removeElement(selected);
							window.getSelectedCourses().addElement(selected);
						}
					}
				}
			}
		}
		/**
		 * Check if all the required sections for each course have at least one open section
		 * 
		 * @param selected The list of courses to check
		 * @return Whether all sections that should be present are present
		 */
		private boolean checkSectionsPresent(Course c) {
			boolean toReturn = true;
			
			if(c.shouldHaveSection("LAB") && c.getLab() == null) {
                JOptionPane.showMessageDialog(null, c.getNumber() + " has no open LAB sections", "Error", JOptionPane.ERROR_MESSAGE);
                toReturn = false;
			}	
			else if(c.shouldHaveSection("QZ") && c.getQuiz() == null) {
                JOptionPane.showMessageDialog(null, c.getNumber() + " has no open QZ sections", "Error", JOptionPane.ERROR_MESSAGE);
                toReturn = false;
			}
			else if(c.shouldHaveSection("REC") && c.getRec() == null) {
                JOptionPane.showMessageDialog(null, c.getNumber() + " has no open REC sections", "Error", JOptionPane.ERROR_MESSAGE);
                toReturn = false;
			}
			else if(c.shouldHaveSection("LEC") && (c.getSections() == null || c.getSections().size() == 0)) {
                JOptionPane.showMessageDialog(null, c.getNumber() + " has no open LEC/other sections", "Error", JOptionPane.ERROR_MESSAGE);
                toReturn = false;
			}
			
			return toReturn;
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	public class RemoveItemButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			window.removeSelectedCourse();
		}
	}
	
	public class CreateScheduleListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		    Main.setState(ProgramState.SCHEDULE_VIEWER);
		}
	}
	
	private enum SearchMethod{
		COURSE_NAME,
		COURSE_NUMBER,
		INVALID;
	}
	
	private SearchMethod getSearchMethod(){
		String name = window.getCourseNameSearchText();
		String number = window.getCourseNumSearchText();
		if (name.length() > 0 && name.charAt(0) != '(' &&
                (number.length() == 0 || number.charAt(0) == '(')){
                return SearchMethod.COURSE_NAME;
            }
            else if (number.length() > 0 && number.charAt(0) != '(' &&
                (name.length() == 0 || name.charAt(0) == '(')){
                return SearchMethod.COURSE_NUMBER;
            }
            else{
                return SearchMethod.INVALID;
            }

	}
	
	public ArrayList<Course> getSelectedCourses(){
		ArrayList<Course> schedule = new ArrayList<Course>();
		DefaultListModel<Course> selectedCourses = window.getSelectedCourses();

		for(int i = 0; i < selectedCourses.size(); i++){
			schedule.add(selectedCourses.getElementAt(i));
		}
	    ArrayList<Course> temp = new ArrayList<Course>();
	    for(Course c : schedule){
	        if(c.getSections().size() > 0){
	        	temp.add(c);
	        }
	        if(c.getLab() != null){
	        	temp.add(c.getLab());
	        }
	        if(c.getRec() != null){
	            temp.add(c.getRec());
	        }
            if(c.getQuiz() != null){
            	temp.add(c.getQuiz());
	        }
	    }
	    return temp;
	}
}
