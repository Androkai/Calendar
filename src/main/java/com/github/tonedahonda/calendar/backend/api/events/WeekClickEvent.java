package com.github.tonedahonda.calendar.backend.api.events;

import com.github.tonedahonda.calendar.backend.dateTime.DateTime;
import com.github.tonedahonda.calendar.backend.item.Items;
import com.github.tonedahonda.calendar.frontend.gui.calendar.Calendar;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class WeekClickEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    boolean cancelled;

    ItemStack item;
    DateTime date;

    Player player;
    Calendar calendar;

    public WeekClickEvent(ItemStack item, Player player, Calendar calendar) {

        this.item = item;
        this.date = getClickedDate(item, calendar);

        this.player = player;
        this.calendar = calendar;

    }

    @Override
    public boolean isCancelled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setCancelled(boolean arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public HandlerList getHandlers() {
        // TODO Auto-generated method stub
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
        HashMap<ItemStack, DateTime> weekItems = (HashMap<ItemStack, DateTime>) calendar.getItems().get(Items.WEEK);
        DateTime date = weekItems.get(item);

        return date;
    }

}
