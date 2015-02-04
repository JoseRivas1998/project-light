package com.tcg.light.desktop;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.light.Game;

public class DesktopLauncher extends JFrame implements ActionListener{

	private JTextField widthField, heightField;
	
	private JButton run;
	
	private JCheckBox fullScreenB, vSyncB;
	
	private JSlider volumeSlide;
	
	private JTextField vText;
	
	private float volume;
	
	private boolean fullScreen, vSync;
	
	private int width, height;
	
	private static final long serialVersionUID = -6747633674982423257L;

	public static void main (String[] arg) {
		new DesktopLauncher();
	}
	
	public DesktopLauncher() {
		fullScreenB = new JCheckBox("Fullscreen");
		fullScreenB.setSelected(false);
		fullScreenB.addActionListener(this);
		vSyncB = new JCheckBox("vSync");
		vSyncB.setSelected(true);
		fullScreen = fullScreenB.isSelected();
		vSync = vSyncB.isSelected();

		widthField = new JTextField("800", 5);
		heightField = new JTextField("600", 5);
		
		volumeSlide = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		vText = new JTextField(volumeSlide.getValue() + "%");
		vText.setEditable(false);
		
		volumeSlide.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				vText.setText(volumeSlide.getValue() + "%");
				
			}
			
		});
		
		run = new JButton("Run");
		run.addActionListener(this);

		JPanel title = new JPanel();
		title.setLayout(new FlowLayout());
		title.add(new JLabel(Game.TITLE));
	    
	    JPanel screenSettings = new JPanel();
	    screenSettings.setLayout(new FlowLayout());
	    screenSettings.add(new JLabel("Width"));
	    screenSettings.add(widthField);
	    screenSettings.add(new JLabel("Height"));
	    screenSettings.add(heightField);
	    screenSettings.add(fullScreenB);
	    screenSettings.add(vSyncB);
	    
	    JPanel audioSettings = new JPanel();
	    audioSettings.setLayout(new FlowLayout());
	    audioSettings.add(new JLabel("Volume"));
	    audioSettings.add(volumeSlide);
	    audioSettings.add(vText);
	    
	    JPanel settings = new JPanel();
	    settings.setLayout(new BorderLayout());
	    settings.add(audioSettings, BorderLayout.SOUTH);
	    settings.add(screenSettings, BorderLayout.NORTH);
	    
	    getContentPane().add(title, BorderLayout.NORTH);
	    getContentPane().add(settings, BorderLayout.CENTER);
	    getContentPane().add(run, BorderLayout.SOUTH);
	    
	    setSize(400, 150);
	    setTitle("Tiny Country Games Launcher");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setResizable(false);
	    
	    ImageIcon img = new ImageIcon(this.getClass().getResource("16.png"));
	    
	    setIconImage(img.getImage());
	    setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fullScreenB) {
			widthField.setEditable(!fullScreenB.isSelected());
			heightField.setEditable(!fullScreenB.isSelected());
			int tw = 800, th = 600;
			if(fullScreenB.isSelected()) {
				tw = Toolkit.getDefaultToolkit().getScreenSize().width;
				th = Toolkit.getDefaultToolkit().getScreenSize().height;
			} 
			widthField.setText("" + tw);
			heightField.setText("" + th);
		}
		if(e.getSource() == run) {
			fullScreen = fullScreenB.isSelected();
			vSync = vSyncB.isSelected();
			if(fullScreen) {
				width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
				height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
			} else {
				try {
					width = new Integer(widthField.getText().trim()).intValue();
				} catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(this, "Width must be a number!", "Invalid", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					height = new Integer(heightField.getText().trim()).intValue();
				} catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(this, "Height must be a number!", "Invalid", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			setVisible(false);
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.width = width;
			config.height = height;
			config.fullscreen = fullScreen;
			config.vSyncEnabled = vSync;
			volume = volumeSlide.getValue() / 100f;
			Game g = new Game();
			Game.VOLUME = volume;
			new LwjglApplication(g, config);
		}
		
	}
}
