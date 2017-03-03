package com.github.tonedahonda.calendar.frontend.listener.inventory;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.appointments.Appointment;
import com.github.tonedahonda.calendar.backend.configs.AppointmentDataConfig;
import com.github.tonedahonda.calendar.backend.dateTime.DateTime;
import com.github.tonedahonda.calendar.backend.item.Items;
import com.github.tonedahonda.calendar.backend.storage.Storage;
import com.github.tonedahonda.calendar.backend.storage.StorageUtils;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentManager;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentTrash;
import com.github.tonedahonda.calendar.frontend.gui.calendar.Calendar;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class AppointmentTrashListener {

    AppointmentDataConfig appointmentDataConfig = Main.getAppointmentDataConfig();
    StorageUtils storageUtils = Main.getStorageUtils();

    public AppointmentTrashListener(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        Storage storage = Main.storages.get(player);
        AppointmentTrash appointmentTrash = storage.getAppointmentTrash();
        DateTime dateTime = appointmentTrash.getDateTime();
        HashMap<Items, Object> items = appointmentTrash.getItems();

        if (item.isSimilar((ItemStack) items.get(Items.BACK))) {
            player.openInventory(createAppointmentManager(player, dateTime));
        }

        HashMap<ItemStack, Appointment> appointmentItems = (HashMap<ItemStack, Appointment>) items.get(Items.APPOINTMENT);
        if (appointmentItems.containsKey(item)) {
            Appointment appointment = appointmentItems.get(item);
            if ((appointment.getCreator() == Main.sUUID && player.hasPermission("calendar.appointment.restore.public"))
                    || (appointment.getCreator() == player.getUniqueId() && player.hasPermission("calendar.appointment.restore.private"))) {

                appointmentDataConfig.restoreAppointment(appointment);
                player.openInventory(createCalendar(player, dateTime));
            }
        }

    }

    private Inventory createCalendar(Player player, DateTime dateTime) {
        Calendar calendar = new Calendar(player, dateTime);
        storageUtils.storageCalendar(player, calendar);

        return calendar.getInventory();
    }

    private Inventory createAppointmentManager(Player player, DateTime dateTime) {
        AppointmentManager manager = new AppointmentManager(player, dateTime);
        storageUtils.storageAppointmentManager(player, manager);

        return manager.getInventory();
    }

}
