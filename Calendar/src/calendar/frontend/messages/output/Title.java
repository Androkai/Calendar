package calendar.frontend.messages.output;

import org.bukkit.entity.Player;

import calendar.frontend.messages.output.ReflectionUtils.VersionType;

public class Title extends ReflectionUtils {
	
	Player player;
	
	String title;
	String subtitle;
	
	int fadeIn;
	int stay;
	int fadeOut;
	
	public Title(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {

		this.player 	= player;
		
		this.title 		= title;
		this.subtitle 	= subtitle;
		
		this.fadeIn 	= fadeIn;
		this.stay 		= stay;
		this.fadeOut 	= fadeOut;
		
	}
	
	public Title(Player player, String title, String subtitle) {
		this(player, title, subtitle, 1, 2, 1);
	}
	
	public void send() {
		try {
			final Class<?> PacketPlayOutTitle = getVersionClass("PacketPlayOutTitle", VersionType.MINECRAFT);
			final Class<?> EnumTitleAction = PacketPlayOutTitle.getClasses()[0];
			final Class<?> IChatBaseComponent = getVersionClass("IChatBaseComponent", VersionType.MINECRAFT);
			
				Object titlePacket = PacketPlayOutTitle.getConstructor(EnumTitleAction, IChatBaseComponent, int.class, int.class, int.class)
										.newInstance(EnumTitleAction.getEnumConstants()[0], toChatComponent(title), fadeIn, stay, fadeOut);
				Object subtitlePacket = PacketPlayOutTitle.getConstructor(EnumTitleAction, IChatBaseComponent, int.class, int.class, int.class)
										.newInstance(EnumTitleAction.getEnumConstants()[1], toChatComponent(subtitle), fadeIn, stay, fadeOut);
				
					sendPacket(player, titlePacket);
					sendPacket(player, subtitlePacket);
				
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

}
