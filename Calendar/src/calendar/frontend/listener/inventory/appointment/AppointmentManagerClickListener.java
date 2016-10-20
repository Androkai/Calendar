package calendar.frontend.listener.inventory.appointment;

import java.time.LocalDateTime;
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

public class AppointmentManagerClickListener {

	AppointmentDataConfig appointmentDataConfig = main.getAppointmentDataConfig();
	StorageUtils storageUtils = main.getStorageUtils();
	
	public AppointmentManagerClickListener(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		
			Storage storage = main.storages.get(player);
				AppointmentManager appointmentManager = storage.getAppointmentManager();
				HashMap<Items, Object> items = appointmentManager.getItems();
				
				
					if(item.isSimilar((ItemStack) items.get(Items.backToCalendar))) {
						player.openInventory(createCalendar(player, appointmentManager.getDate()));
					}
					
					HashMap<Items, Appointment> publicAppointmentItems = (HashMap<Items, Appointment>) items.get(Items.publicAppointments);
					if(publicAppointmentItems.containsKey(item)) {
						Appointment appointment = publicAppointmentItems.get(item);
							player.openInventory(createAppointmentRemove(player, appointment));
					}
					HashMap<Items, Appointment> privateAppointmentItems = (HashMap<Items, Appointment>) items.get(Items.privateAppointments);
					if(privateAppointmentItems.containsKey(item)) {
						Appointment appointment = privateAppointmentItems.get(item);
							player.openInventory(createAppointmentRemove(player, appointment));
					}
			
	}
	
	private Inventory createCalendar(Player player, Date date) {
		Calendar calendar = new Calendar(player, date);
		storageUtils.storageCalendar(player, calendar);

		
		return calendar.getInventory();
	}
	
	private Inventory createAppointmentRemove(Player player, Appointment appointment) {
		AppointmentRemove appointmentRemove = new AppointmentRemove(player, appointment);
		storageUtils.storageAppointmentRemove(player, appointmentRemove);
		
		return appointmentRemove.getInventory();
	}

}
