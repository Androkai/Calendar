package backend.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import backend.main.main;

public class Config {
	
	protected Config() {
	}
	
	/*
	 * Loads and, if the YML File not exists calls createConfig(), the YML
	 * configuration File. Declares the YML configuration variable 'config'
	 */
	protected FileConfiguration loadConfig(File parentFile, String configName) {
		
		/*
		 * Creates a new FileConfiguration Object 'config' with the createConfig() method.
		 * 'Config' will be our configuration Object to work with.
		 */
		FileConfiguration config = createConfig(parentFile, configName);
		
		/*
		 * Sets some options of the FileConfiguration 'config'.
		 */
		config.options().copyHeader(true);
		config.options().copyDefaults(true);
		
		saveConfig(config, new File(parentFile, configName));
		
		return config;
	}
	
	
	/*
	 * Saves the configuration of the current 'config' variable.
	 */
	protected void saveConfig(FileConfiguration config, File configFile) {
		
		/*
		 * Tries to save the configuration in the given file 'configFile'.
		 */
		try {
			config.save(configFile);
		} catch (IOException exeption) {
			exeption.printStackTrace();
		}
	}
	
	
	/*
	 * Saves the configuration and loads it again.
	 */
	protected FileConfiguration reloadConfig(FileConfiguration config, File configFile, String configName) {
		
		/*
		 * Declares a new configuration to the existing FileConfiguartion 'config'.
		 */
		config = loadConfig(configFile, configName);
		
		return config;
	}

	
	/*
	 * Deletes the current configuration File.
	 */
	protected void delteConfig(File parentFile, String configName) {
		
		/*
		 * Declares a File to the given path 'configPath'.
		 */
		File configFile = new File(parentFile, configName);
		
		/*
		 * Checks if the File exists.
		 * If so, deletes the File.
		 */
		if(configFile.exists()){
			configFile.delete();
		}
	}

	
	/*
	 * Creates a new configuration File, if no File with the same path exists.
	 */
	protected FileConfiguration createConfig(File parentFile, String configName) {
		
		/*
		 * Creates the new File.
		 */
		File configFile = new File(parentFile, configName);
		
		/*
		 * Checks if the File exists.
		 * If not, creates the File and all parent Folders.
		 */
		if(!configFile.exists()){
			configFile.getParentFile().mkdirs();
			main.instance.saveResource(configName, false);
		}
		
		/*
		 * Creates the new YamlConfiguration 'config', which will be our new Config.
		 */
		FileConfiguration config = new YamlConfiguration();
		
			/*
			 * Tries to load the configuration into the new 'config' Object
			 */
			try {
				
				config.load(configFile);
				main.instance.getLogger().info("Succsessfully loaded " + configName + "!");
				
			} catch (IOException | InvalidConfigurationException e) {
				main.instance.getLogger().warning("Error while loading " + configName + "!");
				e.printStackTrace();
			}
			
		/*
		 * Returns the loaded configuration Object 'config'.
		 */
		return config;
	}
	
}
