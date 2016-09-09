package frontend.gui;

import java.time.LocalDate;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import backend.date.Date;
import backend.item.Items;

public class Storage {
	
	Player holder;
	
	Date date;
	LocalDate timeSystem;
	Inventory inventory;
	HashMap<Items, Object> items = new HashMap<Items, Object>();
	

	public Player getHolder() {
		return holder;
	}


	public void setHolder(Player holder) {
		this.holder = holder;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public LocalDate getTimeSystem() {
		return timeSystem;
	}


	public void setTimeSystem(LocalDate timeSystem) {
		this.timeSystem = timeSystem;
	}


	public Inventory getInventory() {
		return inventory;
	}


	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}


	public HashMap<Items, Object> getItems() {
		return items;
	}


	public void setItems(HashMap<Items, Object> items) {
		this.items = items;
	}


	public boolean allNull() {
		return 	   
				   inventory 	== null
				&& items 		== null;
	}

}
