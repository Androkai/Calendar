package calendar.frontend.configs;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.LocaleUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

import calendar.backend.configs.Config;
import calendar.backend.item.EnchantmentProperties;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.backend.main.main;
import calendar.frontend.gui.InventoryProperties;
import net.md_5.bungee.api.ChatColor;

public class CalendarConfig extends Config {
	
	String defaultPath;
	String path;
	
	public CalendarConfig(){
		super(main.instance.getDataFolder(), "CalendarConfig.yml");
		
		config = super.loadConfig();
	}
	
	/*
	 * Method to get the properties of the calendar out of the config.
	 */
	public HashMap<InventoryProperties, Object> getCalendarProperties() {
		HashMap<InventoryProperties, Object> properties = new HashMap<InventoryProperties, Object>();
		
		String title;
		int size;
		HashMap<Items, HashMap<ItemProperties, Object>> items = new HashMap<Items, HashMap<ItemProperties, Object>>();
		
		/*
		 * Gets the properties of the calendar out of the configuration file
		 */
		
		properties.put(InventoryProperties.HOLDER, null);
		
		//Calendar
		path = "title";
		title = getFormatString(path);
		properties.put(InventoryProperties.HEADER, title);
		
		path = "size";
		size = getInteger(path);
		properties.put(InventoryProperties.SIZE, size);
		
			//Items
			defaultPath = "items.";
			
				//Today
				path = defaultPath + "today.";
				items.put(Items.TODAY, getItemProperties(path));
				
				//Day
				path = defaultPath + "day.";
				items.put(Items.DAY, getItemProperties(path));
				
				//Week
				path = defaultPath + "week.";
				items.put(Items.WEEK, getItemProperties(path));
				
				//Appointment
				path = defaultPath + "appointment.";
				items.put(Items.APPOINTMENT, getItemProperties(path));
				
				//Next month
				path = defaultPath + "nextMonth.";
				items.put(Items.nextMonth, getItemProperties(path));
				
				//Previous month
				path = defaultPath + "previousMonth.";
				items.put(Items.previousMonth, getItemProperties(path));
				
			properties.put(InventoryProperties.ITEMS, items);	
		
		return properties;
	}
	
	public TimeZone getTimeZone() {
		return TimeZone.getTimeZone(getString("timeZone"));
	}
	
	public Locale getLocal() {
		return LocaleUtils.toLocale(getString("locale"));
	}


}
