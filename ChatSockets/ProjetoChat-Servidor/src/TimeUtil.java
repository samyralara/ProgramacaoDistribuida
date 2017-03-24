import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtil {
	
	public static String getDateTime() { 
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy "); 
		Date date = new Date(); 
		return dateFormat.format(date); 
	}

}
