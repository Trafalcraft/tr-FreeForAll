package com.trafalcraft.meleGeneral.pannel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CreateOptionInventory {

	public static void loadInventoryOption(Player p, String arene){
		
		Inventory inv = Bukkit.createInventory(p, 36, arene + ";optionlist;pvp");
		
		ItemStack item = new ItemStack(Material.ENDER_PEARL);		
		ItemMeta i = item.getItemMeta();
		i.setDisplayName("teleportation");
		item.setItemMeta(i);
		
		inv.setItem(11, item);
		
		ItemStack item2 = new ItemStack(Material.BOOK);		
		ItemMeta i2 = item2.getItemMeta();
		i2.setDisplayName("information");
		item2.setItemMeta(i2);
		
		inv.setItem(15, item2);
		
		ItemStack item4 = new ItemStack(Material.ARROW);		
		ItemMeta i4 = item4.getItemMeta();
		i4.setDisplayName("back");
		item4.setItemMeta(i4);
		
		inv.setItem(31, item4);
		
		p.openInventory(inv);
	}
}
