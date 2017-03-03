package com.github.tonedahonda.calendar.backend.configs;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.item.EnchantmentProperties;
import com.github.tonedahonda.calendar.backend.item.ItemProperties;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Config {

    File file;
    protected FileConfiguration config;

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

        // Sets some options of the FileConfiguration 'config'.
        config.options().copyHeader(true);
        config.options().copyDefaults(true);

        saveConfig();

        return config;
    }


    // Saves the configuration of the current 'config' variable.
    public void saveConfig() {
		
		/*
		 * Tries to save the configuration in the given file 'configFile'.
		 */
        try {
            config.save(file);
        } catch (IOException exeption) {
            exeption.printStackTrace();
        }
    }

    // Method to get the size of the config file in bytes.
    public long getByteSize() {
        return file.length();
    }


    // Saves the configuration and loads it again.
    public FileConfiguration reloadConfig() {
		
		/*
		 * Declares a new configuration to the existing FileConfiguartion 'config'.
		 */
        config = loadConfig();

        return config;
    }


    // Deletes the current configuration File.
    public void deleteConfig() {
		
		/*
		 * Checks if the File exists.
		 * If so, deletes the File.
		 */
        if (file.exists()) {
            file.delete();
        }
    }


    // Creates a new configuration File, if no File with the same path exists.
    protected FileConfiguration createConfig() {
		
		/*
		 * Checks if the File exists.
		 * If not, creates the File and all parent Folders.
		 */
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            Main.instance.saveResource(file.getName(), false);
        }

        // Creates the new YamlConfiguration 'config', which will be our new Config.
        FileConfiguration config = new YamlConfiguration();

        // Tries to load the configuration into the new 'config' Object
        try {

            config.load(file);
            Main.instance.getLogger().info("Successfully loaded " + file.getName() + "!");

        } catch (IOException | InvalidConfigurationException e) {
            Main.instance.getLogger().warning("Error while loading " + file.getName() + "!");
            e.printStackTrace();
        }

        // Returns the loaded configuration Object 'config'.
        return config;
    }

    /*
     * Configuration file getters
     */
    protected String getString(String path) {
        String string = config.getString(path);
        return string;
    }

    protected void setString(String path, String string) {
        createSection(path);
        config.set(path, string);
    }

    protected String getFormatString(String path) {
        String arg = getString(path);
        if (arg != null) {
            arg = ChatColor.translateAlternateColorCodes('&', arg);
        }
        return arg;
    }

    protected void setFormatString(String path, String arg) {
        createSection(path);
        if (arg != null) {
            arg = arg.replaceAll("§", "&");
        }
        config.set(path, arg);
    }

    protected int getInteger(String path) {
        int arg = config.getInt(path);
        return arg;
    }

    protected void setInteger(String path, int arg) {
        createSection(path);
        config.set(path, arg);
    }

    protected long getLong(String path) {
        long arg = Long.valueOf(config.getString(path));
        return arg;
    }

    protected void setLong(String path, long arg) {
        createSection(path);
        config.set(path, arg);
    }

    protected boolean getBoolean(String path) {
        boolean arg = config.getBoolean(path);
        return arg;
    }

    protected void setBoolean(String path, boolean arg) {
        createSection(path);
        config.set(path, arg);
    }

    protected List<String> getListString(String path) {
        List<String> arg = (List<String>) config.getList(path);
        return arg;
    }

    protected void setListString(String path, List<String> arg) {
        createSection(path);
        config.set(path, arg);
    }

    protected List<String> getListFormatString(String path) {
        List<String> arg = (List<String>) config.getList(path);
        if (arg != null) {
            for (String line : arg) {
                arg.set(arg.indexOf(line), ChatColor.translateAlternateColorCodes('&', line));
            }
        }
        return arg;
    }

    protected void setListFormatString(String path, List<String> arg) {
        createSection(path);
        if (arg != null) {
            for (String line : arg) {
                arg.set(arg.indexOf(line), line.replaceAll("§", "&"));
            }
        }
        config.set(path, arg);
    }

    protected Material getMaterial(String path) {
        if (getString(path) != null) {
            return Material.valueOf(getString(path));
        }
        return null;
    }

    protected void setMaterial(String path, Material arg) {
        createSection(path);
        config.set(path, arg.name());
    }

    protected HashMap<EnchantmentProperties, Object> getEnchantmentProperties(String path) {
        HashMap<EnchantmentProperties, Object> enchantmentProperties = new HashMap<EnchantmentProperties, Object>();

        if (config.contains(path)) {
            enchantmentProperties.put(EnchantmentProperties.STRENGTH, getInteger(path + "strength"));
            enchantmentProperties.put(EnchantmentProperties.TYPE, getString(path + "type"));

            enchantmentProperties.put(EnchantmentProperties.IGNOREMAX, getBoolean(path + "ignoremax"));
            enchantmentProperties.put(EnchantmentProperties.HIDE, getBoolean(path + "hide"));

            return enchantmentProperties;
        }
        return null;
    }

    protected HashMap<ItemProperties, Object> getItemProperties(String path) {
        HashMap<ItemProperties, Object> itemProperties = new HashMap<ItemProperties, Object>();

        itemProperties.put(ItemProperties.TOGGLE, getBoolean(path + "toggle"));
        itemProperties.put(ItemProperties.SLOT, getInteger(path + "slot"));

        itemProperties.put(ItemProperties.NAME, getFormatString(path + "name"));
        itemProperties.put(ItemProperties.LORE, getListFormatString(path + "lore"));

        itemProperties.put(ItemProperties.MATERIAL, getMaterial(path + "material"));
        itemProperties.put(ItemProperties.ID, (short) getInteger(path + "id"));
        itemProperties.put(ItemProperties.AMOUNT, getString(path + "amount"));

        itemProperties.put(ItemProperties.ENCHANTMENT, getEnchantmentProperties(path + "enchantment."));

        return itemProperties;
    }

    protected void setItemProperties(String path, HashMap<ItemProperties, Object> itemProperties) {
        createSection(path);

        boolean toggle = (boolean) itemProperties.get(ItemProperties.TOGGLE);
        setBoolean(path + "toggle", toggle);
        int slot = (int) itemProperties.get(ItemProperties.SLOT);
        setInteger(path + "slot", slot);

        String name = (String) itemProperties.get(ItemProperties.NAME);
        setFormatString(path + "name", name);
        List<String> lore = (List<String>) itemProperties.get(ItemProperties.LORE);
        setListFormatString(path + "lore", lore);

        Material material = (Material) itemProperties.get(ItemProperties.MATERIAL);
        setMaterial(path + "material", material);
        int id = (int) itemProperties.get(ItemProperties.ID);
        setInteger(path + "id", id);
        String amount = (String) itemProperties.get(ItemProperties.AMOUNT);
        setString(path + "amount", amount);
    }

    // Method to create a new config section.
    private void createSection(String path) {
        config.createSection(path);
        saveConfig();
    }
}