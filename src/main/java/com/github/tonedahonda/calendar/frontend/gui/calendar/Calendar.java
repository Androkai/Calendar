package com.github.tonedahonda.calendar.frontend.gui.calendar;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.appointments.Appointment;
import com.github.tonedahonda.calendar.backend.configs.AppointmentConfig;
import com.github.tonedahonda.calendar.backend.configs.AppointmentDataConfig;
import com.github.tonedahonda.calendar.backend.configs.CalendarConfig;
import com.github.tonedahonda.calendar.backend.dateTime.DateTime;
import com.github.tonedahonda.calendar.backend.dateTime.DateTimeUtils;
import com.github.tonedahonda.calendar.backend.item.*;
import com.github.tonedahonda.calendar.frontend.gui.InvProperties;
import com.github.tonedahonda.calendar.frontend.gui.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Calendar extends InventoryUtils {

    CalendarConfig calendarConfig = Main.getCalendarConfig();
    AppointmentConfig appointmentConfig = Main.getAppointmentConfig();
    AppointmentDataConfig appointmentDataConfig = Main.getAppointmentDataConfig();
    DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();
    ItemUtils itemUtils = Main.getItemUtils();

    Player player;
    DateTime date;
    LocalDateTime timeSystem;

    Inventory inventory;
    static HashMap<Items, Object> items = new HashMap<Items, Object>();

    public Calendar(Player player, DateTime dateTime) {
        super(dateTime, null, items);

        this.player = player;
        this.date = new DateTime(dateTime);
        this.timeSystem = dateTime.toLocalDateTime();

        this.inventory = createCalendarInventory(player, dateTime, timeSystem);

    }

    /*
     * Getters for the calendar parameters
     */
    public DateTime getDateTime() {
        return date;
    }

    public LocalDateTime getTimeSystem() {
        return timeSystem;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public HashMap<Items, Object> getItems() {
        return items;
    }


    /*
     * Method to create the calendar inventory.
     */
    private Inventory createCalendarInventory(Player player, DateTime dateTime, LocalDateTime timeSystem) {
        dateTime = new DateTime(dateTime);

        HashMap<InvProperties, Object> calendarProperties = calendarConfig.getCalendarProperties();

        LocalDateTime firstDayOfMonth = LocalDateTime.of((int) dateTime.getYear(), (int) dateTime.getMonth(), 1, 1, 1);
        int firstWeekDay = firstDayOfMonth.getDayOfWeek().getValue();
        int weeksThisMonth = (int) Math.ceil((double) ((dateTimeUtils.getCurrentMonthMax(timeSystem) + firstWeekDay)) / (double) 7);
        int weeksThisMonthSlot = (int) Math.ceil((double) ((dateTimeUtils.getCurrentMonthMax(timeSystem) + firstWeekDay) - 1) / (double) 7);

        // Creates an empty inventory with the size of the calendar
        int size = getSize(weeksThisMonthSlot, (int) calendarProperties.get(InvProperties.SIZE));
        String name = (replacePlaceholder((String) calendarProperties.get(InvProperties.TITLE), dateTime, null));
        Inventory inventory = Bukkit.createInventory(null, size, name);

        @SuppressWarnings("unchecked")
        HashMap<Items, HashMap<ItemProperties, Object>> itemProperties = (HashMap<Items, HashMap<ItemProperties, Object>>) calendarProperties.get(InvProperties.ITEMS);

        HashMap<ItemStack, DateTime> dayItems = new HashMap<ItemStack, DateTime>();
        HashMap<ItemStack, DateTime> weekItems = new HashMap<ItemStack, DateTime>();

        // Variables for the dayOfMonth and weekOfMonth while creating the calendar.
        int dayOfMonth = 1;
        int weekOfMonth = 1;

        // Declares the first slots of the day and week item.
        int daySlot = (int) firstWeekDay - 1;
        int weekSlot = 7;

        //Goes threw every week of the month.
        //Counts up: weekSlot, daySlot, weekOfMonth
        for (; weekOfMonth <= weeksThisMonth; weekOfMonth++, weekSlot = weekSlot + 9, daySlot = daySlot + 2) {
            dateTime.setWeek(weekOfMonth);
            timeSystem = dateTime.toLocalDateTime();

            // Goes threw each day of a week
            // Counts up: dayOfWeek, dayOfMonth, daySlot
            for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++, dayOfMonth++, daySlot++) {
                dateTime.setDay(dayOfMonth);
                timeSystem = dateTime.toLocalDateTime();

                // Creates the day or today item, adds the appointment properties and sets it into the inventory
                if (dateTime.getDate().isNow()) {
                    ItemStack todayItem = createItem(itemProperties.get(Items.TODAY), dateTime, null);
                    todayItem = addAllAppointmentPropertiesToItem(player.getUniqueId(), dateTime, timeSystem, todayItem, itemProperties);

                    inventory.setItem(daySlot, todayItem);
                    dayItems.put(todayItem, new DateTime(dateTime));
                    items.put(Items.TODAY, todayItem);
                } else {
                    ItemStack dayItem = createItem(itemProperties.get(Items.DAY), dateTime, null);
                    dayItem = addAllAppointmentPropertiesToItem(player.getUniqueId(), dateTime, timeSystem, dayItem, itemProperties);

                    inventory.setItem(daySlot, dayItem);
                    dayItems.put(dayItem, new DateTime(dateTime));
                }

                // Checks if it's the end of the week.
                if (isEndOfWeek(dateTime, daySlot)) {
                    daySlot++;
                    dayOfMonth++;
                    break;
                }

                // Checks if it's the end of the month.
                if (isEndOfMonth(dateTime, timeSystem)) {
                    weekOfMonth = weeksThisMonth + 1;
                    dayOfWeek = 7 + 1;
                    break;
                }

            }


            // Creates the week item and sets it into the week slot.
            ItemStack weekItem = createItem(itemProperties.get(Items.WEEK), dateTime, null);
            inventory.setItem(weekSlot, weekItem);
            weekItems.put(weekItem, new DateTime(dateTime));

        }
        // Add the items of the calendar to the items HashMap
        items.put(Items.DAY, dayItems);
        items.put(Items.WEEK, weekItems);


        setItem(Items.nextMonth, inventory, itemProperties);
        setItem(Items.previousMonth, inventory, itemProperties);
        setItem(Items.EXIT, inventory, itemProperties);

        return inventory;
    }

    /*
     * Method to calculate the needed inventory size for the calendar.
     */
    protected int getSize(int weeks, int minSize) {
        int size = weeks * 9;
        if (size < minSize) {
            size = minSize;
        }
        return size;
    }

    /*
     * Method to check if the given day if the last day of the month.
     */
    protected boolean isEndOfMonth(DateTime date, LocalDateTime timeSystem) {

        if (date.getDay() == dateTimeUtils.getCurrentMonthMax(timeSystem)) {
            return true;
        }

        return false;
    }

    /*
     * Method to check if the given day is the last day of the week.
     */
    protected boolean isEndOfWeek(DateTime date, int daySlot) {

        if (date.getWeek() == 1) {
            if (daySlot >= (7 - 1)) {
                return true;
            }
        }

        return false;
    }


    /*
     * Method to add an appointment to an item.
     */
    protected ItemStack addAppointmentPropertiesToItem(Appointment appointment, HashMap<ItemProperties, Object> appointmentProperties, ItemStack item, DateTime dateTime, LocalDateTime timeSystem) {
        dateTime = new DateTime(dateTime);

        if (item != null) {
            ItemMeta meta = item.getItemMeta();

            if (!appointment.isDeleted()) {
                String name = (String) appointmentProperties.get(ItemProperties.NAME);
                if (name != null) {
                    name = replacePlaceholder(name, dateTime, appointment);
                    item = itemUtils.changeName(item, name);
                }

                List<String> lore = meta.getLore();
                if (appointmentProperties.get(ItemProperties.LORE) != null) {
                    lore = new ArrayList<String>((List<String>) appointmentProperties.get(ItemProperties.LORE));
                    item = itemUtils.changeLore(item, lore);
                }

                Material material = (Material) appointmentProperties.get(ItemProperties.MATERIAL);
                item = itemUtils.changeMaterial(item, material);

                short id = (short) appointmentProperties.get(ItemProperties.ID);
                item = itemUtils.changeId(item, id);

                String amount = (String) appointmentProperties.get(ItemProperties.AMOUNT);
                if (amount != null) {
                    amount = replacePlaceholder(amount, dateTime, appointment);
                    item = itemUtils.changeAmount(item, Integer.valueOf(amount));
                }

                String prefix;
                prefix = appointmentConfig.getPrefixes().get(Prefixes.HEADER);
                String header = appointment.getHeader();
                if (prefix != null) {
                    header = conact(prefix, header);
                }
                header = replacePlaceholder(header, dateTime, appointment);
                lore.add(header);

                prefix = appointmentConfig.getPrefixes().get(Prefixes.DESCRIPTION);
                List<String> description = appointment.getDescription();
                for (String line : description) {
                    if (prefix != null) {
                        line = conact(prefix, line);
                    }
                    line = replacePlaceholder(line, dateTime, appointment);
                    lore.add(line);
                }

                HashMap<EnchantmentProperties, Object> enchantment = (HashMap<EnchantmentProperties, Object>) appointmentProperties.get(ItemProperties.ENCHANTMENT);
                if (enchantment != null) {
                    meta.addEnchant(
                            Enchantment.getByName((String) enchantment.get(EnchantmentProperties.TYPE)),
                            (int) enchantment.get(EnchantmentProperties.STRENGTH),
                            (boolean) enchantment.get(EnchantmentProperties.IGNOREMAX));

                    if ((boolean) enchantment.get(EnchantmentProperties.HIDE)) {
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                }

                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }

        return item;
    }

    /*
     * Method to add all appointment properties to an item.
     */
    protected ItemStack addAllAppointmentPropertiesToItem(UUID creator, DateTime dateTime, LocalDateTime timeSystem, ItemStack item, HashMap<Items, HashMap<ItemProperties, Object>> calendarItems) {

        for (Appointment appointment : appointmentDataConfig.getAppointmentsFromDate(Main.sUUID, dateTime.getDate())) {
            item = addAppointmentPropertiesToItem(appointment, calendarItems.get(Items.APPOINTMENT), item, dateTime, timeSystem);
        }

        for (Appointment appointment : appointmentDataConfig.getAppointmentsFromDate(creator, dateTime.getDate())) {
            item = addAppointmentPropertiesToItem(appointment, calendarItems.get(Items.APPOINTMENT), item, dateTime, timeSystem);
        }
        return item;
    }

    // Adds to Strings together
    private String conact(String arg1, String arg2) {
        StringBuilder stringBuild = new StringBuilder(arg1);
        stringBuild.append(arg2);
        return stringBuild.toString();
    }

}
