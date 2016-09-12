package calendar.frontend.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;

import calendar.backend.configs.Config;
import calendar.backend.configs.ConfigUtils;
import calendar.backend.main.main;
import net.md_5.bungee.api.ChatColor;

public class CommandConfig extends Config implements ConfigUtils {

	FileConfiguration config;
	
	public CommandConfig() {
		super(main.instance.getDataFolder(), "CommandConfig.yml");
		
		config = super.loadConfig();
	}
	
	public HashMap<CommandErrors, String> getErrors() {	
		
		HashMap<CommandErrors, String> errors = new HashMap<CommandErrors, String>();
		
		String path = "Errors.";
		
		errors.put(CommandErrors.noPermissions, main.tag + getString(path + "noPermissions"));
		errors.put(CommandErrors.notPlayer, main.tag + getString(path + "notPlayer"));
		errors.put(CommandErrors.unkownCommand, main.tag + getString(path + "unknownCommand"));
		
		return errors;
	}
	
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
