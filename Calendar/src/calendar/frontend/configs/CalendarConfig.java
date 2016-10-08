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
import calendar.backend.configs.ConfigUtils;
import calendar.backend.item.EnchantmentProperties;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.backend.item.LoreProperties;
import calendar.backend.main.main;
import calendar.frontend.gui.InventoryProperties;
import net.md_5.bungee.api.ChatColor;

public class CalendarConfig extends Config implements ConfigUtils {
	
	FileConfiguration config;
	
	
	public CalendarConfig(){
		super(main.instance.getDataFolder(), "CalendarConfig.yml");
		
		config = super.loadConfig();
	}
	
	/*
	 * Method to get the properties of the calendar out of the config.
	 */
	public HashMap<InventoryProperties, Object> getCalendarProperties() {
	
		HashMap<InventoryProperties, Object> calendar = new HashMap<InventoryProperties, Object>();
		
		String title;
		int size;
		
		HashMap<Items, HashMap<ItemProperties, Object>> items = new HashMap<Items, HashMap<ItemProperties, Object>>();
		
		String defaultPath;
		String path;
		
		/*
		 * Gets the properties of the calendar out of the configuration file
		 */
		
		//Calendar
		path = "title";
		title = getString(path);
		
		path = "size";
		size = getInteger(path);
		
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
				items.put(Items.APPOINTMENT, getAppointmentProperties(path));
		
		/*
		 * Puts the getted properties into the calendar properties HashMap.
		 */
		calendar.put(InventoryProperties.HOLDER, null);
		calendar.put(InventoryProperties.HEADER, title);
		calendar.put(InventoryProperties.SIZE, size);
		calendar.put(InventoryProperties.ITEMS, items);	
		
		return calendar;
	}
	
	/*
	 * Method the get the item properties, of an given path, out of the config file.
	 */
	private HashMap<ItemProperties, Object> getItemProperties(String path){
		HashMap<ItemProperties, Object> item = new HashMap<ItemProperties, Object>();
		
		/*
		 * Puts all item properties, out of the configuration file, into the 'itemProperties' HashMap.
		 */
		item.put(ItemProperties.NAME, getString(path + "name"));
		item.put(ItemProperties.LORE, getListString(path + "lore"));
		
		item.put(ItemProperties.MATERIAL, Material.valueOf(config.getString(path + "material")));
		item.put(ItemProperties.ID, getInteger(path + "id"));
		item.put(ItemProperties.AMOUNT, config.getString(path + "amount"));
		
		item.put(ItemProperties.ENCHANTMENT, getEnchantment(path + "enchantment."));
		
		/*
		 * Returns the HashMap 'itemProperties'
		 */
		return item;
	}
	
	private HashMap<ItemProperties, Object> getAppointmentProperties(String path) {
		HashMap<ItemProperties, Object> item = new HashMap<ItemProperties, Object>();
			
			item.put(ItemProperties.NAME, getString(path + "name"));
			item.put(ItemProperties.ID, getInteger(path + "id"));
			item.put(ItemProperties.AMOUNT, config.getString(path + "amount"));
			
			item.put(ItemProperties.LORE, getAppointmentLore(path + "lore."));
			item.put(ItemProperties.ENCHANTMENT, getEnchantment(path + "enchantment."));
			
		return item;
	}
	
	public TimeZone getTimeZone() {
		return TimeZone.getTimeZone(config.getString("timeZone"));
	}
	
	public Locale getLocal() {
		return LocaleUtils.toLocale(config.getString("locale"));
	}
	
	/*
	 * (non-Javadoc)
	 * @see calendar.backend.configs.Config#reloadConfig()
	 */
	public FileConfiguration reloadConfig() {
		return config = super.reloadConfig();
	}

	/*
	 * @see backend.configs.ConfigUtils#getString(java.lang.String)
	 */
	@Override
	public String getString(String path) {
		String string = config.getString(path);
			if(string != null) {
				return ChatColor.translateAlternateColorCodes('&', config.getString(path));
			}else{
				return null;
			}
	}
	
	/*
	 * @see backend.configs.ConfigUtils#getInteger(java.lang.String)
	 */
	@Override
	public Integer getInteger(String path) {
		return config.getInt(path);
	}
	
	/*
	 * @see backend.configs.ConfigUtils#getLong(java.lang.String)
	 */
	@Override
	public Long getLong(String path) {
		return Long.valueOf(config.getString(path));
	}

	/*
	 * @see backend.configs.ConfigUtils#getBoolean(java.lang.String)
	 */
	@Override
	public Boolean getBoolean(String path) {
		return config.getBoolean(path);
	}

	/*
	 * @see backend.configs.ConfigUtils#getListString(java.lang.String)
	 */
	@Override
	public List<String> getListString(String path) {
		
		if (config.getList(path) != null) {
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) config.getList(path);
		
			for(int index = 0; index < list.size(); index++){
				list.set(index, ChatColor.translateAlternateColorCodes('&', list.get(index)));
			}
			
			return (List<String>) list;
		}
		
		return null;
	}

	/*
	 * @see backend.configs.ConfigUtils#getArrayListString(java.lang.String)
	 */
	@Override
	public ArrayList<String> getArrayListString(String path) {
		
		List<String> list = getListString(path);
		ArrayList<String> arrayList = new ArrayList<String>();
		
		for(int index = 0; index < list.size(); index++){
			arrayList.add(list.get(index));
		}
		
		return arrayList;
	}
	
	/*
	 * Method to get the enchantment properties of an item.
	 */
	public HashMap<EnchantmentProperties, Object> getEnchantment(String path) {
		HashMap<EnchantmentProperties, Object> enchantment = new HashMap<EnchantmentProperties, Object>();
		
			if(config.contains(path)) {
				enchantment.put(EnchantmentProperties.TOGGLE, getBoolean(path + "toggle"));
				enchantment.put(EnchantmentProperties.TYPE, config.getString(path + "type"));
				enchantment.put(EnchantmentProperties.STRENGTH, getInteger(path + "strength"));
				enchantment.put(EnchantmentProperties.IGNOREMAX, getBoolean(path + "ignoreMax"));
				
					return enchantment;
			}
			
			return null;
	}
	
	/*
	 * Method to get the name properties of an item.
	 */
	private HashMap<LoreProperties, Object> getAppointmentLore(String path) {
		HashMap<LoreProperties, Object> lore = new HashMap<LoreProperties, Object>();
		
		if(config.contains(path)) {
			lore.put(LoreProperties.lore, getListString(path + "lore"));
			lore.put(LoreProperties.headerPrefix, getString(path + "prefix.header"));
			lore.put(LoreProperties.descriptionPrefix, getString(path + "prefix.description"));
			
			return lore;
		}
			
		return null;
	}

}
