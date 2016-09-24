package calendar.frontend.gui;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import calendar.backend.date.Date;
import calendar.backend.date.DateUtils;
import calendar.backend.item.ItemCreator;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.backend.main.main;
import calendar.frontend.configs.CalendarConfig;

public class Calendar {
	
	CalendarConfig calendarConfig = main.getCalendarConfig();
	DateUtils dateUtils = main.getDateUtils();
	
	
	Date date;
	LocalDateTime timeSystem;
	
	Inventory inventory;
	HashMap<Items, Object> items 	= new HashMap<Items, Object>();
	
	public Calendar(LocalDateTime timeSystem){
		
		date = new Date(timeSystem);
		inventory = this.createInventory(date, timeSystem);
		
	}
	
	/*
	 * Getters for the calendar parameters
	 */
	public Date getCalendarDate() {
		return date;
	}
	
	public LocalDateTime getCalendarTimeSystem() {
		return timeSystem;
	}
	
	public Inventory getCalendarInventory() {
		return inventory;
	}
	
	public HashMap<Items, Object> getCalendarItems() {
		return items;
	}
	
	
	private Inventory createInventory(Date date, LocalDateTime timeSystem) {
		/*
		 * Gets the properties of the calendar.
		 */
		HashMap<InventoryProperties, Object> calendarProperties = calendarConfig.getCalendarProperties();
		
		Date firstDayOfMonth = new Date(date);
		firstDayOfMonth.setDay(1);
		firstDayOfMonth.setWeek(1);
		int firstWeekDay = (int) dateUtils.getDayOfWeek(firstDayOfMonth);
		int weeksThisMonth = (int) Math.ceil((double) (timeSystem.getMonth().maxLength() + firstWeekDay) / (double) 7);
		
		/*
		 * Creates the calendar inventory.
		 */
		int size = getSize(weeksThisMonth);
		String name = (this.replacePlaceholder((String) calendarProperties.get(InventoryProperties.HEADER), new Date(timeSystem), timeSystem));
		Inventory inventory = Bukkit.createInventory(null, size, name);
		
			/*
			 * Gets the properties of the calendar items
			 */
			@SuppressWarnings("unchecked")
			HashMap<Items, HashMap<ItemProperties, Object>> calendarItems = (HashMap<Items, HashMap<ItemProperties, Object>>) calendarProperties.get(InventoryProperties.ITEMS);
			
			ArrayList<ItemStack> dayItems 	= new ArrayList<ItemStack>();
			ArrayList<ItemStack> weekItems = new ArrayList<ItemStack>();
			
			/*
			 * Declares variables for the day and week of month to set them into the calendar.
			 */
			int dayOfMonth = 1;
			int weekOfMonth = 1;
			
			/*
			 * Declares variables for the slot of the day and week items.
			 */
			int daySlot = (int) firstWeekDay - 1;
			
			int weekSlot = 7;
				
				/*
				 * Goes threw every week of the month.
				 */
				for(;weekOfMonth <= weeksThisMonth; weekOfMonth++, weekSlot = weekSlot + 9, daySlot = daySlot + 2){
					date.setWeek(weekOfMonth);
					
						/*
						 * Goes threw each day of the week and adds up the dayOfMonth value for each week day.
						 */
						for(int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++, dayOfMonth++, daySlot++){
							date.setDay(dayOfMonth);
							System.out.println(dayOfMonth);
							
							/*
							 * Creates the item of the iterator dayOfWeek and sets it into the calendar.
							 */
							if(isToday(date, timeSystem)){
								ItemStack todayItem = createItem(calendarItems.get(Items.TODAY), date, timeSystem);
								inventory.setItem(daySlot, todayItem);
								dayItems.add(todayItem);
								items.put(Items.TODAY, todayItem);
							}else{
								ItemStack dayItem = createItem(calendarItems.get(Items.DAY), date, timeSystem);
								inventory.setItem(daySlot, dayItem);
								dayItems.add(dayItem);
							}
							
								if(isEndOfWeek(date, daySlot)) {
									daySlot++;
									dayOfMonth++;
									break;
								}
								
								if(isEndOfMonth(date, timeSystem)) {
									weekOfMonth = weeksThisMonth + 1;
									dayOfWeek = 7 + 1;
									break;
								}
							
						}
						
						
						/*
						 * Creates the item of the iterator week and sets it into the calendar.
						 */
						ItemStack weekItem = createItem(calendarItems.get(Items.WEEK), date, timeSystem);
						inventory.setItem(weekSlot, weekItem);
						weekItems.add(weekItem);
						
				}
				
				// Add the items of the calendar to the items HashMap
				items.put(Items.DAY, dayItems);
				items.put(Items.WEEK, weekItems);
		
		return inventory;
	}
	
