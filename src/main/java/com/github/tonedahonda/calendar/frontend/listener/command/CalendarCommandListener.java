package com.github.tonedahonda.calendar.frontend.listener.command;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.configs.CalendarConfig;
import com.github.tonedahonda.calendar.backend.configs.MessageConfig;
import com.github.tonedahonda.calendar.backend.dateTime.DateTime;
import com.github.tonedahonda.calendar.backend.storage.StorageUtils;
import com.github.tonedahonda.calendar.frontend.gui.calendar.Calendar;
import com.github.tonedahonda.calendar.frontend.messages.MessageHandler;
import com.github.tonedahonda.calendar.frontend.messages.MessageHandler.Error;
import com.github.tonedahonda.calendar.frontend.messages.MessageHandler.Notification;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.time.LocalDateTime;

public class CalendarCommandListener extends CommandListener {

    CalendarConfig calendarConfig = Main.getCalendarConfig();
    MessageConfig commandConfig = Main.getMessageConfig();
    StorageUtils storageUtils = Main.getStorageUtils();

    public CalendarCommandListener(CommandSender sender, Command command, String label, String[] args) {
        super(sender);

        if (command.getName().equalsIgnoreCase("calendar")) {

            if (isPlayer(sender)) {
                Player player = (Player) sender;
                MessageHandler handler = new MessageHandler(player);

                switch (args.length) {

                    //Calendar
                    case 0:
                        if (hasPermission("calendar.open")) {
                            player.openInventory(createCalendar(player));
                        }
                        break;

                    case 1:
                        switch (args[0]) {

                            //Help
                            case "help":
                                sendHelp();
                                break;

                            //Info
                            case "info":
                                sendInfo(player);
                                break;

                            //Reload
                            case "reload":
                                if (hasPermission("calendar.reload")) {
                                    reloadConfigs();
                                    handler.sendNotification(Notification.CONFIGSRELOAD);
                                }
                                break;

                            default:
                                handler.sendError(Error.UNKNOWNCOMMAND);
                        }
                        break;

                    default:
                        handler.sendError(Error.UNKNOWNCOMMAND);
                }
            }
        }

    }

    private Inventory createCalendar(Player player) {
        LocalDateTime timeSystem = LocalDateTime.now(calendarConfig.getTimeZone().toZoneId());
        DateTime date = new DateTime(timeSystem);

        Calendar calendar = new Calendar(player, date);
        storageUtils.storageCalendar(player, calendar);

        return calendar.getInventory();
    }

    private void sendInfo(CommandSender sender) {

        Plugin Calendar = Main.instance;
        String prefix = messageConfig.getPluginPrefix();
        String ver = Calendar.getDescription().getVersion();
        String author = Calendar.getDescription().getAuthors().get(0);
        String web = Calendar.getDescription().getWebsite();

        sender.sendMessage("");
        sender.sendMessage("                 " + prefix);
        sender.sendMessage("                 " + ChatColor.BLUE + ChatColor.ITALIC + "Version: " + ChatColor.WHITE + ChatColor.ITALIC + ver);
        sender.sendMessage("          " + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Author: " + ChatColor.GOLD + ChatColor.ITALIC + author + ChatColor.WHITE);
        sender.sendMessage("");
        sender.sendMessage(" " + ChatColor.ITALIC + web);
        sender.sendMessage("");

    }

    private void reloadConfigs() {

        Main.getCalendarConfig().reloadConfig();
        Main.getMessageConfig().reloadConfig();
        Main.getAppointmentConfig().reloadConfig();
        Main.getAppointmentDataConfig().reloadConfig();

    }


}
