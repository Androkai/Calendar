package calendar.frontend.messages;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import calendar.backend.Main;
import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.Flags;
import calendar.backend.configs.CalendarConfig;
import calendar.backend.dateTime.DateTime;
import calendar.backend.dateTime.DateTimeUtils;


public class MessageUtils {
	
	CalendarConfig calendarConfig = Main.getCalendarConfig();
	DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();
	
	/*
	 * Constructs a list out of a given string.
	 */
	public List<String> stringToList(String string) {
		List<String> description = new ArrayList<String>();
		String[] lines = string.split("%n");
		
			for(String line : lines) {
				line = addSpaces(line);
					description.add(line);
			}
			
		return description;
	}
	
	/*
	 * Adds spaces to a given string
	 */
	public String addSpaces(String string) {
		string = string.replaceAll("_", " ");
			return string;
	}
	
	/*
	 * Method to replace placeholder.
	 */
	public String replacePlaceholder(String message, DateTime date, Appointment appointment) {
		date = new DateTime(date);
		message = new String(message);
		
			if(date != null) {
				message = replaceDateTimePlaceholder(message, date);
			}
			
			if(appointment != null) {
				message = replaceAppointmentPlaceholder(message, appointment);
			}
		
		return message;
	}
	
	@SuppressWarnings("static-access")
	public String replaceDateTimePlaceholder(String message, DateTime date){
		date = new DateTime(date);
		LocalDateTime timeSystem = date.toLocalDateTime();
		message = new String(message);
		
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(calendarConfig.getLocal());
		
		// Replaces Date unit placeholder
		message = message
				.replaceAll("%date_second%", timeSystem.format(formatter.ofPattern("ss")))
				.replaceAll("%date_minute%", timeSystem.format(formatter.ofPattern("mm")))
				.replaceAll("%date_hour%", timeSystem.format(formatter.ofPattern("HH")))
				.replaceAll("%date_hour_am_pm%", timeSystem.format(formatter.ofPattern("hh")))
				.replaceAll("%date_day%", timeSystem.format(formatter.ofPattern("dd")))
				.replaceAll("%date_week%", String.valueOf(date.getWeek()))
				.replaceAll("%date_month%", timeSystem.format(formatter.ofPattern("MM")))
				.replaceAll("%date_year%", timeSystem.format(formatter.ofPattern("yyyy")))
				.replaceAll("%date_day_name%", timeSystem.getDayOfWeek().getDisplayName(TextStyle.FULL, calendarConfig.getLocal()))
				.replaceAll("%date_month_name%", timeSystem.getMonth().getDisplayName(TextStyle.FULL, calendarConfig.getLocal()));
		
		return message;
	}
	
	@SuppressWarnings("static-access")
	public String replaceAppointmentPlaceholder(String message, Appointment appointment) {
			
				if(appointment.getCreator() == Main.sUUID) {
					message = replaceAll(message, "%appointment_status%", "public");
				}
				if(appointment.getCreator() != Main.sUUID) {
					message = replaceAll(message, "%appointment_status%", "private");
				}
		
				message = replaceAll(message, "%appointment_name%", appointment.getName());
				message = replaceAll(message, "%appointment_header%", appointment.getHeader());
				message = replaceAll(message, "%appointment_description%", appointment.getDescription().toString());
				HashMap<Flags, Boolean> flags = appointment.getFlags();
					message = replaceAll(message, "%appointment_edited%", String.valueOf(flags.get(Flags.EDITED)));
					message = replaceAll(message, "%appointment_deleted%", String.valueOf(flags.get(Flags.DELETED)));
			
				DateTime dateTime = appointment.getDateTime();
				LocalDateTime timeSystem = dateTime.toLocalDateTime();
			
					DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(calendarConfig.getLocal());
					message = message
							.replaceAll("%appointment_date_second%", timeSystem.format(formatter.ofPattern("ss")))
							.replaceAll("%appointment_date_minute%", timeSystem.format(formatter.ofPattern("mm")))
							.replaceAll("%appointment_date_hour%", timeSystem.format(formatter.ofPattern("HH")))
							.replaceAll("%appointment_date_hour_am_pm%", timeSystem.format(formatter.ofPattern("hh")))
							.replaceAll("%appointment_date_day%", timeSystem.format(formatter.ofPattern("dd")))
							.replaceAll("%appointment_date_week%", String.valueOf(dateTime.getWeek()))
							.replaceAll("%appointment_date_month%", timeSystem.format(formatter.ofPattern("MM")))
							.replaceAll("%appointment_date_year%", timeSystem.format(formatter.ofPattern("yyyy")))
							.replaceAll("%appointment_date_day_name%", DayOfWeek.of(timeSystem.getDayOfWeek().getValue()).getDisplayName(TextStyle.FULL, calendarConfig.getLocal()))
							.replaceAll("%appointment_date_month_name%", Month.of((int) dateTime.getMonth()).getDisplayName(TextStyle.FULL, calendarConfig.getLocal()));
			
		return message;
	}
	
	public String replaceAll(String string, String regex, String replacement) {
		
		if(string != null && replacement != null) {
			string = string.replaceAll(regex, replacement);
		}else{
			string = string.replaceAll(regex, "");
		}
		
			return string;
	}

}
