package calendar.backend.storage;

import java.util.HashMap;

import org.bukkit.entity.Player;

import calendar.backend.main.main;
import calendar.frontend.gui.AppointmentAdd;
import calendar.frontend.gui.AppointmentManager;
import calendar.frontend.gui.AppointmentRemove;
import calendar.frontend.gui.Calendar;

public class StorageUtils {
	
	public void storageCalendar(Player player, Calendar calendar) {
		Storage storage = getPlayerStorage(player);
			storage.setCalendar(calendar);
				updateStorageMap(player, storage);
	}
	
	public void storageAppointmentManager(Player player, AppointmentManager appointmentManager) {
		Storage storage = getPlayerStorage(player);
			storage.setAppointmentManager(appointmentManager);
				updateStorageMap(player, storage);
	}
	
	public void storageAppointmentRemove(Player player, AppointmentRemove appointmentRemove) {
		Storage storage = getPlayerStorage(player);
			storage.setAppointmentRemove(appointmentRemove);
				updateStorageMap(player, storage);
	}
	
	public void storageAppointmentAdd(Player player, AppointmentAdd appointmentAdd) {
		Storage storage = getPlayerStorage(player);
			storage.setAppointmentAdd(appointmentAdd);
				updateStorageMap(player, storage);
	}
	
	public Storage getPlayerStorage(Player player) {
		// Gets the map with all storages
		HashMap<Player, Storage> storages = main.storages;
		
		Storage storage;
		
		// Checks if the storage exists
		if(storages.containsKey(player)){
			
			// Gets the existing storage out of the map
			storage = storages.get(player);
			// Deletes the storage out of the map
			storages.remove(player);
			
		}else{
			
			// Creates a new Storage instance
			storage = new Storage();
			// Sets the holder of the storage
			storage.setHolder(player);
			
		}
		
			return storage;
	}
	
	public void updateStorageMap(Player player, Storage storage) {
		// Gets the map with all storages
		HashMap<Player, Storage> storages = main.storages;
		
		// Puts the new or edited storage in the storage map
		storages.put(player, storage);
		
		// Sets the storage map to the edited one
		main.storages = storages;
	}

}