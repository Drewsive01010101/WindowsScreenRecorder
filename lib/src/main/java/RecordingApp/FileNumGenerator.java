package RecordingApp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileNumGenerator {

	
public String createFileNumber() {
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	LocalDateTime current = LocalDateTime.now();
	String time = dtf.format(current);
	return time;
	
}	
	
	
	
}
