package calendar.frontend.listener.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import calendar.backend.date.DateTime;
import calendar.backend.date.DateTimeUtils;
import calendar.backend.main.Main;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.configs.CalendarConfig;
import calendar.frontend.configs.MessageConfig;
import calendar.frontend.gui.calendar.Calendar;
import calendar.frontend.messages.Error;
import calendar.frontend.messages.Notification;

public class CalendarCommandListener extends CommandListener {

	CalendarConfig calendarConfig = Main.getCalendarConfig();
	MessageConfig commandConfig = Main.getMessageConfig();
	StorageUtils storageUtils = Main.getStorageUtils();
	
	HashMap<Error, String> errors = commandConfig.getErrors();
	HashMap<Notification, String> notifications = commandConfig.getNotifications();
	
	public CalendarCommandListener(CommandSender sender, Command command, String label, String[] args) {
		super(sender);
		
		if(command.getName().equalsIgnoreCase("calendar")){
			
			if(isPlayer(sender)) {
				Player player = (Player) sender;
				
					switch(args.length) {
			
						case 0:
							if(hasPermission("calendar.open")) {
								player.openInventory(createCalendar(player));
							}
							break;
					
						case 1:
							switch(args[0]) {
							
								case "help":
									sendHelp();
									break;
										
								case "info":
									sendInfo(player);
									break;
									
								case "reload":
									if(hasPermission("calendar.reload")) {
										reloadConfigs();
										sendNotification(Notification.configsReloaded);
									}
									break;
										
								default:
										sendError(Error.unkownCommand);
										break;
							}
							break;
					
						default:
							sendError(Error.unkownCommand);
							break;
					}
			}
		}
		
	}
	
	private Inventory createCalendar(Player player) {
		LocalDateTime timeSystem = LocalDateTime.now(calendarConfig.getTimeZone().toZoneId());
		DateTime date = new DateTime(timeSystem);
		
		Calendar calendar = new Calendar(player, date);
		storageUtils.storageCalendar(player, calendar);

		return calendar.getInventory();
	}
	
	private void sendInfo(Player player) {
		
		Plugin Calendar = Main.instance;
		String ver = Calendar.getDescription().getVersion();
		String author = Calendar.getDescription().getAuthors().get(0);
		String web = Calendar.getDescription().getWebsite();
		
		sender.sendMessage("");
		sender.sendMessage("                 " + Main.prefix + "");
		sender.sendMessage("                 §1§oVersion: §f"+ ver +"");
		sender.sendMessage("          §2§oAuthor: §6§o"+ author +"§f");
		sender.sendMessage("");
		sender.sendMessage(" §o"+web+"");
		sender.sendMessage("");
		
	}

	private void reloadConfigs() {
		
		Main.getCalendarConfig().reloadConfig();
		Main.getMessageConfig().reloadConfig();
		Main.getAppointmentConfig().reloadConfig();
		Main.getAppointmentDataConfig().reloadConfig();
		
	}
	
	
}
