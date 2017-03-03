package com.github.tonedahonda.calendar.frontend.listener.inventory;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.storage.Storage;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentAdd;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentManager;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentRemove;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentTrash;
import com.github.tonedahonda.calendar.frontend.gui.calendar.Calendar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InventoryEventCaller implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        HashMap<Player, Storage> storages = Main.storages;

        if (inventory != null) {
            if (storages.containsKey(player)) {
                event.setCancelled(true);
                Storage storage = storages.get(player);

                Calendar calendar = storage.getCalendar();
                if (calendar != null) {
                    if (inventory.equals(calendar.getInventory())) {
                        new CalendarListener(event);
                    }
                }

                AppointmentManager appointmentManager = storage.getAppointmentManager();
                if (appointmentManager != null) {
                    if (inventory.equals(appointmentManager.getInventory())) {
                        new AppointmentManagerListener(event);
                    }
                }

                AppointmentAdd appointmentAdd = storage.getAppointmentAdd();
                if (appointmentAdd != null) {
                    if (inventory.equals(appointmentAdd.getInventory())) {
                        new AppointmentAddListener(event);
                    }
                }

                AppointmentRemove appointmentRemove = storage.getAppointmentRemove();
                if (appointmentRemove != null) {
                    if (inventory.equals(appointmentRemove.getInventory())) {
                        new AppointmentRemoveListener(event);
                    }
                }

                AppointmentTrash appointmentTrash = storage.getAppointmentTrash();
                if (appointmentTrash != null) {
                    if (inventory.equals(appointmentTrash.getInventory())) {
                        new AppointmentTrashListener(event);
                    }
                }
            }
        }

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        new InventoryCloseListener(event);

    }

}
