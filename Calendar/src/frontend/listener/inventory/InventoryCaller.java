package frontend.listener.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryCaller implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		new Click(event);
		
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		
		new Close(event);
		
	}

}
