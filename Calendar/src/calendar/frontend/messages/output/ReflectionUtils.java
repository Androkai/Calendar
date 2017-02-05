package calendar.frontend.messages.output;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import calendar.frontend.messages.output.ReflectionUtils.VersionType;

public class ReflectionUtils {
	
	public enum VersionType {CRAFTBUKKIT, MINECRAFT};
	public String getVersionPath(VersionType type) {
		String path = Bukkit.getServer().getClass().getPackage().getName();
		String version = path.substring(path.lastIndexOf(".") + 1);
		
			switch(type) {
				case CRAFTBUKKIT:
					path = "org.bukkit.craftbukkit." + version;
				break;
				
				case MINECRAFT:	
					path = "net.minecraft.server." + version;
				break;
			}
			
		return path;
	}
	
	public Class<?> getVersionClass(String name, VersionType type) throws ClassNotFoundException {
		String path = getVersionPath(type);
			return Class.forName(path + "." + name);
	}
	
	public Object getPlayerConnection(Player player) {
		try {
			final Class<?> CraftPlayer = getVersionClass("entity.CraftPlayer", VersionType.CRAFTBUKKIT);
			
				Object craftPlayer = CraftPlayer.cast(player);
				Object entityPlayer = CraftPlayer.getMethod("getHandle").invoke(craftPlayer);
					Field connection = entityPlayer.getClass().getField("playerConnection");
						connection.setAccessible(true);
						
			return connection.get(entityPlayer);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void sendPacket(Player player, Object packet) {
		try {
			final Class<?> PlayerConnection = getVersionClass("PlayerConnection", VersionType.MINECRAFT);
			final Class<?> Packet = getVersionClass("Packet", VersionType.MINECRAFT);
			
				Object connection = getPlayerConnection(player);
				PlayerConnection.getMethod("sendPacket", Packet).invoke(connection, packet);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object toChatComponent(String text) {
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
