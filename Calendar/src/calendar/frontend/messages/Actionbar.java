package calendar.frontend.messages;

import org.bukkit.entity.Player;

public class Actionbar extends ReflectionUtils{
	
	Player player;
	String text;
	
	int fadeIn;
	int stay;
	int fadeOut;
	
	public Actionbar(Player player, String text, int fadeIn, int stay, int fadeOut) {
		
		this.player = player;
		this.text 	= text;
		
		this.fadeIn 	= fadeIn;
		this.stay 		= stay;
		this.fadeOut 	= fadeOut;
		
	}
	
	public Actionbar(Player player, String text) {
		this(player, text, 1, 2, 1);
	}
	
	public void send() {
		try {
			final Class<?> PacketPlayOutTitle = getVersionClass("PacketPlayOutTitle", VersionType.MINECRAFT);
			final Class<?> EnumTitleAction	  = PacketPlayOutTitle.getClasses()[0];
			final Class<?> IChatBaseComponent = getVersionClass("IChatBaseComponent", VersionType.MINECRAFT);
			
				Object message = toChatComponent(text);
				Object action  = EnumTitleAction.getEnumConstants()[2];
				
					Object packet = PacketPlayOutTitle.getConstructor(EnumTitleAction, IChatBaseComponent, int.class, int.class, int.class).newInstance(action, message, fadeIn, stay, fadeOut);
						sendPacket(player, packet);
					
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clear() {
		try {
			final Class<?> PacketPlayOutTitle = getVersionClass("PacketPlayOutTitle", VersionType.MINECRAFT);
			final Class<?> EnumTitleAction = PacketPlayOutTitle.getClasses()[0];
			final Class<?> IChatBaseComponent = getVersionClass("IChatBaseComponent", VersionType.MINECRAFT);
			
				Object clearPacket = PacketPlayOutTitle.getConstructor(EnumTitleAction, IChatBaseComponent)
										.newInstance(EnumTitleAction.getEnumConstants()[4], null);
				
					sendPacket(player, clearPacket);
				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Object toChatComponent(String text) {
		try {
			final Class<?> IChatBaseComponent = getVersionClass("IChatBaseComponent", VersionType.MINECRAFT);
			final Class<?> ChatSerializer = IChatBaseComponent.getClasses()[0];
			
				Object component = ChatSerializer.getMethod("a", String.class).invoke(ChatSerializer, "{\"text\":\"" + text + "\"}");
			
			return component;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
