package RecordingApp;




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;






public class ScreenRecorder {
	

	public static void main(String[] args) throws Exception{
		ScreenRecorderUI rec = new ScreenRecorderUI(); 
		captureWindow window = new captureWindow();
		captureAudio audio = new captureAudio();
        FileNumGenerator generator = new FileNumGenerator();
        String time = generator.createFileNumber();
			
		rec.createFrame(500,450);
		
		rec.createText(350, 40, 100, 50,"Please select file to save audio and video");

JButton userFile = rec.createButton(150, 30, 100, 100,"Choose File",true);	

		userFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
	
File r = rec.createFileSystem();	
window.setFileDir(r);
audio.setFileDir(r);
				}
        
			});
		

		
		JButton ffmpeg = rec.createButton(150, 30, 280, 310,"Combine",false);	
		ffmpeg.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent click) {
  
	String w = window.getFileNumber();
	String a = audio.getFileNumber();
	String o = "output"+time+".mp4";
	System.out.println("File of window is: "+w+" "+"file of audio "+a);
    String output = window.parentDir();
    File f = new File(output);
    
	ProcessBuilder builder = new ProcessBuilder();
	builder.directory(f);
	System.out.println("Parent directory IS "+builder.directory());
	//Map<String, String> environment = builder.environment();
	
	builder.command("ffmpeg", "-i", w,"-i", a, "-vcodec", "copy" ,o);
	try {
				Process pr= builder.start();
				try {
					int resultBoolInt= pr.waitFor();
					pr.exitValue();
					
					System.out.println("result is "+resultBoolInt);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		ffmpeg.setEnabled(false);
			
			}
			
			
		});
		
		
		JButton start =rec.createButton(150, 50, 280, 200,"start",true);
	start.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
			
               
				window.start(0,0);
				audio.startAudio();
			    ffmpeg.setVisible(false);	
			    ffmpeg.setEnabled(true);
			}
			 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	
	
	
	JButton stop =rec.createButton(150, 50, 280, 250,"stop",true);
	stop.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent click) {
			
		    try {
		    	window.stop();
				audio.stop();
				ffmpeg.setVisible(true);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	
	
	
	
	rec.frameVisibility(true);


	 
	}

	
	
}
