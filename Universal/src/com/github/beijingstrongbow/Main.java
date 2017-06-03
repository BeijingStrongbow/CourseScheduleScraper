package com.github.beijingstrongbow;

import java.awt.EventQueue;

import com.github.beijingstrongbow.userinterface.ScheduleBuilderWindow;
import com.github.beijingstrongbow.userinterface.managers.ScheduleBuilderManager;

public class Main {
		
	private static ScheduleBuilderManager manager;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		manager = new ScheduleBuilderManager();
	}
}
