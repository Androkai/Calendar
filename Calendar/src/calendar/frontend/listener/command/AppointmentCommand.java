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
import calendar.backend.configs.AppointmentConfig;
import calendar.backend.date.Date;
import calendar.backend.date.DateUtils;
import calendar.backend.main.main;
import calendar.frontend.configs.CommandConfig;
import calendar.frontend.configs.CommandErrors;
import calendar.frontend.configs.CommandNotifications;

public class AppointmentCommand {
	
	AppointmentConfig appointmentConfig = main.getAppointmentConfig();
	CommandConfig commandConfig = main.getCommandConfig();
	
	DateUtils dateUtils = main.getDateUtils();
	
	HashMap<CommandErrors, String> errors = commandConfig.getErrors();
	HashMap<CommandNotifications, String> notifications = commandConfig.getNotifications();
	
	public AppointmentCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("appointment")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
					if(args.length == 0) {
					
						player.sendMessage("/appointment [add/remove/restore] [public/private] [mm_dd_yyyy] [name] [header] [description]");
						
					}else
					if(args.length == 4) {
						
						if(args[0].equalsIgnoreCase("remove")) {
							if(args[1].equalsIgnoreCase("private")) {
								if(player.hasPermission("calendar.appointment.remove.private")) {
								
									if(this.removeAppointment(player, args)) {
										player.sendMessage(notifications.get(CommandNotifications.appointmentRemoved));
									}else{
										player.sendMessage(errors.get(CommandErrors.appointmentNotExists));;
									}
									
								}else{
									player.sendMessage(errors.get(CommandErrors.noPermissions));
								}
							
							}else
							if(args[1].equalsIgnoreCase("public")) {
								if(player.hasPermission("calendar.appointment.remove.public")) {
									
									if(this.removeAppointment(player, args)) {
										player.sendMessage(notifications.get(CommandNotifications.appointmentRemoved));
									}else{
										player.sendMessage(errors.get(CommandErrors.appointmentNotExists));;
									}
									
								}else{
									player.sendMessage(errors.get(CommandErrors.noPermissions));
								}
							}
							
						}else if(args[0].equalsIgnoreCase("restore")) {
							if(args[1].equalsIgnoreCase("private")) {
								if(player.hasPermission("calendar.appointment.restore.private")) {
								
									if(this.restoreAppointment(player, args)) {
										player.sendMessage(notifications.get(CommandNotifications.appointmentRestored));
									}
									
								}else{
									player.sendMessage(errors.get(CommandErrors.noPermissions));
								}
							
							}else
							if(args[1].equalsIgnoreCase("public")) {
								if(player.hasPermission("calendar.appointment.restore.public")) {
									
									if(this.restoreAppointment(player, args)) {
										player.sendMessage(notifications.get(CommandNotifications.appointmentRestored));
									}
									
								}else{
									player.sendMessage(errors.get(CommandErrors.noPermissions));
								}
							}
						}else{
							player.sendMessage(errors.get(CommandErrors.unkownCommand));
						}
						
					}else
					if(args.length == 6) {
						
						if(args[0].equalsIgnoreCase("add")) {
							if(args[1].equalsIgnoreCase("private")) {
								if(player.hasPermission("calendar.appointment.add.private")) {
								
									if(this.addAppointment(player, args)) {
										player.sendMessage(notifications.get(CommandNotifications.appointmentAdded));
									}else{
										player.sendMessage(errors.get(CommandErrors.appointmentAlreadyExists));
									}
									
								}else{
									player.sendMessage(errors.get(CommandErrors.noPermissions));
								}
							
							}else
							if(args[1].equalsIgnoreCase("public")) {
								if(player.hasPermission("calendar.appointment.add.public")) {
									
									if(this.addAppointment(player, args)) {
										player.sendMessage(notifications.get(CommandNotifications.appointmentAdded));
									}else{
										player.sendMessage(errors.get(CommandErrors.appointmentAlreadyExists));
									}
									
								}else{
									player.sendMessage(errors.get(CommandErrors.noPermissions));
								}
							}
						}else{
							player.sendMessage(errors.get(CommandErrors.unkownCommand));
						}
					}else{
						player.sendMessage(errors.get(CommandErrors.unkownCommand));
					}
			}else{
				sender.sendMessage(errors.get(CommandErrors.notPlayer));
			}
			
		}
		
	}
	
	private boolean addAppointment(Player player, String[] args) {
		
		UUID creator				 = player.getUniqueId();
			if(isPublic(args[1])){
				creator = main.sUUID;
			}
		Date date					 = dateUtils.fromString(args[2]);
		String name 				 = args[3];
		String header 				 = args[4].replaceAll("_", " ");
		List<String> description 	 = stringToList(args[5]);
		HashMap<Flags, Boolean> flags = new HashMap<Flags, Boolean>(); flags.put(Flags.EDITED, false); flags.put(Flags.DELETED, false);
		
		
			if(appointmentConfig.addAppointment(new Appointment(creator, date, name, header, description, flags))){
				return true;
			}
		
		return false;
	}
	
	private boolean removeAppointment(Player player, String[] args) {
		
		UUID creator = player.getUniqueId();
			if(isPublic(args[1])) {
				creator = main.sUUID;
			}
		Date date	 = dateUtils.fromString(args[2]);
		String name	 = args[3];
		
			if(appointmentConfig.removeAppointment(appointmentConfig.getAppointment(creator, date, name))) {
				return true;
			}
		
		return false;
	}
	
	private boolean restoreAppointment(Player player, String[] args) {
		UUID creator = player.getUniqueId();
			if(isPublic(args[1])) {
				creator = main.sUUID;
			}
		Date date	 = dateUtils.fromString(args[2]);
		String name	 = args[3];
			if(appointmentConfig.restoreAppointment(appointmentConfig.getAppointment(creator, date, name))) {
				return true;
			}
		
		return false;
	}
	
	/*
	 * Check if it's public or private
	 */
	private boolean isPublic(String status) {
		
			if(status.equalsIgnoreCase("private")) {
				return false;
			}
		
			if(status.equalsIgnoreCase("public")) {
				return true;
			}
		
		return false;
		
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
