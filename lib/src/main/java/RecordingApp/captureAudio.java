package RecordingApp;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;




public class captureAudio   {

FileNumGenerator generator = new FileNumGenerator();
	//we need to get the line in which takes the input from mic
	//put the input into a short array 
	//use xuggler to encode it into the stream
	boolean stopper=false;
	TargetDataLine line;
	File file = null;
String time = generator.createFileNumber();
	
	
	//obtain mixers info 
	static Mixer.Info[] audioComponents = AudioSystem.getMixerInfo();
	
	//get the mic targetDataLine

	TargetDataLine getMic() throws Exception {
		
		AudioFormat format = new AudioFormat(44100,16,2,true,false);
		TargetDataLine line =null;
		DataLine.Info info = new DataLine.Info(TargetDataLine.class,format); // format is an AudioFormat object
		if (!AudioSystem.isLineSupported(info)) {
			throw new Exception();
		}
		// Obtain and open the line.
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);

		} catch (LineUnavailableException ex) {
			ex.printStackTrace(); 
		}
		return line;
	}




	void startAudio() throws Exception {

		line= getMic();
		line.start();

		setstopper(true);


		Thread thread = new Thread() {
			@Override
			public void run() {

				while(audioRunning()) {
					AudioInputStream audioStream = new AudioInputStream(line);
										
					File file = new File(filePath());
					
					
					try {
						AudioSystem.write(audioStream,AudioFileFormat.Type.WAVE,file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}



			}

		};

		thread.start();



	}


	boolean audioRunning() {

		return stopper==true;	

	}


	void setstopper(boolean x) {
		this.stopper=x;
	}
	void stop() throws Exception {

		setstopper(false);
		line.stop();
		line.drain();
		line.close();


	}
	
	public String filePath() {
		File audioFile = getFileDir();
		String pathString;
		if(audioFile==null) {
			pathString = System.getProperty("user.home").concat("\\Desktop\\saved").concat(time).concat(".wav");
		}else {pathString = audioFile.getPath().concat("\\saved").concat(time).concat(".wav");
		
		
		}
		System.out.println("pathString audio is "+pathString);
		return pathString;
		}
		
		
		public void setFileDir(File f) {
			file = f;
		}
		public File getFileDir() {
			System.out.println(file);
			return file;
			
		}
		
		String getFileNumber() {
			
			String fileNum = "saved"+time+".wav";
			return fileNum;
				
			}

}
