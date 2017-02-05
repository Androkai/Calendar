package calendar.frontend.listener.command;

import java.time.LocalDateTime;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import calendar.backend.Main;
import calendar.backend.configs.CalendarConfig;
import calendar.backend.configs.MessageConfig;
import calendar.backend.dateTime.DateTime;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.gui.calendar.Calendar;
import calendar.frontend.messages.MessageHandler;
import calendar.frontend.messages.MessageHandler.Error;
import calendar.frontend.messages.MessageHandler.Notification;
import calendar.frontend.messages.MessageHandler.OutputType;
import calendar.frontend.messages.output.Chat;
import calendar.frontend.messages.output.Chat.ClickAction;
import calendar.frontend.messages.output.Chat.HoverAction;
public class CalendarCommandListener extends CommandListener {

	CalendarConfig calendarConfig = Main.getCalendarConfig();
	MessageConfig commandConfig = Main.getMessageConfig();
	StorageUtils storageUtils = Main.getStorageUtils();
	
	public CalendarCommandListener(CommandSender sender, Command command, String label, String[] args) {
		super(sender);
		
		if(command.getName().equalsIgnoreCase("calendar")){
			
			if(isPlayer(sender)) {
				Player player = (Player) sender;
				MessageHandler handler = new MessageHandler(player);
				
					switch(args.length) {
						
						//Calendar
						case 0:
							if(hasPermission("calendar.open")) {
								player.openInventory(createCalendar(player));
							}
							break;
					
						case 1:
							switch(args[0]) {
								
								//Help
								case "help":
									sendHelp();
								break;
										
								//Info
								case "info":
									sendInfo(player);
								break;
								
								//Reload
								case "reload":
									if(hasPermission("calendar.reload")) {
										reloadConfigs();
										handler.sendNotification(Notification.CONFIGSRELOAD);
									}
								break;
										
								default:
										handler.sendError(Error.UNKNOWNCOMMAND);
							}
						break;
					
						default:
							handler.sendError(Error.UNKNOWNCOMMAND);
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
	
	private void sendInfo(CommandSender sender) {
		
		Plugin Calendar = Main.instance;
		String prefix = messageConfig.getPluginPrefix();
		String ver = Calendar.getDescription().getVersion();
		String author = Calendar.getDescription().getAuthors().get(0);
		String web = Calendar.getDescription().getWebsite();
		
		sender.sendMessage("");
		sender.sendMessage("                 " + prefix + "");
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
