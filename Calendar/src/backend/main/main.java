package backend.main;

import java.sql.Date;
import java.util.Calendar;

import org.bukkit.plugin.java.JavaPlugin;

import backend.configs.CalendarConfig;
import eventListener.CalendarCommand;

public class main extends JavaPlugin {
	
	public static main instance;
	
	private static CalendarConfig calendarConfig;
	
	public void onEnable() {
		instance = this;
		
		registerObjects();
		registerCommands();
	}
	
	public void onDisable() {
		
	}
	
	private void registerObjects() {
		
		calendarConfig = new CalendarConfig();
		
	}
	
	private void registerCommands() {
		
		getCommand("calendar").setExecutor(new CalendarCommand());
		
	}
	
	public static CalendarConfig getCalendarConfig() {
		return calendarConfig;
	}

}
