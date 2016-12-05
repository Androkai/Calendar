package calendar.frontend.listener.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import calendar.backend.main.Main;
import calendar.backend.storage.Storage;
import calendar.frontend.gui.appointment.AppointmentAdd;
import calendar.frontend.gui.appointment.AppointmentManager;
import calendar.frontend.gui.appointment.AppointmentRemove;
import calendar.frontend.gui.calendar.Calendar;
public class InventoryCloseListener {
	
	public InventoryCloseListener(InventoryCloseEvent event){
		
		Inventory inventory = event.getInventory();
		
		if(event.getPlayer() instanceof Player){
			Player player = (Player) event.getPlayer();
			Storage storage = Main.storages.get(player);
			
			/*
			 * Checks if the Storage of the player isn't null, to prevent NullPointerExeptions.
			 */
			if(storage != null){
				
				Calendar calendar = storage.getCalendar();
				if(calendar != null) {
					if(inventory.equals(calendar.getInventory())) {
						storage.setCalendar(null);
					}
				}
				
				AppointmentManager appointmentManager = storage.getAppointmentManager();
				if(appointmentManager != null) {
					if(inventory.equals(appointmentManager.getInventory())) {
						storage.setAppointmentManager(null);
					}
				}
				
				AppointmentAdd appointmentAdd = storage.getAppointmentAdd();
				if(appointmentAdd != null) {
					if(inventory.equals(appointmentAdd.getInventory())) {
						if(storage.getInputParameter() == null) {
							storage.setAppointmentAdd(null);
						}
					}
				}
				
				AppointmentRemove appointmentRemove = storage.getAppointmentRemove();
				if(appointmentRemove != null) {
					if(inventory.equals(appointmentRemove.getInventory())) {
						storage.setAppointmentRemove(null);
					}
				}
				
				
				if(storage.allNull()){
					Main.storages.remove(player);
				}else{
					Main.storages.put(player, storage);
				}
			}
			
		}
		
	}

}
