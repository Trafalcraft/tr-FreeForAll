package com.trafalcraft.meleGeneral.pannel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.trafalcraft.meleGeneral.file.FileControler;

public class CreateAreneList {

	public static void loadInventoryArena(Player p){
		
		int size = 9;
		
		if(FileControler.getAllFile().size() <= 9){
			size = 9;
		}else if(FileControler.getAllFile().size() <= 18){
			size = 18;
		}else if(FileControler.getAllFile().size() <= 27){
			size = 27;
		}else if(FileControler.getAllFile().size() <= 36){
			size = 36;
		}else if(FileControler.getAllFile().size() <= 45){
			size = 45;
		}else if(FileControler.getAllFile().size() <= 54){
			size = 54;
		}
		
		Inventory inv = Bukkit.createInventory(p, size, "arenalist;null;pvp");
		
		for(String file: FileControler.getAllName()){
			
			ItemStack item = new ItemStack(Material.SIGN);		
			ItemMeta i = item.getItemMeta();
			i.setDisplayName(file);
			item.setItemMeta(i);
			
			inv.addItem(item);
		}
		
		p.openInventory(inv);
	}
}
