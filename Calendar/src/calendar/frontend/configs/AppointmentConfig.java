package calendar.frontend.configs;

import java.io.File;
import java.util.HashMap;

import org.bukkit.inventory.Inventory;

import calendar.backend.configs.Config;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.backend.item.Prefixes;
import calendar.backend.main.main;
import calendar.frontend.gui.InventoryProperties;

public class AppointmentConfig extends Config {

	String path;
	String defaultPath;
	
	public AppointmentConfig() {
		super(main.instance.getDataFolder(), "AppointmentConfig.yml");
		
		config = super.loadConfig();
	}
	
	public HashMap<Prefixes, String> getPrefixes() {
		HashMap<Prefixes, String> prefixes = new HashMap<Prefixes, String>();
		
		defaultPath = "prefixes.";
		
			path = defaultPath + "header";
			prefixes.put(Prefixes.HEADER, getFormatString(path));
			
			path = defaultPath + "description";
			prefixes.put(Prefixes.DESCRIPTION, getFormatString(path));
		
		return prefixes;
	}
	
	public HashMap<InventoryProperties, Object> getAppointmentManagerInventoryProperties() {
		HashMap<InventoryProperties, Object> appointmentManagerInventoryProperties = new HashMap<InventoryProperties, Object>();
		
		String title;
		int size;
		HashMap<Items, HashMap<ItemProperties, Object>> items = new HashMap<Items, HashMap<ItemProperties, Object>>();
		
			/*
			 * Gets the values out of the config
			 */
			defaultPath = "inventorys.appointmentManager.";
				
				//Holder
				appointmentManagerInventoryProperties.put(InventoryProperties.HOLDER, null);
			
				//Title
				path = defaultPath + "title";
				title = getFormatString(path);
				appointmentManagerInventoryProperties.put(InventoryProperties.HEADER, title);
				
				// Size
				path = defaultPath + "size";
				size = getInteger(path);
				appointmentManagerInventoryProperties.put(InventoryProperties.SIZE, size);
				
				//Items
				defaultPath = defaultPath + "items.";
				
					//backToCalendar
					path = defaultPath + "backToCalendar.";
					items.put(Items.backToCalendar, getItemProperties(path));
					
					//publicAppointments
					path = defaultPath + "publicAppointments.";
					items.put(Items.publicAppointments, getItemProperties(path));
					
					//privateAppointments
					path = defaultPath + "privateAppointments.";
					items.put(Items.privateAppointments, getItemProperties(path));
				
				appointmentManagerInventoryProperties.put(InventoryProperties.ITEMS, items);
		
		return appointmentManagerInventoryProperties;
	}
	
	public HashMap<InventoryProperties, Object> getAddAppointmentInventoryProperties() {
		HashMap<InventoryProperties, Object> properties = new HashMap<InventoryProperties, Object>();
		
		String title;
		int size;
		HashMap<Items, HashMap<ItemProperties, Object>> items = new HashMap<Items, HashMap<ItemProperties, Object>>();
		
			defaultPath = "inventorys.addAppointment.";
			
				//Holder
				properties.put(InventoryProperties.HOLDER, null);
				
				//Title
				path = defaultPath + "title";
				title = getFormatString(path);
				properties.put(InventoryProperties.HEADER, title);
				
				//Size
				path = defaultPath + "size";
				size = getInteger(path);
				properties.put(InventoryProperties.SIZE, size);
				
				//Items
				defaultPath = defaultPath + "items.";
					
					//backToCalendar
					path = defaultPath + "backToCalendar.";
					items.put(Items.backToCalendar, getItemProperties(path));
					
					//setStatus
					path = defaultPath + "setStatus.";
					items.put(Items.setStatus, getItemProperties(path));
					
					//setName
					path = defaultPath + "setName.";
					items.put(Items.setName, getItemProperties(path));
					
					//setHeader
					path = defaultPath + "setHeader.";
					items.put(Items.setHeader, getItemProperties(path));
		
					//setDescription
					path = defaultPath + "setDescription.";
					items.put(Items.setDescription, getItemProperties(path));
				
				properties.put(InventoryProperties.ITEMS, items);
		
		return properties;
	}
	
	public HashMap<InventoryProperties, Object> getRemoveAppointmentInventoryProperties() {
		HashMap<InventoryProperties, Object> properties = new HashMap<InventoryProperties, Object>();
		
		String title;
		int size;
		HashMap<Items, HashMap<ItemProperties, Object>> items = new HashMap<Items, HashMap<ItemProperties, Object>>();
		
			defaultPath = "inventorys.removeAppointment.";
			
				//Holder
				properties.put(InventoryProperties.HOLDER, null);
		
				//Title
				path = defaultPath + "title";
				title = getFormatString(path);
				properties.put(InventoryProperties.HEADER, title);
				
				//Size
				path = defaultPath + "size";
				size = getInteger(path);
				properties.put(InventoryProperties.SIZE, size);
				
				//Items
				defaultPath = defaultPath + "items.";
				
					//backToCalendar
					path = defaultPath + "backToCalendar.";
					items.put(Items.backToCalendar, getItemProperties(path));
					
					//confirm
					path = defaultPath + "confirm.";
					items.put(Items.CONFIRM, getItemProperties(path));
					
				properties.put(InventoryProperties.ITEMS, items);
		
		return properties;
	}
	
	

}
