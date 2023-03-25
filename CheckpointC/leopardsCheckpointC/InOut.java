package leopardsCheckpointC;

import java.awt.FileDialog;
import java.awt.Frame;


public class InOut {
	
		static String readLocation;
		static String writeLocation;
		
	public static String getFile() {
		
		Frame frame1 = new Frame();
		FileDialog FD = new FileDialog(frame1, "Open file", 0);
		FD.setVisible(true);
		String fileName = FD.getFile();
		String directory = FD.getDirectory();
		readLocation = directory + fileName;
		return readLocation;
	}
	public static String getWriteLocation() {
		Frame frame1 = new Frame();
		FileDialog FD = new FileDialog(frame1, "Save to disk", 1);
		FD.setVisible(true);
		String fileName = FD.getFile();
		String directory = FD.getDirectory();
		writeLocation = directory + fileName;
		return writeLocation;
	}

}

