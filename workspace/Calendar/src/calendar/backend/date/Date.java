package calendar.backend.date;

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
		
		this.day 	= date.getDay();
		this.week 	= date.getWeek();
		this.month 	= date.getMonth();
		this.year 	= date.getYear();
		
	}
	
	public Date(String dateString) {
		String[] units = dateString.split(".");
		
			if(units.length == 3) {
				
				this.month = Long.valueOf(units[0]);
				this.day   = Long.valueOf(units[1]);
				this.week  = day / 7;
				this.year  = Long.valueOf(units[2]);
				
			}
		
	}
	
	public boolean isBefore(Date date) {
		
		if(year <= date.getYear()) {
			if(month <= date.getMonth()) {
				if(day < date.getDay()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isToday(Date date){
		
		if(year == date.getYear()){
			if(month == date.getMonth()){
				if(day == date.getDay()){
					return true;
				}
			}
		}
		
		return false;
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
