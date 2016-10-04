package calendar.frontend.listener.command;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import calendar.backend.main.main;
import calendar.frontend.configs.CommandConfig;
import calendar.frontend.configs.CommandErrors;
import calendar.frontend.gui.Calendar;
import calendar.frontend.gui.StorageUtils;

public class CalendarCommand {

	CommandConfig commandConfig = main.getCommandConfig();
	StorageUtils storageUtils = main.getStorageUtils();
	
	HashMap<CommandErrors, String> errors = commandConfig.getErrors();
	
	public CalendarCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("calendar")){
			
			// Checks if the sender is a Player
			if(sender instanceof Player){
				// Casts the sender to a Player.
				Player player = (Player) sender;
			
				if(args.length == 0) {
					// Checks if the player has permissions.
					if(player.hasPermission("calendar.open")){
				
						// Creates a new calendar instance and saves it with storageCalendar()
						Calendar calendar = new Calendar(player, LocalDateTime.now());
						storageUtils.storageCalendar(player, calendar);
				
						// Opens the inventory of the calendar for the command executor.
						player.openInventory(calendar.getCalendarInventory());
						
					}else{
						player.sendMessage(errors.get(CommandErrors.noPermissions));
					}
					
				}else 
					
					if(args.length == 1) {
						
						if(args[0].equalsIgnoreCase("info")) {
							
							Plugin Calendar = main.instance;
							String ver = Calendar.getDescription().getVersion();
							String author = Calendar.getDescription().getAuthors().get(0);
							String web = Calendar.getDescription().getWebsite();
							
							sender.sendMessage("");
							sender.sendMessage("                 " +main.tag + "");
							sender.sendMessage("                 §1§oVersion: §f"+ ver +"");
							sender.sendMessage("          §2§oAuthor: §6§o"+author+"§f");
							sender.sendMessage("");
							sender.sendMessage(" §o"+web+"");
							sender.sendMessage("");
							
						}else
							
						if(args[0].equalsIgnoreCase("reload")) {
							
							if(player.hasPermission("calendar.reload")){
								
								main.getCalendarConfig().reloadConfig();
								main.getCommandConfig().reloadConfig();
								main.getAppointmentConfig().reloadConfig();
								
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
				sender.sendMessage(errors.get(CommandErrors.notPlayer));;
			}
		}
		
	}

	
	
	
}
