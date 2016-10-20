package calendar.backend.api.events;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import calendar.backend.date.Date;
import calendar.backend.item.Items;
import calendar.frontend.gui.Calendar;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.ItemBucket;

public class CalendarClickEvent extends Event implements Cancellable {
	
    private static final HandlerList handlers = new HandlerList();
	boolean cancelled;

	Player player;
	Calendar calendar;
	
	ClickType click;
	InventoryAction action;
	ItemStack item;
	
	public CalendarClickEvent(Player player, Calendar calendar, ClickType click, InventoryAction action, ItemStack item) {
		
		this.player = player;
		this.calendar = calendar;
		
		this.click = click;
		this.action = action;
		this.item = item;
		
		callDayClickEvent(player, calendar, item);
		callWeekClickEvent(player, calendar, item);
	}
	
	public Player getWhoClicked() {
		return player;
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
	
	public ClickType getClick() {
		return click;
	}
	
	public InventoryAction getAction() {
		return action;
	}
	
	public ItemStack getClickedItem() {
		return item;
	}
	
	/*
	 * Method to call the DayClickEvent if the item is a day item.
	 */
	private void callDayClickEvent(Player player, Calendar calendar, ItemStack item) {
		HashMap<ItemStack, Date> dayItems = (HashMap<ItemStack, Date>) calendar.getItems().get(Items.DAY);
		
			if(dayItems.containsKey(item)) {
				Bukkit.getPluginManager().callEvent(new DayClickEvent(item, player, calendar));
			}
	}
	
	/*
	 * Method to call the WeekClickEvent if the item is a week item.
	 */
	private void callWeekClickEvent(Player player, Calendar calendar, ItemStack item) {
		HashMap<ItemStack, Date> weekItems = (HashMap<ItemStack, Date>) calendar.getItems().get(Items.WEEK);
		
			if(weekItems.containsKey(item)) {
				Bukkit.getPluginManager().callEvent(new WeekClickEvent(item, player, calendar));
			}
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }

	
}
