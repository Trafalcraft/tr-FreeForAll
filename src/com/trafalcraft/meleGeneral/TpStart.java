package com.trafalcraft.meleGeneral;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.trafalcraft.meleGeneral.Controler.PlayerControle;
import com.trafalcraft.meleGeneral.file.FileControler;

public class TpStart {
	
	

	public static void tpStart(ArrayList<Player> al, String arene){
	
		
		
		final ArrayList <Location> spawnList = new ArrayList<Location>();
			for(int i = 0;i < FileControler.getArena(arene).getInt("nbrspawn");i++){
				
				Location loc = new Location(Bukkit.getWorld(FileControler.getArena(arene).getString("world")),FileControler.getArena(arene).getDouble("spawn." + (i+1) + ".x"),FileControler.getArena(arene).getDouble("spawn." + (i+1) + ".y"),FileControler.getArena(arene).getDouble("spawn." + (i+1) + ".z"),(float)FileControler.getArena(arene).getDouble("spawn." + (i+1) + ".yaw"),(float)FileControler.getArena(arene).getDouble("spawn." + (i+1) + ".pitch"));
				spawnList.add(loc);
			}
				
			for(final Player p : al){
		
				final Location loc1 = new Location(Bukkit.getWorld(FileControler.getArena(arene).getString("world")),  FileControler.getArena(arene).getDouble("eau.x"),  FileControler.getArena(arene).getDouble("eau.y"),  FileControler.getArena(arene).getDouble("eau.z"),  (float)FileControler.getArena(arene).getDouble("eau.yaw"),  (float)FileControler.getArena(arene).getDouble("eau.pitch"));
				p.teleport(loc1);
				
				p.setHealth(0);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

   	                @Override
   	                public void run() {
   	                  
   	                	p.spigot().respawn();
   	                	
   	                	p.teleport(loc1);
	   	 				Random rand = new Random();
	   	 				int rd = rand.nextInt(spawnList.size());
	   	 				p.teleport(spawnList.get(rd));
	   	 				p.setGameMode(GameMode.SURVIVAL);
	   	 				spawnList.remove(rd);
	   	 				
	   	 				p.setFireTicks(1);
	   	 				
	   	 				p.setFoodLevel(20);	
	   	 				p.setHealth(20);
	   	 				p.setSaturation(0);
	   	 				p.getActivePotionEffects().clear();
	   	 				
	   	 				PlayerControle.getJoueur(p.getName()).setInGame(true);
	   	 				
	   	 				if(!PlayerControle.getJoueur(p.getName()).getClasse().equalsIgnoreCase("defaut")){
	   	 					
	   	 					ManageClasses.loadChestClass(p, PlayerControle.getJoueur(p.getName()).getClasse());
	   	 					
	   	 				}else{
	   	 					p.getInventory().clear();
	   	 					ItemStack item = new ItemStack(Material.STONE_SWORD);
	   	 					p.getInventory().setItem(0, item);
	   	 				}
   	                }
   	            },3);
				
				
				
			}
	}
}
