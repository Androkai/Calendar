package com.github.tonedahonda.calendar.frontend.gui.appointment;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.appointments.Appointment;
import com.github.tonedahonda.calendar.backend.appointments.AppointmentUtils;
import com.github.tonedahonda.calendar.backend.configs.AppointmentConfig;
import com.github.tonedahonda.calendar.backend.configs.AppointmentDataConfig;
import com.github.tonedahonda.calendar.backend.dateTime.DateTime;
import com.github.tonedahonda.calendar.backend.dateTime.DateTimeUtils;
import com.github.tonedahonda.calendar.backend.item.ItemProperties;
import com.github.tonedahonda.calendar.backend.item.Items;
import com.github.tonedahonda.calendar.frontend.gui.InvProperties;
import com.github.tonedahonda.calendar.frontend.gui.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class AppointmentRemove extends InventoryUtils {

    AppointmentConfig appointmentConfig = Main.getAppointmentConfig();
    AppointmentDataConfig appointmentDateConfig = Main.getAppointmentDataConfig();
    AppointmentUtils appointmentUtils = Main.getAppointmentUtils();
    DateTimeUtils dateUtils = Main.getDateTimeUtils();

    Player player;
    Appointment appointment;
    DateTime date;

    Inventory inventory;
    static HashMap<Items, Object> items = new HashMap<Items, Object>();

    public AppointmentRemove(Player player, Appointment appointment) {
        super(appointment.getDateTime(), appointment, items);

        this.player = player;
        this.appointment = appointment;
        this.date = new DateTime(appointment.getDateTime());

        this.inventory = createRemoveAppointmentInventory();

    }

    public Appointment getAppointment() {
        return appointment;
    }

    public DateTime getDate() {
        return date;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public HashMap<Items, Object> getItems() {
        return items;
    }


    private Inventory createRemoveAppointmentInventory() {
        HashMap<InvProperties, Object> properties = appointmentConfig.getRemoveAppointmentInventoryProperties();

        String header = (String) properties.get(InvProperties.TITLE);
        header = replacePlaceholder(header, date, appointment);
        int size = (int) properties.get(InvProperties.SIZE);
        Inventory inventory = Bukkit.createInventory(player, size, header);

        HashMap<Items, HashMap<ItemProperties, Object>> itemProperties = (HashMap<Items, HashMap<ItemProperties, Object>>) properties.get(InvProperties.ITEMS);

        HashMap<ItemProperties, Object> backToCalendarProperties = itemProperties.get(Items.BACK);
        ItemStack backToCalendarItem = createItem(backToCalendarProperties, date, appointment);
        items.put(Items.BACK, backToCalendarItem);
        inventory.setItem((int) backToCalendarProperties.get(ItemProperties.SLOT), backToCalendarItem);

        HashMap<ItemProperties, Object> confirmProperties = itemProperties.get(Items.CONFIRM);
        ItemStack confirmItem = createItem(confirmProperties, date, appointment);
        items.put(Items.CONFIRM, confirmItem);
        inventory.setItem((int) confirmProperties.get(ItemProperties.SLOT), confirmItem);
        ;


        return inventory;
    }


}
