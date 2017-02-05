package calendar.backend.appointments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import calendar.backend.Main;
import calendar.backend.dateTime.DateTime;

public class Appointment {
	
	DateTime dateTime;
	UUID creator;
	
	String name;
	HashMap<Flags, Boolean> flags = new HashMap<Flags, Boolean>();
	
	String header;
	List<String> description;
	
	public Appointment(UUID creator, DateTime dateTime, String name,
					   String header, List<String> description,
					   HashMap<Flags, Boolean> flags) {
		
		this.dateTime = dateTime;
		this.creator = creator;
		this.name = name;
		
		this.header = header;
		this.description = description;
		
		this.flags = flags;
		
	}
	
	public Appointment(UUID creator, DateTime dateTime) {
		this(creator, dateTime, null, null, new ArrayList<String>(), Main.getAppointmentUtils().getNullFlags());
	}
	
	public Appointment(Appointment appointment) {
		this(appointment.getCreator(), appointment.getDateTime(), appointment.getName(), appointment.getHeader(), appointment.getDescription(), appointment.getFlags());
	}
	
	// Method to output an Appointment
	public String toString() {
		return
				  "{"
				+ creator 				+ ", "
				+ dateTime.toString() 	+ ", "
				+ name 					+ ", "
				+ flags.toString() 		+ ", "
				+ header 				+ ", "
				+ description 			+ ""
				+ "}";
	}
	
	public DateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(DateTime date) {
		this.dateTime = date;
	}
	
	public UUID getCreator() {
		return creator;
	}
	public void setCreator(UUID creator) {
		this.creator = creator;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name =name;
	}
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	
	public List<String> getDescription() {
		return description;
	}
	public void setDescription(List<String> description) {
		this.description = description;
	}
	
	public HashMap<Flags, Boolean> getFlags() {
		return flags;
	}
	public void setFlags(HashMap<Flags, Boolean> flags) {
		this.flags = flags;
	}
	
	//Method to check if the appointment is delete
	public boolean isDeleted() {
		boolean isDeleted = flags.get(Flags.DELETED);
		
		return isDeleted;
	}

}
