package org.cerberus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JFormattedTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UIPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6652272867601440169L;

	/**
	 * Create the panel.
	 */
	
	private JSpinner codeSpinner;
	private JLabel lblPad;
	
	public UIPanel() {
		setLayout(null);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setBounds(255, 64, 117, 29);
		btnEncrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser();
		        f.setFileSelectionMode(JFileChooser.FILES_ONLY); 
		        f.setApproveButtonText("Encrypt");
		        if (f.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		        	try {
		        		int code = (int) codeSpinner.getValue();
		        		byte[] data = Files.readAllBytes(Paths.get(f.getSelectedFile().toURI()));
		        		byte[] outputData = Crypt.crypt(data, code);
		        		
		        		if (outputData == null) {
		        			return;
		        		}
		        		
		        		Files.write(Paths.get(new File(f.getSelectedFile().getPath() + ".cbin").toURI()), outputData);
		        	} catch (IOException ex) {
		        		ex.printStackTrace();
		        	}
		        }
			}
			
		});
		add(btnEncrypt);
		
		JLabel lblCode = new JLabel("Code");
		lblCode.setBounds(106, 21, 41, 16);
		add(lblCode);
		
		codeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Constants.MAX_CODE, 1));
		codeSpinner.setBounds(148, 15, 117, 28);
		add(codeSpinner);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.setBounds(277, 16, 95, 29);
		btnGenerate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int value = (int) (Math.random() * Constants.MAX_CODE);
				codeSpinner.setModel(new SpinnerNumberModel(value, 0, Constants.MAX_CODE, 1));
			}
			
		});
		add(btnGenerate);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			            "Cerberus Encrypted Binary File", "bin");
			    f.setFileFilter(filter);
			    f.addChoosableFileFilter(filter);
		        f.setFileSelectionMode(JFileChooser.FILES_ONLY); 
		        f.setApproveButtonText("Decrypt");
		        if (f.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		        	try {
		        		int code = (int) codeSpinner.getValue();
		        		byte[] data = Files.readAllBytes(Paths.get(f.getSelectedFile().toURI()));
		        		byte[] outputData = Crypt.crypt(data, code);
		        		
		        		if (outputData == null) {
		        			return;
		        		}
		        		
		        		Files.write(Paths.get(new File(f.getSelectedFile().getPath().substring(0, f.getSelectedFile().getPath().length() - ".cbin".length())).toURI()), outputData);
		        	} catch (IOException ex) {
		        		ex.printStackTrace();
		        	}
		        }
			}
			
		});
		btnDecrypt.setBounds(129, 64, 117, 29);
		add(btnDecrypt);
		
		lblPad = new JLabel("Pad: No Pad Loaded");
		lblPad.setBounds(6, 94, 366, 16);
		add(lblPad);

	}

	public void updatePadFile(File padFile) {
		lblPad.setText("Pad: " + padFile.getPath());
	}
}
