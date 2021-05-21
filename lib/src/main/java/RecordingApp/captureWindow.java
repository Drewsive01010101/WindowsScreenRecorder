package RecordingApp;


import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;


import javax.sound.sampled.LineUnavailableException;
import javax.swing.Timer;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;



//CODEC_ID_PNG
public class captureWindow {

	protected Robot capture;
	public GraphicsDevice screen;
	protected Timer timer;
	long startTime;
    File file = null;
	FileNumGenerator generator = new FileNumGenerator();
	String time = generator.createFileNumber();
	
	private GraphicsEnvironment ge;
   protected captureAudio audio;
	captureWindow() throws LineUnavailableException{ 
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
	}
	//gets the array devices
	public GraphicsDevice[] getScreenDevices() {
		GraphicsDevice[] gd = ge.getScreenDevices();	
		return gd;
	}	
	
	
	//returns the number of elements in array of devices
	int screenDeviceCount() {
		GraphicsDevice[] gd = ge.getScreenDevices();
		int arrCount= 0;
		for(int i=0;i<gd.length;i++) {
			arrCount++;

		}
		return arrCount;
	}	

	//returns the given device from the GraphicDevices array providing the index
	GraphicsDevice getDevice(int index) throws Exception {
		System.out.println(screenDeviceCount());

		if(index>screenDeviceCount()) {
			throw new Exception("not in index range");
		}else {
			GraphicsDevice[] devices = getScreenDevices();
			GraphicsDevice x = devices[index];
			return x;
		}

	} 

	//takes in a GraphicDevice object and returns the graphics config details 
	
	private GraphicsConfiguration[] deviceConfig(GraphicsDevice device) {
		GraphicsConfiguration[] deviceDetails = device.getConfigurations();
		return deviceDetails;
	}
	
	
	//gets bounds from graphic Config Device Details i.e. the array that contains x,y coords,and width and height returns a rect obj
	protected Rectangle rectGetBounds(GraphicsDevice device) {
		GraphicsConfiguration[] deviceStats=deviceConfig(device);	
		Rectangle bounds=null;
		for(int i=0;i<deviceStats.length;i++) {
			bounds =deviceStats[i].getBounds() ;
		}
		return bounds;
	}
	
	
	//starts screen capture using timer object
 IMediaWriter writer;
	void start(int lagTime,int betweenFrames) {
	//executes one time
		  
		try {
			final String outputFilename=filePath();
			System.out.println("outputFileName is "+outputFilename);
			//IMediaWriter object in which writes to a premade IContainer
			
			 writer = ToolFactory.makeWriter(outputFilename);

			screen = getDevice(0);
			capture = new Robot(screen);
			
		
			System.out.println("Robot");
			startTime = System.nanoTime();
		
			//create an addvideostream with the desired codec id format in which the video needs to follow in the encodeVideo method
			 writer.addVideoStream(0, 0, ICodec.ID.AV_CODEC_ID_MPEG4,rectGetBounds(screen).width,rectGetBounds(screen).height);
			// writer.addAudioStream(0, 0, ICodec.ID.AV_CODEC_ID_AAC, 1, 44100);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}



		//the timer calls this ActionListener continuously 
		ActionListener captureScreen = new ActionListener() {

		/*
		 * get Buffered image from screencapture
		 * convert it to another image type using convertToType() method 
		 * create an encodevideostream that will use the addVideostream standards
		 * when the process is over we flush();
		 * then we close();		  	
		 */
		
			@Override
		public void actionPerformed(ActionEvent action) {

			try {

					System.out.println("capturing");	
	
				
			
					
				//returns an object of type BufferedImage so we can store it in Image of course	
					BufferedImage Image =	capture.createScreenCapture(rectGetBounds(screen));
               
          
                    //Image.getGraphics().drawImage(new File("c:/"), betweenFrames, betweenFrames, betweenFrames, betweenFrames, mouseX, mouseY, lagTime, betweenFrames, null)
					//converts to type
					BufferedImage imageOfImageType=convertToType(Image,BufferedImage.TYPE_3BYTE_BGR);
					
					//parameters: streamId, the time betweenframes
					writer.encodeVideo(0, imageOfImageType, System.nanoTime()-startTime, TimeUnit.NANOSECONDS);
					//writer.encodeAudio(0,blah);
				

				}
				catch(Exception e) {System.out.println(e.getMessage());}

			}		

		};
		timer= new Timer(betweenFrames,captureScreen);
		timer.setInitialDelay(lagTime);
		timer.start();

	}

	//stops screen capture through timer object

	void stop() throws Exception {

		timer.stop();
		writer.flush();
		writer.close();



	}

	//we want to take img and turn it into a new image with the correct target(aka image type)	
	
	BufferedImage convertToType(BufferedImage img,int target){
		
		BufferedImage revisedImage;
		if(img.getType()==target) {
			revisedImage=img;
		}else {
			revisedImage = new BufferedImage(img.getWidth(),img.getHeight(),target);
			revisedImage.getGraphics().drawImage(img,0,0, null);
		}
		return revisedImage;



	}

/*takes in the window file variable that was set or not from UI in which determines if null or not.
 * if window file null(aka there was no set file method performed) set to static else set to the setFile */
//returns string for outputfilename
	public String filePath() {
	File windowFile = getFileDir();
	String pathString;
	if(windowFile==null) {
		pathString = System.getProperty("user.home").concat("\\Desktop\\saved").concat(time).concat(".mp4");
	//file num gen
	}else {pathString = windowFile.getPath().concat("\\saved").concat(time).concat(".mp4");
	

	}
	System.out.println("pathString window is "+pathString);
	return pathString;
	}
	
	public String parentDir() {
		File windowFile = getFileDir();
		String parentDir;
		if(windowFile==null) {
			parentDir = System.getProperty("user.home").concat("\\Desktop");
		
		}else {parentDir = windowFile.getPath();
		}
		System.out.println("parent file is "+ parentDir);
		return parentDir;
	}
	
	public void setFileDir(File f) {
		file = f;
	}
	public File getFileDir() {
		System.out.println(file);
		return file;
		
	}
	
String getFileNumber() {
	
String fileNum = "saved"+time+".mp4";
return fileNum;
	
}
	
	
	boolean testSomeLibraryMethod() {

		return true;
	}
	
	
	
	
	
}