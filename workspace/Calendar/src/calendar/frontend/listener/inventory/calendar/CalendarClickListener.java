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
import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.Flags;
import calendar.backend.date.DateTime;
import calendar.backend.date.DateTimeUtils;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.backend.main.Main;
import calendar.backend.storage.Storage;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.configs.CalendarConfig;
import calendar.frontend.gui.appointment.AppointmentAdd;
import calendar.frontend.gui.appointment.AppointmentManager;
import calendar.frontend.gui.appointment.AppointmentTrash;
import calendar.frontend.gui.calendar.Calendar;

public class CalendarClickListener {
	
	StorageUtils storageUtils = Main.getStorageUtils();
	DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();
	CalendarConfig calendarConfig = Main.getCalendarConfig();
	
	public CalendarClickListener(InventoryClickEvent event){
		Player player 			= (Player) event.getWhoClicked();
		ClickType click 		= event.getClick();
		InventoryAction action 	= event.getAction();
		ItemStack item 			= event.getCurrentItem();
		
		Storage storage 		= Main.storages.get(player);
				Calendar calendar 				= storage.getCalendar();
				HashMap<Items, Object> items 	= calendar.getItems();
				
				
				CalendarClickEvent calendarClickEvent = new CalendarClickEvent(player, calendar, click, action, item);
				Bukkit.getPluginManager().callEvent(calendarClickEvent);
				
					if(!calendarClickEvent.isCancelled()) {
						
						HashMap<ItemStack, DateTime> dayItems = (HashMap<ItemStack, DateTime>) calendar.getItems().get(Items.DAY);
						if(dayItems.containsKey(item)) {
							DateTime date 					= dayItems.get(item);
									
									player.openInventory(createAppointmentManager(player, date));
						}
						
						if(item.isSimilar((ItemStack) items.get(Items.previousMonth))) {
							LocalDateTime timeSystem = dateTimeUtils.removeMonth(calendar.getTimeSystem());
							DateTime date = new DateTime(timeSystem);
							
								player.openInventory(createCalendar(player, date));
							
						}
						
						if(item.isSimilar((ItemStack) items.get(Items.nextMonth))) {
							LocalDateTime timeSystem = dateTimeUtils.addMonth(calendar.getTimeSystem());
							DateTime date = new DateTime(timeSystem);
							
								player.openInventory(createCalendar(player, date));
							
						}
						
						if(item.isSimilar((ItemStack) items.get(Items.EXIT))) {
							player.closeInventory();
						}
						
					}
	}
	
	
	private Inventory createCalendar(Player player, DateTime date) {
		
		// Creates a new calendar instance and saves it with storageCalendar()
		Calendar calendar = new Calendar(player, date);
		storageUtils.storageCalendar(player, calendar);

		
		return calendar.getInventory();
	}
	
	private Inventory createAppointmentManager(Player player, DateTime date) {
		AppointmentManager appointmentManager = new AppointmentManager(player, date);
		storageUtils.storageAppointmentManager(player, appointmentManager);
		
		return appointmentManager.getInventory();
	}

}
