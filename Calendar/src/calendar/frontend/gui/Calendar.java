package calendar.frontend.gui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import calendar.backend.appointments.Appointment;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.date.Date;
import calendar.backend.date.DateUtils;
import calendar.backend.item.EnchantmentProperties;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.ItemUtils;
import calendar.backend.item.Items;
import calendar.backend.item.Prefixes;
import calendar.backend.main.main;
import calendar.frontend.configs.AppointmentConfig;
import calendar.frontend.configs.CalendarConfig;

public class Calendar extends InventoryUtils {
	
	CalendarConfig calendarConfig = main.getCalendarConfig();
	AppointmentConfig appointmentConfig = main.getAppointmentConfig();
	AppointmentDataConfig appointmentDataConfig = main.getAppointmentDataConfig();
	DateUtils dateUtils = main.getDateUtils();
	ItemUtils itemUtils = main.getItemUtils();
	
	Player player;
	Date date;
	LocalDateTime timeSystem;
	
	Inventory inventory;
	HashMap<Items, Object> items 	= new HashMap<Items, Object>();
	
	public Calendar(Player player, Date date){
		
		this.player = player;
		this.date = new Date(date);
		this.timeSystem = dateUtils.toLocalDateTime(date);
		
		this.inventory = createCalendarInventory(player, date, timeSystem);
		
	}
	
	/*
	 * Getters for the calendar parameters
	 */
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
	
	
	/*
	 * Method to create the calendar inventory.
	 */
	private Inventory createCalendarInventory(Player player, Date date, LocalDateTime timeSystem) {
		date = new Date(date);
		
		HashMap<InventoryProperties, Object> calendarProperties = calendarConfig.getCalendarProperties();
		
		LocalDateTime firstDayOfMonth = LocalDateTime.of((int) date.getYear(),(int) date.getMonth(), 1, 1, 1);
		int firstWeekDay = firstDayOfMonth.getDayOfWeek().getValue();
		int weeksThisMonth = (int) Math.ceil((double) ((dateUtils.getCurrentMonthMax(timeSystem) + firstWeekDay)) / (double) 7);
		int weeksThisMonthSlot = (int) Math.ceil((double) ((dateUtils.getCurrentMonthMax(timeSystem) + firstWeekDay) - 1) / (double) 7);
		
		// Creates an empty inventory with the size of the calendar
		int size = getSize(weeksThisMonthSlot, (int) calendarProperties.get(InventoryProperties.SIZE));
		String name = (replacePlaceholder((String) calendarProperties.get(InventoryProperties.HEADER), date, null));
		Inventory inventory = Bukkit.createInventory(null, size, name);
		
			@SuppressWarnings("unchecked")
			HashMap<Items, HashMap<ItemProperties, Object>> calendarItems = (HashMap<Items, HashMap<ItemProperties, Object>>) calendarProperties.get(InventoryProperties.ITEMS);
			
			HashMap<ItemStack, Date> dayItems 	= new HashMap<ItemStack, Date>();
			HashMap<ItemStack, Date> weekItems = new HashMap<ItemStack, Date>();
			
			// Variables for the dayOfMonth and weekOfMonth while creating the calendar.
			int dayOfMonth = 1;
			int weekOfMonth = 1;
			
			// Declares the first slots of the day and week item.
			int daySlot = (int) firstWeekDay - 1;
			int weekSlot = 7;
				
				//Goes threw every week of the month.
				//Counts up: weekSlot, daySlot, weekOfMonth
				for(;weekOfMonth <= weeksThisMonth; weekOfMonth++, weekSlot = weekSlot + 9, daySlot = daySlot + 2){
					date.setWeek(weekOfMonth);
					timeSystem = dateUtils.toLocalDateTime(date);
					
						// Goes threw each day of a week
						// Counts up: dayOfWeek, dayOfMonth, daySlot
						for(int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++, dayOfMonth++, daySlot++){
							date.setDay(dayOfMonth);
							timeSystem = dateUtils.toLocalDateTime(date);
							
							// Creates the day or today item, adds the appointment properties and sets it into the inventory
							if(dateUtils.isToday(date, LocalDateTime.now())){
								ItemStack todayItem = createItem(calendarItems.get(Items.TODAY), date, null);
								todayItem = addAllAppointmentPropertiesToItem(player.getUniqueId(), date, timeSystem, todayItem, calendarItems);
								
								inventory.setItem(daySlot, todayItem);
								dayItems.put(todayItem, new Date(date));
								items.put(Items.TODAY, todayItem);
							}else{
								ItemStack dayItem = createItem(calendarItems.get(Items.DAY), date, null);
								dayItem = addAllAppointmentPropertiesToItem(player.getUniqueId(), date, timeSystem, dayItem, calendarItems);
								
								inventory.setItem(daySlot, dayItem);
								dayItems.put(dayItem, new Date(date));
							}
								
								// Checks if it's the end of the week.
								if(isEndOfWeek(date, daySlot)) {
									daySlot++;
									dayOfMonth++;
									break;
								}
								
								// Checks if it's the end of the month.
								if(isEndOfMonth(date, timeSystem)) {
									weekOfMonth = weeksThisMonth + 1;
									dayOfWeek = 7 + 1;
									break;
								}
							
						}
						
						
						// Creates the week item and sets it into the week slot.
						ItemStack weekItem = createItem(calendarItems.get(Items.WEEK), date, null);
						inventory.setItem(weekSlot, weekItem);
						weekItems.put(weekItem, new Date(date));
						
				}
				
				
				ItemStack nextMonthItem = createItem(calendarItems.get(Items.nextMonth), date, null);
				inventory.setItem((int) calendarItems.get(Items.nextMonth).get(ItemProperties.SLOT), nextMonthItem);
				
				ItemStack previousMonthItem = createItem(calendarItems.get(Items.previousMonth), date, null);
				inventory.setItem((int) calendarItems.get(Items.previousMonth).get(ItemProperties.SLOT), previousMonthItem);
				
				// Add the items of the calendar to the items HashMap
				items.put(Items.DAY, dayItems);
				items.put(Items.WEEK, weekItems);
				items.put(Items.nextMonth, nextMonthItem);
				items.put(Items.previousMonth, previousMonthItem);
		
		return inventory;
	}
	
