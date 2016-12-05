package calendar.backend.main;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import calendar.backend.appointments.AppointmentUtils;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.date.DateTime;
import calendar.backend.date.DateTimeUtils;
import calendar.backend.item.ItemUtils;
import calendar.backend.placeholder.Placeholder;
import calendar.backend.storage.Storage;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.configs.AppointmentConfig;
import calendar.frontend.configs.CalendarConfig;
import calendar.frontend.configs.MessageConfig;
import calendar.frontend.listener.chat.ChatEventCaller;
import calendar.frontend.listener.command.CommandEventCaller;
import calendar.frontend.listener.inventory.InventoryEventCaller;
import calendar.frontend.notifications.Reminder;

public class Main extends JavaPlugin {
	
	public static String prefix;
	
	public static Main instance;
	public static UUID sUUID = UUID.fromString("bb5fb548-4dff-4eb1-b9f6-f618be8ed6e9");
	public static HashMap<Player, Storage> storages = new HashMap<Player, Storage>();
	
	private static Reminder reminder;
	
	private static CalendarConfig calendarConfig;
	private static AppointmentConfig appointmentConfig;
	private static AppointmentDataConfig appointmentDataConfig;
	private static MessageConfig messageConfig;
	
	private static DateTimeUtils dateTimeUtils;
	private static StorageUtils storageUtils;
	private static ItemUtils itemUtils;
	private static AppointmentUtils appointmentUtils;
	
	/*
	 * (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	public void onEnable() {
		instance = this;
		
		registerObjects();
		hook();
		registerEvents();
		registerCommands();
		
		Main.prefix = messageConfig.getPluginPrefix();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	public void onDisable() {
	}
	
	/*
	 * Method to register all Objects needed while runtime.
	 */
	private void registerObjects() {
		
		calendarConfig = new CalendarConfig();
		appointmentConfig = new AppointmentConfig();
		appointmentDataConfig = new AppointmentDataConfig();
		messageConfig = new MessageConfig();
		
		dateTimeUtils = new DateTimeUtils();
		storageUtils = new StorageUtils();
		itemUtils = new ItemUtils();
		appointmentUtils = new AppointmentUtils();
		
		reminder = new Reminder();
		
	}
	
	/*
	 * Method to register all events used by the plugin.
	 */
	private void registerEvents() {
		
		Bukkit.getPluginManager().registerEvents(new InventoryEventCaller(), this);
		Bukkit.getPluginManager().registerEvents(new ChatEventCaller(), this);
		
	}
	
	/*
	 * Method to register all commands.
	 */
	private void registerCommands() {
		
		CommandEventCaller commandCaller = new CommandEventCaller();
		
		getCommand("calendar").setExecutor(commandCaller);
		getCommand("appointment").setExecutor(commandCaller);
		
	}
	
	/*
	 * Method to register the placeholder.
	 */
	private void hook() {
		
		if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			Bukkit.getLogger().info("Loading Placeholders...");
				new Placeholder(this).hook();
		}
		
	}
	
	/*
	 * Getters for the Objects
	 */
	public static Reminder getReminder() {
		return reminder;
	}
	
	public static CalendarConfig getCalendarConfig() {
		return calendarConfig;
	}
	
	public static AppointmentConfig getAppointmentConfig() {
		return appointmentConfig;
	}
	
	public static AppointmentDataConfig getAppointmentDataConfig() {
		return appointmentDataConfig;
	}
	
	public static MessageConfig getMessageConfig() {
		return messageConfig;
	}
	
	public static DateTimeUtils getDateTimeUtils() {
		return dateTimeUtils;
	}
	
	public static StorageUtils getStorageUtils() {
		return storageUtils;
	}
	
	public static ItemUtils getItemUtils() {
		return itemUtils;
	}
	
	public static AppointmentUtils getAppointmentUtils() {
		return appointmentUtils;
	}

}
