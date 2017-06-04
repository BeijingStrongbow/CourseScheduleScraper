package com.github.beijingstrongbow.userinterface.managers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	public class LoadScheduleListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int year = dialog.getYear();
			String semester = dialog.getSemester();
			
			if(year != -1 && !semester.equals("")){
				InitializationDialogManager.this.year = year;
				InitializationDialogManager.this.semester = semester;
				dialog.dispose();
				Main.setState(ProgramState.PROCESS_WEB_DATA);
			}
		}
	}
}
