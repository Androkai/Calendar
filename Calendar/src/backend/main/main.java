package backend.main;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import backend.configs.CalendarConfig;
import eventListener.CalendarCommand;
import frontend.gui.Storage;
import frontend.listener.inventory.InventoryCaller;

public class main extends JavaPlugin {
	
	public static main instance;
	public static HashMap<Player, Storage> storages = new HashMap<Player, Storage>();
	
	private static CalendarConfig calendarConfig;
	
	public void onEnable() {
		instance = this;
		
		registerObjects();
		registerEvents();
		registerCommands();
	}
	
	public void onDisable() {
		
	}
	
	private void registerObjects() {
		
		calendarConfig = new CalendarConfig();
		
	}
	
	private void registerEvents() {
		
		Bukkit.getPluginManager().registerEvents(new InventoryCaller(), this);
		
	}
	
	private void registerCommands() {
		
		getCommand("calendar").setExecutor(new CalendarCommand());
		
	}
	
	public static CalendarConfig getCalendarConfig() {
		return calendarConfig;
	}

}
