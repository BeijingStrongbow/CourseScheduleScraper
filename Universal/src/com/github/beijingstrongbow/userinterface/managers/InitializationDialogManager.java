package com.github.beijingstrongbow.userinterface.managers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.github.beijingstrongbow.Main;
import com.github.beijingstrongbow.Main.ProgramState;
import com.github.beijingstrongbow.userinterface.InitializationDialog;

public class InitializationDialogManager {
	
	private InitializationDialog dialog;
	
	private int year = -1;
	
	private String semester = "";
	
	public InitializationDialogManager(){
		dialog = new InitializationDialog(this);
	}
	
	public int getYear(){
		return year;
	}
	
	public String getSemester(){
		return semester;
	}
	
	public void showWindow(){
		dialog.setVisible(true);
	}
	
	public void hideWindow(){
		dialog.setVisible(false);
	}
	
	public void dispose(){
		dialog.dispose();
	}
	
	public class LoadScheduleListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int year = dialog.getYear();
			String semester = dialog.getSemester();
			
			if(year != -1 && !semester.equals("")){
				InitializationDialogManager.this.year = year;
				InitializationDialogManager.this.semester = semester;
				Main.setState(ProgramState.PROCESS_WEB_DATA);
			}
		}
	}
	
	public class CloseWindowListener implements WindowListener{

		@Override
		public void windowOpened(WindowEvent e) {}

		@Override
		public void windowClosing(WindowEvent e) {}

		@Override
		public void windowIconified(WindowEvent e) {}

		@Override
		public void windowDeiconified(WindowEvent e) {}

		@Override
		public void windowActivated(WindowEvent e) {}

		@Override
		public void windowDeactivated(WindowEvent e) {}

		@Override
		public void windowClosed(WindowEvent e) {
			System.exit(0);
		}
	}
}
