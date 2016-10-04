package calendar.frontend.listener.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import calendar.backend.appointments.Flags;
import calendar.backend.configs.AppointmentConfig;
import calendar.backend.date.Date;
import calendar.backend.date.DateUtils;
import calendar.backend.main.main;
import calendar.frontend.configs.CommandConfig;
import calendar.frontend.configs.CommandErrors;

public class AppointmentCommand {
	
	AppointmentConfig appointmentConfig = main.getAppointmentConfig();
	CommandConfig commandConfig = main.getCommandConfig();
	
	DateUtils dateUtils = main.getDateUtils();
	
	HashMap<CommandErrors, String> errors = commandConfig.getErrors();
	
	
	public AppointmentCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("appointment")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
					if(args.length == 6) {
						
						if(args[0].equalsIgnoreCase("add")) {
							if(player.hasPermission("calendar.appointment.add")) {
								
								if(!createAppointment(player, args)) {
									player.sendMessage(errors.get(CommandErrors.appointmentAlreadyExists));
								}
								
							}else{
								player.sendMessage(errors.get(CommandErrors.noPermissions));
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
	
	private boolean createAppointment(Player player, String[] args) {
		
		UUID creator				 = player.getUniqueId();
		Date date					 = dateUtils.fromString(args[1]);
		int number 					 = Integer.valueOf(args[4]);
		String header 				 = args[2].replaceAll("_", " ");
		List<String> description 	 = stringToList(args[3]);
		boolean isPublic			 = Boolean.valueOf(args[5]);
			if(isPublic){
				creator = main.sUUID;
			}
		HashMap<Flags, Boolean> flags = new HashMap<Flags, Boolean>(); flags.put(Flags.EDITED, false); flags.put(Flags.DELETED, false);
		
			if(appointmentConfig.createAppointment(creator, date, number, header, description, flags)){
				return true;
			}
		
		return false;
	}
	
	private List<String> stringToList(String string) {
		List<String> description = new ArrayList<String>();
		
		String[] lines = string.split("%next%");
			for(String line : lines) {
				line = line.replaceAll("_", " ");
				
				description.add(line);
			}
			
		return description;
	}
	
}
