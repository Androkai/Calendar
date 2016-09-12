package calendar.backend.date;

public class DateUtils {
	
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
	
}
