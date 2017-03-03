package com.github.tonedahonda.calendar.frontend.messages;

import com.github.tonedahonda.calendar.frontend.messages.MessageHandler.OutputType;

public class Message {

    String prefix;
    String message;

    MessageMeta meta;

    public Message(String message, String prefix) {
        this.prefix = prefix;
        this.message = message;
        this.meta = readMeta();
    }

    public Message(String message) {
        this(message, "");
    }

    public Message() {
        this(null, null);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageMeta getMessageMeta() {
        return meta;
    }

    public String replaceAll(String regex, String replacement) {
        return replacement != null ? message.replaceAll(regex, replacement) : message.replaceAll(regex, "");
    }

    private MessageMeta readMeta() {
        MessageMeta meta = new MessageMeta();

        if (message.contains("{") && message.contains("}")) {
            String comp = message.substring(message.lastIndexOf("{"), message.lastIndexOf("}") + 1);
            message = message.replace(comp, "");
            comp = comp.substring(1, comp.length() - 1);

            String[] data = comp.split(";");
            if (data.length > 0) {
                for (String value : data) {

                    if (value.startsWith("Output")) {
                        OutputType output = OutputType.valueOf(value.split(":")[1].toUpperCase());
                        meta.setOutputType(output);
                    }

                    if (value.startsWith("Prefix")) {
                        boolean withPrefix = Boolean.valueOf(value.split(":")[1].toLowerCase());
                        meta.setWithPrefix(withPrefix);
                    }


                }
            }
        }

        return meta;
    }

    @Override
    public String toString() {
        return meta.isWithPrefix() ? prefix + message : message;
    }
}
