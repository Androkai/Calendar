package calendar.backend.appointments;

import java.util.ArrayList;
import java.util.HashMap;

import calendar.backend.date.DateTime;
import calendar.backend.date.DateTimeUtils;
import calendar.backend.main.Main;

public class AppointmentUtils {

	private DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();
	
	public HashMap<AppointmentProperties, Object> toHashMap(Appointment appointment) {
		HashMap<AppointmentProperties, Object> appointmentMap = new HashMap<AppointmentProperties, Object>();
		
			appointmentMap.put(AppointmentProperties.DATE, appointment.getDateTime());
			appointmentMap.put(AppointmentProperties.CREATOR, appointment.getCreator());
			appointmentMap.put(AppointmentProperties.NAME, appointment.getName());
			appointmentMap.put(AppointmentProperties.FLAGS, appointment.getFlags());
			appointmentMap.put(AppointmentProperties.HEADER, appointment.getHeader());
			appointmentMap.put(AppointmentProperties.DESCRIPTION, appointment.getDescription());
		
		return appointmentMap;
	}
	
	public ArrayList<Appointment> removeDeletedAppointments(ArrayList<Appointment> appointments) {
		if(appointments != null) {
			ArrayList<Appointment> deletedAppointments = new ArrayList<Appointment>();
				for(Appointment appointment : appointments) {
					if(appointment.isDeleted()) {
						deletedAppointments.add(appointment);
					}
				}
				
			appointments.removeAll(deletedAppointments);
		}
		
		return appointments;
	}
	
	public ArrayList<Appointment> removeNotDeletedAppointments(ArrayList<Appointment> appointments) {
		if(appointments != null) {
			ArrayList<Appointment> notDeletedAppointments = new ArrayList<Appointment>();
				for(Appointment appointment : appointments) {
					if(!appointment.isDeleted()) {
						notDeletedAppointments.add(appointment);
					}
				}
				
			appointments.removeAll(notDeletedAppointments);
		}
		
		return appointments;
	}
	
	// Method to check if the appointment is on the given date.
	public boolean isOnDate(DateTime date, Appointment appointment) {
		return dateTimeUtils.equalsDay(date, appointment.getDateTime());
	} 
	
	public HashMap<Flags, Boolean> getNullFlags() {
		HashMap<Flags, Boolean> flags = new HashMap<Flags, Boolean>();
			flags.put(Flags.DELETED, false);
			flags.put(Flags.EDITED, false);
		return flags;
	}
	
}
