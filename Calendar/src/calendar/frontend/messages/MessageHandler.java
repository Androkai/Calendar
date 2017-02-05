package calendar.frontend.messages;

import java.awt.im.InputMethodRequests;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import calendar.backend.Main;
import calendar.backend.configs.MessageConfig;
public class MessageHandler {
	
	MessageConfig config = Main.getMessageConfig();
	
	Player player;
	
	public MessageHandler(Player player) {
		this.player = player;
	}
	
	public static void broadcastMessage(String message, OutputType type) {
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			MessageHandler handler = new MessageHandler(player);
				handler.sendMessage(message, type);
		}
	}
	public static void broadcastMessage(Message message) {
		broadcastMessage(message.toString(), message.getMessageMeta().getOutputType());
	}
	
	// Method to send a message of a given OutputType.
	public enum OutputType {CHAT, TITLE, SUBTITLE, ACTIONBAR, BOSSBAR}
	public void sendMessage(String message, OutputType type) {
		
		switch(type) {
			case CHAT: sendChat(message);
			break;
			
			case TITLE: sendTitle(message, "");
			break;
			
			case SUBTITLE: sendTitle("", message);
			break;
			
			case ACTIONBAR: sendActionbar(message);
			break;
			
			case BOSSBAR: sendBossbar(message);
			break;
		}
	}
	public void sendMessage(Message message) {
		sendMessage(message.toString(), message.getMessageMeta().getOutputType());
	}
	
	// Method to send a Chat message.
	private void sendChat(String message) {
		player.sendMessage(message);
	}
	
	// Method to send an Actionbar with the default parameters.
	private void sendActionbar(String message) {
		new Actionbar(player, message).send();
	}
	
	// Method to send a Title with the default parameters.
	private void sendTitle(String title, String subtitle) {
		new Title(player, title, subtitle).send();
	}

	// Method to send a Bossbar with the default parameters.
	private void sendBossbar(String message) {
		throw new UnsupportedOperationException("Not supported yet!");
	}
	
	public enum Error {UNKNOWNCOMMAND, NOTPLAYER, NOPERMISSIONS}
	public void sendError(Error error) {
		HashMap<Error, Message> errors = config.getErrors();
			Message message = errors.get(error);
				
				sendMessage(message.toString(), message.getMessageMeta().getOutputType());
	}
	
	public enum Notification {CONFIGSRELOAD, REMINDERENABLE, REMINDERDISABLE}
	public void sendNotification(Notification notification) {
		HashMap<Notification, Message> notifications = config.getNotifications();
			Message message = notifications.get(notification);
				
				sendMessage(message.toString(), message.getMessageMeta().getOutputType());
	}
	
	public enum Request {TIME, NAME, HEADER, DESCRIPTION}
	public void sendInputRequest(Request request) {
		HashMap<Request, Message> requests = config.getRequests();
			Message message = requests.get(request);
				
				sendMessage(message.toString(), message.getMessageMeta().getOutputType());
	}

}
