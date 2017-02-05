package calendar.frontend.gui.appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import calendar.backend.Main;
import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.AppointmentUtils;
import calendar.backend.configs.AppointmentConfig;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.dateTime.DateTime;
import calendar.backend.dateTime.DateTimeUtils;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.ItemUtils;
import calendar.backend.item.Items;
import calendar.backend.item.Prefixes;
import calendar.frontend.gui.InvProperties;
import calendar.frontend.gui.InventoryUtils;

public class AppointmentManager extends InventoryUtils {
	
	AppointmentConfig appointmentConfig = Main.getAppointmentConfig();
	AppointmentDataConfig appointmentDataConfig = Main.getAppointmentDataConfig();
	
	DateTimeUtils dateUtils = Main.getDateTimeUtils();
	AppointmentUtils appointmentUtils = Main.getAppointmentUtils();
	ItemUtils itemUtils = Main.getItemUtils();
	
	Player player;
	DateTime dateTime;
	
	Inventory inventory;
	static HashMap<Items, Object> items = new HashMap<Items, Object>();
	
	public AppointmentManager(Player player, DateTime dateTime) {
		super(dateTime, null, items);
		
		this.player = player;
		this.dateTime = new DateTime(dateTime);
		
		this.inventory = createInventory();
		
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
	
	
	private Inventory createInventory() {
		HashMap<InvProperties, Object> appointmentManagerProperties = appointmentConfig.getAppointmentManagerInventoryProperties();
		
		ArrayList<Appointment> publicAppointments = appointmentDataConfig.getAppointmentsFromDate(Main.sUUID, dateTime.getDate());
		ArrayList<Appointment> privateAppointments = appointmentDataConfig.getAppointmentsFromDate(player.getUniqueId(), dateTime.getDate());
		
		HashMap<ItemStack, Appointment> publicAppointmentItems = new HashMap<ItemStack, Appointment>();
			publicAppointments = appointmentUtils.removeDeletedAppointments(publicAppointments);
		HashMap<ItemStack, Appointment> privateAppointmentItems = new HashMap<ItemStack, Appointment>();
			privateAppointments = appointmentUtils.removeDeletedAppointments(privateAppointments);
		
		int bufferSlots = (int) appointmentManagerProperties.get(InvProperties.SIZE);
		int slot = 0;
		
		String title = (String) appointmentManagerProperties.get(InvProperties.TITLE);
		title = replacePlaceholder(title, dateTime, null);
		int size = getSize(bufferSlots, publicAppointments, privateAppointments);
		Inventory inventory = Bukkit.createInventory(player, size, title);
		
		
		HashMap<Items, HashMap<ItemProperties, Object>> itemProperties = (HashMap<Items, HashMap<ItemProperties, Object>>) appointmentManagerProperties.get(InvProperties.ITEMS);
		
			slot = getPublicSlot(bufferSlots, slot);
					for(Appointment appointment : publicAppointments) {
							ItemStack appointmentItem = createItem(itemProperties.get(Items.publicAppointments), dateTime, appointment);
								appointmentItem = addAppointmentDescription(appointmentItem, appointment.getHeader(), appointment.getDescription(), dateTime, appointment);
									publicAppointmentItems.put(appointmentItem, new Appointment(appointment));
										inventory.setItem(slot, appointmentItem);
							
								slot++;
					}
					items.put(Items.publicAppointments, publicAppointmentItems);
		
			slot = getPrivateSlot(slot);
					for(Appointment appointment : privateAppointments) {
							ItemStack appointmentItem = createItem(itemProperties.get(Items.privateAppointments), dateTime, appointment);
								appointmentItem = addAppointmentDescription(appointmentItem, appointment.getHeader(), appointment.getDescription(), dateTime, appointment);
									privateAppointmentItems.put(appointmentItem, new Appointment(appointment));
										inventory.setItem(slot, appointmentItem);
							
							slot++;
					}
					items.put(Items.privateAppointments, privateAppointmentItems);
					
			slot = 8;
			
			setItem(Items.addAppointment, inventory, itemProperties);
			setItem(Items.restoreAppointment, inventory, itemProperties);
			setItem(Items.BACK, inventory, itemProperties);
				
		return inventory;
	}
	
	private int getSize(int bufferSlots, ArrayList<Appointment> publicAppointments, ArrayList<Appointment> privateAppointments) {
		int size = bufferSlots;
		
			for(int appointments = 0; appointments < publicAppointments.size(); appointments = appointments + 9) {
				size = size + 9;
			}
			for(int appointments = 0; appointments < privateAppointments.size(); appointments = appointments + 9) {
				size = size + 9;
			}
		
		return size;
	}
	
	private int getPublicSlot(int bufferSlots, int slot) {
		return slot + bufferSlots;
	}
	private int getPrivateSlot(int slot) {
		for(; (slot % 9) > 0; slot++) {}
			return slot;
	}
	
	private ItemStack addAppointmentDescription(ItemStack item, String header, List<String> description, DateTime date, Appointment appointment) {
		String prefix;
		
		if(item != null) {
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			
					if(header != null) {
						prefix = appointmentConfig.getPrefixes().get(Prefixes.HEADER);
						header = new String(header);
					
							header = prefix + header;
							header = replacePlaceholder(header, date, appointment);
							lore.add(header);
					}
				
					if(description != null) {
						prefix = appointmentConfig.getPrefixes().get(Prefixes.DESCRIPTION);
						description = new ArrayList<>(description);
					
							for(String line : description) {
								int index = lore.indexOf(line);		
									line = prefix + line;
									line = replacePlaceholder(line, date, appointment);
									lore.add(line);
							}
				}
					
				if(meta.hasLore()) {
					lore.addAll(meta.getLore());
				}
				
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		
		return item;
	}

}
