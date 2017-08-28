package com.trafalcraft.meleGeneral;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBarAPI;

import com.trafalcraft.meleGeneral.Controler.ArenaControle;
import com.trafalcraft.meleGeneral.Controler.PlayerControle;

public class Leave {

	public static void leave(Player p){
		if(PlayerControle.contains(p.getName())){
			
			BossBarAPI.removeAllBars(p);
			
			ClearPotion.clearEffect(p);
			
			String arene = PlayerControle.getJoueur(p.getName()).getArene();
			boolean status = PlayerControle.getJoueur(p.getName()).getInGame();
			
			ArenaControle.getArena(arene).removePlayer(Bukkit.getPlayer(p.getName()));
			ArenaControle.getArena(arene).setNbrJoueur(ArenaControle.getArena(arene).getNbrJoueur() - 1);
			PlayerControle.removePlayer(p.getName());
			
			 if(ArenaControle.getArena(arene).getStatus().equalsIgnoreCase("in-game")){	
				
				if(status == true){
					
					ArenaControle.getArena(arene).setNbrPlayerInLife(ArenaControle.getArena(arene).getNbrPlayerInLife() - 1);
					
					if(ArenaControle.getArena(arene).getNbrPlayerInLife() == 1){
						
						ArenaControle.getArena(arene).stopGameTimer();
						ArenaControle.getArena(arene).WhoWin();
						ArenaControle.getArena(arene).setStatus("finish");
					}
				}
			
			}
		}
	}
}
