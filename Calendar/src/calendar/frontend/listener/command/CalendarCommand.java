package calendar.frontend.listener.command;

import java.time.LocalDateTime;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import calendar.backend.main.main;
import calendar.frontend.gui.Calendar;
import calendar.frontend.gui.Storage;
import calendar.frontend.gui.StorageUtils;

public class CalendarCommand {

	StorageUtils storageUtils = main.getStorageUtils();
	
	public CalendarCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("calendar")){
			
			// Checks if the sender is a Player
			if(sender instanceof Player){
				// Casts the sender to a Player.
				Player player = (Player) sender;
			
				// Checks if the player has permissions.
				if(player.hasPermission("Calendar.open")){
				
					// Creates a new calendar instance and saves it with storageCalendar()
					Calendar calendar = new Calendar(LocalDateTime.now());
					storageUtils.storageCalendar(player, calendar);
				
					// Opens the inventory of the calendar for the command executor.
					player.openInventory(calendar.getCalendarInventory());
				}
			
			}
		}
		
	}

	
	
	
}
