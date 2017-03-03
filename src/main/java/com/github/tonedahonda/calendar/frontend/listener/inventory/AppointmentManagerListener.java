package com.github.tonedahonda.calendar.frontend.listener.inventory;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.appointments.Appointment;
import com.github.tonedahonda.calendar.backend.configs.AppointmentDataConfig;
import com.github.tonedahonda.calendar.backend.dateTime.DateTime;
import com.github.tonedahonda.calendar.backend.item.Items;
import com.github.tonedahonda.calendar.backend.storage.Storage;
import com.github.tonedahonda.calendar.backend.storage.StorageUtils;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentAdd;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentManager;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentRemove;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentTrash;
import com.github.tonedahonda.calendar.frontend.gui.calendar.Calendar;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class AppointmentManagerListener {

    AppointmentDataConfig appointmentDataConfig = Main.getAppointmentDataConfig();
    StorageUtils storageUtils = Main.getStorageUtils();

    public AppointmentManagerListener(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        Storage storage = Main.storages.get(player);
        AppointmentManager appointmentManager = storage.getAppointmentManager();
        DateTime dateTime = appointmentManager.getDateTime();
        HashMap<Items, Object> items = appointmentManager.getItems();


        if (item.isSimilar((ItemStack) items.get(Items.BACK))) {
            player.openInventory(createCalendar(player, storage.getCalendarDateTime()));
        }

        if (item.isSimilar((ItemStack) items.get(Items.addAppointment))) {
            player.openInventory(createAppointmentAdd(player, dateTime));
        }

        if (item.isSimilar((ItemStack) items.get(Items.restoreAppointment))) {
            player.openInventory(createAppointmentTrash(player, dateTime));
        }

        HashMap<Items, Appointment> publicAppointmentItems = (HashMap<Items, Appointment>) items.get(Items.publicAppointments);
        if (publicAppointmentItems.containsKey(item)) {
            Appointment appointment = publicAppointmentItems.get(item);
            player.openInventory(createAppointmentRemove(player, appointment));
        }
        HashMap<Items, Appointment> privateAppointmentItems = (HashMap<Items, Appointment>) items.get(Items.privateAppointments);
        if (privateAppointmentItems.containsKey(item)) {
            Appointment appointment = privateAppointmentItems.get(item);
            player.openInventory(createAppointmentRemove(player, appointment));
        }

    }

    private Inventory createCalendar(Player player, DateTime date) {
        Calendar calendar = new Calendar(player, date);
        storageUtils.storageCalendar(player, calendar);


        return calendar.getInventory();
    }

    private Inventory createAppointmentAdd(Player player, DateTime date) {
        AppointmentAdd appointmentAdd = new AppointmentAdd(player, new Appointment(player.getUniqueId(), date), date);
        storageUtils.storageAppointmentAdd(player, appointmentAdd);

        return appointmentAdd.getInventory();
    }

    private Inventory createAppointmentRemove(Player player, Appointment appointment) {
        AppointmentRemove appointmentRemove = new AppointmentRemove(player, appointment);
        storageUtils.storageAppointmentRemove(player, appointmentRemove);

        return appointmentRemove.getInventory();
    }

    private Inventory createAppointmentTrash(Player player, DateTime dateTime) {
        AppointmentTrash trash = new AppointmentTrash(player, dateTime);
        storageUtils.storageAppointmentTrash(player, trash);

        return trash.getInventory();
    }

}
