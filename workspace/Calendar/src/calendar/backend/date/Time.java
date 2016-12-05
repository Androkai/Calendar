package calendar.backend.date;

public class Time {
	
	long second;
	long minute;
	long hour;
	
	public Time(long second, long minute, long hour) {
		
		this.second = second;
		this.minute = minute;
		this.hour   = hour;
		
	}
	
	public Time(Time time) {
		
		this.second = time.getSecond();
		this.minute = time.getMinute();
		this.hour 	= time.getHour();
		
	}
	
	public Time(String timeString) {
		String[] units = timeString.split(":");
		
			if(units.length == 3) {
				
				this.hour   = Long.valueOf(units[0]);
				this.minute = Long.valueOf(units[1]);
				this.second = Long.valueOf(units[2]);
			
			}
		
	}

	public long getSecond() {
		return second;
	}

	public void setSecond(long second) {
		this.second = second;
	}

	public long getMinute() {
		return minute;
	}

	public void setMinute(long minute) {
		this.minute = minute;
	}

	public long getHour() {
		return hour;
	}

	public void setHour(long hour) {
		this.hour = hour;
	}
	
	public String toString() {
		
		String format =
				"{"
				+ getHour()		+ ":"
				+ getMinute() 	+ ":"
				+ getSecond() 	+ ""
			  + "}";
		
		return format;
	}
	
}
