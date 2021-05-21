package RecordingApp;


import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javax.swing.JTextField;

public class ScreenRecorderUI {
	private	JFrame  recordingGUIFrame;
	ScreenRecorderUI() {
		recordingGUIFrame = new JFrame();
		
	}
	


	public void createFrame(int x,int y) {


		recordingGUIFrame.setSize(x,y);
		recordingGUIFrame.setLayout(null);
		recordingGUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    recordingGUIFrame.setResizable(false);
	   

	}

	public void frameVisibility(boolean x) {
		recordingGUIFrame.setVisible(x);
		
	}

	public JButton createButton(int x,int y,int moveX,int moveY,String text,boolean isVisible) {	
		JButton recordingGUIButton = new JButton();
		recordingGUIButton.setBounds(moveX,moveY,x, y);
		recordingGUIButton.setText(text);
		recordingGUIButton.setVisible(isVisible);
		recordingGUIFrame.add(recordingGUIButton);
		return recordingGUIButton;
	}

public void createText(int x,int y,int moveX,int moveY,String text) {
	JTextField txt = new JTextField();
	txt.setText(text);
	txt.setBounds(moveX, moveY,x,y);
	txt.setVisible(true);
    txt.setEditable(false);
	recordingGUIFrame.add(txt);
}

public File createFileSystem() {
	final JFileChooser fileDir = new JFileChooser();
	fileDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
	int value = fileDir.showSaveDialog(this.recordingGUIFrame);
	File file=null;
		if(value == JFileChooser.APPROVE_OPTION) {
			 file =fileDir.getSelectedFile();
		}
		System.out.println(file);
	  return file;
	}


public void remove(JButton b) {
	recordingGUIFrame.remove(b);
}










}
