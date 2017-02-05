package calendar.frontend.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import calendar.backend.Main;
import calendar.backend.appointments.Appointment;
import calendar.backend.configs.CalendarConfig;
import calendar.backend.dateTime.DateTime;
import calendar.backend.dateTime.DateTimeUtils;
import calendar.backend.item.EnchantmentProperties;
import calendar.backend.item.ItemCreator;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.frontend.messages.MessageUtils;

public class InventoryUtils extends MessageUtils {
	
	CalendarConfig calendarConfig = Main.getCalendarConfig();
	DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();
	
	DateTime dateTime;
	Appointment appointment;
	HashMap<Items, Object> items;
	
	public InventoryUtils(DateTime dateTime, Appointment appointment, HashMap<Items, Object> items) {
		
		this.dateTime = dateTime;
		this.appointment = appointment;
		this.items    = items;
	}
	
	public void setItem(Items itemName, Inventory inventory, HashMap<Items, HashMap<ItemProperties, Object>> properties) {
		
		HashMap<ItemProperties, Object> itemProperties = properties.get(itemName);
		ItemStack item = createItem(itemProperties, dateTime, appointment);
		items.put(itemName, item);
		inventory.setItem((int) itemProperties.get(ItemProperties.SLOT), item);
		
	}
	
	public ItemStack createItem(HashMap<ItemProperties, Object> itemProperties, DateTime date, Appointment appointment) {
		itemProperties = new HashMap<>(itemProperties);
		ItemStack item = null;
		
		if((boolean) itemProperties.get(ItemProperties.TOGGLE)) {
				String name = (String) itemProperties.get(ItemProperties.NAME);
					name = replacePlaceholder(name, date, appointment);
					
				List<String> lore = (List<String>) itemProperties.get(ItemProperties.LORE);
				if(lore != null) {
					lore = new ArrayList<>(lore);
						for(String line : lore) {
							int index = lore.indexOf(line);
								line = replacePlaceholder(line, date, appointment);
									lore.set(index, line);
						}
				}
			
				Material material = (Material) itemProperties.get(ItemProperties.MATERIAL);
				
				int amount = Integer.valueOf(replacePlaceholder((String) itemProperties.get(ItemProperties.AMOUNT), date, appointment));
				
				short id = (short) itemProperties.get(ItemProperties.ID);
		
				item = new ItemCreator(material, amount, (short) id, name, lore).getItem();
		
					ItemMeta meta = item.getItemMeta();
			
						meta.setDisplayName(name);
						meta.setLore(lore);
				
						HashMap<EnchantmentProperties, Object> enchantment = (HashMap<EnchantmentProperties, Object>) itemProperties.get(ItemProperties.ENCHANTMENT);
						if(enchantment != null) {
							meta.addEnchant(
									Enchantment.getByName((String) enchantment.get(EnchantmentProperties.TYPE)), 
									(int) enchantment.get(EnchantmentProperties.STRENGTH), 
									(boolean) enchantment.get(EnchantmentProperties.IGNOREMAX));
					
							if((boolean) enchantment.get(EnchantmentProperties.HIDE)) {
								meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
							}
						}	
				
					item.setItemMeta(meta);
			}
		
		return item;
	}
	

}
