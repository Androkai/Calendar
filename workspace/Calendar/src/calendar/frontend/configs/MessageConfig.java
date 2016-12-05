package calendar.frontend.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;

import calendar.backend.configs.Config;
import calendar.backend.main.Main;
import calendar.frontend.messages.Error;
import calendar.frontend.messages.Notification;
import net.md_5.bungee.api.ChatColor;

public class MessageConfig extends Config {
	
	public MessageConfig() {
		super(Main.instance.getDataFolder(), "MessageConfig.yml");
		
		config = super.loadConfig();
	}
	
	public String getPluginPrefix() {
		String prefix = getFormatString("prefix");
		
		return prefix;
	}
	
	public List<String> getHelp() {
		return getListFormatString("help");
	}
	
	public HashMap<Error, String> getErrors() {	
		HashMap<Error, String> errors = new HashMap<Error, String>();
		String path = "errors.";
		
		errors.put(Error.noPermissions, 				getCommandString(path + "noPermissions"));
		errors.put(Error.notPlayer, 					getCommandString(path + 	"notPlayer"));
		errors.put(Error.unkownCommand, 				getCommandString(path + "unknownCommand"));
		
		errors.put(Error.appointmentAlreadyExsists, 	getCommandString(path + "appointmentAlreadyExsists"));
		errors.put(Error.appointmentNotExists, 			getCommandString(path + "appointmentNotExsists"));
		
		return errors;
	}
	
	public HashMap<Notification, String> getNotifications() {
		HashMap<Notification, String> notifications = new HashMap<Notification, String>();
		String path = "notifications.";
		
		notifications.put(Notification.configsReloaded, 		getCommandString(path + "configsReloaded"));
		
		notifications.put(Notification.appointmentAdded, 		getCommandString(path + "appointmentAdded"));
		notifications.put(Notification.appointmentRemoved, 		getCommandString(path + "appointmentRemoved"));
		notifications.put(Notification.appointmentRestored, 	getCommandString(path + "appointmentRestored"));
		
		notifications.put(Notification.reminderEnabled, 		getCommandString(path + "reminderEnabled"));
		notifications.put(Notification.reminderDisabled, 		getCommandString(path + "reminderDisabled"));
		
		return notifications;
	}
	
	public String getCommandString(String path) {
		return Main.prefix + getFormatString(path);
	}
}
