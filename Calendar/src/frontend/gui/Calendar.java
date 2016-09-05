package frontend.gui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.avaje.ebeaninternal.server.query.LoadedPropertiesCache;

import backend.configs.CalendarConfig;
import backend.date.Date;
import backend.item.ItemCreator;
import backend.item.ItemProperties;
import backend.item.Items;
import backend.main.main;

public class Calendar {
	
	CalendarConfig calendarConfig = main.getCalendarConfig();
	
	public Inventory createCalendar(LocalDateTime localDate) {
		Date date = new Date(localDate);
		/*
		 * Gets the properties of the calendar.
		 */
		HashMap<InventoryProperties, Object> calendarProperties = calendarConfig.getCalendarProperties();
		int weeks = (int) Math.ceil((double) localDate.getMonth().maxLength() / (double) 7);
		
		/*
		 * Creates the calendar inventory.
		 */
		int size = getSize(weeks);
		String name = (String) calendarProperties.get(InventoryProperties.HEADER);
		Inventory inventory = Bukkit.createInventory(null, size, name);
		
			/*
			 * Gets the properties of the calendar items
			 */
			HashMap<Items, HashMap<ItemProperties, Object>> calendarItems = (HashMap<Items, HashMap<ItemProperties, Object>>) calendarProperties.get(InventoryProperties.ITEMS);
		
			/*
			 * Declares variables for the day and week of month to set them into the calendar.
			 */
			int dayOfMonth = 0;
			int weekOfMonth = 0;
			
			/*
			 * Declares variables for the slot of the day and week items.
			 */
			int daySlot = 0;
			int weekSlot = 7 + 1;
				
				/*
				 * Goes threw every week of the month.
				 */
				for(;weekOfMonth <= weeks; weekOfMonth++){
					date.setWeek(weekOfMonth);
					/*
					 * Creates the item of the iterator week and sets it into the calendar.
					 */
					inventory.setItem(weekSlot, createItem(calendarItems.get(Items.WEEK), date));
					
						/*
						 * Goes threw each day of the week and adds up the dayOfMonth value for each week day.
						 */
						for(int dayOfWeek = 0; dayOfWeek <= 7; dayOfWeek++, dayOfMonth++){
							date.setDay(dayOfMonth);
							/*
							 * Creates the item of the iterator dayOfWeek and sets it into the calendar.
							 */
							if(isToday(date, localDate)){
								inventory.setItem(daySlot, createItem(calendarItems.get(Items.TODAY), date));
							}else{
								inventory.setItem(daySlot, createItem(calendarItems.get(Items.DAY), date));
							}
							
								/*
								 * Checks if the day is the last day of the month, if so it stops the day cycle.
								 */
								if(dayOfMonth == localDate.getMonth().maxLength()){
									break;
								}
							
							/*
							 * Adds up the slot for the day item.
							 */
							daySlot++;
						}
						
						/*
						 * Checks if the week is the last of the month, if so it stops the week cycle.
						 */
						if(weekOfMonth == weeks){
							break;
						}
						
						/*
						 * Adds up the week and day slot for a new line of the calendar.
						 */
						weekSlot = weekSlot + 9;
						daySlot = daySlot + 2;
					
				}
		
		return inventory;
	}
	
	/*
	 * Method to calculate the needed inventory size for the calendar.
	 */
	private int getSize(int weeks){
		return weeks * 9;
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
	public ItemStack createItem(HashMap<ItemProperties, Object> itemProperties, Date date){
		
		/*
		 * Declares new variables for the item properties.
		 */
		String name = this.replacePlaceholder((String) itemProperties.get(ItemProperties.NAME), date);
		Material material = (Material) itemProperties.get(ItemProperties.MATERIAL);
		int id = (int) itemProperties.get(ItemProperties.ID);
		int amount = Integer.valueOf(this.replacePlaceholder((String) itemProperties.get(ItemProperties.AMOUNT), date));
		
		List<String> lore = null;
			if(itemProperties.get(ItemProperties.LORE) != null){
				lore = new ArrayList<String>((List<String>) itemProperties.get(ItemProperties.LORE));
					if(lore != null){
						for(String line : lore){
							lore.set(lore.indexOf(line), this.replacePlaceholder(line, date));
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
	private String replacePlaceholder(String message, Date date){
		date = new Date(date);
		message = new String(message);
		
		/*
		 * Replaces all Date number placeholders
		 */
		message = message
				.replaceAll("%second%", String.valueOf(date.getSecond()))
				.replaceAll("%minute%", String.valueOf(date.getMinute()))
				.replaceAll("%hour%", String.valueOf(date.getHour()))
				.replaceAll("%day%", String.valueOf(date.getDay()))
				.replaceAll("%week%", String.valueOf(date.getWeek()))
				.replaceAll("%month%", String.valueOf(date.getMonth()))
				.replaceAll("%year%", String.valueOf(date.getYear()));
		
		return message;
	}
	
}
