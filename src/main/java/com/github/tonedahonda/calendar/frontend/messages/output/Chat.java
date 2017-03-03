package com.github.tonedahonda.calendar.frontend.messages.output;

import org.bukkit.entity.Player;

public class Chat extends ReflectionUtils {

    public enum HoverAction {
        SHOW_TEXT(0);
        private final int value;

        HoverAction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    ;

    public enum ClickAction {
        OPEN_URL(0), OPEN_FILE(1), RUN_COMMAND(2), SUGGEST_COMMAND(3);
        private final int value;

        private ClickAction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    ;

    Player player;
    String message;

    HoverAction hoverAction;
    String hoverText;

    ClickAction clickAction;
    String clickText;

    public Chat(Player player, String message, HoverAction hoverAction, String hoverText, ClickAction clickAction, String clickText) {
        this.player = player;
        this.message = message;

        this.hoverAction = hoverAction;
        this.hoverText = hoverText;

        this.clickAction = clickAction;
        this.clickText = clickText;

    }

    public Chat(Player player, String message) {
        this(player, message, null, null, null, null);
    }

    public void send() {
        try {
            final Class<?> IChatBaseComponent = getVersionClass("IChatBaseComponent", VersionType.MINECRAFT);
            final Class<?> ChatModifier = getVersionClass("ChatModifier", VersionType.MINECRAFT);
            final Class<?> ChatHoverable = getVersionClass("ChatHoverable", VersionType.MINECRAFT);
            final Class<?> EnumHoverAction = ChatHoverable.getClasses()[0];
            final Class<?> ChatClickable = getVersionClass("ChatClickable", VersionType.MINECRAFT);
            final Class<?> EnumClickAction = ChatClickable.getClasses()[0];
            final Class<?> PacketPlayOutChat = getVersionClass("PacketPlayOutChat", VersionType.MINECRAFT);

            Object comp = toChatComponent(message);
            Object chatModifier = ChatModifier.getConstructor().newInstance();

            if (hoverAction != null) {
                Object chatHoverable = ChatHoverable.getConstructor(EnumHoverAction, IChatBaseComponent).newInstance(EnumHoverAction.getEnumConstants()[hoverAction.getValue()], toChatComponent(hoverText));
                ChatModifier.getMethod("setChatHoverable", ChatHoverable).invoke(chatModifier, chatHoverable);
            }

            if (clickAction != null) {
                Object chatClickable = ChatClickable.getConstructor(EnumClickAction, String.class).newInstance(EnumClickAction.getEnumConstants()[clickAction.getValue()], clickText);
                ChatModifier.getMethod("setChatClickable", ChatClickable).invoke(chatModifier, chatClickable);
            }

            IChatBaseComponent.getMethod("setChatModifier", ChatModifier).invoke(comp, chatModifier);

            Object packet = PacketPlayOutChat.getConstructor(IChatBaseComponent, byte.class).newInstance(comp, (byte) 0);
            sendPacket(player, packet);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
