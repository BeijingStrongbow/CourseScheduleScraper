package com.github.beijingstrongbow.userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.beijingstrongbow.userinterface.managers.InitializationDialogManager;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class InitializationDialog{

	private final JPanel contentPanel = new JPanel();
	private final JLabel uxSemesterLabel = new JLabel("Semester");
	private JTextField uxSemesterField;
	private JTextField uxYearField;
	
	private JDialog dialog;

	/**
	 * Create the dialog.
	 */
	public InitializationDialog(InitializationDialogManager manager) {
		dialog = new JDialog();
		
		dialog.addWindowListener(manager.new CloseWindowListener());
		dialog.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(15, 5, 15, 15));
		dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 2, 0, 45));
		uxSemesterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		uxSemesterLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPanel.add(uxSemesterLabel);
		{
			uxSemesterField = new JTextField();
			uxSemesterField.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(uxSemesterField);
			uxSemesterField.setColumns(10);
		}
		{
			JLabel uxYearLabel = new JLabel("Year");
			uxYearLabel.setHorizontalAlignment(SwingConstants.CENTER);
			uxYearLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(uxYearLabel);
		}
		{
			uxYearField = new JTextField();
			uxYearField.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(uxYearField);
			uxYearField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			dialog.getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Load Schedule");
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				dialog.getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(manager.new LoadScheduleListener());
			}
		}
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.pack();
		
		Dimension frameSize = dialog.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		dialog.setLocation((int) (0.5 * (screenSize.getWidth() - frameSize.getWidth())), (int) (0.5 * (screenSize.getHeight() - frameSize.getHeight())));
	}
	
	public void setVisible(boolean visible){
		dialog.setVisible(visible);
	}
	
	public void dispose(){
		dialog.dispose();
	}
	
	public int getYear(){
		String year = uxYearField.getText();
		int yearInt = -1;
		try{
			yearInt = Integer.parseInt(year);
		}
		catch(NumberFormatException ex){
			JOptionPane.showMessageDialog(null, "The year must be an integer", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return yearInt;
	}

	public String getSemester(){
		String semester = uxSemesterField.getText();
		
		if(semester.equalsIgnoreCase("spring") || semester.equalsIgnoreCase("summer") || semester.equalsIgnoreCase("fall")){
			return semester;
		}
		else{
			JOptionPane.showMessageDialog(null, "The semester must be either Spring, Summer, or Fall", "Error", JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}
}
