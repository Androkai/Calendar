package calendar.backend.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import calendar.backend.date.Date;
import calendar.backend.main.main;
import net.md_5.bungee.api.ChatColor;

public class AppointmentConfig extends Config implements ConfigUtils {
	
	FileConfiguration config;
	
	public AppointmentConfig() {
		super(new File(main.instance.getDataFolder(), "appointments"), "Data.yml");
		
		config = super.loadConfig();
	}
	
	
	private String dateToPath(Date date) {
		
		String path = String.valueOf(date.getMonth())
					 + "." +
					  String.valueOf(date.getDay())
					 + "." +
					  String.valueOf(date.getYear());
		
		return path;
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
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) config.getList(path);
		
			for(int index = 0; index < list.size(); index++){
				list.set(index, ChatColor.translateAlternateColorCodes('&', list.get(index)));
			}
			
			return (List<String>) list;
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
