package com.github.tonedahonda.calendar.backend;

import com.github.tonedahonda.calendar.backend.appointments.AppointmentUtils;
import com.github.tonedahonda.calendar.backend.configs.AppointmentConfig;
import com.github.tonedahonda.calendar.backend.configs.AppointmentDataConfig;
import com.github.tonedahonda.calendar.backend.configs.CalendarConfig;
import com.github.tonedahonda.calendar.backend.configs.MessageConfig;
import com.github.tonedahonda.calendar.backend.dateTime.DateTimeUtils;
import com.github.tonedahonda.calendar.backend.item.ItemUtils;
import com.github.tonedahonda.calendar.backend.placeholder.Placeholder;
import com.github.tonedahonda.calendar.backend.storage.Storage;
import com.github.tonedahonda.calendar.backend.storage.StorageUtils;
import com.github.tonedahonda.calendar.frontend.listener.chat.ChatEventCaller;
import com.github.tonedahonda.calendar.frontend.listener.command.CommandEventCaller;
import com.github.tonedahonda.calendar.frontend.listener.inventory.InventoryEventCaller;
import com.github.tonedahonda.calendar.frontend.notifications.Reminder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    public static Main instance;
    public static UUID sUUID = UUID.fromString("bb5fb548-4dff-4eb1-b9f6-f618be8ed6e9");
    public static HashMap<Player, Storage> storages = new HashMap<Player, Storage>();

    private static Reminder reminder;

    private static CalendarConfig calendarConfig;
    private static AppointmentConfig appointmentConfig;
    private static AppointmentDataConfig appointmentDataConfig;
    private static MessageConfig messageConfig;

    private static DateTimeUtils dateTimeUtils;
    private static StorageUtils storageUtils;
    private static ItemUtils itemUtils;
    private static AppointmentUtils appointmentUtils;

    /*
     * (non-Javadoc)
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    public void onEnable() {
        instance = this;

        registerObjects();
        hook();
        registerEvents();
        registerCommands();
    }

    /*
     * (non-Javadoc)
     * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
     */
    public void onDisable() {
    }

    /*
     * Method to register all Objects needed while runtime.
     */
    private void registerObjects() {

        calendarConfig = new CalendarConfig();
        appointmentConfig = new AppointmentConfig();
        appointmentDataConfig = new AppointmentDataConfig();
        messageConfig = new MessageConfig();

        dateTimeUtils = new DateTimeUtils();
        storageUtils = new StorageUtils();
        itemUtils = new ItemUtils();
        appointmentUtils = new AppointmentUtils();

        reminder = new Reminder();

    }

    /*
     * Method to register all events used by the plugin.
     */
    private void registerEvents() {

        Bukkit.getPluginManager().registerEvents(new InventoryEventCaller(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEventCaller(), this);

    }

    /*
     * Method to register all commands.
     */
    private void registerCommands() {

        CommandEventCaller commandCaller = new CommandEventCaller();

        getCommand("calendar").setExecutor(commandCaller);
        getCommand("appointment").setExecutor(commandCaller);

    }

    /*
     * Method to register the placeholder.
     */
    private void hook() {

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Bukkit.getLogger().info("Loading Placeholders...");
            new Placeholder(this).hook();
        }

    }

    /*
     * Getters for the Objects
     */
    public static Reminder getReminder() {
        return reminder;
    }

    public static CalendarConfig getCalendarConfig() {
        return calendarConfig;
    }

    public static AppointmentConfig getAppointmentConfig() {
        return appointmentConfig;
    }

    public static AppointmentDataConfig getAppointmentDataConfig() {
        return appointmentDataConfig;
    }

    public static MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public static DateTimeUtils getDateTimeUtils() {
        return dateTimeUtils;
    }

    public static StorageUtils getStorageUtils() {
        return storageUtils;
    }

    public static ItemUtils getItemUtils() {
        return itemUtils;
    }

    public static AppointmentUtils getAppointmentUtils() {
        return appointmentUtils;
    }

}
