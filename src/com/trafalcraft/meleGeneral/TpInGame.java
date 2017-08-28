package com.trafalcraft.meleGeneral;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.trafalcraft.meleGeneral.Controler.PlayerControle;
import com.trafalcraft.meleGeneral.file.FileControler;

public class TpInGame {
	public static void tpGame(final Player p) {
		
		final String arene = PlayerControle.getJoueur(p.getName()).getArene();
		
		Random rand = new Random();
		int rd = 0; 
		while(rd == 0){
			rd = rand.nextInt(FileControler.getArena(arene).getInt("nbrspawn"));
		}
		final Location loc = new Location(Bukkit.getWorld(FileControler.getArena(arene).getString("world")), FileControler.getArena(arene).getDouble("spawn." + rd + ".x"),  FileControler.getArena(arene).getDouble("spawn." + rd + ".y"),  FileControler.getArena(arene).getDouble("spawn." + rd + ".z"),  (float)FileControler.getArena(arene).getDouble("spawn." + rd + ".yaw"),  (float)FileControler.getArena(arene).getDouble("spawn." + rd + ".pitch"));
		p.teleport(loc);
		
		p.setHealth(0);;
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

               @Override
               public void run() {
                 
               	p.spigot().respawn();
               	
               	p.teleport(loc);
        		
        		p.setFireTicks(1);
        		
        		if(!(PlayerControle.getJoueur(p.getName()).getClasse().equalsIgnoreCase("defaut")) || FileControler.getArena(arene).contains("classe.defaut")){
        			
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
