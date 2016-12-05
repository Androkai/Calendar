package calendar.backend.api.events;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import calendar.backend.date.DateTime;
import calendar.backend.item.ItemCreator;
import calendar.backend.item.Items;
import calendar.frontend.gui.appointment.AppointmentManager;
import calendar.frontend.gui.calendar.Calendar;

public class DayClickEvent extends Event implements Cancellable {
	
    private static final HandlerList handlers = new HandlerList();
	boolean cancelled;
    
	ItemStack item;
	DateTime date;
	
	Player player;
	Calendar calendar;
	
	public DayClickEvent(ItemStack item, Player player, Calendar calendar) {
	
		this.item = item;
		this.date = getClickedDate(item, calendar);
		
		this.player = player;
		this.calendar = calendar;
		
	}
	
	public ItemStack getClickedItem() {
		return item;
	}
	
	public DateTime getClickedDate() {
		return date;
	}
	
	public Player getWhoClicked() {
		return player;
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
    public static HandlerList getHandlerList() {
        return handlers;
    }

	/*
	 * Method to get the day it Player clicked.
	 */
	private DateTime getClickedDate(ItemStack item, Calendar calendar) {
		@SuppressWarnings("unchecked")
		HashMap<ItemStack, DateTime> dayItems = (HashMap<ItemStack, DateTime>) calendar.getItems().get(Items.DAY);
		DateTime date = dayItems.get(item);
		
		return date;
	}
	
}
