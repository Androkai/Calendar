package com.github.tonedahonda.calendar.frontend.listener.inventory;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.appointments.Appointment;
import com.github.tonedahonda.calendar.backend.configs.AppointmentDataConfig;
import com.github.tonedahonda.calendar.backend.dateTime.DateTime;
import com.github.tonedahonda.calendar.backend.item.Items;
import com.github.tonedahonda.calendar.backend.storage.Storage;
import com.github.tonedahonda.calendar.backend.storage.StorageUtils;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentManager;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentRemove;
import com.github.tonedahonda.calendar.frontend.gui.calendar.Calendar;
import com.github.tonedahonda.calendar.frontend.messages.MessageHandler;
import com.github.tonedahonda.calendar.frontend.messages.MessageHandler.Error;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class AppointmentRemoveListener {

    AppointmentDataConfig appointmentDataConfig = Main.getAppointmentDataConfig();
    StorageUtils storageUtils = Main.getStorageUtils();

    public AppointmentRemoveListener(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        MessageHandler handler = new MessageHandler(player);
        ItemStack item = event.getCurrentItem();

        Storage storage = Main.storages.get(player);
        AppointmentRemove appointmentRemove = storage.getAppointmentRemove();
        HashMap<Items, Object> items = appointmentRemove.getItems();


        if (item.isSimilar((ItemStack) items.get(Items.BACK))) {
            player.openInventory(createAppointmentManager(player, appointmentRemove.getDate()));
        }

        if (item.isSimilar((ItemStack) items.get(Items.CONFIRM))) {
            Appointment appointment = appointmentRemove.getAppointment();
            if ((appointment.getCreator() == Main.sUUID && player.hasPermission("calendar.appointment.remove.public"))
                    || (appointment.getCreator() == player.getUniqueId() && player.hasPermission("calendar.appointment.remove.private"))) {

                appointmentDataConfig.removeAppointment(appointment);
                player.openInventory(createCalendar(player, appointmentRemove.getDate()));
            } else {
                player.closeInventory();
                handler.sendError(Error.NOPERMISSIONS);
            }
        }

    }

    private Inventory createCalendar(Player player, DateTime date) {
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
