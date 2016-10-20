package calendar.backend.storage;

import java.time.LocalDate;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import calendar.backend.date.Date;
import calendar.backend.item.Items;
import calendar.frontend.gui.AppointmentAdd;
import calendar.frontend.gui.AppointmentManager;
import calendar.frontend.gui.AppointmentRemove;
import calendar.frontend.gui.Calendar;

public class Storage {
	
	Player holder;
	
	Calendar calendar;
	
	AppointmentManager appointmentManager;
	AppointmentAdd appointmentAdd;
	AppointmentRemove appointmentRemove;
	

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
	
	
	public boolean allNull() {
		return 	   
			calendar 			== null
		&&  appointmentManager 	== null
		&&  appointmentAdd 		== null
		&&  appointmentRemove	== null;
	}

}
