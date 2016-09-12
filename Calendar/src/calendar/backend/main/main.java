package calendar.backend.main;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import calendar.backend.date.DateUtils;
import calendar.frontend.configs.CalendarConfig;
import calendar.frontend.configs.CommandConfig;
import calendar.frontend.gui.Storage;
import calendar.frontend.gui.StorageUtils;
import calendar.frontend.listener.command.CommandCaller;
import calendar.frontend.listener.inventory.InventoryCaller;

public class main extends JavaPlugin {
	
	public static String tag = "[§6§lCalendar§r]";
	
	public static main instance;
	public static HashMap<Player, Storage> storages = new HashMap<Player, Storage>();
	
	private static CalendarConfig calendarConfig;
	private static CommandConfig commandConfig;
	
	private static DateUtils dateUtils;
	private static StorageUtils storageUtils;
	
	/*
	 * (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	public void onEnable() {
		instance = this;
		
		registerObjects();
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
		commandConfig = new CommandConfig();
		
		dateUtils = new DateUtils();
		storageUtils = new StorageUtils();
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
		
		
		getCommand("calendar").setExecutor(new CommandCaller());
		
	}
	
	/*
	 * Getters for the Objects
	 */
	public static CalendarConfig getCalendarConfig() {
		return calendarConfig;
	}
	
	public static DateUtils getDateUtils() {
		return dateUtils;
	}
	
	public static StorageUtils getStorageUtils() {
		return storageUtils;
	}

}
