package calendar.backend.appointments;

import java.util.ArrayList;
import java.util.HashMap;

import calendar.backend.date.Date;
import calendar.backend.date.DateUtils;
import calendar.backend.main.main;

public class AppointmentUtils {

	private DateUtils dateUtils = main.getDateUtils();
	
	public HashMap<AppointmentProperties, Object> toHashMap(Appointment appointment) {
		HashMap<AppointmentProperties, Object> appointmentMap = new HashMap<AppointmentProperties, Object>();
		
			appointmentMap.put(AppointmentProperties.DATE, appointment.getDate());
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
	
	// Method to check if the appointment is on the given date.
	public boolean isOnDate(Date date, Appointment appointment) {
		return dateUtils.equalsDay(date, appointment.getDate());
	} 
	
}
