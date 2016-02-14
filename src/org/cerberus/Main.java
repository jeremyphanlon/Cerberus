package org.cerberus;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

public class Main {
	
	public static UIPanel UI_PANEL;

	public static void main(String[] args) {
		initUI();
	}
	
	private static void initUI() {
		final JFrame frame = new JFrame();
		frame.setSize(400, 160);
		frame.setTitle("Cerberus");
		JMenuBar menubar = createMenuBar();
		frame.setJMenuBar(menubar);
		UI_PANEL = new UIPanel();
		frame.add(UI_PANEL);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private static JMenuBar createMenuBar() {
		JMenuBar mb = new JMenuBar();
		
		JMenu pad = new JMenu("Pad");
		
		JMenuItem createPad = new JMenuItem("Create Pad");
		createPad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser();
		        f.setFileSelectionMode(JFileChooser.FILES_ONLY); 
		        f.setApproveButtonText("Create Pad");
		        if (f.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		        	Crypt.generateVernamPad(f.getSelectedFile());
		        	Crypt.loadPad(f.getSelectedFile());
		        }
			}
			
		});
		pad.add(createPad);
		
		JMenuItem loadPad = new JMenuItem("Load Pad");
		loadPad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser();
		        f.setFileSelectionMode(JFileChooser.FILES_ONLY); 
		        f.setApproveButtonText("Load Pad");
		        if (f.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		        	Crypt.loadPad(f.getSelectedFile());
		        }
			}
			
		});
		pad.add(loadPad);
		
		mb.add(pad);
		
		return mb;
	}
	
}
