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

import calendar.backend.main.main;
import calendar.frontend.configs.CalendarConfig;

public class DateUtils {
	
	CalendarConfig calendarConfig = main.getCalendarConfig();
	
	/*
	 * Method to create a date with a timeSystem, but 0 paramaters.
	 */
	public Date getNullDate(){
		return new Date(0, 0, 0, 0, 0, 0, 0);
	}
	
	/*
	 * Method to get a date out of a given string
	 */
	public Date fromString(String dateString) {
		
		String[] dateUnits = dateString.split("_")
				;
			if(dateUnits.length == 3) {
				Date date = getNullDate();
				
				date.setMonth(Long.valueOf(dateUnits[0]));
				date.setDay	(Long.valueOf(dateUnits[1]));
				date.setYear(Long.valueOf(dateUnits[2]));
				
				return date;
			}
		
		return getNullDate();
	}
	
	/*
	 * Method to check if the given date is today.
	 */
	public boolean isToday(Date date, LocalDateTime localDate){
		
		if(date.getYear() == localDate.getYear()){
			if(date.getMonth() == localDate.getMonthValue()){
				if(date.getDay() == localDate.getDayOfMonth()){
					return true;
				}
			}
		}
		
		return false;
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
	 * Create a new LocalDateTime object with the parameters of the Date object.
	 */
	public LocalDateTime toLocalDateTime(Date date) {
		date = new Date(date);
		
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
