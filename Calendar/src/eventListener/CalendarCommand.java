package eventListener;

import java.time.LocalDateTime;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import frontend.gui.Calendar;

public class CalendarCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("calendar")){
			Player player = (Player) sender;
			
			player.openInventory(new Calendar().createCalendar(LocalDateTime.now()));
		}
		
		return true;
	}

	
	
	
}
