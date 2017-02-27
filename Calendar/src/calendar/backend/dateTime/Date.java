package calendar.backend.dateTime;

import java.time.LocalDate;

public class Date {
	
	long day;
	long week;
	long month;
	long year;
	
	public Date(long day, long week, long month, long year) {
		
		this.day 	= day;
		this.week 	= week;
		this.month 	= month;
		this.year 	= year;
		
	}
	
	public Date(Date date) {
		this(date.getDay(), date.getWeek(), date.getMonth(), date.getYear());
	}
	
	public Date(LocalDate date) {
		this(date.getDayOfMonth(), date.getDayOfMonth() / 7, date.getMonthValue(), date.getYear());
	}
	
	public Date() {
		this(0, 0, 0, 0);
	}

	public long getDay() {
		return day;
	}

	public void setDay(long day) {
		this.day = day;
	}

	public long getWeek() {
		return week;
	}

	public void setWeek(long week) {
		this.week = week;
	}

	public long getMonth() {
		return month;
	}

	public void setMonth(long month) {
		this.month = month;
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}
	
	public boolean isBefore(Date date) {
		
		if(year <= date.getYear() 
			|| month <= date.getMonth() 
			|| day < date.getDay())
				return true;
		
		return false;
	}
	public boolean isAt(Date date) {
		
		if(year == date.getYear() 
			&& month == date.getMonth()
			&& day == date.getDay())
				return true;
		
		return false;
	}
	public boolean isAfter(Date date) {
		
		if(year >= date.getYear()
			|| month >= date.getMonth()
			|| day > date.getDay())
				return true;
		
		return false;
	}
	
	public Boolean isPending() {
		return isBefore(new Date(LocalDate.now()));
	}
	public Boolean isOver() {
		return isAfter(new Date(LocalDate.now()));
	}
	public boolean isNow(){
		return isAt(new Date(LocalDate.now()));
	}
	
	public static Date fromString(String str) {
		String[] parts = str.split(".");
		long day;
		long week;
		long month;
		long year;
		
		try{
			switch(parts.length) {
				case 1:
					year = Long.valueOf(str);
						return new Date(0, 0, 0, year);
				case 2:
					year = Long.valueOf(parts[0]);
					month =  Long.valueOf(parts[1]);
						return new Date(0, 0, month, year);
				case 3:
					year = Long.valueOf(parts[0]);
					month = Long.valueOf(parts[1]);
					day = Long.valueOf(parts[2]);
					week = day / 7;
						return new Date(day, week, month, year);
			}
		}catch(NumberFormatException exeption) {}
		return new Date();
	}
	
	public String toString() {
		
		String format =
				"{"
				+ getMonth() 	+ "."
				+ getDay()		+ "."
				+ getYear()		+ ""
			  + "}";
		
	return format;
	}
	
}
