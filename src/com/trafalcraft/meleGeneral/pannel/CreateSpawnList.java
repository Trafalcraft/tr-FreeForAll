package com.trafalcraft.meleGeneral.pannel;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.trafalcraft.meleGeneral.file.FileControler;

public class CreateSpawnList {
	
	public static void loadInventorySpawn(Player p, String arene){
		
		int size = 9;
		
		if(FileControler.getArena(arene).getInt("nbrspawn") <= 9){
			size = 9;
		}else if(FileControler.getArena(arene).getInt("nbrspawn") <= 18){
			size = 18;
		}else if(FileControler.getArena(arene).getInt("nbrspawn") <= 27){
			size = 27;
		}else if(FileControler.getArena(arene).getInt("nbrspawn") <= 36){
			size = 36;
		}else if(FileControler.getArena(arene).getInt("nbrspawn") <= 45){
			size = 45;
		}else if(FileControler.getArena(arene).getInt("nbrspawn") <= 54){
			size = 54;
		}
		
		Inventory inv = Bukkit.createInventory(p, size, arene +  ";spawnlist;pvp");
		
		
		for(int i2 = 0; i2 < FileControler.getArena(arene).getInt("nbrspawn"); i2++){
			
			ItemStack item = new ItemStack(Material.BED);		
			ItemMeta i = item.getItemMeta();
			i.setDisplayName("spawn " + (i2+1));
			item.setItemMeta(i);
			
			List<String> lore = new ArrayList<String>();
			lore.add(FileControler.getArena(arene).getInt("spawn." + i2 + ".x") + "");
			lore.add(FileControler.getArena(arene).getInt("spawn." + i2 + ".y") + "");
			lore.add(FileControler.getArena(arene).getInt("spawn." + i2 + ".z") + "");
			i.setLore(lore);
			
			inv.addItem(item);
		}
		
		p.openInventory(inv);
		
	}
}
