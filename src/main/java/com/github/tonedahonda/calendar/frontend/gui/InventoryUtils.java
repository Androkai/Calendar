package com.github.tonedahonda.calendar.frontend.gui;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.appointments.Appointment;
import com.github.tonedahonda.calendar.backend.configs.CalendarConfig;
import com.github.tonedahonda.calendar.backend.dateTime.DateTime;
import com.github.tonedahonda.calendar.backend.dateTime.DateTimeUtils;
import com.github.tonedahonda.calendar.backend.item.EnchantmentProperties;
import com.github.tonedahonda.calendar.backend.item.ItemCreator;
import com.github.tonedahonda.calendar.backend.item.ItemProperties;
import com.github.tonedahonda.calendar.backend.item.Items;
import com.github.tonedahonda.calendar.frontend.messages.MessageUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryUtils extends MessageUtils {

    CalendarConfig calendarConfig = Main.getCalendarConfig();
    DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();

    DateTime dateTime;
    Appointment appointment;
    HashMap<Items, Object> items;

    public InventoryUtils(DateTime dateTime, Appointment appointment, HashMap<Items, Object> items) {

        this.dateTime = dateTime;
        this.appointment = appointment;
        this.items = items;
    }

    public void setItem(Items itemName, Inventory inventory, HashMap<Items, HashMap<ItemProperties, Object>> properties) {

        HashMap<ItemProperties, Object> itemProperties = properties.get(itemName);
        ItemStack item = createItem(itemProperties, dateTime, appointment);
        items.put(itemName, item);
        inventory.setItem((int) itemProperties.get(ItemProperties.SLOT), item);

    }

    public ItemStack createItem(HashMap<ItemProperties, Object> itemProperties, DateTime date, Appointment appointment) {
        itemProperties = new HashMap<>(itemProperties);
        ItemStack item = null;

        if ((boolean) itemProperties.get(ItemProperties.TOGGLE)) {
            String name = (String) itemProperties.get(ItemProperties.NAME);
            name = replacePlaceholder(name, date, appointment);

            List<String> lore = (List<String>) itemProperties.get(ItemProperties.LORE);
            if (lore != null) {
                lore = new ArrayList<>(lore);
                for (String line : lore) {
                    int index = lore.indexOf(line);
                    line = replacePlaceholder(line, date, appointment);
                    lore.set(index, line);
                }
            }

            Material material = (Material) itemProperties.get(ItemProperties.MATERIAL);

            int amount = Integer.valueOf(replacePlaceholder((String) itemProperties.get(ItemProperties.AMOUNT), date, appointment));

            short id = (short) itemProperties.get(ItemProperties.ID);

            item = new ItemCreator(material, amount, (short) id, name, lore).getItem();

            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(name);
            meta.setLore(lore);

            HashMap<EnchantmentProperties, Object> enchantment = (HashMap<EnchantmentProperties, Object>) itemProperties.get(ItemProperties.ENCHANTMENT);
            if (enchantment != null) {
                meta.addEnchant(
                        Enchantment.getByName((String) enchantment.get(EnchantmentProperties.TYPE)),
                        (int) enchantment.get(EnchantmentProperties.STRENGTH),
                        (boolean) enchantment.get(EnchantmentProperties.IGNOREMAX));

                if ((boolean) enchantment.get(EnchantmentProperties.HIDE)) {
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
            }

            item.setItemMeta(meta);
        }

        return item;
    }


}
