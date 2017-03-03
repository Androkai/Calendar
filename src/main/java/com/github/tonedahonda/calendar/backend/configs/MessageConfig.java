package com.github.tonedahonda.calendar.backend.configs;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.frontend.messages.Message;
import com.github.tonedahonda.calendar.frontend.messages.MessageHandler.Error;
import com.github.tonedahonda.calendar.frontend.messages.MessageHandler.Notification;
import com.github.tonedahonda.calendar.frontend.messages.MessageHandler.Request;

import java.util.HashMap;
import java.util.List;

public class MessageConfig extends Config {

    public MessageConfig() {
        super(Main.instance.getDataFolder(), "MessageConfig.yml");

        config = super.loadConfig();
    }

    public String getPluginPrefix() {
        String prefix = getFormatString("prefix");

        return prefix;
    }

    public List<String> getHelp() {
        return getListFormatString("help");
    }

    public HashMap<Error, Message> getErrors() {
        HashMap<Error, Message> errors = new HashMap<Error, Message>();
        String path = "errors.";

        errors.put(Error.NOPERMISSIONS, getCommandString(path + "noPermissions"));
        errors.put(Error.NOTPLAYER, getCommandString(path + "notPlayer"));
        errors.put(Error.UNKNOWNCOMMAND, getCommandString(path + "unknownCommand"));

        return errors;
    }

    public HashMap<Notification, Message> getNotifications() {
        HashMap<Notification, Message> notifications = new HashMap<Notification, Message>();
        String path = "notifications.";

        notifications.put(Notification.CONFIGSRELOAD, getCommandString(path + "configsReloaded"));
        notifications.put(Notification.REMINDERENABLE, getCommandString(path + "reminderEnabled"));
        notifications.put(Notification.REMINDERDISABLE, getCommandString(path + "reminderDisabled"));

        return notifications;
    }

    public HashMap<Request, Message> getRequests() {
        HashMap<Request, Message> requests = new HashMap<Request, Message>();
        String path = "requests.";

        requests.put(Request.TIME, getCommandString(path + "time"));
        requests.put(Request.NAME, getCommandString(path + "name"));
        requests.put(Request.HEADER, getCommandString(path + "header"));
        requests.put(Request.DESCRIPTION, getCommandString(path + "description"));

        return requests;
    }

    public Message getCommandString(String path) {
        return new Message(getFormatString(path), getPluginPrefix());
    }
}
