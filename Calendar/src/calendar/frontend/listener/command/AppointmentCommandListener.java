package calendar.frontend.listener.command;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import calendar.backend.Main;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.configs.MessageConfig;
import calendar.backend.dateTime.DateTimeUtils;
import calendar.frontend.messages.MessageHandler;
import calendar.frontend.messages.MessageHandler.Error;
import calendar.frontend.messages.MessageHandler.Notification;
import calendar.frontend.messages.MessageHandler.OutputType;
import calendar.frontend.notifications.Reminder;

public class AppointmentCommandListener extends CommandListener {
	
	AppointmentDataConfig appointmentDataConfig = Main.getAppointmentDataConfig();
	MessageConfig commandConfig = Main.getMessageConfig();
	DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();
	
	public AppointmentCommandListener(CommandSender sender, Command command, String label, String[] args) {
		super(sender);
		
		if(command.getName().equalsIgnoreCase("appointment")) {
			if(isPlayer(sender)) {
				Player player = (Player) sender;
				MessageHandler handler = new MessageHandler(player);
				
					switch(args.length) {
						case 1:
							switch(args[0]) {
								case "info":
									player.sendMessage("§7File size: " + appointmentDataConfig.getByteSize() + " bytes");
									break;
							
								case "reminder":
									Reminder reminder = Main.getReminder();
										switch(String.valueOf(reminder.isRunning())) {
											case "false":
													reminder.enable();
													handler.sendNotification(Notification.REMINDERENABLE);
											break;
											
											case "true":
													reminder.disable();
													handler.sendNotification(Notification.REMINDERDISABLE);
											break;
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
	
}
