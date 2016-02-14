package org.cerberus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class Crypt {
	
	private static File PAD_FILE;
	
	public static byte[] crypt(byte[] data, int code) {
		
		byte[] pad = readVernamPad();
		
		if (pad == null) {
			JOptionPane.showMessageDialog(null, "Error: Could not read pad.", "Cerberus", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		else {
			for (int i = 0; i < data.length; i++) {
				data[i] = (byte) (data[i] ^ pad[(i + code) % Constants.MAX_CODE]);
			}
			return data;
		}
		
	}
	
	private static byte[] readVernamPad() {
		if (PAD_FILE == null) {
			JOptionPane.showMessageDialog(null, "Error: No pad is loaded.", "Cerberus", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		Path path = Paths.get(PAD_FILE.toURI());
	    try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void generateVernamPad(File f) {
		Path path = Paths.get(f.toURI());
		
		byte[] pad = new byte[Constants.MAX_CODE];
		
		for (int i = 0; i < Constants.MAX_CODE; i++) {
			pad[i] = (byte) (Math.random() * 255);
		}
		
	    try {
			Files.write(path, pad);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadPad(File padFile) {
		PAD_FILE = padFile;
		Main.UI_PANEL.updatePadFile(padFile);
	}
	
}
