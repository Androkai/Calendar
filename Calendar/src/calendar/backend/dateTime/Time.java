package calendar.backend.dateTime;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
		this(time.getSecond(), time.getMinute(), time.getHour());
	}
	
	public Time(LocalTime time) {
		this(time.getSecond(), time.getMinute(), time.getHour());
	}
	
	public Time() {
		this(0, 0, 0);
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
	
	public boolean isBefore(Time time) {
		
		if(hour <= time.getHour() 
			|| minute <= time.getMinute() 
			|| second < time.getSecond())
				return true;
		
		return false;
		
	}
	public boolean isNow() {
		Time time = new Time(LocalTime.now());
		
		if(hour == time.getHour() 
			&& minute == time.getMinute()
			&& second == time.getSecond())
				return true;
		
		return false;
	}
	public boolean isAfter(Time time) {
		
		if(hour >= time.getHour()
			|| minute >= time.getMinute()
			|| second >= time.getSecond())
				return true;
		
		return false;
	}
	
	public boolean isPending() {
		return isBefore(new Time(LocalTime.now()));
	}
	public boolean isPending(Time time) {
		return isAfter(new Time(second - time.getSecond(), minute - time.getMinute(), hour - time.getHour())) && !isOver();
	}
	
	public boolean isOver() {
		return isAfter(new Time(LocalTime.now()));
	}
	public boolean isOver(Time time) {
		return isBefore(new Time(second + time.getSecond(), minute + time.getMinute(), hour + time.getHour())) && !isPending();
	}
	
	public static Time fromString(String str) {
		String[] parts = str.split(":");
		long hour;
		long minute;
		long second;
		
		try{
			switch(parts.length) {
				case 1:
					hour = Long.valueOf(str);
						return new Time(0, 0, hour);
						
				case 2:
					hour = Long.valueOf(parts[0]);
					minute = Long.valueOf(parts[1]);
						return new Time(0, minute, hour);
						
				case 3:
					hour = Long.valueOf(parts[0]);
					minute = Long.valueOf(parts[1]);
					second = Long.valueOf(parts[2]);
						return new Time(second, minute, hour);
			}
		}catch(NumberFormatException exeption) {}
		return new Time();
	}
	
	public long toTicks() {
		long ticks;
		
			long hourTicks = 	(long) (hour * Math.pow(60, 2) 		* 20);
			long minuteTicks = 	(long) (minute * Math.pow(60, 1) 	* 20);
			long secondTicks = 	(long) (second 						* 20);
			ticks = hourTicks + minuteTicks + secondTicks;
			
		return ticks;
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
