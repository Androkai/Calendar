package backend.item;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCreator {
	
	ItemStack item;
	
	/*
	 * Creates an ItemStack with the given parameters.
	 */
	public ItemCreator(Material material,  int amount, short id, String name, List<String> lore){
		
		// Creates a new ItemStack into the item variable.
		item = new ItemStack(material, amount, id);
		
		// Gets the ItemMeta
			ItemMeta itemMeta = item.getItemMeta();
			
			// Sets the displayName of the ItemMeta
			itemMeta.setDisplayName(name);
			
			// Sets the lore of the ItemMeta
			itemMeta.setLore(lore);
			
		// Sets the ItemMeta to the ItemStack 'item'
		item.setItemMeta(itemMeta);
	}
	
	public ItemCreator(Material material, int amount, short id, String name){
		this(material, amount, id, name, null);
	}
	
	public ItemCreator(Material material, short id, String name, List<String> lore){
		this(material, 1, id, name, lore);
	}
	
	public ItemCreator(Material material, String name){
		this(material, 1, (short) 0, name, null);
	}
	
	/*
	 * Returns the created ItemStack.
	 */
	public ItemStack getItem(){
		return item;
	}

}
