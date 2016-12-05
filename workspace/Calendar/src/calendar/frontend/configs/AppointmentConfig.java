package calendar.frontend.configs;

import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.util.HashMap;

import org.bukkit.inventory.Inventory;

import calendar.backend.configs.Config;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.backend.item.Prefixes;
import calendar.backend.main.Main;
import calendar.frontend.gui.InvProperties;
import calendar.frontend.notifications.ReminderProperties;

public class AppointmentConfig extends Config {

	String path;
	String defaultPath;
	
	public AppointmentConfig() {
		super(Main.instance.getDataFolder(), "AppointmentConfig.yml");
		
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
	
	public HashMap<ReminderProperties, Object> getReminder() {
		HashMap<ReminderProperties, Object> reminderProperties = new HashMap<ReminderProperties, Object>();
		
		boolean toggle;
		int startAt;
		int repeatAfter;
		
			defaultPath = "reminder.";
			
				path = defaultPath + "toggle";
				reminderProperties.put(ReminderProperties.TOGGLE, getBoolean(path));
				
				path = defaultPath + "message";
				reminderProperties.put(ReminderProperties.MESSAGE, getFormatString(path));
				
				path = defaultPath + "startAt";
				reminderProperties.put(ReminderProperties.startAt, getInteger(path));
				
				path = defaultPath + "repeatAfter";
				reminderProperties.put(ReminderProperties.repeatAfter, getInteger(path));
		
		return reminderProperties;
	}
	
	public HashMap<InvProperties, Object> getAppointmentManagerInventoryProperties() {
		HashMap<InvProperties, Object> properties = new HashMap<InvProperties, Object>();
		HashMap<Items, HashMap<ItemProperties, Object>> items = new HashMap<Items, HashMap<ItemProperties, Object>>();
		
			/*
			 * Gets the values out of the config
			 */
			defaultPath = "inventorys.appointmentManager.";
				
				getInventoryProperties(properties, defaultPath);
				
				//Items
				defaultPath = defaultPath + "items.";
				
					//backToCalendar
					path = defaultPath + "backToCalendar.";
					items.put(Items.BACK, getItemProperties(path));
					
					//addAppointment
					path = defaultPath + "addAppointment.";
					items.put(Items.addAppointment, getItemProperties(path));
					
					//restoreAppointment
					path = defaultPath + "restoreAppointment.";
					items.put(Items.restoreAppointment, getItemProperties(path));
					
					//publicAppointments
					path = defaultPath + "publicAppointments.";
					items.put(Items.publicAppointments, getItemProperties(path));
					
					//privateAppointments
					path = defaultPath + "privateAppointments.";
					items.put(Items.privateAppointments, getItemProperties(path));
				
				properties.put(InvProperties.ITEMS, items);
		
		return properties;
	}
	
	public HashMap<InvProperties, Object> getAddAppointmentInventoryProperties() {
		HashMap<InvProperties, Object> properties = new HashMap<InvProperties, Object>();
		HashMap<Items, HashMap<ItemProperties, Object>> items = new HashMap<Items, HashMap<ItemProperties, Object>>();
		
			defaultPath = "inventorys.addAppointment.";
			
				 getInventoryProperties(properties, defaultPath);
				
				//Items
				defaultPath = defaultPath + "items.";
					
					//backToCalendar
					path = defaultPath + "backToCalendar.";
					items.put(Items.BACK, getItemProperties(path));
					
					//setStatus
					path = defaultPath + "setStatus.";
					items.put(Items.setStatus, getItemProperties(path));
					
					//setTime
					path = defaultPath + "setTime.";
					items.put(Items.setTime, getItemProperties(path));
					
					//setName
					path = defaultPath + "setName.";
					items.put(Items.setName, getItemProperties(path));
					
					//setHeader
					path = defaultPath + "setHeader.";
					items.put(Items.setHeader, getItemProperties(path));
		
					//setDescription
					path = defaultPath + "setDescription.";
					items.put(Items.setDescription, getItemProperties(path));
					
					//Confirm
					path = defaultPath + "confirm.";
					items.put(Items.CONFIRM, getItemProperties(path));
				
				properties.put(InvProperties.ITEMS, items);
		
		return properties;
	}
	
	public HashMap<InvProperties, Object> getRemoveAppointmentInventoryProperties() {
		HashMap<InvProperties, Object> properties = new HashMap<InvProperties, Object>();
		HashMap<Items, HashMap<ItemProperties, Object>> items = new HashMap<Items, HashMap<ItemProperties, Object>>();
		
			defaultPath = "inventorys.removeAppointment.";
			
				getInventoryProperties(properties, defaultPath);
				
				//Items
				defaultPath = defaultPath + "items.";
				
					//backToCalendar
					path = defaultPath + "backToCalendar.";
					items.put(Items.BACK, getItemProperties(path));
					
					//confirm
					path = defaultPath + "confirm.";
					items.put(Items.CONFIRM, getItemProperties(path));
					
				properties.put(InvProperties.ITEMS, items);
		
		return properties;
	}
	
	public HashMap<InvProperties, Object> getAppointmentTrashInventoryProperties() {
		HashMap<InvProperties, Object> properties = new HashMap<InvProperties, Object>();
		HashMap<Items, HashMap<ItemProperties, Object>> items = new HashMap<Items, HashMap<ItemProperties, Object>>();
		
			defaultPath = "inventorys.appointmentTrash.";
			
				 getInventoryProperties(properties, defaultPath);
				
				//Items
				defaultPath = defaultPath + "items.";
					
					//backToCalendar
					path = defaultPath + "backToCalendar.";
					items.put(Items.BACK, getItemProperties(path));
					
					//deletedAppointment
					path = defaultPath + "deletedAppointments.";
					items.put(Items.APPOINTMENT, getItemProperties(path));
					
				properties.put(InvProperties.ITEMS, items);
					
					
		return properties;
	}
	
	public void getInventoryProperties(HashMap<InvProperties, Object> properties, String defaultPath) {
		
		
			this.defaultPath = defaultPath;
			
				//Holder
				properties.put(InvProperties.HOLDER, null);
			
				//Title
				path = defaultPath + "title";
				properties.put(InvProperties.TITLE, getFormatString(path));
			
				//Size
				path = defaultPath + "size";
				properties.put(InvProperties.SIZE, getInteger(path));
			
	}
	
	

}
