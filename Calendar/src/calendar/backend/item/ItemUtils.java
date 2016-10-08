package calendar.backend.item;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
	
	public ItemStack changeName(ItemStack item, String name) {
		
		if(item != null) {
			ItemMeta meta = item.getItemMeta();
				if(name != null) {
					meta.setDisplayName(name);
				}
		}
			return item;
	}
	
	
	public ItemStack changeMaterial(ItemStack item, Material material) {
		
		if(item != null) {
			if(material != null) {
				item.setType(material);
			}
		}
			return item;
	}
	
	public ItemStack changeId(ItemStack item, short id) {
		
		if(item != null) {
			if(id != 0) {
				item.setDurability(id);
			}
		}
			return item;
	}
	
	public ItemStack changeAmount(ItemStack item, int amount) {
		
		if(item != null){
			if(amount != 0) {
				item.setAmount(amount);
			}
		}
			return item;
	}
	
	public ItemStack changeLore(ItemStack item, List<String> lore) {
		
		if(item != null) {
			ItemMeta meta = item.getItemMeta();
				if(lore != null) {
					meta.setLore(lore);
				}
		}
			return item;
	}

}
