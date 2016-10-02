package calendar.backend.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.Flags;
import calendar.backend.date.Date;
import calendar.backend.main.main;
import net.md_5.bungee.api.ChatColor;

public class AppointmentConfig extends Config implements ConfigUtils {
	
	FileConfiguration config;
	
	public AppointmentConfig() {
		super(main.instance.getDataFolder(), "Data.yml");
		
		config = super.loadConfig();
	}
	
	/*
	 * Gets all appointments of a player, from a specific date.
	 */
	public ArrayList<Appointment> getAppointmentsFromDate(Player creator, Date date) {
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		String appointmentListPath = createAppointmentListPath(creator, date);
		
		if(config.contains(appointmentListPath)) {
			Set<String> numbers = config.getConfigurationSection(appointmentListPath).getKeys(false);
			
			for(String number : numbers) {
			
				Appointment appointment = getAppointment(creator, date, Integer.valueOf(number));
				appointments.add(appointment);
			
			}
		}
		
		return appointments;
		
	}
	
	/*
	 * Method to get an Appointment out of the config.
	 */
	public Appointment getAppointment(Player creator, Date date, int number) {
		String appointmentPath = createAppointmentPath(creator, date, number);
		
		Appointment appointment;
		
		String header 					= getHeader(appointmentPath);
		List<String> description 		= getDescription(appointmentPath);
		HashMap<Flags, Boolean> flags 	= getFlags(appointmentPath);
		
		appointment = new Appointment(date, creator, header, description, flags);
		
		return appointment;
	}
	
	/*
	 * Method to get the head of an given appointment path.
	 */
	private String getHeader(String appointmentPath) {
		String header = getString(appointmentPath + "header");
		
		return header;
	}
	
	
	/*
	 * Method to get the description of an given appointment path.
	 */
	private List<String> getDescription(String appointmentPath) {
		String descriptionPath = appointmentPath + "description";
		
		return getListString(descriptionPath);
	}
	
	/*
	 * Method to get the flags of a given appointment path.
	 */
	private HashMap<Flags, Boolean> getFlags(String appointmentPath) {
		String flagPath = appointmentPath + "flags.";
		
		HashMap<Flags, Boolean> flags = new HashMap<Flags, Boolean>();
		
			boolean isPublic;
			boolean isEdited;
			boolean isDeleted;
			
			isPublic = getBoolean(flagPath 	+ "public");
			isEdited = getBoolean(flagPath 	+ "edited");
			isDeleted = getBoolean(flagPath + "deleted");
		
			flags.put(Flags.PUBLIC,  isPublic);
			flags.put(Flags.EDITED,  isEdited);
			flags.put(Flags.DELETED, isDeleted);
		
		return flags;
	}
	
	/*
	 * Method to create an appointment path.
	 */
	private String createAppointmentPath(Player creator, Date date, int number) {
		
		String appointmentPath;
		
		String creatorUUID = String.valueOf(creator.getUniqueId());
		String datePath	   = dateToDatePath(date);
		
		appointmentPath =
				creatorUUID
				 + "." +
				datePath
				 + "." +
				number
				 + ".";
		
		return appointmentPath;
	}
	
	/*
	 * Method to create an appointment list path.
	 */
	private String createAppointmentListPath(Player creator, Date date) {
		
		String appointmentPath;
		
		String creatorUUID = String.valueOf(creator.getUniqueId());
		String datePath	   = dateToDatePath(date);
		
		appointmentPath =
				creatorUUID
				 + "." +
				datePath
				+  ".";
		
		return appointmentPath;
		
	}
	
	/*
	 * Method to convert a date into a configuration path.
	 */
	private String dateToDatePath(Date date) {
		
		String datePath = 
					  String.valueOf(date.getMonth())
					 + "_" +
					  String.valueOf(date.getDay())
					 + "_" +
					  String.valueOf(date.getYear());
		
		return datePath;
	}
	
	/*
	 * Method to convert a date path into a date Object.
	 */
	private Date datePathToDate(String path) {
		
		String[] dateUnits = path.split(".");
		
		Date date = main.getDateUtils().getNullDate();
		
		date.setMonth(Long.valueOf(dateUnits[0]));
		date.setDay	 (Long.valueOf(dateUnits[1]));
		date.setMonth(Long.valueOf(dateUnits[2]));
		
		return date;
	}

	/*
	 * @see backend.configs.ConfigUtils#getString(java.lang.String)
	 */
	@Override
	public String getString(String path) {
		return ChatColor.translateAlternateColorCodes('&', config.getString(path));
	}
	
	/*
	 * @see backend.configs.ConfigUtils#getInteger(java.lang.String)
	 */
	@Override
	public Integer getInteger(String path) {
		return config.getInt(path);
	}
	
	/*
	 * @see backend.configs.ConfigUtils#getLong(java.lang.String)
	 */
	@Override
	public Long getLong(String path) {
		return Long.valueOf(config.getString(path));
	}

	/*
	 * @see backend.configs.ConfigUtils#getBoolean(java.lang.String)
	 */
	@Override
	public Boolean getBoolean(String path) {
		return config.getBoolean(path);
	}

	/*
	 * @see backend.configs.ConfigUtils#getListString(java.lang.String)
	 */
	@Override
	public List<String> getListString(String path) {
		
		if (config.getList(path) != null) {
			
			// Copies the list out of the config.
			@SuppressWarnings("unchecked")
			List<String> configList = (List<String>) config.getList(path);
			List<String> list = new ArrayList<String>(configList);
			
			for(int index = 0; index < list.size(); index++){
				String line = " §2▌ §r" + list.get(index);
				
				if(list.get(index) != null) {
					list.set(index, ChatColor.translateAlternateColorCodes('&', line));
				}else{
					list.set(index, line);
				}
			}
			
			return list;
		}
		
		return null;
	}

	/*
	 * @see backend.configs.ConfigUtils#getArrayListString(java.lang.String)
	 */
	@Override
	public ArrayList<String> getArrayListString(String path) {
		
		List<String> list = getListString(path);
		ArrayList<String> arrayList = new ArrayList<String>();
		
		for(int index = 0; index < list.size(); index++){
			arrayList.add(list.get(index));
		}
		
		return arrayList;
	}

}
