package calendar.frontend.listener.command;

import java.util.HashMap;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import calendar.backend.main.Main;
import calendar.frontend.configs.MessageConfig;
import calendar.frontend.messages.Error;
import calendar.frontend.messages.Notification;

public class CommandListener {
	
	MessageConfig messageConfig = Main.getMessageConfig();
	
	List<String> help = messageConfig.getHelp();
	HashMap<Error, String> errors = messageConfig.getErrors();
	HashMap<Notification, String> notifications = messageConfig.getNotifications();
	
	CommandSender sender;
	
	public CommandListener(CommandSender sender) {
		
		this.sender = sender;
		
	}
	
	public void sendHelp() {
		for(String line : help) {
			sender.sendMessage(line);
		}
	}
	
	public void sendError(Error error) {
		sender.sendMessage(errors.get(error));
	}
	public void sendNotification(Notification notification) {
		sender.sendMessage(notifications.get(notification));
	}
	
	public boolean isPlayer(CommandSender sender) {
		
		if(sender instanceof Player) {
			return true;
		}else{
			sendError(Error.notPlayer);
			return false;
		}
		
	}
	
	public boolean hasPermission(String permissions) {
		
		if(sender.hasPermission(permissions)) {
			return true;
		}else{
			sendError(Error.noPermissions);
			return false;
		}
		
	}
	
}
