package calendar.backend.date;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Date {
	
	long second;
	long minute;
	long hour;
	long day;
	long week;
	long month;
	long year;
	
	/*
	 * Is called when an instance of this class is created.
	 * Declares all class variables with the given parameters.
	 */
	public Date(
				long second,
				long minute,
				long hour,
				long day,
				long week,
				long month,
				long year){
		
		this.second  = second;
		this.minute  = minute;
		this.hour 	 = hour;
		this.day 	 = day;
		this.week 	 = week;
		this.month 	 = month;
		this.year 	 = year;
		
	}
	
	/*
	 * Is called when an instance of this class is created.
	 * Declares all variables with an Date Object.
	 */
	public Date(Date date){
		
		this.second = date.getSecond();
		this.minute = date.getMinute();
		this.hour 	= date.getHour();
		this.day 	= date.getDay();
		this.week 	= date.getWeek();
		this.month 	= date.getMonth();
		this.year 	= date.getYear();
	}
	
	public Date(LocalDateTime date){
		
		this.second = date.getSecond();
		this.minute = date.getMinute();
		this.hour   = date.getHour();
		this.day    = date.getDayOfMonth();
        this.week = (long)Math.ceil((double) day / 7.0);
		this.month  = date.getMonthValue();
		this.year   = date.getYear();
		
	}
	

	public long getSecond() {
		return second;
	}

	public long getMinute() {
		return minute;
	}

	public long getHour() {
		return hour;
	}

	public long getDay() {
		return day;
	}

	public long getWeek() {
		return week;
	}

	public long getMonth() {
		return month;
	}

	public long getYear() {
		return year;
	}
	
	/*
	 * Setters for date parameters.
	 */

	public void setSecond(long second) {
		this.second = second;
	}

	public void setMinute(long minute) {
		this.minute = minute;
	}

	public void setHour(long hour) {
		this.hour = hour;
	}

	public void setDay(long day) {
		this.day = day;
	}

	public void setWeek(long week) {
		this.week = week;
	}

	public void setMonth(long month) {
		this.month = month;
	}

	public void setYear(long year) {
		this.year = year;
	}

}
