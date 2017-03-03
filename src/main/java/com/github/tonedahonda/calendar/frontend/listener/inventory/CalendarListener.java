package com.github.tonedahonda.calendar.frontend.listener.inventory;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.api.events.CalendarClickEvent;
import com.github.tonedahonda.calendar.backend.configs.CalendarConfig;
import com.github.tonedahonda.calendar.backend.dateTime.DateTime;
import com.github.tonedahonda.calendar.backend.dateTime.DateTimeUtils;
import com.github.tonedahonda.calendar.backend.item.Items;
import com.github.tonedahonda.calendar.backend.storage.Storage;
import com.github.tonedahonda.calendar.backend.storage.StorageUtils;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentManager;
import com.github.tonedahonda.calendar.frontend.gui.calendar.Calendar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.util.HashMap;

@SuppressWarnings("unused")
public class CalendarListener {

    StorageUtils storageUtils = Main.getStorageUtils();
    DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();
    CalendarConfig calendarConfig = Main.getCalendarConfig();

    public CalendarListener(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ClickType click = event.getClick();
        InventoryAction action = event.getAction();
        ItemStack item = event.getCurrentItem();

        Storage storage = Main.storages.get(player);
        Calendar calendar = storage.getCalendar();
        HashMap<Items, Object> items = calendar.getItems();


        CalendarClickEvent calendarClickEvent = new CalendarClickEvent(player, calendar, click, action, item);
        Bukkit.getPluginManager().callEvent(calendarClickEvent);

        if (!calendarClickEvent.isCancelled()) {

            @SuppressWarnings("unchecked")
            HashMap<ItemStack, DateTime> dayItems = (HashMap<ItemStack, DateTime>) calendar.getItems().get(Items.DAY);
            if (dayItems.containsKey(item)) {
                DateTime dateTime = dayItems.get(item);
                player.openInventory(createAppointmentManager(player, dateTime));
            }

            if (item.isSimilar((ItemStack) items.get(Items.previousMonth))) {
                LocalDateTime timeSystem = dateTimeUtils.removeMonth(calendar.getTimeSystem());
                DateTime date = new DateTime(timeSystem);

                player.openInventory(createCalendar(player, date));

            }

            if (item.isSimilar((ItemStack) items.get(Items.nextMonth))) {
                LocalDateTime timeSystem = dateTimeUtils.addMonth(calendar.getTimeSystem());
                DateTime date = new DateTime(timeSystem);

                player.openInventory(createCalendar(player, date));

            }

            if (item.isSimilar((ItemStack) items.get(Items.EXIT))) {
                player.closeInventory();
            }

        }
    }


    private Inventory createCalendar(Player player, DateTime date) {

        // Creates a new calendar instance and saves it with storageCalendar()
        Calendar calendar = new Calendar(player, date);
        storageUtils.storageCalendar(player, calendar);


        return calendar.getInventory();
    }

    private Inventory createAppointmentManager(Player player, DateTime date) {
        AppointmentManager appointmentManager = new AppointmentManager(player, date);
        storageUtils.storageAppointmentManager(player, appointmentManager);

        return appointmentManager.getInventory();
    }

}
