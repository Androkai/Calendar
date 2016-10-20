package calendar.frontend.gui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.AppointmentUtils;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.date.Date;
import calendar.backend.item.EnchantmentProperties;
import calendar.backend.item.ItemCreator;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.ItemUtils;
import calendar.backend.item.Items;
import calendar.backend.item.Prefixes;
import calendar.backend.main.main;
import calendar.frontend.configs.AppointmentConfig;
import net.minecraft.server.v1_10_R1.DataWatcher.Item;

public class AppointmentManager extends InventoryUtils {
	
	AppointmentConfig appointmentConfig = main.getAppointmentConfig();
	AppointmentDataConfig appointmentDataConfig = main.getAppointmentDataConfig();
	
	AppointmentUtils appointmentUtils = main.getAppointmentUtils();
	ItemUtils itemUtils = main.getItemUtils();
	
	Player player;
	Date date;
	LocalDateTime timeSystem;
	
	Inventory inventory;
	HashMap<Items, Object> items = new HashMap<Items, Object>();
	
	public AppointmentManager(Player player, Date date) {
		
		this.player = player;
		this.date = new Date(date);
		this.timeSystem = dateUtils.toLocalDateTime(date);
		
		this.inventory = createAppointmentManagerInventory();
		
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
	
	
	private Inventory createAppointmentManagerInventory() {
		HashMap<InventoryProperties, Object> appointmentManagerProperties = appointmentConfig.getAppointmentManagerInventoryProperties();
		
		ArrayList<Appointment> publicAppointments = appointmentDataConfig.getAppointmentsFromDate(main.sUUID, date);
		ArrayList<Appointment> privateAppointments = appointmentDataConfig.getAppointmentsFromDate(player.getUniqueId(), date);
		
		HashMap<ItemStack, Appointment> publicAppointmentItems = new HashMap<ItemStack, Appointment>();
			publicAppointments = appointmentUtils.removeDeletedAppointments(publicAppointments);
		HashMap<ItemStack, Appointment> privateAppointmentItems = new HashMap<ItemStack, Appointment>();
			privateAppointments = appointmentUtils.removeDeletedAppointments(privateAppointments);
		
		int bufferSlots = (int) appointmentManagerProperties.get(InventoryProperties.SIZE);
		int slot = 0;
		
		String title = (String) appointmentManagerProperties.get(InventoryProperties.HEADER);
		title = replacePlaceholder(title, date, null);
		int size = getSize(bufferSlots, publicAppointments, privateAppointments);
		Inventory inventory = Bukkit.createInventory(player, size, title);
		
		
		HashMap<Items, HashMap<ItemProperties, Object>> itemProperties = (HashMap<Items, HashMap<ItemProperties, Object>>) appointmentManagerProperties.get(InventoryProperties.ITEMS);
		
			slot = getPublicSlot(bufferSlots, slot);
					for(Appointment appointment : publicAppointments) {
							ItemStack appointmentItem = createItem(itemProperties.get(Items.publicAppointments), date, appointment);
								appointmentItem = addAppointmentDescription(appointmentItem, appointment.getHeader(), appointment.getDescription(), date, appointment);
									publicAppointmentItems.put(appointmentItem, new Appointment(appointment));
										inventory.setItem(slot, appointmentItem);
							
								slot++;
					}
					items.put(Items.publicAppointments, publicAppointmentItems);
		
			slot = getPrivateSlot(slot);
					for(Appointment appointment : privateAppointments) {
							ItemStack appointmentItem = createItem(itemProperties.get(Items.privateAppointments), date, appointment);
								appointmentItem = addAppointmentDescription(appointmentItem, appointment.getHeader(), appointment.getDescription(), date, appointment);
									privateAppointmentItems.put(appointmentItem, new Appointment(appointment));
										inventory.setItem(slot, appointmentItem);
							
							slot++;
					}
					items.put(Items.privateAppointments, privateAppointmentItems);
					
			slot = 8;
			
			HashMap<ItemProperties, Object> backToCalendarProperties = itemProperties.get(Items.backToCalendar);
				ItemStack backToCalendarItem = createItem(backToCalendarProperties, date, null);
				items.put(Items.backToCalendar, backToCalendarItem);
				inventory.setItem((int) backToCalendarProperties.get(ItemProperties.SLOT), backToCalendarItem);
				
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
	
	private ItemStack addAppointmentDescription(ItemStack item, String header, List<String> description, Date date, Appointment appointment) {
		String prefix;
		
		if(item != null) {
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
				if(meta.hasLore()) {
					lore = meta.getLore();
				}
			
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
				
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		
		return item;
	}

}
