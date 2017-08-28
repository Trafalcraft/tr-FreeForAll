package com.trafalcraft.meleGeneral;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Maps;

public class ManageClasses {
	private final static Map<String, ItemStack[]> listChestClass = Maps.newHashMap();
	
	public static void addChestClass(Player p, Location loc, String classeName){
		if(loc.getWorld().getBlockAt(loc).getType() == Material.SIGN || loc.getWorld().getBlockAt(loc).getType() == Material.SIGN_POST || loc.getWorld().getBlockAt(loc).getType() == Material.WALL_SIGN){
			p.getInventory().clear();
			Location chestLoc = new Location(loc.getWorld(), loc.getX(), loc.getY()-2, loc.getZ());
			if(chestLoc.getWorld().getBlockAt(chestLoc).getType() != Material.CHEST && chestLoc.getWorld().getBlockAt(chestLoc).getType() != Material.TRAPPED_CHEST){
				chestLoc.setY(chestLoc.getY()-1);
			}
			if(chestLoc.getWorld().getBlockAt(chestLoc).getType() != Material.CHEST && chestLoc.getWorld().getBlockAt(chestLoc).getType() != Material.TRAPPED_CHEST){
				return;
			}
			Chest chest = (Chest) chestLoc.getWorld().getBlockAt(chestLoc).getState();
			ItemStack[] itemTab = chest.getBlockInventory().getContents();
			if(!listChestClass.containsKey(classeName)){
				listChestClass.put(classeName, itemTab);
			}
			p.getInventory().setHelmet(itemTab[26]);
			p.getInventory().setChestplate(itemTab[25]);
			p.getInventory().setLeggings(itemTab[24]);
			p.getInventory().setBoots(itemTab[23]);
			p.getInventory().setItemInOffHand(itemTab[22]);
			for(int i =0;i<itemTab.length-5;i++){
				p.getInventory().setItem(i,itemTab[i]);
			}
		}
	}
	
	public static void loadChestClass(Player p , String classeName){
		if(!listChestClass.containsKey(classeName)){
			return;
		}
		ItemStack[] itemTab = listChestClass.get(classeName);
		p.getInventory().setHelmet(itemTab[26]);
		p.getInventory().setChestplate(itemTab[25]);
		p.getInventory().setLeggings(itemTab[24]);
		p.getInventory().setBoots(itemTab[23]);
		p.getInventory().setItemInOffHand(itemTab[22]);
		for(int i =0;i<itemTab.length-5;i++){
			p.getInventory().setItem(i,itemTab[i]);
		}
	}
	
}
