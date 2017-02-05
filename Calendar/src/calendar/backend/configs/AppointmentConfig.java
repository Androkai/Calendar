package calendar.backend.configs;

import java.util.HashMap;

import calendar.backend.Main;
import calendar.backend.dateTime.Time;
import calendar.backend.item.ItemProperties;
import calendar.backend.item.Items;
import calendar.backend.item.Prefixes;
import calendar.frontend.gui.InvProperties;
import calendar.frontend.messages.Message;
import calendar.frontend.notifications.Reminder.ReminderAttributes;
public class AppointmentConfig extends Config {
	
	public AppointmentConfig() {
		super(Main.instance.getDataFolder(), "AppointmentConfig.yml");
		
		config = super.loadConfig();
	}
	
	public HashMap<ReminderAttributes, Object> getReminderAttributes() {
		HashMap<ReminderAttributes, Object> attributes = new HashMap<ReminderAttributes, Object>();
		String path = "reminder.";
			
			attributes.put(ReminderAttributes.TOGGLE, getBoolean(path + "toggle"));
			attributes.put(ReminderAttributes.MESSAGE, new Message(getFormatString(path + "message")));
			attributes.put(ReminderAttributes.STARTTIME, Time.fromString(getString(path + "startTime")));
			attributes.put(ReminderAttributes.WAITTIME, Time.fromString(getString(path + "waitTime")));
			
		return attributes;
	}
	
	public HashMap<Prefixes, String> getPrefixes() {
		HashMap<Prefixes, String> prefixes = new HashMap<Prefixes, String>();
		String defaultPath = "prefixes.";
		String path;
		
			path = defaultPath + "header";
			prefixes.put(Prefixes.HEADER, getFormatString(path));
			
			path = defaultPath + "description";
			prefixes.put(Prefixes.DESCRIPTION, getFormatString(path));
		
		return prefixes;
	}
	
	public HashMap<InvProperties, Object> getAppointmentManagerInventoryProperties() {
		HashMap<InvProperties, Object> properties = new HashMap<InvProperties, Object>();
		HashMap<Items, HashMap<ItemProperties, Object>> items = new HashMap<Items, HashMap<ItemProperties, Object>>();
		String defaultPath = "inventorys.appointmentManager.";
		String path;		
		
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
		String defaultPath = "inventorys.addAppointment.";
		String path;	
		
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
		String defaultPath = "inventorys.removeAppointment.";
		String path;	
		
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
		String defaultPath = "inventorys.appointmentTrash.";
		String path;
		
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
		String path;	
		
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
