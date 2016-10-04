package calendar.frontend.listener.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCaller implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("calendar")){
			new CalendarCommand(sender, command, label, args);
		}
		
		if(command.getName().equalsIgnoreCase("appointment")){
			new AppointmentCommand(sender, command, label, args);
		}
		
		return true;
	}

}
