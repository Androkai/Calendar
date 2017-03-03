package com.github.tonedahonda.calendar.frontend.gui.appointment;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.appointments.Appointment;
import com.github.tonedahonda.calendar.backend.appointments.AppointmentUtils;
import com.github.tonedahonda.calendar.backend.configs.AppointmentConfig;
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

public class AppointmentAdd extends InventoryUtils {

    AppointmentConfig appointmentConfig = Main.getAppointmentConfig();
    AppointmentUtils appointmentUtils = Main.getAppointmentUtils();
    DateTimeUtils dateUtils = Main.getDateTimeUtils();

    Player player;
    Appointment appointment;
    DateTime date;

    Inventory inventory;
    static HashMap<Items, Object> items = new HashMap<Items, Object>();

    public AppointmentAdd(Player player, Appointment appointment, DateTime date) {
        super(date, appointment, items);

        this.player = player;
        this.appointment = appointment;
        this.date = new DateTime(date);

        this.inventory = createAddAppointmentInventory();

    }

    public Appointment getAppointment() {
        return appointment;
    }

    public DateTime getDateTime() {
        return date;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public HashMap<Items, Object> getItems() {
        return items;
    }


    private Inventory createAddAppointmentInventory() {
        HashMap<InvProperties, Object> addAppointmentInventory = appointmentConfig.getAddAppointmentInventoryProperties();

        String header = (String) addAppointmentInventory.get(InvProperties.TITLE);
        header = replacePlaceholder(header, date, appointment);
        int size = (int) addAppointmentInventory.get(InvProperties.SIZE);
        Inventory inventory = Bukkit.createInventory(player, size, header);

        HashMap<Items, HashMap<ItemProperties, Object>> properties = (HashMap<Items, HashMap<ItemProperties, Object>>) addAppointmentInventory.get(InvProperties.ITEMS);

        setItem(Items.BACK, inventory, properties);

        setItem(Items.setStatus, inventory, properties);
        setItem(Items.setTime, inventory, properties);
        setItem(Items.setName, inventory, properties);
        setItem(Items.setHeader, inventory, properties);
        setItem(Items.setDescription, inventory, properties);
        setItem(Items.CONFIRM, inventory, properties);

        return inventory;
    }

    public void setItem(Items itemName, Inventory inventory, HashMap<Items, HashMap<ItemProperties, Object>> properties) {

        HashMap<ItemProperties, Object> itemProperties = properties.get(itemName);
        ItemStack item = createItem(itemProperties, date, appointment);
        items.put(itemName, item);
        inventory.setItem((int) itemProperties.get(ItemProperties.SLOT), item);

    }
}
