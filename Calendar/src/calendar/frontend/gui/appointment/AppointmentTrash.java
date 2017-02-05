package calendar.frontend.gui.appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import calendar.backend.Main;
import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.AppointmentUtils;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.dateTime.Date;
import calendar.backend.dateTime.DateTime;
import calendar.backend.dateTime.DateTimeUtils;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.frontend.gui.InvProperties;
import calendar.frontend.gui.InventoryUtils;

public class AppointmentTrash extends InventoryUtils {
	
	AppointmentDataConfig appointmentDataConfig = Main.getAppointmentDataConfig();
	
	AppointmentUtils appointmentUtils = Main.getAppointmentUtils();
	DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();
	
	Player player;
	DateTime dateTime;
	LocalDateTime timeSystem;
	
	Inventory inventory;
	static HashMap<Items, Object> items = new HashMap<Items, Object>();
	
	public AppointmentTrash(Player player, DateTime dateTime) {
		super(dateTime, null, items);
		
		this.player 	= player;
		this.dateTime 	= new DateTime(dateTime);
		this.timeSystem = dateTime.toLocalDateTime();
		
		this.inventory  = createInventory(player, dateTime);
	}
	
	public DateTime getDateTime() {
		return dateTime;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public HashMap<Items, Object> getItems() {
		return items;
	}
	
	public Inventory createInventory(Player player, DateTime dateTime) {
		HashMap<InvProperties, Object> properties = Main.getAppointmentConfig().getAppointmentTrashInventoryProperties();
		Date date = dateTime.getDate();
		
			ArrayList<Appointment> publicAppointments  = appointmentDataConfig.getAppointmentsFromDate(Main.sUUID, date);
			ArrayList<Appointment> privateAppointments = appointmentDataConfig.getAppointmentsFromDate(player.getUniqueId(), date);
				ArrayList<Appointment> appointments = new ArrayList<Appointment>();
					appointments.addAll(appointmentUtils.removeNotDeletedAppointments(publicAppointments));
					appointments.addAll(appointmentUtils.removeNotDeletedAppointments(privateAppointments));
					
			
			String title = (String) properties.get(InvProperties.TITLE);
			int minSize = (int) properties.get(InvProperties.SIZE);
			int size = getSize(appointments, minSize);
			Inventory inventory = Bukkit.createInventory(null, size, title);
			
				int slot = minSize;
					HashMap<Items, HashMap<ItemProperties, Object>> itemProperties = (HashMap<Items, HashMap<ItemProperties, Object>>) properties.get(InvProperties.ITEMS);
					HashMap<ItemStack, Appointment> appointmentItems = new HashMap<ItemStack, Appointment>();
				
						for(Appointment appointment : appointments) {
							ItemStack item = createItem(itemProperties.get(Items.APPOINTMENT), dateTime, appointment);
								appointmentItems.put(item, appointment);
									inventory.setItem(slot, item);
							slot++;
						}
						items.put(Items.APPOINTMENT, appointmentItems);
				
			setItem(Items.BACK, inventory, itemProperties);
			
		
		return inventory;
	}
	
	private int getSize(ArrayList<Appointment> appointments, int minSize) {
		int size = minSize;
		
			for(int i = 0; i < appointments.size(); i = i + 9) {
				size = size + 9;
			}
			
		return size;
	}

}
