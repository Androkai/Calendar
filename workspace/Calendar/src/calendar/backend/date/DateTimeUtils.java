package calendar.backend.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.ArrayList;

import calendar.backend.main.Main;
import calendar.frontend.configs.CalendarConfig;

public class DateTimeUtils {
	
	CalendarConfig calendarConfig = Main.getCalendarConfig();
	
	/*
	 * Methods to create a DateTime, Date, Time with 0 paramaters.
	 */
	public DateTime getNullDateTime(){
		return new DateTime(1, 1, 1, 1, 1, 1, 1);
	}
	
	public Date getNullDate() {
		return new Date(1, 1, 1, 1);
	}
	
	public Time getNullTime() {
		return new Time(1, 1, 1);
	}
	
	/*
	 * Method to get a date out of a given string
	 */
	public Date fromString(String dateString) {
		Date date = getNullDate();
		String[] dateUnits = dateString.split("_");
		
			if(dateUnits.length == 3) {
				
				date.setMonth(Long.valueOf(dateUnits[0]));
				date.setDay	(Long.valueOf(dateUnits[1]));
				date.setYear(Long.valueOf(dateUnits[2]));
				
				return date;
			}
		
		return getNullDate();
	}
	
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
	
	/*
	 * Create a new LocalDateTime object with the parameters of the Date object.
	 */
	public LocalDateTime toLocalDateTime(DateTime date) {
		date = new DateTime(date);
		
		int year = (int) date.getYear();
		int month = (int) date.getMonth();
		int day = (int) date.getDay();
		LocalDate localDate = LocalDate.of(year, month, day);
		
		int hour = (int) date.getHour();
		int minute = (int) date.getMinute();
		int second = (int) date.getSecond();
		LocalTime localTime = LocalTime.of(hour, minute, second);
		
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		
			return localDateTime;
		
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
