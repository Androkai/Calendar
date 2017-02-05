package calendar.backend.storage;

import org.bukkit.entity.Player;
import calendar.backend.appointments.AppointmentProperties;
import calendar.backend.dateTime.DateTime;
import calendar.frontend.gui.appointment.AppointmentAdd;
import calendar.frontend.gui.appointment.AppointmentManager;
import calendar.frontend.gui.appointment.AppointmentRemove;
import calendar.frontend.gui.appointment.AppointmentTrash;
import calendar.frontend.gui.calendar.Calendar;

public class Storage {
	
	Player holder;
	
	Calendar calendar;
	AppointmentManager appointmentManager;
	AppointmentAdd appointmentAdd;
	AppointmentRemove appointmentRemove;
	AppointmentTrash appointmentTrash;
	
	DateTime calendarDateTime;
	AppointmentProperties inputParameter;
	

	public Player getHolder() {
		return holder;
	}
	public void setHolder(Player holder) {
		this.holder = holder;
	}
	
	public Calendar getCalendar() {
		return this.calendar;
	}
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	
	public AppointmentManager getAppointmentManager() {
		return this.appointmentManager;
	}
	public void setAppointmentManager(AppointmentManager appointmentManager) {
		this.appointmentManager = appointmentManager;
	}
	
	public AppointmentAdd getAppointmentAdd() {
		return this.appointmentAdd;
	}
	public void setAppointmentAdd(AppointmentAdd appointmentAdd) {
		this.appointmentAdd = appointmentAdd;
	}
	
	public AppointmentRemove getAppointmentRemove() {
		return this.appointmentRemove;
	}
	public void setAppointmentRemove(AppointmentRemove appointmentRemove) {
		this.appointmentRemove = appointmentRemove;
	}
	
	public AppointmentTrash getAppointmentTrash() {
		return this.appointmentTrash;
	}
	public void setAppointmentTrash(AppointmentTrash appointmentTrash) {
		this.appointmentTrash = appointmentTrash;
	}
	
	public DateTime getCalendarDateTime() {
		return calendarDateTime;
	}
	public void setCalendarDateTime(DateTime calendarDateTime) {
		this.calendarDateTime = calendarDateTime;
	}
	
	public AppointmentProperties getInputParameter() {
		return inputParameter;
	}
	public void setInputParameter(AppointmentProperties parameter) {
		inputParameter = parameter;
	}
	
	public boolean allNull() {
		return 	   
			calendar 			== null
		&&  appointmentManager 	== null
		&&  appointmentAdd 		== null
		&&  appointmentRemove	== null
		&&  appointmentTrash    == null
		&&	inputParameter		== null;
	}

}
