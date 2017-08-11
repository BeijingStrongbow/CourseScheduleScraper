package com.github.beijingstrongbow.userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.github.beijingstrongbow.userinterface.managers.InitializationDialogManager.CloseWindowListener;

public class LoadingProgressWindow {

	private JFrame frame;
	
	private JProgressBar uxProgressBar;
	
	private double currentProgress;
	
	private ProgressMode mode;
	
	public enum ProgressMode {
		UPDATE,
		COURSE_DATA;
	}

	/**
	 * Create the application.
	 */
	public LoadingProgressWindow(ProgressMode mode) {
		this.mode = mode;
		EventQueue.invokeLater(new Runnable(){
			
			@Override
			public void run(){
				initialize();
			}
		});
	}

	public void showWindow(){
		frame.setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPanel = new JPanel();
		frame.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setPreferredSize(new Dimension(300, 100));
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel uxLoadingLabel = new JLabel();
		uxLoadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		if(mode == ProgressMode.COURSE_DATA) {
			uxLoadingLabel.setText("Loading course data...");
		}
		else if(mode == ProgressMode.UPDATE) {
			uxLoadingLabel.setText("Downloading update...");
		}
		
		uxLoadingLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPanel.add(uxLoadingLabel);
		
		uxProgressBar = new JProgressBar();
		contentPanel.add(uxProgressBar);
		currentProgress = uxProgressBar.getValue();
		
		frame.pack();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension windowSize = frame.getSize();
		
		frame.setLocation((int) (0.5 * (screenSize.getWidth() - windowSize.getWidth())), (int) (0.5 * (screenSize.getHeight() - windowSize.getHeight())));
	}

	public void addProgress(double amount){
		currentProgress += amount;
		uxProgressBar.setValue((int) currentProgress);
	}
	
	public void dispose(){
		frame.dispose();
	}
}
