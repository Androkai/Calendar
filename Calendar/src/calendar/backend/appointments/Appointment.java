package calendar.backend.appointments;

import org.bukkit.entity.Player;

import calendar.backend.date.Date;
import calendar.backend.date.DateUtils;
import calendar.backend.main.main;

public class Appointment {
	
	private DateUtils dateUtils = main.getDateUtils();
	
	// Appointment parameters
	Date appointmentDate;
	Player appointmentCreator;
	
	// Appointment Flags
	boolean isPrivate;
	boolean isDeleted;
	
	public Appointment(Date date, Player creator, boolean isPrivate, boolean isDeleted) {
		
		this.appointmentDate = date;
		this.appointmentCreator = creator;
		
		this.isPrivate = isPrivate;
		this.isDeleted = isDeleted;
	}
	
	// Getters for the appointment parameters
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	
	public Player getCreator() {
		return appointmentCreator;
	}
	
	public boolean isPrivate() {
		return isPrivate;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}
	
	/*
	 * Method to check if the appointment is on the given date.
	 */
	public boolean isOnDate(Date date) {
		return dateUtils.equalsDay(date, appointmentDate);
	}

}
