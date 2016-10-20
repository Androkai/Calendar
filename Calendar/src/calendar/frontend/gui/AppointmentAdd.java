package calendar.frontend.gui;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import calendar.backend.appointments.AppointmentUtils;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.date.Date;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.backend.main.main;

public class AppointmentAdd extends InventoryUtils {
	
	AppointmentDataConfig appointmentDataConfig = main.getAppointmentDataConfig();
	AppointmentUtils appointmentUtils = main.getAppointmentUtils();
	
	Player player;
	Date date;
	LocalDateTime timeSystem;
	
	Inventory inventory;
	HashMap<Items, Object> items = new HashMap<Items, Object>();
	
	public AppointmentAdd(Player player, Date date, LocalDateTime timeSystem) {
		
		this.player = player;
		this.date = date;
		this.timeSystem = timeSystem;
		
		this.inventory = createAddAppointmentInventory();
		
	}
	
	public Date getDate() {
		return date;
	}
	
	public LocalDateTime getTimeSystem() {
		return timeSystem;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public HashMap<Items, Object> getItems() {
		return items;
	}
	
	
	private Inventory createAddAppointmentInventory() {
		HashMap<InventoryProperties, Object> addAppointmentInventory = null;
			HashMap<Items, HashMap<ItemProperties, Object>> item = (HashMap<Items, HashMap<ItemProperties, Object>>) addAppointmentInventory.get(InventoryProperties.ITEMS);
			String header 	= (String) addAppointmentInventory.get(InventoryProperties.HEADER);
			int size		= (int) addAppointmentInventory.get(InventoryProperties.SIZE);
			Inventory inventory = Bukkit.createInventory(player, size, header);
			
		
		return inventory;
	}
}
