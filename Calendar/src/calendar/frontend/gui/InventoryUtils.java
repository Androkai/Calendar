package calendar.frontend.gui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.Flags;
import calendar.backend.date.Date;
import calendar.backend.date.DateUtils;
import calendar.backend.item.EnchantmentProperties;
import calendar.backend.item.ItemCreator;
import calendar.backend.item.ItemProperties;
import calendar.backend.main.main;
import calendar.frontend.configs.CalendarConfig;
import net.minecraft.server.v1_10_R1.Enchantments;

public class InventoryUtils {
	
	CalendarConfig calendarConfig = main.getCalendarConfig();
	DateUtils dateUtils = main.getDateUtils();
	
	public ItemStack createItem(HashMap<ItemProperties, Object> itemProperties, Date date, Appointment appointment) {
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
	
	/*
	 * Method to replace placeholder.
	 */
	public String replacePlaceholder(String message, Date date, Appointment appointment) {
		date = new Date(date);
		message = new String(message);
		
			if(date != null) {
				message = replaceDatePlaceholder(message, date);
			}
			
			if(appointment != null) {
				message = replaceAppointmentPlaceholder(message, appointment);
			}
		
		return message;
	}
	
	public String replaceDatePlaceholder(String message, Date date){
		date = new Date(date);
		LocalDateTime timeSystem = dateUtils.toLocalDateTime(date);
		message = new String(message);
		
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(calendarConfig.getLocal());
		// Replaces Date unit placeholder
		message = message
				.replaceAll("%date_second%", timeSystem.format(formatter.ofPattern("ss")))
				.replaceAll("%date_minute%", timeSystem.format(formatter.ofPattern("mm")))
				.replaceAll("%date_hour%", timeSystem.format(formatter.ofPattern("hh")))
				.replaceAll("%date_day%", timeSystem.format(formatter.ofPattern("dd")))
				.replaceAll("%date_week%", String.valueOf(date.getWeek()))
				.replaceAll("%date_month%", timeSystem.format(formatter.ofPattern("MM")))
				.replaceAll("%date_year%", timeSystem.format(formatter.ofPattern("yyyy")))
				.replaceAll("%date_day_name%", timeSystem.getDayOfWeek().getDisplayName(TextStyle.FULL, calendarConfig.getLocal()))
				.replaceAll("%date_month_name%", timeSystem.getMonth().getDisplayName(TextStyle.FULL, calendarConfig.getLocal()));
		
		return message;
	}
	
	public String replaceAppointmentPlaceholder(String message, Appointment appointment) {
		
			message = message
					.replaceAll("%appointment_name%", appointment.getName());
			
			message = message
					.replaceAll("%appointment_header%", appointment.getHeader());
		
			HashMap<Flags, Boolean> flags = appointment.getFlags();
			
			message = message
					.replaceAll("%appointment_edited%", String.valueOf(flags.get(Flags.EDITED)))
					.replaceAll("%appointment_deleted%", String.valueOf(flags.get(Flags.DELETED)));
			
			Date date = appointment.getDate();
			LocalDateTime timeSystem = dateUtils.toLocalDateTime(date);
			
			DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(calendarConfig.getLocal());
			message = message
					.replaceAll("%appointment_date_second%", timeSystem.format(formatter.ofPattern("ss")))
					.replaceAll("%appointment_date_minute%", timeSystem.format(formatter.ofPattern("mm")))
					.replaceAll("%appointment_date_hour%", timeSystem.format(formatter.ofPattern("hh")))
					.replaceAll("%appointment_date_day%", timeSystem.format(formatter.ofPattern("dd")))
					.replaceAll("%appointment_date_week%", String.valueOf(date.getWeek()))
					.replaceAll("%appointment_date_month%", timeSystem.format(formatter.ofPattern("MM")))
					.replaceAll("%appointment_date_year%", timeSystem.format(formatter.ofPattern("yyyy")))
					.replaceAll("%appointment_date_day_name%", DayOfWeek.of(timeSystem.getDayOfWeek().getValue()).getDisplayName(TextStyle.FULL, calendarConfig.getLocal()))
					.replaceAll("%appointment_date_month_name%", Month.of((int) date.getMonth()).getDisplayName(TextStyle.FULL, calendarConfig.getLocal()));
			
		return message;
	}
	

}
