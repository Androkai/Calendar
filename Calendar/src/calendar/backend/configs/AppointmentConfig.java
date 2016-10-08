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
	
	/*
	 * Method to check if the given appointment already exists.
	 */
	public boolean isAppointment(Appointment appointment) {
		String path = createAppointmentPath(appointment.getCreator(), appointment.getDate(), appointment.getName());
			if(config.isConfigurationSection(path)) {
				return true;
			}
		
		return false;
	}
	
	/*
	 * Method to restore a deleted appointment.
	 */
	public boolean restoreAppointment(Appointment appointment) {
		UUID creator  = appointment.getCreator();
		Date date	  = appointment.getDate();
		String name   = appointment.getName();
		
		HashMap<Flags, Boolean> flags = appointment.getFlags();
		
			if(isAppointment(appointment) && appointment.isDeleted()){
				String path = createAppointmentPath(creator, date, name);
					
					flags.put(Flags.DELETED, false);
					setFlags(path, flags);
					appointment.setFlags(flags);
					
						super.saveConfig();
							return true;
			}
		
		return false;
	}
	
	/*
	 * Method to delete an existing appointment
	 */
	public boolean removeAppointment(Appointment appointment) {
		UUID creator  = appointment.getCreator();
		Date date	  = appointment.getDate();
		String name   = appointment.getName();
		
		HashMap<Flags, Boolean> flags = appointment.getFlags();
		
			if(isAppointment(appointment) && !appointment.isDeleted()){
				String path = createAppointmentPath(creator, date, name);
					
					flags.put(Flags.DELETED, true);
					setFlags(path, flags);
					appointment.setFlags(flags);
					
						super.saveConfig();
							return true;
			}
		
		return false;
	}
	
	/*
	 * Method to create a new appointment.
	 */
	public boolean addAppointment(Appointment appointment) {
		UUID creator  = appointment.getCreator();
		Date date	  = appointment.getDate();
		String name   = appointment.getName();
		
		String header = appointment.getHeader();
		List<String> description = appointment.getDescription();
		HashMap<Flags, Boolean> flags = appointment.getFlags();
		
			if(isAppointment(appointment) == false || getAppointment(creator, date, name).isDeleted()) {
				String path = createAppointmentPath(creator, date, name);
				
					setHeader(path, header);
					setDescription(path, description);
					setFlags(path, flags);
					saveConfig();
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
		String path = createAppointmentListPath(creator, date);
		
		if(config.contains(path)) {
			Set<String> names = config.getConfigurationSection(path).getKeys(false);
			
			for(String name : names) {
			
				Appointment appointment = getAppointment(creator, date, name);
				appointments.add(appointment);
			
			}
		}
		
		return appointments;
		
	}
	
	/*
	 * Method to get an Appointment out of the config.
	 */
	public Appointment getAppointment(UUID creator, Date date, String name) {
		String path = createAppointmentPath(creator, date, name);
		
		Appointment appointment;
		
			String header 					= getHeader(path);
			List<String> description 		= getDescription(path);
			HashMap<Flags, Boolean> flags 	= getFlags(path);
		
		appointment = new Appointment(creator, date, name, header, description, flags);
		
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
	private String createAppointmentPath(UUID creator, Date date, String name) {
		
		String appointmentPath;
		
		String datePath	   = dateToDatePath(date);
		
		appointmentPath =
				creator
				 + "." +
				datePath
				 + "." +
				name
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
			if(config.getString(path) != null) {
				return ChatColor.translateAlternateColorCodes('&', config.getString(path));
			}
		return null;
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
