package calendar.frontend.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;

import calendar.backend.configs.Config;
import calendar.backend.main.main;
import net.md_5.bungee.api.ChatColor;

public class CommandConfig extends Config {
	
	public CommandConfig() {
		super(main.instance.getDataFolder(), "CommandConfig.yml");
		
		config = super.loadConfig();
	}
	
	public HashMap<CommandErrors, String> getErrors() {	
		HashMap<CommandErrors, String> errors = new HashMap<CommandErrors, String>();
		String path = "Errors.";
		
		errors.put(CommandErrors.noPermissions, 			getCommandString(path + "noPermissions"));
		errors.put(CommandErrors.notPlayer, 				getCommandString(path + 	"notPlayer"));
		errors.put(CommandErrors.unkownCommand, 			getCommandString(path + "unknownCommand"));
		
		errors.put(CommandErrors.appointmentAlreadyExsists, getCommandString(path + "appointmentAlreadyExsists"));
		errors.put(CommandErrors.appointmentNotExists, 		getCommandString(path + "appointmentNotExsists"));
		
		return errors;
	}
	
	public HashMap<CommandNotifications, String> getNotifications() {
		HashMap<CommandNotifications, String> notifications = new HashMap<CommandNotifications, String>();
		String path = "Notifications.";
		
		notifications.put(CommandNotifications.configsReloaded, 	getCommandString(path + "configsReloaded"));
		
		notifications.put(CommandNotifications.appointmentAdded, 	getCommandString(path + "appointmentAdded"));
		notifications.put(CommandNotifications.appointmentRemoved, 	getCommandString(path + "appointmentRemoved"));
		notifications.put(CommandNotifications.appointmentRestored, getCommandString(path + "appointmentRestored"));
		
		return notifications;
	}
	
	public String getCommandString(String path) {
		return main.tag + getFormatString(path);
	}
}
