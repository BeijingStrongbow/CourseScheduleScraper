package com.github.beijingstrongbow;

import java.awt.EventQueue;

import com.github.beijingstrongbow.userinterface.ScheduleBuilderWindow;
import com.github.beijingstrongbow.userinterface.managers.InitializationDialogManager;
import com.github.beijingstrongbow.userinterface.managers.ScheduleBuilderManager;
import com.github.beijingstrongbow.webscraper.WebScraper;

public class Main {
		
	private static ScheduleBuilderManager builder;
	
	private static WebScraper scraper;
	
	private static InitializationDialogManager initDialog;
	
	private static ProgramState state;
	
	public enum ProgramState{
		INITIALIZATION,
		PROCESS_WEB_DATA,
		SCHEDULE_BUILDER,
		SCHEDULE_VIEWER,
		WAITING,
		DONE;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		initDialog = new InitializationDialogManager();
		builder = new ScheduleBuilderManager();
		scraper = new WebScraper();
		state = ProgramState.INITIALIZATION;
		
		while(true){
			switch(state){
				case INITIALIZATION:
					initDialog.showWindow();
					state = ProgramState.WAITING;
					break;
				case PROCESS_WEB_DATA:
					scraper.populateDatabases(Integer.toString(initDialog.getYear()), initDialog.getSemester());
					state = ProgramState.SCHEDULE_BUILDER;
					break;
				case SCHEDULE_BUILDER:
					builder.showWindow();
					state = ProgramState.WAITING;
					break;
				case SCHEDULE_VIEWER:
					//todo
					break;
			}
		}
	}
	
	public static void setState(ProgramState state){
		Main.state = state;
	}
}
