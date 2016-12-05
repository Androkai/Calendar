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
import java.util.List;
import java.util.Locale;

public class DateTime {
	
	Time time;
	long second;
	long minute;
	long hour;
	
	Date date;
	long day;
	long week;
	long month;
	long year;
	
	public DateTime(
				long second,
				long minute,
				long hour,
				long day,
				long week,
				long month,
				long year){
		
		this.time    = new Time(second, minute, hour);
		this.second  = second;
		this.minute  = minute;
		this.hour 	 = hour;
		
		this.date    = new Date(day, week, month, year);
		this.day 	 = day;
		this.week 	 = week;
		this.month 	 = month;
		this.year 	 = year;
		
	}
	
	public DateTime() {
		this(0, 0, 0, 0, 0, 0, 0);
	}
	
	public DateTime(Date date, Time time) {
		this(time.getSecond(), time.getMinute(), time.getHour(), date.getDay(), date.getWeek(), date.getMonth(), date.getYear());
	}
	
	public DateTime(DateTime dateTime){
		this(dateTime.getSecond(), dateTime.getMinute(), dateTime.getHour(), dateTime.getDay(), dateTime.getWeek(), dateTime.getMonth(), dateTime.getYear());
	}
	
	public DateTime(LocalDateTime dateTime){
		
		this.time   = new Time(dateTime.getSecond(), dateTime.getMinute(), dateTime.getHour());
		this.second = dateTime.getSecond();
		this.minute = dateTime.getMinute();
		this.hour   = dateTime.getHour();
		
		this.date   = new Date(dateTime.getDayOfMonth(), (long) Math.ceil((double) dateTime.getDayOfMonth() / 7.0),dateTime.getMonthValue(), dateTime.getYear());
		this.day    = dateTime.getDayOfMonth();
        this.week   = (long) Math.ceil((double) day / 7.0);
		this.month  = dateTime.getMonthValue();
		this.year   = dateTime.getYear();
		
	}
	
	public Time getTime() {
		return time;
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
	
	public Date getDate() {
		return date;
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
	
	public void setTime(Time time) {
		
		this.time 	= time;
		this.second = time.getSecond();
		this.minute = time.getMinute();
		this.hour   = time.getHour(); 
		
	}

	public void setSecond(long second) {
		this.second = second;
		time.setSecond(second);
	}

	public void setMinute(long minute) {
		this.minute = minute;
		time.setMinute(minute);
	}

	public void setHour(long hour) {
		this.hour = hour;
		time.setHour(hour);
	}
	
	public void setDate(Date date) {
			
		this.date 	= date;
		this.day 	= date.getDay();
		this.week 	= date.getWeek();
		this.month 	= date.getMonth();
		this.year 	= date.getYear();
	}

	public void setDay(long day) {
		this.day = day;
		date.setDay(day);
	}

	public void setWeek(long week) {
		this.week = week;
		date.setWeek(week);
	}

	public void setMonth(long month) {
		this.month = month;
		date.setMonth(month);
	}

	public void setYear(long year) {
		this.year = year;
		date.setYear(year);
	}
	
	// Method to out put the day
	public String toString() {
		
		String format =
				"{"
				+ getMonth() 	+ "."
				+ getDay()		+ "."
				+ getYear()		+ " "
				+ getHour()		+ ":"
				+ getMinute() 	+ ":"
				+ getSecond() 	+ ""
			  + "}";
		
		return format;
	}

}
