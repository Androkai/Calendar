package frontend.listener.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import backend.main.main;
import frontend.gui.Storage;

public class Click {
	
	public Click(InventoryClickEvent event){
		
		Player player = (Player) event.getWhoClicked();
		
		Inventory inventory = event.getClickedInventory();
		ItemStack item = event.getCurrentItem();
		
		/*
		 * Checks if the player has a calendar inventory.
		 */
		if(main.storages.containsKey(player)){
			/*
			 * Cancels the event.
			 */
			event.setCancelled(true);
			
			/*
			 * Gets the storage of the player.
			 */
			Storage storage = main.storages.get(player);
			
			
		}
	}

}
