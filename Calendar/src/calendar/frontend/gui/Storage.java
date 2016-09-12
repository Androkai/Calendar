package calendar.frontend.gui;

import java.time.LocalDate;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import calendar.backend.date.Date;
import calendar.backend.item.Items;

public class Storage {
	
	Player storageHolder;
	
	Calendar calendar;
	

	public Player getStorageHolder() {
		return storageHolder;
	}
	public void setStorageHolder(Player storageHolder) {
		this.storageHolder = storageHolder;
	}
	
	public Calendar getCalendar() {
		return this.calendar;
	}
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}


	public boolean allNull() {
		return 	   
			calendar 		== null;
	}

}
