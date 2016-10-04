package calendar.backend.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
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
	
	public boolean createAppointment(UUID creator, Date date, int number, String header, List<String> description, HashMap<Flags, Boolean> flags) {
		String path = createAppointmentPath(creator, date, number);
		
		if(!config.contains(path)) {
			
				setHeader(path, header);
				setDescription(path, description);
				setFlags(path, flags);
				
				super.saveConfig();
		}else{
			return false;
		}
		
		return true;
	}
	
	/*
	 * Gets all appointments of a player, from a specific date.
	 */
	public ArrayList<Appointment> getAppointmentsFromDate(UUID creator, Date date) {
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
	public Appointment getAppointment(UUID creator, Date date, int number) {
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
	private String getHeader(String path) {
		path = path + "header";
		
		String header = getString(path);
		return header;
	}
	
	private void setHeader(String path, String header) {
		path = path + "header";
		
		setString(path, header);
	}
	
	
	/*
	 * Method to get the description of an given appointment path.
	 */
	private List<String> getDescription(String path) {
		path = path + "description";
		
		return getListString(path);
	}
	
	/*
	 * Method to set the description of an given appointment path.
	 */
	private void setDescription(String path, List<String> description) {
		path = path + "description";
		
		setListString(path, description);
	}
	
	/*
	 * Method to get the flags of a given appointment path.
	 */
	private HashMap<Flags, Boolean> getFlags(String path) {
		path = path + "flags.";
		
		HashMap<Flags, Boolean> flags = new HashMap<Flags, Boolean>();
		
			boolean isEdited;
			boolean isDeleted;
			
			isEdited = getBoolean(path 	+ "edited");
			isDeleted = getBoolean(path + "deleted");
		
			flags.put(Flags.EDITED,  isEdited);
			flags.put(Flags.DELETED, isDeleted);
		
		return flags;
	}
	
	private void setFlags(String path, HashMap<Flags, Boolean> flags) {
		path = path + "flags.";
		
			boolean isEdited 	= flags.get(Flags.EDITED);
			boolean isDeleted 	= flags.get(Flags.DELETED);
			
			setBoolean(path + "edited", isEdited);
			setBoolean(path + "deleted", isDeleted);
	}
	
	/*
	 * Method to create an appointment path.
	 */
	private String createAppointmentPath(UUID creator, Date date, int number) {
		
		String appointmentPath;
		
		String datePath	   = dateToDatePath(date);
		
		appointmentPath =
				creator
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
	private String createAppointmentListPath(UUID creator, Date date) {
		String path;
		String datePath	   = dateToDatePath(date);
		
		path = creator + "." + datePath +  ".";
		
		return path;
		
	}
	
	/*
	 * Method to convert a date into a configuration path.
	 */
	private String dateToDatePath(Date date) {
		
		String path = 
					  String.valueOf(date.getMonth())
					 + "_" +
					  String.valueOf(date.getDay())
					 + "_" +
					  String.valueOf(date.getYear());
		
		return path;
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
	 * (non-Javadoc)
	 * @see calendar.backend.configs.Config#reloadConfig()
	 */
	public FileConfiguration reloadConfig() {
		return config = super.reloadConfig();
	}

	/*
	 * @see backend.configs.ConfigUtils#getString(java.lang.String)
	 */
	@Override
	public String getString(String path) {
		return ChatColor.translateAlternateColorCodes('&', config.getString(path));
	}
	
	public void setString(String path, String value) {
		createSection(path);
	
		value = value.replaceAll("§", "&");
		config.set(path, value);
	}
	/*
	 * @see backend.configs.ConfigUtils#getInteger(java.lang.String)
	 */
	@Override
	public Integer getInteger(String path) {
		return config.getInt(path);
	}
	
	public void setInteger(String path, int value) {
		config.set(path, value);
	}
	
	/*
	 * @see backend.configs.ConfigUtils#getLong(java.lang.String)
	 */
	@Override
	public Long getLong(String path) {
		return Long.valueOf(config.getString(path));
	}
	
	public void setLong(String path, long value) {
		createSection(path);
		
		config.set(path, value);
	}

	/*
	 * @see backend.configs.ConfigUtils#getBoolean(java.lang.String)
	 */
	@Override
	public Boolean getBoolean(String path) {
		return config.getBoolean(path);
	}
	
	public void setBoolean(String path, boolean value) {
		createSection(path);
		
		config.set(path, value);
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
				String line = list.get(index);
				
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
	
	public void setListString(String path, List<String> list) {
		createSection(path);
		
		for(String line : list) {
			int index = list.indexOf(line);
			
			list.set(index, line.replaceAll("§", "&"));
		}
		
		config.set(path, list);
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
	
	/*
	 * Method to create an new config section.
	 */
	private void createSection(String path) {
		config.createSection(path);
		super.saveConfig();
	}

}
