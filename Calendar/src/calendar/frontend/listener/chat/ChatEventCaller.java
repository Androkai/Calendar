package calendar.frontend.listener.chat;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import calendar.backend.Main;
import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.AppointmentProperties;
import calendar.backend.dateTime.Time;
import calendar.backend.storage.Storage;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.gui.appointment.AppointmentAdd;
import calendar.frontend.messages.MessageUtils;

public class ChatEventCaller extends MessageUtils implements Listener {
	
	StorageUtils storageUtils = Main.getStorageUtils();
	
	@EventHandler
	public void onAsyncChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		
			if(Main.storages.containsKey(player)) {
				Storage storage = Main.storages.get(player);
				
					if(storage.getInputParameter() != null) {
						event.setCancelled(true);
						
							AppointmentProperties parameter = storage.getInputParameter();
							Appointment appointment = storage.getAppointmentAdd().getAppointment();
							storage.setInputParameter(null);
							
								if(parameter.equals(AppointmentProperties.STATUS)) {
									if(isPublic(message)) {
										appointment.setCreator(Main.sUUID);
									}
									
									if(isPrivate(message)) {
										appointment.setCreator(player.getUniqueId());
									}
 								}
								
								if(parameter.equals(AppointmentProperties.TIME)) {
										Time time = Time.fromString(message);
										appointment.getDateTime().setTime(time);
											
								}
								
								if(parameter.equals(AppointmentProperties.NAME)) {
									String name = addSpaces(message);
										appointment.setName(name);
								}
								
								if(parameter.equals(AppointmentProperties.HEADER)) {
									appointment.setHeader(message);
								}
								
								if(parameter.equals(AppointmentProperties.DESCRIPTION)) {
									List<String> description = stringToList(message);
										appointment.setDescription(description);
								}
								
								AppointmentAdd appointmentAdd = new AppointmentAdd(player, appointment, storage.getAppointmentAdd().getDateTime());
								storageUtils.storageAppointmentAdd(player, appointmentAdd);
								player.openInventory(appointmentAdd.getInventory());
									
					}
					
			}
	}
	
	/*
	 * Check if status is public
	 */
	private boolean isPublic(String status) {
				
		if(status.equalsIgnoreCase("public")) {
			return true;
		}else{
			return false;
		}
		
	}
	
	/*
	 * Check if status is private
	 */
	private boolean isPrivate(String status) {
		if(status.equalsIgnoreCase("private")) {
			return true;
		}else{
			return false;
		}
	}

}
