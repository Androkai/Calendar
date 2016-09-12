package calendar.frontend.listener.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCaller implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		// Checks if the command name equals one of the calendar commands.
		if(command.getName().equalsIgnoreCase("calendar")){
			new CalendarCommand(sender, command, label, args);
		}
		
		
		return true;
	}

}