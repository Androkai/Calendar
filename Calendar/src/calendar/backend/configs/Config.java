package calendar.backend.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import calendar.backend.main.main;

public class Config {
	
	File file;
	FileConfiguration config;
	
	protected Config(File parentFile, String configName) {
		file = new File(parentFile, configName);
	}
	
	/*
	 * Loads and, if the YML File not exists calls createConfig(), the YML
	 * configuration File. Declares the YML configuration variable 'config'
	 */
	protected FileConfiguration loadConfig() {
		
		/*
		 * Creates a new FileConfiguration Object 'config' with the createConfig() method.
		 * 'Config' will be our configuration Object to work with.
		 */
		config = createConfig();
		
		/*
		 * Sets some options of the FileConfiguration 'config'.
		 */
		config.options().copyHeader(true);
		config.options().copyDefaults(true);
		
		saveConfig();
		
		return config;
	}
	
	
	/*
	 * Saves the configuration of the current 'config' variable.
	 */
	protected void saveConfig() {
		
		/*
		 * Tries to save the configuration in the given file 'configFile'.
		 */
		try {
			config.save(file);
		} catch (IOException exeption) {
			exeption.printStackTrace();
		}
	}
	
	
	/*
	 * Saves the configuration and loads it again.
	 */
	protected FileConfiguration reloadConfig() {
		
		/*
		 * Declares a new configuration to the existing FileConfiguartion 'config'.
		 */
		config = loadConfig();
		
		return config;
	}

	
	/*
	 * Deletes the current configuration File.
	 */
	protected void delteConfig() {
		
		/*
		 * Checks if the File exists.
		 * If so, deletes the File.
		 */
		if(file.exists()){
			file.delete();
		}
	}

	
	/*
	 * Creates a new configuration File, if no File with the same path exists.
	 */
	protected FileConfiguration createConfig() {
		
		/*
		 * Checks if the File exists.
		 * If not, creates the File and all parent Folders.
		 */
		if(!file.exists()){
			file.getParentFile().mkdirs();
			main.instance.saveResource(file.getName(), false);
		}
		
		/*
		 * Creates the new YamlConfiguration 'config', which will be our new Config.
		 */
		FileConfiguration config = new YamlConfiguration();
		
			/*
			 * Tries to load the configuration into the new 'config' Object
			 */
			try {
				
				config.load(file);
				main.instance.getLogger().info("Successfully loaded " + file.getName() + "!");
				
			} catch (IOException | InvalidConfigurationException e) {
				main.instance.getLogger().warning("Error while loading " + file.getName() + "!");
				e.printStackTrace();
			}
			
		/*
		 * Returns the loaded configuration Object 'config'.
		 */
		return config;
	}
	
}