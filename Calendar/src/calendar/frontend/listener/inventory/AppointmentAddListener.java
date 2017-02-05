package calendar.frontend.listener.inventory;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import calendar.backend.Main;
import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.AppointmentProperties;
import calendar.backend.dateTime.DateTime;
import calendar.backend.item.Items;
import calendar.backend.storage.Storage;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.gui.appointment.AppointmentAdd;
import calendar.frontend.gui.appointment.AppointmentManager;
import calendar.frontend.gui.calendar.Calendar;
import calendar.frontend.messages.MessageHandler.Error;
import calendar.frontend.messages.MessageHandler.OutputType;
import calendar.frontend.messages.MessageHandler.Request;
import calendar.frontend.messages.MessageHandler;

public class AppointmentAddListener {
	
	StorageUtils storageUtils = Main.getStorageUtils();
	
	public AppointmentAddListener(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		MessageHandler handler = new MessageHandler(player);
		ItemStack item = event.getCurrentItem();
		
			Storage storage = Main.storages.get(player);
				AppointmentAdd appointmentAdd = storage.getAppointmentAdd();
				HashMap<Items, Object> items = appointmentAdd.getItems();
				DateTime dateTime = appointmentAdd.getDateTime();
				Appointment appointment = appointmentAdd.getAppointment();
				
					
					if(item.isSimilar((ItemStack) items.get(Items.BACK))) {
						player.openInventory(createAppointmentManager(player, dateTime));
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.setStatus))) {
						if(appointment.getCreator() == Main.sUUID) {
							appointment.setCreator(player.getUniqueId());
						}else if(appointment.getCreator() == player.getUniqueId()) {
							appointment.setCreator(Main.sUUID);
						}else{
							appointment.setCreator(player.getUniqueId());
						}
							player.openInventory(createAppointmentAdd(player, appointment, dateTime));
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.setTime))) {
						handler.sendInputRequest(Request.TIME);
							storage.setInputParameter(AppointmentProperties.TIME);
								player.closeInventory();
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.setName))) {
						handler.sendInputRequest(Request.NAME);
							storage.setInputParameter(AppointmentProperties.NAME);
								player.closeInventory();
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.setHeader))) {
						handler.sendInputRequest(Request.HEADER);
							storage.setInputParameter(AppointmentProperties.HEADER);
								player.closeInventory();
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.setDescription))) {
						handler.sendInputRequest(Request.DESCRIPTION);;
							storage.setInputParameter(AppointmentProperties.DESCRIPTION);
								player.closeInventory();
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.CONFIRM))) {
							if(appointment.getCreator() != null && appointment.getName() != null && appointment.getHeader() != null && appointment.getDescription() != null) {
								if((appointment.getCreator() == Main.sUUID && player.hasPermission("calendar.appointment.add.public"))
									|| (appointment.getCreator() == player.getUniqueId() && player.hasPermission("calendar.appointment.add.private"))) {
									
											Main.getAppointmentDataConfig().addAppointment(appointment);
												player.openInventory(createCalendar(player, dateTime));
								}else{
									player.closeInventory();
									handler.sendError(Error.NOPERMISSIONS);
								}
							}
					}
		
	}
	
	private Inventory createAppointmentManager(Player player, DateTime date) {
		AppointmentManager appointmentManager = new AppointmentManager(player, date);
		storageUtils.storageAppointmentManager(player, appointmentManager);
		
		return appointmentManager.getInventory();
	}
	
	private Inventory createAppointmentAdd(Player player, Appointment appointment, DateTime dateTime) {
		AppointmentAdd appointmentAdd = new AppointmentAdd(player, appointment, dateTime);
		storageUtils.storageAppointmentAdd(player, appointmentAdd);
	
	return appointmentAdd.getInventory();
	}
	
	private Inventory createCalendar(Player player, DateTime date) {
		
		// Creates a new calendar instance and saves it with storageCalendar()
		Calendar calendar = new Calendar(player, date);
		storageUtils.storageCalendar(player, calendar);

		
		return calendar.getInventory();
	}

}
