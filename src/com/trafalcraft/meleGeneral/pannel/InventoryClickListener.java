package com.trafalcraft.meleGeneral.pannel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.trafalcraft.meleGeneral.file.FileControler;

public class InventoryClickListener implements Listener {
	
	@EventHandler
	public void onPlayerClickInventoryEvent(InventoryClickEvent e){
		
		if(e.getCurrentItem() != null){
			if(e.getInventory().getName().contains(";")){
				
				if(e.getInventory().getName().split(";")[2].equalsIgnoreCase("pvp")){
				
					if(e.getInventory().getName().split(";")[0].equalsIgnoreCase("arenalist")){
						if(e.getCurrentItem().getType() != Material.AIR){
							
							e.getWhoClicked().closeInventory();
							CreateOptionInventory.loadInventoryOption((Player)e.getWhoClicked(), e.getCurrentItem().getItemMeta().getDisplayName());
							
						}
					}else if(e.getInventory().getName().split(";")[1].equalsIgnoreCase("optionlist")){
						
						if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("teleportation")){
							
							e.getWhoClicked().closeInventory();
							CreateTeleportationInventory.loadInventoryteleportation((Player) e.getWhoClicked(), e.getInventory().getName().split(";")[0]);
						
						}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("information")){
							
							e.getWhoClicked().closeInventory();
							CreateInfoInventory.loadInventoryInfo((Player) e.getWhoClicked(), e.getInventory().getName().split(";")[0]);
							
						}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("back")){
							
							e.getWhoClicked().closeInventory();
							CreateAreneList.loadInventoryArena((Player) e.getWhoClicked());
						}
					}else if(e.getInventory().getName().split(";")[1].equalsIgnoreCase("tpInventory")){
						if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("spawn")){
						
							e.getWhoClicked().closeInventory();
							CreateSpawnList.loadInventorySpawn((Player)e.getWhoClicked(), e.getInventory().getName().split(";")[0]);
							
						}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("spec")){
							
							Location loc = new Location(Bukkit.getWorld(FileControler.getArena(e.getInventory().getName().split(";")[0]).getString("world")), FileControler.getArena(e.getInventory().getName().split(";")[0]).getInt("spec.x"), FileControler.getArena(e.getInventory().getName().split(";")[0]).getInt("spec.y"), FileControler.getArena(e.getInventory().getName().split(";")[0]).getInt("spec.z"), (float)FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("spec.yaw"), (float)FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("spec.pitch"));
							e.getWhoClicked().teleport(loc);
								
						}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("lobby")){
							
							Location loc = new Location(Bukkit.getWorld(FileControler.getArena(e.getInventory().getName().split(";")[0]).getString("world")), FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("lobby.x"), FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("lobby.y"), FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("lobby.z"), (float)FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("lobby.yaw"), (float)FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("lobby.pitch"));
							e.getWhoClicked().teleport(loc);
							
						}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("lobby event")){
							
							Location loc = new Location(Bukkit.getWorld(FileControler.getArena(e.getInventory().getName().split(";")[0]).getString("world")), FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("eventlobby.x"), FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("eventlobby.y"), FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("eventlobby.z"), (float)FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("eventlobby.yaw"), (float)FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("eventlobby.pitch"));
							e.getWhoClicked().teleport(loc);
							
						}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("back")){
							
							e.getWhoClicked().closeInventory();
							CreateOptionInventory.loadInventoryOption((Player)e.getWhoClicked(), e.getInventory().getName().split(";")[0]);
							
						}
						
					}else if(e.getInventory().getName().split(";")[1].equalsIgnoreCase("infoInventory")){
						
						if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("back")){
							
							e.getWhoClicked().closeInventory();
							CreateOptionInventory.loadInventoryOption((Player)e.getWhoClicked(), e.getInventory().getName().split(";")[0]);
							
						}else{
							e.setCancelled(true);
						}
					}else if(e.getInventory().getName().split(";")[1].equalsIgnoreCase("spawnlist")){
						
						e.getWhoClicked().closeInventory();				
						String i = e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[1];
						
						Location loc = new Location(Bukkit.getWorld(FileControler.getArena(e.getInventory().getName().split(";")[0]).getString("world")), FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("spawn." + i + ".x"), FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("spawn." + i + ".y"), FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("spawn." + i + ".z"), (float)FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("spawn." + i + ".yaw"), (float)FileControler.getArena(e.getInventory().getName().split(";")[0]).getDouble("spawn." + i + ".pitch"));
						e.getWhoClicked().teleport(loc);
					}
				}
			}
		}
	}
	
}