	/*
	 * Method to calculate the needed inventory size for the calendar.
	 */
	protected int getSize(int weeks, int minSize){
		int size = weeks * 9;
			if(size < minSize) {
				size = minSize;
			}
		return size;
	}
	
	/*
	 * Method to check if the given day if the last day of the month.
	 */
	protected boolean isEndOfMonth(Date date, LocalDateTime timeSystem) {
		
		if(date.getDay() == dateUtils.getCurrentMonthMax(timeSystem)) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Method to check if the given day is the last day of the week.
	 */
	protected boolean isEndOfWeek(Date date, int daySlot) {
		
		if(date.getWeek() == 1){
			if(daySlot >= (7 - 1)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/*
	 * Method to add an appointment to an item.
	 */
	protected ItemStack addAppointmentPropertiesToItem(Appointment appointment, HashMap<ItemProperties, Object> appointmentProperties, ItemStack item, Date date, LocalDateTime timeSystem) {
		date = new Date(date);
		
		if(item != null) {
		ItemMeta meta = item.getItemMeta();
		
			if(!appointment.isDeleted()) {
				String name = (String) appointmentProperties.get(ItemProperties.NAME);
					if(name != null) {
						name = replacePlaceholder(name, date, null);
						item = itemUtils.changeName(item, name);
					}
					
				List<String> lore = meta.getLore();
					if(appointmentProperties.get(ItemProperties.LORE)!= null){
						lore = new ArrayList<String>((List<String>) appointmentProperties.get(ItemProperties.LORE));
							item = itemUtils.changeLore(item, lore);
					}
				
				Material material = (Material) appointmentProperties.get(ItemProperties.MATERIAL);
					item = itemUtils.changeMaterial(item, material);
				 
				short id = (short) appointmentProperties.get(ItemProperties.ID);
					item = itemUtils.changeId(item, id);
					
				String amount = (String) appointmentProperties.get(ItemProperties.AMOUNT);
					if(amount != null) {
						amount = replacePlaceholder(amount, date, null);
						item = itemUtils.changeAmount(item, Integer.valueOf(amount));
					}
					
				String prefix;
					prefix = appointmentConfig.getPrefixes().get(Prefixes.HEADER);
					String header = appointment.getHeader();
						if(prefix != null) {
							header = conact(prefix, header);
						}
								header = replacePlaceholder(header, date, null);
								lore.add(header);			
			
					prefix = appointmentConfig.getPrefixes().get(Prefixes.DESCRIPTION);
					List<String> description = appointment.getDescription();
						for(String line : description) {
							if(prefix != null) {
							line = conact(prefix, line);
								}
									line = replacePlaceholder(line, date, null);
									lore.add(line);
								}
				
						HashMap<EnchantmentProperties, Object> enchantment = (HashMap<EnchantmentProperties, Object>) appointmentProperties.get(ItemProperties.ENCHANTMENT);
						if(enchantment != null) {
							meta.addEnchant(
								Enchantment.getByName((String) enchantment.get(EnchantmentProperties.TYPE)), 
								(int) enchantment.get(EnchantmentProperties.STRENGTH), 
								(boolean) enchantment.get(EnchantmentProperties.IGNOREMAX));
							meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						}
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
		}
			
		return item;
	}
	
	/*
	 * Method to add all appointment properties to an item.
	 */
	protected ItemStack addAllAppointmentPropertiesToItem(UUID creator, Date date, LocalDateTime timeSystem, ItemStack item, HashMap<Items, HashMap<ItemProperties, Object>> calendarItems) {
		
		for(Appointment appointment : appointmentDataConfig.getAppointmentsFromDate(main.sUUID, date)) {
			item = addAppointmentPropertiesToItem(appointment, calendarItems.get(Items.APPOINTMENT), item, date, timeSystem);
		}
		
		for(Appointment appointment : appointmentDataConfig.getAppointmentsFromDate(creator, date)) {
			item = addAppointmentPropertiesToItem(appointment, calendarItems.get(Items.APPOINTMENT), item, date, timeSystem);
		}
		return item;
	}
	
	// Adds to Strings together
	private String conact(String arg1, String arg2) {
		StringBuilder stringBuild = new StringBuilder(arg1);
			stringBuild.append(arg2);
				return stringBuild.toString();
	}
	
}
