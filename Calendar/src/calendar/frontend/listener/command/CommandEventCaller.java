package calendar.frontend.listener.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandEventCaller implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("calendar")){
			new CalendarCommandListener(sender, command, label, args);
		}
		
		if(command.getName().equalsIgnoreCase("appointment")){
			new AppointmentCommandListener(sender, command, label, args);
		}
		
		return true;
	}

}
