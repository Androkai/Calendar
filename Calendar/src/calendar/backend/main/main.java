package calendar.backend.main;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import calendar.backend.appointments.AppointmentUtils;
import calendar.backend.configs.AppointmentDataConfig;
import calendar.backend.date.DateUtils;
import calendar.backend.item.ItemUtils;
import calendar.backend.placeholder.Placeholder;
import calendar.backend.storage.Storage;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.configs.AppointmentConfig;
import calendar.frontend.configs.CalendarConfig;
import calendar.frontend.configs.CommandConfig;
import calendar.frontend.listener.command.CommandCaller;
import calendar.frontend.listener.inventory.InventoryCaller;

public class main extends JavaPlugin {
	
	public static String tag = "[§6§lCalendar§r] ";
	
	public static main instance;
	public static UUID sUUID = UUID.fromString("bb5fb548-4dff-4eb1-b9f6-f618be8ed6e9");
	public static HashMap<Player, Storage> storages = new HashMap<Player, Storage>();
	
	private static CalendarConfig calendarConfig;
	private static AppointmentConfig appointmentConfig;
	private static AppointmentDataConfig appointmentDataConfig;
	private static CommandConfig commandConfig;
	
	private static DateUtils dateUtils;
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
		registerPlaceholder();
		registerEvents();
		registerCommands();
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
		commandConfig = new CommandConfig();
		
		dateUtils = new DateUtils();
		storageUtils = new StorageUtils();
		itemUtils = new ItemUtils();
		appointmentUtils = new AppointmentUtils();
		
	}
	
	/*
	 * Method to register all events used by the plugin.
	 */
	private void registerEvents() {
		
		Bukkit.getPluginManager().registerEvents(new InventoryCaller(), this);
	}
	
	/*
	 * Method to register all commands.
	 */
	private void registerCommands() {
		
		CommandCaller commandCaller = new CommandCaller();
		
		getCommand("calendar").setExecutor(commandCaller);
		getCommand("appointment").setExecutor(commandCaller);
		
	}
	
	/*
	 * Method to register the placeholder.
	 */
	public void registerPlaceholder() {
		
		if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new Placeholder(this).hook();
		}
		
	}
	
	/*
	 * Getters for the Objects
	 */
	public static CalendarConfig getCalendarConfig() {
		return calendarConfig;
	}
	
	public static AppointmentConfig getAppointmentConfig() {
		return appointmentConfig;
	}
	
	public static AppointmentDataConfig getAppointmentDataConfig() {
		return appointmentDataConfig;
	}
	
	public static CommandConfig getCommandConfig() {
		return commandConfig;
	}
	
	public static DateUtils getDateUtils() {
		return dateUtils;
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
