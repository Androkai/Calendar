package calendar.backend.dateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;

import calendar.backend.Main;
import calendar.backend.configs.CalendarConfig;

public class DateTimeUtils {
	
	CalendarConfig calendarConfig = Main.getCalendarConfig();
	
	/*
	 * Methods to create a DateTime, Date, Time with 0 paramaters.
	 */
	
	/*
	 * Method to get the maxValue of a month
	 */
	public int getCurrentMonthMax(LocalDateTime timeSystem) {
		int maxLength = timeSystem.getMonth().maxLength();
		if(timeSystem.getMonth().getValue() == 2) {
			if(!Year.isLeap(timeSystem.getYear())) {
				maxLength = maxLength - 1;
			}
		}
		
		return maxLength;
	}
	
	
	/*
	 * Method to equals to dates on day exaction. 
	 */
	public boolean equalsDay(DateTime dateOne, DateTime dateTwo) {
		
		if(dateOne.getYear() == dateTwo.getYear()) {
			if(dateOne.getMonth() == dateTwo.getMonth()) {
				if(dateOne.getDay() == dateTwo.getDay()) {
					return true;
				}
			}
		}
		
		return false;
	}

	public long getMinutesOfDay(Time time) {
		
		long minutes = time.getMinute();
		minutes = minutes + time.getHour() * 60;
		
		
		return minutes;
	}
	
	/*
	 * Method to add a month to a given LocalDateTime.
	 */
	public LocalDateTime addMonth(LocalDateTime date) {
		int year = (int) date.getYear();
		int month = (int) date.getMonthValue() + 1;
			if(month > 12) {
				year++;
				month = 1;
			}

		date = LocalDateTime.of(year, month, 1, 1, 1);
		
		return date;
	}
	
	/*
	 * Method to remove a month from a given LocalDateTime;
	 */
	public LocalDateTime removeMonth(LocalDateTime date) {
		int year = (int) date.getYear();
		int month = (int) date.getMonthValue() - 1;
			if(month < 1) {
				year--;
				month = 12;
			}
		
		date = LocalDateTime.of(year, month, 1, 1, 1);
		
		return date;
	}
	
}
