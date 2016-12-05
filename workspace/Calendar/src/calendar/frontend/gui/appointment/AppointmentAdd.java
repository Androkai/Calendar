package calendar.frontend.gui.appointment;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.AppointmentUtils;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.date.DateTime;
import calendar.backend.date.DateTimeUtils;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.backend.main.Main;
import calendar.frontend.configs.AppointmentConfig;
import calendar.frontend.gui.InvProperties;
import calendar.frontend.gui.InventoryUtils;

public class AppointmentAdd extends InventoryUtils {
	
	AppointmentConfig appointmentConfig = Main.getAppointmentConfig();
	AppointmentUtils appointmentUtils = Main.getAppointmentUtils();
	DateTimeUtils dateUtils = Main.getDateTimeUtils();
	
	Player player;
	Appointment appointment;
	DateTime date;
	
	Inventory inventory;
	static HashMap<Items, Object> items = new HashMap<Items, Object>();
	
	public AppointmentAdd(Player player, Appointment appointment, DateTime date) {
		super(date, appointment, items);
		
		this.player = player;
		this.appointment = appointment;
		this.date = new DateTime(date);
		
		this.inventory = createAddAppointmentInventory();
		
	}
	
	public Appointment getAppointment() {
		return appointment;
	}
	
	public DateTime getDate() {
		return date;
	}
	
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public HashMap<Items, Object> getItems() {
		return items;
	}
	
	
	private Inventory createAddAppointmentInventory() {
		HashMap<InvProperties, Object> addAppointmentInventory = appointmentConfig.getAddAppointmentInventoryProperties();
		
			String header 	= (String) addAppointmentInventory.get(InvProperties.TITLE);
			header 			= replacePlaceholder(header, date, appointment);
			int size		= (int) addAppointmentInventory.get(InvProperties.SIZE);
			Inventory inventory = Bukkit.createInventory(player, size, header);
			
			HashMap<Items, HashMap<ItemProperties, Object>> properties = (HashMap<Items, HashMap<ItemProperties, Object>>) addAppointmentInventory.get(InvProperties.ITEMS);
			
				setItem(Items.BACK, inventory, properties);
				
				setItem(Items.setStatus, inventory, properties);
				setItem(Items.setTime, inventory, properties);
				setItem(Items.setName, inventory, properties);
				setItem(Items.setHeader, inventory, properties);
				setItem(Items.setDescription, inventory, properties);
				setItem(Items.CONFIRM, inventory, properties);
				
		return inventory;
	}
	
	public void setItem(Items itemName, Inventory inventory, HashMap<Items, HashMap<ItemProperties, Object>> properties) {
		
		HashMap<ItemProperties, Object> itemProperties = properties.get(itemName);
		ItemStack item = createItem(itemProperties, date, appointment);
		items.put(itemName, item);
		inventory.setItem((int) itemProperties.get(ItemProperties.SLOT), item);
		
	}
}
