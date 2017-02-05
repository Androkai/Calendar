package calendar.frontend.listener.command;

import java.util.HashMap;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import calendar.backend.Main;
import calendar.backend.configs.MessageConfig;
import calendar.frontend.messages.MessageHandler;
import calendar.frontend.messages.MessageHandler.Error;
import calendar.frontend.messages.MessageHandler.OutputType;

public class CommandListener {
	
	MessageConfig messageConfig = Main.getMessageConfig();
	List<String> help = messageConfig.getHelp();
	
	Player player;
	MessageHandler handler;
	
	public CommandListener(CommandSender sender) {
		
		if(isPlayer(sender)) {
			this.player = (Player) sender;
			handler = new MessageHandler(player);
		}
	}
	
	public void sendHelp() {
		for(String line : help) {
			player.sendMessage(line);
		}
	}
	
	public boolean isPlayer(CommandSender sender) {
		
		if(sender instanceof Player) {
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean hasPermission(String permissions) {
		
		if(player.hasPermission(permissions)) {
			return true;
		}else{
			handler.sendError(Error.NOPERMISSIONS);
			return false;
		}
		
	}
	
}
