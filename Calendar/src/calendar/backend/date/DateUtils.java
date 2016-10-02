package calendar.backend.date;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DateUtils {
	
	/*
	 * Method to create a date with a timeSystem, but 0 paramaters.
	 */
	public Date getNullDate(){
		return new Date(0, 0, 0, 0, 0, 0, 0);
	}
	
	
	/*
	 * Method to equals to dates on day exaction. 
	 */
	public boolean equalsDay(Date dateOne, Date dateTwo) {
		
		if(dateOne.getYear() == dateTwo.getYear()) {
			if(dateOne.getMonth() == dateTwo.getMonth()) {
				if(dateOne.getDay() == dateTwo.getDay()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/*
	 * Method to get the day of the week.
	 */
	public long getDayOfWeek(Date date) {
	    
	    int cc = (int) (date.getYear()/100);
	    int yy = (int) (date.getYear() - ((date.getYear()/100)*100));

	    int c = (cc/4) - 2*cc-1;
	    int y = 5*yy/4;
	    int m = (int) (26*(date.getMonth()+1)/10);
	    int d = (int) date.getDay();

	    int dayOfWeek = (c+y+m+d)%7;
		
		return dayOfWeek;
	}
	
}
