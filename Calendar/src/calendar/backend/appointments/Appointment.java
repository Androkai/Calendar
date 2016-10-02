package calendar.backend.appointments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import calendar.backend.date.Date;
import calendar.backend.date.DateUtils;
import calendar.backend.main.main;

public class Appointment {
	
	Date date;
	Player creator;
	
	String header;
	List<String> description;
	
	// Flags
	HashMap<Flags, Boolean> flags = new HashMap<Flags, Boolean>();
	
	public Appointment(Date date, Player creator,
					   String header, List<String> description,
					   HashMap<Flags, Boolean> flags) {
		
		this.date = date;
		this.creator = creator;
		
		this.header = header;
		this.description = description;
		
		this.flags = flags;
		
	}
	
	public Date getDate() {
		return date;
	}
	
	public Player getCreator() {
		return creator;
	}
	
	public String getHeader() {
		return header;
	}
	public List<String> getDescription() {
		return description;
	}
	
	public HashMap<Flags, Boolean> getFlags() {
		return flags;
	}
	public void setFlags(HashMap<Flags, Boolean> flags) {
		this.flags = flags;
	}
	
	// Method to output an Appointment
	public String toString() {
		
		String format =	
										   	   		   		  "\n"
				+ "Date: " 		  + getDate().toString() 	+ "\n"
				+ "Creator: " 	  +  getCreator()     	 	+ "\n"
				+ "Header: " 	  + getHeader() 			+ "\n"
				+ "Description: " + getDescription() 		+ "\n"
				+ "Flags: "		  + flags.toString()		+ "";
		
		return format;
	}

}
