package calendar.frontend.gui.appointment;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
import net.minecraft.server.v1_10_R1.DataWatcher.Item;

public class AppointmentRemove extends InventoryUtils {
	
	AppointmentConfig appointmentConfig = Main.getAppointmentConfig();
	AppointmentDataConfig appointmentDateConfig = Main.getAppointmentDataConfig();
	AppointmentUtils appointmentUtils = Main.getAppointmentUtils();
	DateTimeUtils dateUtils = Main.getDateTimeUtils();
	
	Player player;
	Appointment appointment;
	DateTime date;
	
	Inventory inventory;
	static HashMap<Items, Object> items = new HashMap<Items, Object>();
	
	public AppointmentRemove(Player player, Appointment appointment) {
		super(appointment.getDateTime(), appointment, items);
		
		this.player = player;
		this.appointment = appointment;
		this.date = new DateTime(appointment.getDateTime());
		
		this.inventory = createRemoveAppointmentInventory();
		
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
	
	
	private Inventory createRemoveAppointmentInventory() {
		HashMap<InvProperties, Object> properties = appointmentConfig.getRemoveAppointmentInventoryProperties();
		
		String header = (String) properties.get(InvProperties.TITLE);
		header = replacePlaceholder(header, date, appointment);
		int size = (int) properties.get(InvProperties.SIZE);
		Inventory inventory = Bukkit.createInventory(player, size, header);
		
		HashMap<Items, HashMap<ItemProperties, Object>> itemProperties = (HashMap<Items, HashMap<ItemProperties, Object>>) properties.get(InvProperties.ITEMS); 
		
			HashMap<ItemProperties, Object> backToCalendarProperties = itemProperties.get(Items.BACK);
				ItemStack backToCalendarItem = createItem(backToCalendarProperties, date, appointment);
				items.put(Items.BACK, backToCalendarItem);
				inventory.setItem((int) backToCalendarProperties.get(ItemProperties.SLOT), backToCalendarItem);
				
			HashMap<ItemProperties, Object> confirmProperties = itemProperties.get(Items.CONFIRM);
				ItemStack confirmItem = createItem(confirmProperties, date, appointment);
				items.put(Items.CONFIRM, confirmItem);
				inventory.setItem((int) confirmProperties.get(ItemProperties.SLOT), confirmItem);;
		
		
		return inventory;
	}
	

}
