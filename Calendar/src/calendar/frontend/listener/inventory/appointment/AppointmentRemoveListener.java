package calendar.frontend.listener.inventory.appointment;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import calendar.backend.appointments.Appointment;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.date.Date;
import calendar.backend.item.Items;
import calendar.backend.main.main;
import calendar.backend.storage.Storage;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.gui.AppointmentManager;
import calendar.frontend.gui.AppointmentRemove;
import calendar.frontend.gui.Calendar;

public class AppointmentRemoveListener {
	
	AppointmentDataConfig appointmentDataConfig = main.getAppointmentDataConfig();
	StorageUtils storageUtils = main.getStorageUtils();
	
	public AppointmentRemoveListener(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		
			Storage storage = main.storages.get(player);
				AppointmentRemove appointmentRemove = storage.getAppointmentRemove();
				HashMap<Items, Object> items = appointmentRemove.getItems();
				
					
				if(item.isSimilar((ItemStack) items.get(Items.backToCalendar))) {
					player.openInventory(createCalendar(player, appointmentRemove.getDate()));
				}
				
				if(item.isSimilar((ItemStack) items.get(Items.CONFIRM))) {
					Appointment appointment = appointmentRemove.getAppointment();
						if((appointment.getCreator() == main.sUUID && player.hasPermission("calendar.appointment.remove.public")) 
							|| (appointment.getCreator() == player.getUniqueId() && player.hasPermission("calendar.appointment.remove.private"))) {
								appointmentDataConfig.removeAppointment(appointment);
						}
								player.openInventory(createCalendar(player, appointmentRemove.getDate()));
				}
		
	}
	
	private Inventory createCalendar(Player player, Date date) {
		Calendar calendar = new Calendar(player, date);
		storageUtils.storageCalendar(player, calendar);

		
		return calendar.getInventory();
	}

}
