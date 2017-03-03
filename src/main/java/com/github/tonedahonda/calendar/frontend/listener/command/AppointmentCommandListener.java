package com.github.tonedahonda.calendar.frontend.listener.command;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.configs.AppointmentDataConfig;
import com.github.tonedahonda.calendar.backend.configs.MessageConfig;
import com.github.tonedahonda.calendar.backend.dateTime.DateTimeUtils;
import com.github.tonedahonda.calendar.frontend.messages.MessageHandler;
import com.github.tonedahonda.calendar.frontend.messages.MessageHandler.Error;
import com.github.tonedahonda.calendar.frontend.messages.MessageHandler.Notification;
import com.github.tonedahonda.calendar.frontend.notifications.Reminder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AppointmentCommandListener extends CommandListener {

    AppointmentDataConfig appointmentDataConfig = Main.getAppointmentDataConfig();
    MessageConfig commandConfig = Main.getMessageConfig();
    DateTimeUtils dateTimeUtils = Main.getDateTimeUtils();

    public AppointmentCommandListener(CommandSender sender, Command command, String label, String[] args) {
        super(sender);

        if (command.getName().equalsIgnoreCase("appointment")) {
            if (isPlayer(sender)) {
                Player player = (Player) sender;
                MessageHandler handler = new MessageHandler(player);

                switch (args.length) {
                    case 1:
                        switch (args[0]) {
                            case "info":
                                player.sendMessage(ChatColor.GRAY + "File size: " + appointmentDataConfig.getByteSize() + " bytes");
                                break;

                            case "reminder":
                                Reminder reminder = Main.getReminder();

                                if (player.hasPermission("Calendar.appointment.reminder")) {
                                    switch (String.valueOf(reminder.isRunning())) {
                                        case "false":
                                            reminder.enable();
                                            handler.sendNotification(Notification.REMINDERENABLE);
                                            break;

                                        case "true":
                                            reminder.disable();
                                            handler.sendNotification(Notification.REMINDERDISABLE);
                                            break;
                                    }
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

}