	/*
	 * Method to calculate the needed inventory size for the calendar.
	 */
	private int getSize(int weeks){
		return weeks * 9;
	}
	
	/*
	 * Method to check if the given day if the last day of the month.
	 */
	private boolean isEndOfMonth(Date date, LocalDateTime timeSystem) {
		
		if(date.getDay() == timeSystem.getMonth().maxLength()) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Method to check if the given day is the last day of the week.
	 */
	private boolean isEndOfWeek(Date date, int daySlot) {
		
		if(date.getWeek() == 1){
			if(daySlot >= (7 - 1)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isToday(Date date, LocalDateTime localDate){
		
		if(date.getYear() == localDate.getYear()){
			if(date.getMonth() == localDate.getMonthValue()){
				if(date.getDay() == localDate.getDayOfMonth()){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/*
	 * Method to create an item with a given ItemProperties HashMap.
	 */
	@SuppressWarnings("unchecked")
	public ItemStack createItem(HashMap<ItemProperties, Object> itemProperties, Date date, LocalDateTime timeSystem){
		
		/*
		 * Declares new variables for the item properties.
		 */
		String name = this.replacePlaceholder((String) itemProperties.get(ItemProperties.NAME), date, timeSystem);
		Material material = (Material) itemProperties.get(ItemProperties.MATERIAL);
		int id = (int) itemProperties.get(ItemProperties.ID);
		int amount = Integer.valueOf(this.replacePlaceholder((String) itemProperties.get(ItemProperties.AMOUNT), date, timeSystem));
		
		List<String> lore = null;
			if(itemProperties.get(ItemProperties.LORE) != null){
				lore = new ArrayList<String>((List<String>) itemProperties.get(ItemProperties.LORE));
					if(lore != null){
						for(String line : lore){
							lore.set(lore.indexOf(line), this.replacePlaceholder(line, date, timeSystem));
						}
					}
		}
			
		/*
		 * Checks if the item is toggled, if so it returns the item, if not it return null.
		 */
		if((boolean) itemProperties.get(ItemProperties.TOGGLE)){
			return new ItemCreator(material, amount,(short) id, name, lore).getItem();
		}else{
			return null;
		}
	}
	
	/*
	 * Method to replace date placeholder.
	 */
	private String replacePlaceholder(String message, Date date, LocalDateTime timeSystem){
		date = new Date(date);
		message = new String(message);
		
		/*
		 * Replaces all Date number placeholder
		 */
		message = message
				.replaceAll("%second%", String.valueOf(date.getSecond()))
				.replaceAll("%minute%", String.valueOf(date.getMinute()))
				.replaceAll("%hour%", String.valueOf(date.getHour()))
				.replaceAll("%day%", String.valueOf(date.getDay()))
				.replaceAll("%week%", String.valueOf(date.getWeek()))
				.replaceAll("%month%", String.valueOf(date.getMonth()))
				.replaceAll("%year%", String.valueOf(date.getYear()));
	
	date.setDay(date.getDay() - 1);
	long dayOfWeek = dateUtils.getDayOfWeek(date) + 1;
	
	message = message
			.replaceAll("%dayName%", DayOfWeek.of((int) dayOfWeek).getDisplayName(TextStyle.FULL, Locale.getDefault()))
			.replaceAll("%monthName%", Month.of((int) date.getMonth()).getDisplayName(TextStyle.FULL, Locale.getDefault()));
		
		return message;
	}
	
}
