package calendar.backend.appointments;

import calendar.backend.date.Date;
import calendar.backend.date.DateUtils;
import calendar.backend.main.main;

public class AppointmentUtils {

	private DateUtils dateUtils = main.getDateUtils();
	
	// Method to check if the appointment is on the given date.
	public boolean isOnDate(Date date, Appointment appointment) {
		return dateUtils.equalsDay(date, appointment.getDate());
	} 
	
}
