package calendar.frontend.listener.inventory.appointment;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import calendar.backend.appointments.Appointment;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.date.DateTime;
import calendar.backend.item.Items;
import calendar.backend.main.Main;
import calendar.backend.storage.Storage;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.gui.appointment.AppointmentManager;
import calendar.frontend.gui.appointment.AppointmentRemove;
import calendar.frontend.gui.calendar.Calendar;
import calendar.frontend.messages.Error;

public class AppointmentRemoveListener {
	
	AppointmentDataConfig appointmentDataConfig = Main.getAppointmentDataConfig();
	StorageUtils storageUtils = Main.getStorageUtils();
	
	HashMap<Error, String> errors = Main.getMessageConfig().getErrors();
	
	public AppointmentRemoveListener(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		
			Storage storage = Main.storages.get(player);
				AppointmentRemove appointmentRemove = storage.getAppointmentRemove();
				HashMap<Items, Object> items = appointmentRemove.getItems();
				
					
				if(item.isSimilar((ItemStack) items.get(Items.BACK))) {
					player.openInventory(createAppointmentManager(player, appointmentRemove.getDate()));
				}
				
				if(item.isSimilar((ItemStack) items.get(Items.CONFIRM))) {
					Appointment appointment = appointmentRemove.getAppointment();
						if((appointment.getCreator() == Main.sUUID && player.hasPermission("calendar.appointment.remove.public")) 
							|| (appointment.getCreator() == player.getUniqueId() && player.hasPermission("calendar.appointment.remove.private"))) {
								
								appointmentDataConfig.removeAppointment(appointment);
									player.openInventory(createCalendar(player, appointmentRemove.getDate()));
						}else{
							player.closeInventory();
							player.sendMessage(errors.get(Error.noPermissions));
						}
				}
		
	}
	
	private Inventory createCalendar(Player player, DateTime date) {
		Calendar calendar = new Calendar(player, date);
		storageUtils.storageCalendar(player, calendar);
		
		return calendar.getInventory();
	}
	
	private Inventory createAppointmentManager(Player player, DateTime date) {
		AppointmentManager appointmentManager = new AppointmentManager(player, date);
		storageUtils.storageAppointmentManager(player, appointmentManager);
		
		return appointmentManager.getInventory();
	}

}
