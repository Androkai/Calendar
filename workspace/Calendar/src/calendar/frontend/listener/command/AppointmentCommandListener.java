package calendar.frontend.listener.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.Flags;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.date.Date;
import calendar.backend.date.DateTime;
import calendar.backend.date.DateTimeUtils;
import calendar.backend.main.Main;
import calendar.frontend.configs.MessageConfig;
import calendar.frontend.messages.Error;
import calendar.frontend.messages.Notification;
import calendar.frontend.notifications.Reminder;

public class AppointmentCommandListener extends CommandListener {
	
	AppointmentDataConfig appointmentDataConfig = Main.getAppointmentDataConfig();
	MessageConfig commandConfig = Main.getMessageConfig();
	DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();
	
	HashMap<Error, String> errors = commandConfig.getErrors();
	HashMap<Notification, String> notifications = commandConfig.getNotifications();
	
	public AppointmentCommandListener(CommandSender sender, Command command, String label, String[] args) {
		super(sender);
		
		if(command.getName().equalsIgnoreCase("appointment")) {
			if(isPlayer(sender)) {
				Player player = (Player) sender;
				
					if(args.length == 2) {
						
						if(args[0].equalsIgnoreCase("reminder")) {
							Reminder reminder = Main.getReminder();
							
								if(args[1].equalsIgnoreCase("disable")) {
									if(hasPermission("calendar.appointment.reminder.disable")) {
										
										if(reminder.isRunning()) {
											reminder.disable();
											sendNotification(Notification.reminderDisabled);
										}
										
									}
								}else
								if(args[1].equalsIgnoreCase("enable")) {
									if(hasPermission("calendar.appointment.reminder.enable")) {
										
										if(!reminder.isRunning()) {
											reminder.enable();
											sendNotification(Notification.reminderEnabled);	
										}
										
									}
								}else{
									sendError(Error.unkownCommand);;
								}
						}
					}else
					if(args.length == 4) {
						
						if(args[0].equalsIgnoreCase("remove")) {
							if(isPrivate(args[1])) {
								if(hasPermission("calendar.appointment.remove.private")) {
								
									this.removeAppointment(player, args);
										sendNotification(Notification.appointmentRemoved);
									
								}
							}else
							if(isPublic(args[1])) {
								if(hasPermission("calendar.appointment.remove.public")) {
									
									this.removeAppointment(player, args);
										sendNotification(Notification.appointmentRemoved);
									
								}
							}
						}else 
						if(args[0].equalsIgnoreCase("restore")) {
							if(isPrivate(args[1])) {
								if(hasPermission("calendar.appointment.restore.private")) {
								
									this.restoreAppointment(player, args);
										sendNotification(Notification.appointmentRestored);
									
								}
							}else
							if(isPublic(args[1])) {
								if(hasPermission("calendar.appointment.restore.public")) {
									
									this.restoreAppointment(player, args);
										sendNotification(Notification.appointmentRestored);
									
								}
							}
						}else{
							sendError(Error.unkownCommand);
						}
						
					}else
					if(args.length == 6) {
						
						if(args[0].equalsIgnoreCase("add")) {
							if(isPrivate(args[1])) {
								if(hasPermission("calendar.appointment.add.private")) {
								
									this.addAppointment(player, args);
										sendNotification(Notification.appointmentAdded);
								}
							
							}else
							if(isPublic(args[1])) {
								if(hasPermission("calendar.appointment.add.public")) {
									
									this.addAppointment(player, args);
										sendNotification(Notification.appointmentAdded);
									
								}
							}
						}else{
							sendError(Error.unkownCommand);
						}
					}else{
						sendError(Error.unkownCommand);
					}
			}
		}
		
	}
	
	private void addAppointment(Player player, String[] args) {
		
		UUID creator				 = player.getUniqueId();
			if(isPublic(args[1])){
				creator = Main.sUUID;
			}
		Date date					 = dateTimeUtils.fromString(args[2]);
		String name 				 = args[3];
			name = name.replaceAll("_", " ");
		String header 				 = args[4].replaceAll("_", " ");
		List<String> description 	 = stringToList(args[5]);
		HashMap<Flags, Boolean> flags = new HashMap<Flags, Boolean>(); flags.put(Flags.EDITED, false); flags.put(Flags.DELETED, false);
		
		
			appointmentDataConfig.addAppointment(new Appointment(creator, new DateTime(date, dateTimeUtils.getNullTime()), name, header, description, flags));
		
	}
	
	private void removeAppointment(Player player, String[] args) {
		
		UUID creator = player.getUniqueId();
			if(isPublic(args[1])) {
				creator = Main.sUUID;
			}
		Date date	 = dateTimeUtils.fromString(args[2]);
		String name	 = args[3];
			name = name.replaceAll("_", " ");
		
			appointmentDataConfig.removeAppointment(appointmentDataConfig.getAppointment(creator, date, name));
		
	}
	
	private void restoreAppointment(Player player, String[] args) {
		UUID creator = player.getUniqueId();
			if(isPublic(args[1])) {
				creator = Main.sUUID;
			}
		Date date	 = dateTimeUtils.fromString(args[2]);
		String name	 = args[3];
			name = name.replaceAll("_", " ");
			
				appointmentDataConfig.restoreAppointment(appointmentDataConfig.getAppointment(creator, date, name));
	}
	
	/*
	 * Check if status is public
	 */
	private boolean isPublic(String status) {
				
		if(status.equalsIgnoreCase("public")) {
			return true;
		}else{
			return false;
		}
		
	}
	
	/*
	 * Check if status is private
	 */
	private boolean isPrivate(String status) {
		if(status.equalsIgnoreCase("private")) {
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * Constructs a list out of a given string.
	 */
	private List<String> stringToList(String string) {
		List<String> description = new ArrayList<String>();
		String[] lines = string.split("%n");
		
			for(String line : lines) {
				line = line.replaceAll("_", " ");
				
				description.add(line);
			}
			
		return description;
	}
	
}
