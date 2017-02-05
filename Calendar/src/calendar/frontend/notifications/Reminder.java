package calendar.frontend.notifications;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import calendar.backend.Main;
import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.AppointmentUtils;
import calendar.backend.configs.AppointmentConfig;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.dateTime.Date;
import calendar.backend.dateTime.DateTime;
import calendar.backend.dateTime.DateTimeUtils;
import calendar.backend.dateTime.Time;
import calendar.frontend.messages.Message;
import calendar.frontend.messages.MessageHandler;
import calendar.frontend.messages.MessageHandler.OutputType;
import calendar.frontend.messages.MessageUtils;

public class Reminder extends MessageUtils {
	
	public enum ReminderAttributes {TOGGLE, MESSAGE, STARTTIME, WAITTIME}

	AppointmentConfig appoinmentConfig = Main.getAppointmentConfig();
	AppointmentDataConfig appointments = Main.getAppointmentDataConfig();
	
	
	BukkitTask task = null;
	
	public Reminder() {
		enable();
	}
	
	public void enable() {
		run();
	}
	
	public void disable() {
		task.cancel();
		task = null;
	}
	
	public boolean isRunning() {
		return task != null? true : false;
	}
	
	private void run() {
		HashMap<ReminderAttributes, Object> attributes = appoinmentConfig.getReminderAttributes();
		boolean toggle = (Boolean) attributes.get(ReminderAttributes.TOGGLE);
		Message message = (Message) attributes.get(ReminderAttributes.MESSAGE);
		Time startTime = (Time) attributes.get(ReminderAttributes.STARTTIME);
		Time waitTime = (Time) attributes.get(ReminderAttributes.WAITTIME);
		
		if(toggle) {
			task = new BukkitRunnable() {
				
				@Override
				public void run() {
			 		ArrayList<Appointment> pendingAppointments = getPendingAppointments(Main.sUUID, new Date(LocalDate.now()), startTime);
			 		
			 		for(Appointment appointment : pendingAppointments) {
			 			if(!appointment.isDeleted()) {
			 				MessageHandler.broadcastMessage(new Message(replaceAppointmentPlaceholder(message.toString(), appointment)));
			 			}
			 		}
				}
				
			}.runTaskTimerAsynchronously(Main.instance, (1200 - (LocalTime.now().getSecond() * 20)), waitTime.toTicks());
		}
	}
	
	private ArrayList<Appointment> getPendingAppointments(UUID uuid, Date date, Time startTime) {
		ArrayList<Appointment> publicAppointments = appointments.getAppointmentsFromDate(Main.sUUID, date);
		ArrayList<Appointment> pendingAppointments = new ArrayList<Appointment>();
		
			for(Appointment appointment : publicAppointments) {
				
				if(appointment.getDateTime().getTime().isPending(startTime) || appointment.getDateTime().getTime().isNow()) {
					pendingAppointments.add(appointment);
				}
			}
			
		return pendingAppointments;
	}

}
