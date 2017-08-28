package com.trafalcraft.meleGeneral.Controler;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBarAPI;
import org.inventivetalent.bossbar.BossBarAPI.Color;
import org.inventivetalent.bossbar.BossBarAPI.Property;
import org.inventivetalent.bossbar.BossBarAPI.Style;

import com.google.common.collect.Maps;
import com.trafalcraft.meleGeneral.ClearPotion;
import com.trafalcraft.meleGeneral.Data.Joueur;
import com.trafalcraft.meleGeneral.file.FileControler;

import net.md_5.bungee.api.chat.TextComponent;

public class PlayerControle {
	private final static Map<String, Joueur> inGamePlayers = Maps.newHashMap();
	
	public static boolean addPlayer(String name, String aname,boolean bungeeCoord){
		if(!inGamePlayers.containsKey(name)){
			if(ArenaControle.getArena(aname).getStatus().equalsIgnoreCase("lobby")){
				if(ArenaControle.getArena(aname).getNbrJoueur() < FileControler.getArena(aname).getInt("maxplayer")){
					if(name != null){
						Joueur player = new Joueur(Bukkit.getPlayer(name), "defaut", aname, FileControler.getArena(aname).getInt("nbrvie"));
						inGamePlayers.put(name, player);
						ArenaControle.getArena(aname).addPlayer(Bukkit.getPlayer(name));
						ArenaControle.getArena(aname).setNbrJoueur(ArenaControle.getArena(aname).getNbrJoueur() + 1);
						
						Location loc = new Location(Bukkit.getWorld(FileControler.getArena(aname).getString("world")),FileControler.getArena(aname).getDouble("lobby.x"),FileControler.getArena(aname).getDouble("lobby.y"),FileControler.getArena(aname).getDouble("lobby.z"),(float)FileControler.getArena(aname).getDouble("lobby.yaw"),(float)FileControler.getArena(aname).getDouble("lobby.pitch"));
						Bukkit.getPlayer(name).teleport(loc);
						Bukkit.getPlayer(name).setHealth(20);
						Bukkit.getPlayer(name).setFoodLevel(20);
						Bukkit.getPlayer(name).setSaturation(20);
						Bukkit.getPlayer(name).getInventory().clear();
						ClearPotion.clearEffect(Bukkit.getPlayer(name));
						Bukkit.getPlayer(name).setGameMode(GameMode.SURVIVAL);
						
						if(ArenaControle.getArena(aname).getPlayerList().size() == 1){
							
							BossBarAPI.removeAllBars(Bukkit.getPlayer(name));
							BossBarAPI.addBar(Bukkit.getPlayer(name), new TextComponent("§b" + "mélée générale"), Color.BLUE, Style.PROGRESS, 1.0f);
						
						}else{
							
							BossBarAPI.removeAllBars(Bukkit.getPlayer(name));
							BossBarAPI.addBar(Bukkit.getPlayer(name), new TextComponent("§b" + "mélée générale"), Color.BLUE, Style.PROGRESS, (float)1, 20*ArenaControle.getArena(aname).getTemps(), 1, Property.PLAY_MUSIC);
							
							Bukkit.getPlayer(name).setLevel(ArenaControle.getArena(aname).getTemps());;
						}
						
						for(Player p : ArenaControle.getArena(aname).getPlayerList()){
							p.sendMessage("§3§lPVP> §b" + name + " a rejoint la partie (" + ArenaControle.getArena(aname).getNbrJoueur() + "/" + FileControler.getArena(aname).getInt("maxplayer") + ")");
						}
					}
					return true;
				}else{
					if(!bungeeCoord){
						Bukkit.getPlayer(name).sendMessage("§3§lPVP> §b" + "la partie est déjà pleine");
					}
				}
			}else{
				if(!bungeeCoord){
					Bukkit.getPlayer(name).sendMessage("§3§lPVP> §b" + "la partie est déjà en cours");
				}
			}
		}else{
			if(!bungeeCoord){
				Bukkit.getPlayer(name).sendMessage("§3§lPVP> §b" + "vous êtes déjà dans une arène");
			}
		}
		return false;
	}
	
	public static void removePlayer(String name){
			inGamePlayers.remove(name);
	}
	
	public static Joueur getJoueur(String s){
		return inGamePlayers.get(s);
	}
	
	public static boolean contains(String s){
		if(inGamePlayers.containsKey(s)){
			return true;
		}else{
			return false;
		}
	}
}
