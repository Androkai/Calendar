package calendar.frontend.listener.inventory.appointment;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import calendar.backend.appointments.Appointment;
import calendar.backend.appointments.AppointmentProperties;
import calendar.backend.date.DateTime;
import calendar.backend.item.Items;
import calendar.backend.main.Main;
import calendar.backend.storage.Storage;
import calendar.backend.storage.StorageUtils;
import calendar.frontend.gui.appointment.AppointmentAdd;
import calendar.frontend.gui.appointment.AppointmentManager;
import calendar.frontend.gui.calendar.Calendar;
import calendar.frontend.messages.Error;

public class AppointmentAddListener {
	
	StorageUtils storageUtils = Main.getStorageUtils();
	
	HashMap<Error, String> errors = Main.getMessageConfig().getErrors();
	
	public AppointmentAddListener(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		
			Storage storage = Main.storages.get(player);
				AppointmentAdd appointmentAdd = storage.getAppointmentAdd();
				HashMap<Items, Object> items = appointmentAdd.getItems();
				
					
					if(item.isSimilar((ItemStack) items.get(Items.BACK))) {
						player.openInventory(createAppointmentManager(player, appointmentAdd.getDate()));
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.setStatus))) {
						storage.setInputParameter(AppointmentProperties.STATUS);
								player.closeInventory();
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.setTime))) {
						storage.setInputParameter(AppointmentProperties.TIME);
							player.closeInventory();
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.setName))) {
						storage.setInputParameter(AppointmentProperties.NAME);
							player.closeInventory();
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.setHeader))) {
						storage.setInputParameter(AppointmentProperties.HEADER);
						 	player.closeInventory();
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.setDescription))) {
						storage.setInputParameter(AppointmentProperties.DESCRIPTION);
							player.closeInventory();
					}
					
					if(item.isSimilar((ItemStack) items.get(Items.CONFIRM))) {
						Appointment appointment = appointmentAdd.getAppointment();
							if(appointment.getCreator() != null && appointment.getName() != null && appointment.getHeader() != null && appointment.getDescription() != null) {
								if((appointment.getCreator() == Main.sUUID && player.hasPermission("calendar.appointment.add.public"))
									|| (appointment.getCreator() == player.getUniqueId() && player.hasPermission("calendar.appointment.add.private"))) {
									
											Main.getAppointmentDataConfig().addAppointment(appointment);
												player.openInventory(createCalendar(player, appointmentAdd.getDate()));
								}else{
									player.closeInventory();
									player.sendMessage(errors.get(Error.noPermissions));
								}
							}
					}
		
	}
	
	private Inventory createAppointmentManager(Player player, DateTime date) {
		AppointmentManager appointmentManager = new AppointmentManager(player, date);
		storageUtils.storageAppointmentManager(player, appointmentManager);
		
		return appointmentManager.getInventory();
	}
	
	private Inventory createCalendar(Player player, DateTime date) {
		
		// Creates a new calendar instance and saves it with storageCalendar()
		Calendar calendar = new Calendar(player, date);
		storageUtils.storageCalendar(player, calendar);

		
		return calendar.getInventory();
	}

}
