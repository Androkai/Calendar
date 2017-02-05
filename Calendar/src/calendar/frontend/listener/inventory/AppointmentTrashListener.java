package calendar.frontend.listener.inventory;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import calendar.backend.Main;
import calendar.backend.appointments.Appointment;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.dateTime.DateTime;
import calendar.backend.item.Items;
import calendar.backend.storage.Storage;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.gui.appointment.AppointmentManager;
import calendar.frontend.gui.appointment.AppointmentTrash;
import calendar.frontend.gui.calendar.Calendar;

public class AppointmentTrashListener {
	
	AppointmentDataConfig appointmentDataConfig = Main.getAppointmentDataConfig();
	StorageUtils storageUtils = Main.getStorageUtils();
	
	public AppointmentTrashListener(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		
			Storage storage = Main.storages.get(player);
				AppointmentTrash appointmentTrash = storage.getAppointmentTrash();
				DateTime dateTime				  = appointmentTrash.getDateTime();
				HashMap<Items, Object> items = appointmentTrash.getItems();
				
					if(item.isSimilar((ItemStack) items.get(Items.BACK))) {
						player.openInventory(createAppointmentManager(player, dateTime));
					}
				
					HashMap<ItemStack, Appointment> appointmentItems = (HashMap<ItemStack, Appointment>) items.get(Items.APPOINTMENT);
					if(appointmentItems.containsKey(item)) {
						Appointment appointment = appointmentItems.get(item);
						if((appointment.getCreator() == Main.sUUID && player.hasPermission("calendar.appointment.restore.public")) 
								|| (appointment.getCreator() == player.getUniqueId() && player.hasPermission("calendar.appointment.restore.private"))) {
									
								appointmentDataConfig.restoreAppointment(appointment);
									player.openInventory(createCalendar(player, dateTime));
						}
					}
		
	}
	
	private Inventory createCalendar(Player player, DateTime dateTime) {
		Calendar calendar = new Calendar(player, dateTime);
		storageUtils.storageCalendar(player, calendar);
		
		return calendar.getInventory();
	}
	
	private Inventory createAppointmentManager(Player player, DateTime dateTime) {
		AppointmentManager manager = new AppointmentManager(player, dateTime);
		storageUtils.storageAppointmentManager(player, manager);
		
		return manager.getInventory();
	}

}
