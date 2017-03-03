package com.github.tonedahonda.calendar.backend.configs;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.item.ItemProperties;
import com.github.tonedahonda.calendar.backend.item.Items;
import com.github.tonedahonda.calendar.frontend.gui.InvProperties;
import org.apache.commons.lang.LocaleUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarConfig extends Config {

    String defaultPath;
    String path;

    public CalendarConfig() {
        super(Main.instance.getDataFolder(), "CalendarConfig.yml");

        config = super.loadConfig();
    }

    public TimeZone getTimeZone() {
        String timezone = getString("timeZone");

        if (timezone.equals("default")) {
            return TimeZone.getDefault();
        } else {
            return TimeZone.getTimeZone(timezone);
        }
    }

    public Locale getLocal() {
        String locale = getString("locale");

        if (locale.equals("default")) {
            return Locale.getDefault();
        } else {
            return LocaleUtils.toLocale(locale);
        }
    }

    /*
     * Method to get the properties of the calendar out of the config.
     */
    public HashMap<InvProperties, Object> getCalendarProperties() {
        HashMap<InvProperties, Object> properties = new HashMap<InvProperties, Object>();

        String title;
        int size;
        HashMap<Items, HashMap<ItemProperties, Object>> items = new HashMap<Items, HashMap<ItemProperties, Object>>();

		/*
         * Gets the properties of the calendar out of the configuration file
		 */

        properties.put(InvProperties.HOLDER, null);

        //Calendar
        path = "title";
        title = getFormatString(path);
        properties.put(InvProperties.TITLE, title);

        path = "size";
        size = getInteger(path);
        properties.put(InvProperties.SIZE, size);

        //Items
        defaultPath = "items.";

        //Today
        path = defaultPath + "today.";
        items.put(Items.TODAY, getItemProperties(path));

        //Day
        path = defaultPath + "day.";
        items.put(Items.DAY, getItemProperties(path));

        //Week
        path = defaultPath + "week.";
        items.put(Items.WEEK, getItemProperties(path));

        //Appointment
        path = defaultPath + "appointment.";
        items.put(Items.APPOINTMENT, getItemProperties(path));

        //Next month
        path = defaultPath + "nextMonth.";
        items.put(Items.nextMonth, getItemProperties(path));

        //Previous month
        path = defaultPath + "previousMonth.";
        items.put(Items.previousMonth, getItemProperties(path));

        //Exit
        path = defaultPath + "exit.";
        items.put(Items.EXIT, getItemProperties(path));

        properties.put(InvProperties.ITEMS, items);

        return properties;
    }


}
