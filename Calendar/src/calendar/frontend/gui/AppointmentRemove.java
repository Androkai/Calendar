package calendar.frontend.gui;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.AppointmentUtils;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.date.Date;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.backend.main.main;
import calendar.frontend.configs.AppointmentConfig;
import net.minecraft.server.v1_10_R1.DataWatcher.Item;

public class AppointmentRemove extends InventoryUtils {
	
	AppointmentConfig appointmentConfig = main.getAppointmentConfig();
	
	AppointmentDataConfig appointmentDateConfig = main.getAppointmentDataConfig();
	AppointmentUtils appointmentUtils = main.getAppointmentUtils();
	
	Player player;
	Appointment appointment;
	Date date;
	LocalDateTime timeSystem;
	
	Inventory inventory;
	HashMap<Items, Object> items = new HashMap<Items, Object>();
	
	public AppointmentRemove(Player player, Appointment appointment) {
		
		this.player = player;
		this.appointment = appointment;
		this.date = new Date(appointment.getDate());
		this.timeSystem = dateUtils.toLocalDateTime(date);
		
		this.inventory = createRemoveAppointmentInventory();
		
	}
	
	public Appointment getAppointment() {
		return appointment;
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
	
	
	private Inventory createRemoveAppointmentInventory() {
		HashMap<InventoryProperties, Object> properties = appointmentConfig.getRemoveAppointmentInventoryProperties();
		
		String header = (String) properties.get(InventoryProperties.HEADER);
		header = replacePlaceholder(header, date, appointment);
		int size = (int) properties.get(InventoryProperties.SIZE);
		Inventory inventory = Bukkit.createInventory(player, size, header);
		
		HashMap<Items, HashMap<ItemProperties, Object>> itemProperties = (HashMap<Items, HashMap<ItemProperties, Object>>) properties.get(InventoryProperties.ITEMS); 
		
			HashMap<ItemProperties, Object> backToCalendarProperties = itemProperties.get(Items.backToCalendar);
				ItemStack backToCalendarItem = createItem(backToCalendarProperties, date, appointment);
				items.put(Items.backToCalendar, backToCalendarItem);
				inventory.setItem((int) backToCalendarProperties.get(ItemProperties.SLOT), backToCalendarItem);
				
			HashMap<ItemProperties, Object> confirmProperties = itemProperties.get(Items.CONFIRM);
				ItemStack confirmItem = createItem(confirmProperties, date, appointment);
				items.put(Items.CONFIRM, confirmItem);
				inventory.setItem((int) confirmProperties.get(ItemProperties.SLOT), confirmItem);;
		
		
		return inventory;
	}
	

}
