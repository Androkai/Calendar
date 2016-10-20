package calendar.frontend.listener.inventory.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import calendar.backend.api.events.CalendarClickEvent;
import calendar.backend.api.events.DayClickEvent;
import calendar.backend.date.Date;
import calendar.backend.date.DateUtils;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.backend.main.main;
import calendar.backend.storage.Storage;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.configs.CalendarConfig;
import calendar.frontend.gui.AppointmentManager;
import calendar.frontend.gui.Calendar;

public class CalendarClickListener {
	
	StorageUtils storageUtils = main.getStorageUtils();
	DateUtils dateUtils = main.getDateUtils();
	CalendarConfig calendarConfig = main.getCalendarConfig();
	
	public CalendarClickListener(InventoryClickEvent event){
		Player player 			= (Player) event.getWhoClicked();
		ClickType click 		= event.getClick();
		InventoryAction action 	= event.getAction();
		ItemStack item 			= event.getCurrentItem();
		
		Storage storage 		= main.storages.get(player);
				Calendar calendar 				= storage.getCalendar();
				HashMap<Items, Object> items 	= calendar.getItems();
				
				
				CalendarClickEvent calendarClickEvent = new CalendarClickEvent(player, calendar, click, action, item);
				Bukkit.getPluginManager().callEvent(calendarClickEvent);
				
					if(!calendarClickEvent.isCancelled()) {
						
						HashMap<ItemStack, Date> dayItems = (HashMap<ItemStack, Date>) calendar.getItems().get(Items.DAY);
						if(dayItems.containsKey(item)) {
							if(event.isLeftClick()) {
								Date date 					= dayItems.get(item);
								LocalDateTime timeSystem 	= calendar.getTimeSystem();

								player.openInventory(createAppointmentManager(player, date));
							}
							
							if(event.isRightClick()) {
								Date date 					= dayItems.get(items);
								LocalDateTime timeSystem 	= calendar.getTimeSystem();
								
								
							}
						}
						
						if(item.isSimilar((ItemStack) items.get(Items.previousMonth))) {
							LocalDateTime timeSystem = dateUtils.removeMonth(calendar.getTimeSystem());
							Date date = new Date(timeSystem);
							
							player.openInventory(createCalendar(player, date));
							
						}
						
						if(item.isSimilar((ItemStack) items.get(Items.nextMonth))) {
							LocalDateTime timeSystem = dateUtils.addMonth(calendar.getTimeSystem());
							Date date = new Date(timeSystem);
							
							player.openInventory(createCalendar(player, date));
							
						}
						
					}
	}
	
	
	private Inventory createCalendar(Player player, Date date) {
		
		// Creates a new calendar instance and saves it with storageCalendar()
		Calendar calendar = new Calendar(player, date);
		storageUtils.storageCalendar(player, calendar);

		
		return calendar.getInventory();
	}
	
	private Inventory createAppointmentManager(Player player, Date date) {
		AppointmentManager appointmentManager = new AppointmentManager(player, date);
		storageUtils.storageAppointmentManager(player, appointmentManager);
		
		return appointmentManager.getInventory();
	}

}
