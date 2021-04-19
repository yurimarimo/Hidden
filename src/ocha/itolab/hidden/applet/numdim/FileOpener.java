package ocha.itolab.hidden.applet.numdim;

import java.io.File;
import java.util.Vector;
import java.awt.*;
import javax.swing.*;
import ocha.itolab.hidden.core.data.*;

public class FileOpener {
	
	File currentDirectory, inputFile, outputFile;
	Component windowContainer;
	IndividualCanvas icanvas;
	DimensionCanvas dcanvas;
	
	
	public void setContainer(Component c) {
		windowContainer = c;
	}
	
	
	public void setIndividualCanvas(IndividualCanvas ic) {
		icanvas = ic;
	}
	
	
	public void setDimensionCanvas(DimensionCanvas dc) {
		dcanvas = dc;
	}
	

	public File getFile() {
		JFileChooser fileChooser = new JFileChooser(currentDirectory);
		int selected = fileChooser.showOpenDialog(windowContainer);
		if (selected == JFileChooser.APPROVE_OPTION) { // open selected
			currentDirectory = fileChooser.getCurrentDirectory();
			System.out.println(currentDirectory);
			fileChooser.setCurrentDirectory(currentDirectory);
			return fileChooser.getSelectedFile();
		} else if (selected == JFileChooser.CANCEL_OPTION) { // cancel selected
			return null;
		} 
		
		return null;
	}
	
	
	public IndividualSet readFile() {
		IndividualSet ps;
		inputFile = getFile();
		if (inputFile == null) {
			ps = null;  return null;
		} 
		
		DataFileReader pfr = new DataFileReader();
		ps = pfr.read(inputFile.getAbsolutePath());
			
		return ps;
	}


}
