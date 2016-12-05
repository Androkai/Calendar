package calendar.frontend.notifications;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.AppointmentUtils;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.date.Date;
import calendar.backend.date.DateTime;
import calendar.backend.date.DateTimeUtils;
import calendar.backend.date.Time;
import calendar.backend.main.Main;
import calendar.frontend.configs.AppointmentConfig;
import calendar.frontend.gui.InventoryUtils;
import calendar.frontend.gui.MessageUtils;

public class Reminder extends MessageUtils {

	BukkitScheduler sheduler = Bukkit.getScheduler();
	AppointmentDataConfig appointmentDateConfig = Main.getAppointmentDataConfig();
	
	DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();
	AppointmentUtils appointmentUtils = Main.getAppointmentUtils();
	
	int task;
	
	@SuppressWarnings("deprecation")
	public Reminder() {
		enable();
	}
	
	public void enable() {
		AppointmentConfig appointmentConfig = Main.getAppointmentConfig();
		HashMap<ReminderProperties, Object> reminder = appointmentConfig.getReminder();
		
		if((boolean) reminder.get(ReminderProperties.TOGGLE)) {
			this.task = sheduler.scheduleAsyncRepeatingTask(Main.instance, new Runnable() {
				@Override
				public void run() {
					DateTime dateTime = new DateTime(LocalDateTime.now());
					ArrayList<Appointment> appointments = new ArrayList<Appointment>();
							
						for(Player player : Bukkit.getOnlinePlayers()) {			
							appointments.addAll(appointmentDateConfig.getAppointmentsFromDate(Main.sUUID, dateTime.getDate()));
							appointments.addAll(appointmentDateConfig.getAppointmentsFromDate(player.getUniqueId(), dateTime.getDate()));
							appointments = appointmentUtils.removeDeletedAppointments(appointments);
							
								for(Appointment appointment : appointments) {
									Time appointmentTime  = appointment.getDateTime().getTime();
									Time time			  = new DateTime(LocalDateTime.now()).getTime();
									long remainingMinutes = dateTimeUtils.getMinutesOfDay(appointmentTime) - dateTimeUtils.getMinutesOfDay(time);
									
									
										if(remainingMinutes <= (int) reminder.get(ReminderProperties.startAt) && remainingMinutes >= 0) {
													player.sendMessage(replacePlaceholder((String) reminder.get(ReminderProperties.MESSAGE), dateTime, appointment));
								
										}
								}
						}
						
					
				}
			}, 0, (int) reminder.get(ReminderProperties.repeatAfter));
		}
	}
	
	public void disable() {
		sheduler.cancelTask(task);
	}
	
	public boolean isRunning() {
		return sheduler.isCurrentlyRunning(task);
	}

}
