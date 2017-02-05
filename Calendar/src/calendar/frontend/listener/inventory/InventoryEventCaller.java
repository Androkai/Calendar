package calendar.frontend.listener.inventory;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import calendar.backend.Main;
import calendar.backend.storage.Storage;
import calendar.frontend.gui.appointment.AppointmentAdd;
import calendar.frontend.gui.appointment.AppointmentManager;
import calendar.frontend.gui.appointment.AppointmentRemove;
import calendar.frontend.gui.appointment.AppointmentTrash;
import calendar.frontend.gui.calendar.Calendar;

public class InventoryEventCaller implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		Inventory inventory = event.getClickedInventory();
		HashMap<Player, Storage> storages = Main.storages;
		
		if(inventory != null) {
			if(storages.containsKey(player)) {
				event.setCancelled(true);
					Storage storage = storages.get(player);
					
						Calendar calendar = storage.getCalendar();
						if(calendar != null) {	
							if(inventory.equals(calendar.getInventory())) {
								new CalendarListener(event);
							}
						}
						
						AppointmentManager appointmentManager = storage.getAppointmentManager();
						if(appointmentManager != null) {
							if(inventory.equals(appointmentManager.getInventory())) {
								new AppointmentManagerListener(event);
							}
						}
						
						AppointmentAdd appointmentAdd = storage.getAppointmentAdd();
						if(appointmentAdd != null) {
							if(inventory.equals(appointmentAdd.getInventory())) {
								new AppointmentAddListener(event);
							}
						}
						
						AppointmentRemove appointmentRemove = storage.getAppointmentRemove();
						if(appointmentRemove != null) {
							if(inventory.equals(appointmentRemove.getInventory())) {
								new AppointmentRemoveListener(event);
							}
						}
						
						AppointmentTrash appointmentTrash = storage.getAppointmentTrash();
						if(appointmentTrash != null) {
							if(inventory.equals(appointmentTrash.getInventory())) {
								new AppointmentTrashListener(event);
							}
						}
			}
		}
		
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		new InventoryCloseListener(event);
		
	}

}
