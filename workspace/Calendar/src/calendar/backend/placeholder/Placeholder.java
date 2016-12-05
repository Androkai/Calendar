package calendar.backend.placeholder;

import java.text.Normalizer.Form;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import calendar.backend.date.DateTime;
import calendar.backend.date.DateTimeUtils;
import calendar.backend.main.Main;
import calendar.frontend.configs.CalendarConfig;
import me.clip.placeholderapi.external.EZPlaceholderHook;

public class Placeholder extends EZPlaceholderHook {
	
	DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();
	CalendarConfig calendarConfig = Main.getCalendarConfig();
	
	Plugin plugin;
    
    public Placeholder(Plugin plugin) {
        super(plugin, "Calendar");
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
    	if (player != null) {
    			Locale locale = calendarConfig.getLocal();
    			LocalDateTime timeSystem = LocalDateTime.now();
    			DateTime date = new DateTime(timeSystem);
    			DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(locale);
    			
        		if(identifier.equals("date_second")) {
        			return timeSystem.format(formatter.ofPattern("ss"));
        		}
        		
        		if(identifier.equals("date_minute")) {
        			return timeSystem.format(formatter.ofPattern("mm"));
        		}
        		
        		if(identifier.equals("date_hour")) {
        			return timeSystem.format(formatter.ofPattern("hh"));
        		}
        		
        		if(identifier.equals("date_day")) {
        			return timeSystem.format(formatter.ofPattern("dd"));
        		}
        		
        		
        		if(identifier.equals("date_dayOfWeek")) {
        				return timeSystem.format(formatter.ofPattern("E"));
        		}
        		if(identifier.equals("date_day_name")) {;
        					return timeSystem.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
        		}
        		
        		if(identifier.equals("date_week")) {
        			return String.valueOf(date.getWeek());
        		}
        		
        		if(identifier.equals("date_month")) {
        			return timeSystem.format(formatter.ofPattern("MM"));
        		}
        		if(identifier.equals("date_month_name")) {
        					return timeSystem.getMonth().getDisplayName(TextStyle.FULL, locale);
        		}
        		
        		if(identifier.equals("date_year")) {
        			return timeSystem.format(formatter.ofPattern("yyyy"));
        		}
        		
        		
    	}
        return null;
    }

}
