package com.github.beijingstrongbow.userinterface;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.github.beijingstrongbow.Course;
import com.github.beijingstrongbow.userinterface.managers.ScheduleBuilderManager;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JList;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListModel;

public class ScheduleBuilderWindow {

	private JFrame frame;
	
	private JTextField uxCourseNameField;
	
	private JTextField uxCourseNumberField;
	
	private JList<Course> uxSearchResultsList;
	
	private JList<Course> uxSelectedCoursesList;
	
	private DefaultListModel<Course> searchResults;
	
	private DefaultListModel<Course> selectedCourses;
	
	private ScheduleBuilderManager manager;

	/**
	 * Create the application.
	 */
	public ScheduleBuilderWindow(ScheduleBuilderManager manager) {
		this.manager = manager;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("8dlu"),
				ColumnSpec.decode("15dlu"),
				ColumnSpec.decode("80dlu"),
				ColumnSpec.decode("35dlu"),
				ColumnSpec.decode("15dlu"),
				FormSpecs.BUTTON_COLSPEC,
				ColumnSpec.decode("12dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("100dlu"),
				FormSpecs.BUTTON_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("8dlu"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("50dlu"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("53dlu"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,}));
		
		JLabel uxAddCourseLabel = new JLabel("Add Course");
		uxAddCourseLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		frame.getContentPane().add(uxAddCourseLabel, "2, 2, 6, 1");
		
		JLabel uxSearchByLabel = new JLabel("Search by");
		uxSearchByLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		frame.getContentPane().add(uxSearchByLabel, "3, 4, 7, 1");
		
		JLabel uxClickCourseLabel = new JLabel("Double Click a Course to Add It");
		uxClickCourseLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(uxClickCourseLabel, "8, 4, 3, 1, left, bottom");
		
		JLabel uxCourseNumberLabel = new JLabel("Course number");
		uxCourseNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(uxCourseNumberLabel, "3, 6, 8, 1");
		
		searchResults = new DefaultListModel<Course>();
		uxSearchResultsList = new JList<Course>(searchResults);
		uxSearchResultsList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(uxSearchResultsList, "8, 6, 3, 24, fill, fill");
		uxSearchResultsList.addMouseListener(manager.new ResultsDoubleClickListener());
		
		uxCourseNumberField = new JTextField();
		uxCourseNumberField.setText("(e.g. CHM110)");
		uxCourseNumberField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(uxCourseNumberField, "3, 8, 4, 1, fill, default");
		uxCourseNumberField.setColumns(10);
		
		JLabel uxCourseNameLabel = new JLabel("Course name");
		uxCourseNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(uxCourseNameLabel, "3, 10, 7, 1");
		
		uxCourseNameField = new JTextField();
		uxCourseNameField.setText("(e.g. General Chemistry)");
		uxCourseNameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(uxCourseNameField, "3, 12, 4, 1, fill, default");
		uxCourseNameField.setColumns(10);
		
		JButton uxSearchButton = new JButton("Search");
		uxSearchButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(uxSearchButton, "6, 14, 1, 1");
		uxSearchButton.addActionListener(manager.new SearchButtonListener());
		
		selectedCourses = new DefaultListModel<Course>();
		uxSelectedCoursesList = new JList<Course>(selectedCourses);
		uxSelectedCoursesList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(uxSelectedCoursesList, "3, 16, 4, 14, default, fill");
		
		JButton uxRemoveCourseButton = new JButton("Remove Course");
		uxRemoveCourseButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(uxRemoveCourseButton, "4, 31, 3, 1");
		uxRemoveCourseButton.addActionListener(manager.new RemoveItemButtonListener());
		
		JButton uxCreateScheduleButton = new JButton("Create Schedule");
		uxCreateScheduleButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(uxCreateScheduleButton, "10, 31, 1, 1");
		
		frame.getRootPane().setDefaultButton(uxSearchButton);
				
		frame.pack();
		
		Dimension frameSize = frame.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame.setLocation((int) (0.5 * (screenSize.getWidth() - frameSize.getWidth())), (int) (0.5 * (screenSize.getHeight() - frameSize.getHeight())));
	}
	
	public void setVisible(boolean visible){
		frame.setVisible(visible);
	}
	
	public String getCourseNameSearchText(){
		return uxCourseNameField.getText();
	}
	
	public String getCourseNumSearchText(){
		return uxCourseNumberField.getText();
	}
	
	public void setCourseNameSearchText(String text){
		uxCourseNameField.setText(text);
	}
	
	public void setCourseNumSearchText(String text){
		uxCourseNumberField.setText(text);
	}
	
	public DefaultListModel<Course> getSearchResults(){
		return searchResults;
	}
	
	public DefaultListModel<Course> getSelectedCourses(){
		return selectedCourses;
	}
	
	public void removeSelectedCourse(){
		int selectedIndex = uxSelectedCoursesList.getSelectedIndex();
		if(selectedCourses.size() > 1 && selectedIndex >= 0){
			selectedCourses.remove(selectedIndex);
		}
		else if(selectedCourses.size() == 1){
			selectedCourses.remove(0);
		}
	}
}
