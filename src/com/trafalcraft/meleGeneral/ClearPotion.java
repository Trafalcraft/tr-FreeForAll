package com.trafalcraft.meleGeneral;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class ClearPotion {

	public static void clearEffect(Player p){
		for(PotionEffect effect : p.getActivePotionEffects()){
			p.removePotionEffect(effect.getType());
		}
	}
}
