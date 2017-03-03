package com.github.tonedahonda.calendar.frontend.listener.chat;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.appointments.Appointment;
import com.github.tonedahonda.calendar.backend.appointments.AppointmentProperties;
import com.github.tonedahonda.calendar.backend.dateTime.Time;
import com.github.tonedahonda.calendar.backend.storage.Storage;
import com.github.tonedahonda.calendar.backend.storage.StorageUtils;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentAdd;
import com.github.tonedahonda.calendar.frontend.messages.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatEventCaller extends MessageUtils implements Listener {

    StorageUtils storageUtils = Main.getStorageUtils();

    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (Main.storages.containsKey(player)) {
            Storage storage = Main.storages.get(player);

            if (storage.getInputParameter() != null) {
                event.setCancelled(true);

                AppointmentProperties parameter = storage.getInputParameter();
                Appointment appointment = storage.getAppointmentAdd().getAppointment();
                storage.setInputParameter(null);

                if (parameter.equals(AppointmentProperties.STATUS)) {
                    if (isPublic(message)) {
                        appointment.setCreator(Main.sUUID);
                    }

                    if (isPrivate(message)) {
                        appointment.setCreator(player.getUniqueId());
                    }
                }

                if (parameter.equals(AppointmentProperties.TIME)) {
                    Time time = Time.fromString(message);
                    appointment.getDateTime().setTime(time);

                }

                if (parameter.equals(AppointmentProperties.NAME)) {
                    String name = addSpaces(message);
                    appointment.setName(name);
                }

                if (parameter.equals(AppointmentProperties.HEADER)) {
                    appointment.setHeader(message);
                }

                if (parameter.equals(AppointmentProperties.DESCRIPTION)) {
                    List<String> description = stringToList(message);
                    appointment.setDescription(description);
                }

                AppointmentAdd appointmentAdd = new AppointmentAdd(player, appointment, storage.getAppointmentAdd().getDateTime());
                storageUtils.storageAppointmentAdd(player, appointmentAdd);
                player.openInventory(appointmentAdd.getInventory());

            }

        }
    }

    /*
     * Check if status is public
     */
    private boolean isPublic(String status) {

        if (status.equalsIgnoreCase("public")) {
            return true;
        } else {
            return false;
        }

    }

    /*
     * Check if status is private
     */
    private boolean isPrivate(String status) {
        if (status.equalsIgnoreCase("private")) {
            return true;
        } else {
            return false;
        }
    }

}
